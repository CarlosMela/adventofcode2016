(ns adventofcode2016.day20
  (:require
    [clojure.java.io :as io]))

(def input
  (line-seq (io/reader (io/resource "input20"))))

(def max-ip 4294967295)

(defn get-max [s]
  (BigInteger. (second (re-seq #"\d+" s))))

(defn get-min [s]
  (BigInteger. (first (re-seq #"\d+" s))))

(defn remove-interval [interval target]
  (cond
        (or (> (first target) (second interval)) (< (second target) (first interval)))
                 [target]
        (and (<= (second target) (second interval)) (<= (first interval) (first target)))
                 []
        (and (< (first target) (first interval)) (> (second target) (second interval)))
                 [[(first target) (dec (first interval))] [(inc (second interval)) (second target)]]
        (and (> (first target) (first interval)) (>= (second target) (second interval)))
                 [[(inc (second interval)) (second target)]]
        (and (< (second target) (second interval)) (<= (first target) (first interval)))
                 [[(first target) (dec (first interval)) ]]
        (and (= (second target) (second interval)) (< (first target) (first interval)))
                 [[(first target)   (dec (first interval)) ]]
        (and (= (first target) (first interval)) (< (second interval) (second target)))
                 [[(inc (second interval))   (second target) ]]
        :else :error-case-no-covered))

(->>
  input
  (map #(vector (get-min %) (get-max %)))
  (reduce #(concat (mapcat (partial remove-interval %2) %1)) [[0 max-ip]])
  (map first)
  (apply min))

(->>
  input
  (map #(vector (get-min %) (get-max %)))
  (reduce #(concat (mapcat (partial remove-interval %2) %1)) [[0 max-ip]])
  (map #(inc (- (.longValue (second %)) (.longValue (first %)))))
  (apply +))

