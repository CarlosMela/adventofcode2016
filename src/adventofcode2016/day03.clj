(ns adventofcode2016.day03
  (:require
    [clojure.java.io :as io]))

(def input
  (line-seq (io/reader (io/resource "input03"))))

(defn is-triangle? [t]
  (and (< (nth t 0) (+ (nth t 1) (nth t 2)))
  (< (nth t 1) (+ (nth t 0) (nth t 2)))
  (< (nth t 2) (+ (nth t 1) (nth t 0)))))

(defn extract-dim [t-str]
  (map #(Integer. %) (re-seq #"\w+" (str t-str))))

;;part 1
(->> input
     (map extract-dim)
     (map is-triangle?)
     (map {false 0 true 1})
     (reduce +))

(defn add-to-columns [columns t-str]
  (let [t (extract-dim t-str)]
    {:col1 (conj (:col1 columns) (nth t 0))
     :col2 (conj (:col2 columns) (nth t 1))
     :col3 (conj (:col3 columns) (nth t 2))}))

(defn sum-col [c]
  (->> c
  (map doall) ;;partition returns lazy seq
     (map is-triangle?)
     (map {false 0 true 1})
     (reduce +)))

;;part  2
(->> input
     (reduce add-to-columns {:col1 [], :col2 [] :col3 []})
     (vals)
     (map #(partition 3 %))
     (map sum-col)
     (reduce +))
