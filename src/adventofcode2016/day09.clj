(ns adventofcode2016.day09
  (:require
    [clojure.java.io :as io]))

(def input
  (line-seq (io/reader (io/resource "input09"))))

;;match [full before instructions number times rest]
(defn next-opperation [s]
  (re-find #"(.*?)(\((\d+)x(\d+)\))(.*)" s))

(defn int-val [s]
  (Integer. s))

(defn str-times [s n]
  (apply str (repeat n s)))

(defn data-left [current match]
  (subs current (+ (count (nth match 1)) (count (nth match 2)) (int-val (nth match 3))) (count current)))

(defn sub-match [match]
  (subs (nth match 5) 0 (int-val (nth match 3))))

(defn data-to-add [match]
  (str (nth match 1) (str-times (sub-match match) (int-val (nth match 4))) ))

;;part1
(count
  (loop [ data (first input)
          result ""]
  (if (empty data) result
    (let [ match (next-opperation data)]
      (if (not match) (str result data)
      (recur (data-left data match) (str result (data-to-add match))))))))

(defn length-counter [data]
  (loop [s data acc 0]
    (let [match (when s (next-opperation s))]
      (if match
        (let [before (count (nth match 1 ))
                  number (int-val (nth match 3))
                  rep (int-val (nth match 4))
                  right (nth match 5)
                  left (data-left s match)
                  submatch (sub-match match)]
              (recur left (+ before acc (* rep (length-counter submatch)))))
        (+ acc (count s))))))

;;part2
(length-counter (first input))

