(ns dsann.cljs-utils.dom.protocols.identifiable
  (:require 
    [dsann.utils.protocols.identifiable :as id]
    )
  )

(extend-protocol id/Identifiable
  js/Node
  (id [e] (or (. e (getAttribute "puid")) (.-id e))))
