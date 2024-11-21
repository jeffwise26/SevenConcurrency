(ns functional-clojure.core
  (:gen-class))

(defn accum [inList]
  (if (empty? inList) 0
    (+ (first inList)
       (accum (rest inList)))))

(defn accumReduce [inList]
;;  function, initial value, collection
  (reduce (fn [acc x] (+ acc x)) 0 inList)
)

(defn subReduce [inList]
  (reduce (fn [acc x] (- acc x)) 0 inList)
  )

(defn -main
  [& args]
  (println (accum [1,2]))
  (println (accumReduce [1,2]))
  (println (subReduce [1,2])))


