(ns adventofcode2016.day06
  (:require
    [clojure.java.io :as io]))

(def input
  (line-seq (io/reader (io/resource "input06"))))

(defn process-line [chars-counter line]
  (second (reduce (fn [acc ch-ar]
            (let [ i (first acc)
                   maps (second acc)
                   col-acc (nth maps i)
                   n (get col-acc ch-ar 0)
                   new-map (assoc col-acc ch-ar (if n (inc n) 1))]
              [(inc i) (assoc maps i new-map)]
              ))
    [0 chars-counter] line)))

(def maps [{} {} {} {} {} {} {} {}])

(defn find-key [opp ch-map]
  (reduce #(if (opp (ch-map %2) (ch-map %1)) %2 %1) (keys ch-map)))

;;part1
(->> input
     (reduce process-line maps)
     (map (partial find-key >))
     (reduce str))

;;part2
(->> input
     (reduce process-line maps)
     (map (partial find-key <))
     (reduce str))

