(ns functional-clojure.core
  (:gen-class)
  (:require [clojure.core.reducers :as r])
  (:require [functional-clojure.wikicounter :as w])
  (:require [clojure.core.protocols :refer [CollReduce coll-reduce]]
            [clojure.core.reducers :refer [CollFold coll-fold]]))

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

(defn my-reduce
  ([f coll] (coll-reduce coll f))
  ([f init coll] (coll-reduce coll f init)))

;; take a reducable and transform, return reducer
(defn make-reducer [reducible transformf]
  (reify
    CollReduce
    (coll-reduce [_ f1] (coll-reduce reducible (transformf f1) (f1)))
    (coll-reduce [_ f1 init] (coll-reduce reducible (transformf f1) init)))
  (reify
    CollFold
    (coll-fold [_ n combinef reducef] (coll-fold reducible  n combinef (transformf reducef)))))

(defn my-map [mapf reducible]
  (make-reducer reducible
                (fn [reducef]
                  (fn [acc v]
                    (reducef acc (mapf v))))))

;; fold
(defn my-fold
  ([reducef coll]
   (my-fold reducef reducef coll))
  ([combinef reducef coll]
   (my-fold 512 combinef reducef coll))
  ([n combinef reducef coll]
   (coll-fold coll n combinef reducef)))

(defn parallel-frequencies [coll]
  (r/fold
    (partial merge-with +)
    (fn [count x] (assoc count x (inc (get count x 0))))
    coll))
