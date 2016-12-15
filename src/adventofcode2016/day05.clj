(ns adventofcode2016.day05
  (:require
    [clojure.java.io :as io]
    [digest :as digest]))


(def input
  (nth (line-seq (io/reader (io/resource "input05"))) 0))


(defn get-pw-1 [s]
  (when (= "00000" (subs s 0 5))
    (subs s 5 6)))

;;part1
(loop [solution1 ""
       index 0]
  (if (= (count solution1) 8) solution1
    (let [pw (get-pw-1 (digest/md5 (str input index)))]
    (if pw (recur (str solution1 pw) (inc index))
      (recur solution1 (inc index))))))


(defn replace-index [string index value]
  (str (subs string 0 index)
       value
       (subs string (inc index) (count string))))

(defn replace-index-if-free [string index value]
  (if (not (= (subs string index (inc index)) "-"))
    string
    (replace-index string index value)))

(defn get-pw-2 [s]
  (when (= "00000" (subs s 0 5))
    (subs s 6 7)))

(defn get-pos-2 [s]
  (when (= "00000" (subs s 0 5))
    (subs s 5 6)))

(defn parse-int [s]
  (when s
  (let [i (re-find  #"\d+" s )]
   (when i (Integer. i)))))

;;part2
(loop [solution2 "--------"
       index 0]
  (if (not (re-find #"-" solution2)) solution2
    (let [ md5-code (digest/md5 (str input index))
           pw (get-pw-2 md5-code)
           ind (parse-int (get-pos-2 md5-code))]
    (if (and pw ind (< ind 8)) (recur (replace-index-if-free solution2 ind pw) (inc index))
      (recur solution2 (inc index)))))
  )
