(ns functional-clojure.wikicounter
  (:require [clojure.java.io :as io])
  (:require [clojure.string :as str]))

(defn get-pages [] [(slurp "./resources/wiki.txt") (slurp "./resources/wiki.txt")])
(defn get-words [page] (str/split page #" "))
(defn count-words-0 [words]
  ;; fn; initial value; collection
  (reduce (fn [acc x]
            (if (get acc x)
              (assoc acc x (+ (get acc x) 1))
              (assoc acc x 1)))
          {}, words))

(defn count-words-1 [words]
  (reduce (fn [acc x]
            (assoc acc x (if (contains? acc x)
                           (inc (get acc x))
                           1)))
          {}
          words))

(defn count-words-2 [words]
  (reduce (fn [count word]
            (assoc count word (inc (get count word 0)))) {} words))

(defn count-words []
  (frequencies (get-words (get-pages))))

(defn count-words-parallel [pages]
  (reduce (partial merge-with +)
          (pmap #(frequencies (get-words %)) pages)))