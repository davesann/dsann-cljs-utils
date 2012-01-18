(ns dsann.cljs-utils.dom.data
  (:require 
    [goog.dom.dataset :as gdata]
    [cljs.reader :as reader]
    ))

(defn has-data [elem field]
  (gdata/has elem field))

(defn cljs-data [elem field] 
  (if-let [s (gdata/get elem field)]
    (reader/read-string s)))
