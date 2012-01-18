(ns dsann.cljs-utils.dom.protocols.mutable-tree
  (:require 
    [dsann.utils.protocols.mutable-tree :as mt]
    [goog.dom :as gdom]
    
    [dsann.cljs-utils.js :as ujs]
    )
  )

(extend-protocol mt/MutableTree
  js/Node
  (append! [parent child]
           (gdom/appendChild parent child))

  (insert-at! [parent child idx]
              (gdom/insertChildAt parent child idx))

  (get-children [parent]
                (ujs/array->coll (gdom/getChildren parent)))
  
  (child-count [parent]
               (.-length (gdom/getChildren parent)))
  )

  
