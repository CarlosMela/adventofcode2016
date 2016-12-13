(ns adventofcode2016.day02-test
  (:require [clojure.test :refer :all]
            [adventofcode2016.day02 :refer :all]))

(deftest next-digit-one-numb
  (testing "Next move works for single values"
    (is (= (next-digit 5 "R") 6))))

(deftest find-number
  (testing "Next move works for single values"
    (is (= (find-digit 5 "ULL") 1))))


(deftest test-find-code
  (testing "All the numbers"
    (is (= (find-code ["ULL"
"RRDDD"
"LURDL"
"UUUUD"]) "1985"))))

(run-tests)
