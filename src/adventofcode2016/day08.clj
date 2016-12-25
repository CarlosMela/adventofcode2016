(ns adventofcode2016.day08
  (:require
    [clojure.java.io :as io]))

(def input
  (line-seq (io/reader (io/resource "input08"))))

(def screen-width 50)
(def screen-height 6)

(defn create-screen [width hight light]
  (reduce
    (fn [screen _] (conj screen (apply vector (repeat width light))))
    []
    (range hight)))

(def l (create-screen screen-width screen-height 1))
(def screen-off (create-screen screen-width screen-height 0))

(defn rotate [n v]
  (let [length (count v)]
  (loop [result []
         i 0]
    (if (= length i) result
        (recur
          (conj result (nth v (mod (+ length (- n) i) length)))
          (inc i))))))

(defn rect [A B screen]
  (let [height (count screen)
        width (count (first screen))]
  (into [] (concat
    (map #(into [] (concat (take A %1) (take-last (- width A) %2)))
         l
         (take B screen))
    (take-last (- height B) screen)))))

(defn rotate-row [x n screen]
  (concat
    (take x screen)
    (vector (rotate n (nth screen x)))
    (take-last (- (count screen) x 1) screen)))

(def transpose (partial apply mapv vector))

(defn rotate-column [y n screen]
  (->>
    screen
    transpose
    (rotate-row y n)
    transpose))

(defn exe-match [fn arr v]
  (fn (Integer. (second arr)) (Integer. (last arr)) v))

(defn handle [s v]
  (let [
          row-m (re-find #"rotate row y=(\d+) by (\d+)" s)
          column-m (re-find #"rotate column x=(\d+) by (\d+)" s)
          rect-m (re-find #"rect (\d+)x(\d+)" s)
         ]
    (cond
      row-m (exe-match rotate-row row-m v)
      column-m (exe-match rotate-column column-m v)
      rect-m (exe-match rect rect-m v)
      :else (println "Handle fn wrong match!!"))))

;; part1
(->>
  input
  (reduce #(handle %2 %1) screen-off)
  (map #(reduce + %))
  (reduce +))

;;part2, read console:
(->>
  input
  (reduce #(handle %2 %1) screen-off)
  (.toString)
  (#(string/replace % #"1" "@"))
  (#(string/replace % #"0" "."))
  (#(string/replace % #"] \[" "\n"))
  (#(string/replace % #"\[\[|]]" ""))
  (println))


