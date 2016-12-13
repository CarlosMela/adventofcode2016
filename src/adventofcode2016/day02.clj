(ns adventofcode2016.day02
  (:require
    [clojure.java.io :as io]))

(def key-pad {
               "R" {1 2, 2 3, 4 5, 5 6, 7 8, 8 9}
               "L" {2 1, 3 2, 5 4, 6 5, 8 7, 9 8}
               "U" {4 1, 5 2, 6 3, 7 4, 8 5, 9 6}
               "D" {1 4, 2 5, 3 6, 4 7, 5 8, 6 9}
               })

(def input
  (line-seq (io/reader (io/resource "input02"))))

(defn next-digit [prev move]
  (get (key-pad move) prev prev))

(defn find-digit [n st]
  (reduce next-digit n (re-seq #"\w" st)))

(defn find-code [inst-seq]
  (loop [prev 5
         result ""
         s inst-seq]
    (if (empty? s) result
      (let [cal (find-digit prev (first s))]
      (recur cal (str result cal) (rest s))))))

;; part 1
(find-code input)


(def key-pad {
               "R" {2 3, 3 4, 5 6, 6 7, 7 8, 8 9, "A" "B", "B" "C"}
               "L" {3 2, 4 3, 6 5, 7 6, 8 7, 9 8, "B" "A", "C" "B"}
               "U" {3 1, 6 2, 7 3, 8 4, "A" 6, "B" 7, "C" 8, "D" "B"}
               "D" {1 3, 2 6, 3 7, 4 8, 6 "A", 7 "B", 8 "C", "B" "D"}
               })

;; part 2
(find-code input)
