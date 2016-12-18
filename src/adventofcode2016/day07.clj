(ns adventofcode2016.day07
  (:require
    [clojure.java.io :as io]))

(def input
  (line-seq (io/reader (io/resource "input07"))))

(defn ABBA-match? [match]
  (if match
    (not= (nth match 1) (nth match 2))
    false))

(defn ip_has_ABBA? [ip]
  (ABBA-match?
    (re-find #"(?<first>.)(?<second>.)(\k<second>)(\k<first>)" ip)))

(defn hypernet_has_ABBA? [ip]
    (->>
      (re-seq #"(?<=\[)(.*?)(?=])" ip)
      (map (comp ip_has_ABBA? first))
      (some identity)
      ))

(defn supports_TLS? [ip] (and (ip_has_ABBA? ip)
                              (not (hypernet_has_ABBA? ip))))

;;part1
(->>
input
(map #(if (supports_TLS? %) 1 0))
(reduce +))

(defn ABAs [ip]
    (->>
      ip
     (#(clojure.string/replace % #"\[.+?\]" ""))
      (re-seq #"(?=((\w)(?!\2)\w\2))")
    (map second )
      (map #(if (= (count %) 4) ((fn [st] (list (subs st 0 3) (subs % 1 4))) %) %))
      (flatten)
      ))

(defn BABs [ip]
  (->>
    (re-seq #"(?<=\[)(.*?)(?=])" ip)
    (map (comp ABAs first))
    (flatten)
    ))

(defn invert-str [aba] (str (second aba) (first aba) (second aba)))

(defn supports_SSL? [ip]
  (if (empty? (clojure.set/intersection (set (ABAs ip)) (set (map invert-str (BABs ip)))))
    false
    true))

;;part 2
(->>
input
(map #(if (supports_SSL? %) 1 0))
(reduce +))
