(ns dsann.cljs-utils.js
  (:require 
    [dsann.utils.x.core :as u]
    [goog.async.Delay :as gdelay]
  ))


(defn setTimeout [func ttime]
  (.start (goog.async.Delay. func ttime))) 


(defn doseq-with-yield [a-seq f t1 t2]
  (let [start (js/Date.)]
    ;(u/log-str "yield start" [start (count a-seq)])
    (loop [[i & is] a-seq]
      (when i
        (f i)
        (let [new-t (js/Date.)]
          (if (> (- new-t start) t1)
            (js/setTimeout #(doseq-with-yield is f t1 t2) t2)
            (recur is)))))))

(defn doseq-with-delay [a-seq f t-delay]
  (when (seq a-seq)
    (let [[i & is] a-seq]
      (f i)
      (js/setTimeout #(doseq-with-delay is f t-delay) t-delay)))) 


(defn repeat-with-timeout 
  [f t]
  (let [stop (f)]
    (if-not (= :stop stop)
      ;(js/setTimeout #(repeat-with-timeout f t) t)
      (setTimeout #(repeat-with-timeout f t) t)
      ))) 


(defn map->js [m]
  (let [out (js-obj)]
    (doseq [[k v] m]
      (aset out (name k) v))
    out))


(defn doseq-array [f js-array]
  (when js-array
    (doseq [i (range (.-length js-array))]
      (let [v (aget js-array i)]
        (f v)))))

(defn map-array [f js-array]
  (when js-array
    (for [i (range (.-length js-array))]
      (let [v (aget js-array i)]
        (f v)))))

(defn array->coll [js-array]
  (doall 
    (map-array identity js-array)))

(defn into-by [m f js-array]
  (into {} (map-array (fn [e] [(f e) e]) js-array)))

(defn find-first-index 
  ([pred? js-array] (find-first-index pred? js-array 0))
  ([pred? js-array idx]
    (if (< idx (.-length js-array))
      (let [i (aget js-array idx)]
        (if (pred? i)
          [idx i]
          (recur pred? js-array (inc idx)))))))

(defn find-first [pred? js-array] 
  (if-let [r (find-first-index pred? js-array)]
    (second r)))
