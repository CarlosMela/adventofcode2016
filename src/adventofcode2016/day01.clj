(ns adventofcode2016.day01
  (:require
   [clojure.java.io :as io]))

(def input (slurp (io/resource "input01")))


(def origin {:x 0, :y 0, :dir [0 1] })
(def directions '([0 1] [1 0] [0 -1] [-1 0]))
(defn getDir [s] (first (re-seq #"L|R" s)))
(defn getSteps [s] (Integer. (first (re-seq #"\d+" s))))
(defn nextDirection [current turn] (nth directions (mod (+ ({"R" 1 "L" -1 "0" 0} turn) (.indexOf directions current)) 4) ))

(def instructions (map #(hash-map :cmd (getDir %), :n (getSteps %)) (re-seq #"[R|L]\d+[, |\n]" input)))


(defn move [prev x]
   (let [ d (prev :dir)
          nd (nextDirection d (x :cmd))
         steps (:n x)]
   (hash-map :x (+ (prev :x) (* (first nd) steps )) :y (+ (prev :y) (* (second nd) steps )) :dir nd)))


(defn distancia [{:keys [x y]}]
  (+ (Math/abs x) (Math/abs y)))

;; solution part 1
(distancia  (reduce move origin instructions))

(defn break [big]
  (loop [path big todos []]
  (if (= (:n path) 1)
    (conj (apply list todos) path)
    (recur {:n (- (:n path) 1) :cmd (:cmd path)} (conj todos {:n 1 :cmd "0"})))))

(defn toPoint [pos]
  (vector (:x pos) (:y pos)))

(defn move-one-step [possition movement]
  (let [nd (nextDirection (possition :dir) (movement :cmd))]
    (hash-map :x (+ (possition :x) (* (first nd) (movement :n) )) :y (+ (possition :y) (* (second nd) (movement :n) )) :dir nd)))


(defn find-first-join [o step-by-step]
  (loop [histo {[0 0] true}
         prev o
         n 0]
    (let [ movement (nth step-by-step n)
           nextPos (move-one-step prev movement)]
        (if (or (contains? histo (toPoint nextPos)) (= n (count step-by-step))) nextPos
          (recur (assoc histo (toPoint nextPos) true)
               nextPos
               (+ n 1))))))


;; solution part2
(distancia (find-first-join origin
(mapcat break instructions)))

