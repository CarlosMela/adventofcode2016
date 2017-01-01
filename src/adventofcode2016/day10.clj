(ns adventofcode2016.day10
  (:require
    [clojure.java.io :as io]))

(def input
  (line-seq (io/reader (io/resource "input10"))))

(def start-bots
  (reduce (fn [start-bots line] (if-let [match (re-find #".*value (\d+).*?(\d+)" line)]
                                  (let [b (Integer/parseInt (last match)), v (Integer/parseInt (second match))]
                                  (assoc start-bots b (if-let [found (start-bots b)] (conj found v) [v])))
                                  start-bots)) {} input))

(defn instructions [all-bots-ini]
   (->>
     all-bots-ini
     (filter #(not= (key %) :outputs))
     (filter #(= (count (val %)) 2))
    (reduce (fn [all-bots sel-bot-map]
             (let [ sel-bot (key sel-bot-map)
                    match (->> input
                          (filter #(re-find (re-pattern (str "bot " sel-bot " gives")) %))
                          (#(do #_(println (count %) ) %))
                          (first)
                          (re-find #"low to (bot|output) (\d+) and high to (bot|output) (\d+)"))
                   min-opp (nth match 1)
                   min-num (Integer/parseInt (nth match 2))
                   max-opp (nth match 3)
                   max-num (Integer/parseInt (nth match 4))
                   sel-vals (all-bots sel-bot)
                   min-val (apply min sel-vals)
                   max-val (apply max sel-vals)]
               (->
                 all-bots
                 ((fn [cl] (assoc cl sel-bot (vec (filter #(not= % min-val) (cl sel-bot))))))
                 ((fn [cl] (assoc cl sel-bot (vec (filter #(not= % max-val) (cl sel-bot))))))
                 (#(if (= min-opp "output") (assoc % :outputs (assoc (% :outputs) min-num (vec (conj ((% :outputs) min-num) min-val))))
                   (assoc % min-num (vec (conj (% min-num) min-val)))))
                 (#(if (= max-opp "output") (assoc % :outputs (assoc (% :outputs) max-num (vec (conj ((% :outputs) max-num) max-val))))
                   (assoc % max-num (vec (conj (% max-num) max-val)))))
               ))) all-bots-ini)))

(defn is-sol-bot [bot]
  (and (not-empty (val bot)) (= (apply min (val bot)) 17) (= (apply max (val bot)) 61)))

(defn is-sol [bots]
  (> (count (filter is-sol-bot bots)) 0))

;;part1
(->>
(loop [bots start-bots i 0]
  (if (or (> i 5000) (is-sol bots)) bots
    (let [next-bots (instructions bots)]
      (recur next-bots (inc i)))))
  (filter is-sol-bot)
  (ffirst)
  )

 ;;part2
 (->>
 (loop [bots (assoc start-bots :outputs {}) i 0]
   (if (> i 5000) bots
     (let [next-bots (instructions bots)]
       (recur next-bots (inc i)))))
 (:outputs)
 (#(select-keys % [0 1 2]))
 (map (comp first val))
 (reduce *))

