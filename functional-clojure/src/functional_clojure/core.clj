(ns functional-clojure.core
  (:gen-class)
  (:require [clojure.core.reducers :as r])
  (:require [functional-clojure.wikicounter :as w]))


;; 1 doesn't quite do tail recursion
;; 2 clojure doens't have tail recursion anyways
(defn accum [inList]
  (if (empty? inList)
    0
    (+ (first inList) (accum (rest inList)))))

;; rewrite using loop and recur
(defn accum-loop [inList]
  (loop [list inList acc 0]
    (if (empty? list)
    acc
    (recur (rest list) (+ (first list) acc)))))

(defn accumReduce [inList]
;;  function, initial value, collection
  (reduce + 0 inList))

(defn subReduce [inList]
  (reduce (fn [acc x] (- acc x)) 0 inList))

(defn parallel-sum [numbers]
  (r/fold + numbers))

(defn -main
  [& args]
  (println (accum [1,2]))
  (println (accumReduce [1,2]))
  (println (subReduce [1,2]))
  (println (parallel-sum [1,2]))
  (println (w/count-words)))


