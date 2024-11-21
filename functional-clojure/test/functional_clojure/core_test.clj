(ns functional-clojure.core-test
  (:require [clojure.test :refer :all]
            [functional-clojure.core :refer :all]))

(deftest a-test
  (testing "basic test"
    (is (= 1 1))))
