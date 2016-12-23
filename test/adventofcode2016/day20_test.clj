(ns adventofcode2016.day20-test
  (:require [clojure.test :refer :all]
            [adventofcode2016.day20 :refer :all]))

(deftest split-contained-interval
    (is (=
          (remove-interval [4 7] [2 9])
          [[2 3] [8 9]])))


(deftest keeps-not-intersected-interval
    (is (=
          (remove-interval [0 1] [2 9])
          [[2 9]])))

(deftest returns-only-null-if-compleate-interval-removed
    (is (=
          (remove-interval [2 10] [2 9])
          [])))

(deftest removes-same-interval
    (is (=
          (remove-interval [2 9] [2 9])
          [])))

(deftest remove-right-border
    (is (=
          (remove-interval [9 11] [2 9])
          [[2 8]])))

(deftest remove-left-border
    (is (=
          (remove-interval [1 2] [2 9])
          [[3 9]])))

(deftest remove-inner-righ-border
    (is (=
          (remove-interval [5 9] [2 9])
          [[2 4]])))

(deftest remove-inner-left-border
    (is (=
          (remove-interval [2 5] [2 9])
          [[6 9]])))

(run-tests)
