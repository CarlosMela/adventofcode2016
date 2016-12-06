(ns adventofcode2016.day04
  (:require
   [clojure.java.io :as io]))

(def input
  (line-seq (io/reader (io/resource "input04"))))

(defn get-id [x]
  (Integer. (first(re-seq #"\d+" x))))

(defn is-room [x]
  (let [my-map (reduce #(if (contains? %1 %2) (assoc %1 %2 (+ 1 (get %1 %2))) (assoc %1 %2 1)) {} (re-seq #"[a-z](?=.*\[)" x))]
  (= (re-find #"(?<=\[).+?(?=\])" x)
     (apply str (take 5 (keys (into (sorted-map-by (fn [key1 key2]
                         (compare [(get my-map key2) key1]
                                  [(get my-map key1) key2])))
        my-map)))))))

;; Solution part 1
(reduce
  +
  (map get-id
  (filter is-room input)))


(defn char-range [start end]
  (map char (range (int start) (inc (int end)))))

(defn move [x n]
  (let [letters (char-range \a \z)
        current (.indexOf letters x)]
    (nth letters (mod (+ current n) 26))))

(defn decrypt [text]
  (let [id (get-id text)
        code (re-seq #"[a-z]|\-(?=.*\[)" text)]
    (map #(if (= "-" (str %)) " " (move (.charAt % 0) id)) code)
    ))

;; Solution part 2
(filter #(re-matches #".*pole.*" (first %))
  (map #(vector (apply str (first %)) (second %)) (map #(vector (decrypt %) (get-id %)) (filter is-room input))))
