(ns functional-clojure.wikicounter
  (:require [clojure.java.io :as io])
  (:require [clojure.string :as str]))

(defn getPages [] (slurp "./resources/wiki.txt"))
(defn getWords [page] (str/split page #" "))
(defn countWords [words]
  ;; fn; initial value; collection
  (reduce (fn [acc x]
            (if (get acc x)
              (assoc acc x (+ (get acc x) 1))
              (assoc acc x 1)))
          {}, words))

(defn countWords1 [words]
  (reduce (fn [acc x]
            (assoc acc x (if (contains? acc x)
                           (inc (get acc x))
                           1)))
          {}
          words))

(defn countWords2 [words]
  (reduce (fn [count word]
            (assoc count word (inc (get count word 0)))) {} words))