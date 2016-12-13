(ns adventofcode2016.day03-test
  (:require [clojure.test :refer :all]
            [adventofcode2016.dayxx :refer :all]))

(deftest no-triangle
  (testing "Impasible triangle"
    (is (= (is-triangle? '(5 10 25)) false))))

(run-tests)
