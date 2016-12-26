(load-file "src/adventofcode2016/day09.clj")

(ns adventofcode2016.day09-test
  (:require [clojure.test :refer :all]
            [adventofcode2016.day09 :refer :all]))

(deftest ll-test1
  (testing "Empy string returns 0"
    (is (= (ll "") 0))))

(deftest ll-test2
  (testing "Nil string returns 0"
    (is (= (ll nil) 0))))

(deftest ll-test3
  (testing "One extension" ;; fdfdfd
    (is (= (ll "(2x3)fd") 6))))

(deftest ll-test4
  (testing "Data before" ;; abfdfdfd
    (is (= (ll "ab(2x3)fd") 8))))

(deftest ll-test5
  (testing "Data after" ;; fdfdfdhi
    (is (= (ll "(2x3)fdhi") 8))))

(deftest ll-test6
  (testing "Nested extensions" ;; fdabcabcfdabcabc
    (is (= (ll "(10x2)fd(3x2)abc") 16))))

(deftest ll-test7
  (testing "Complex example"
    (is (= (ll "(25x3)(3x3)ABC(2x3)XY(5x2)PQRSTX(18x9)(3x2)TWO(5x7)SEVEN") 445))))

(deftest ll-test8
  (testing "simple example"
    (is (= (ll "X(8x2)(3x3)ABCY") 20))))

(deftest ll-test9
  (testing "long decompresion"
    (is (= (ll "(27x12)(20x12)(13x14)(7x10)(1x12)A") 241920))))

(run-tests)

