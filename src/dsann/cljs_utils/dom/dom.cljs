(ns dsann.cljs-utils.dom.dom
  (:require  
    [goog.dom :as gdom]
    
    [dsann.utils.x.core :as u]
    [dsann.utils.seq :as useq]
    
    [dsann.cljs-utils.js :as ujs]
    ))


(defn query-selector-all-js [selector element]
  (let [element (or element js/document)]
    (.querySelectorAll element selector)))

(defn query-selector-all [selector element]
  (if-let [r (query-selector-all-js selector element)]
    (ujs/array->coll r)))

(defn get-element-by-class [class element]
  (gdom/getElementByClass class element))

(defn get-elements-by-class 
  ([class element]
    (ujs/array->coll
      (gdom/getElementsByClass class element))))

(defn body []
  (.-body js/document))


(defn doto-node-and-children [node f]
  (when node
    (f node)
    (ujs/doseq-array 
      #(doto-node-and-children % f)
      (gdom/getChildren node))))

;;

(comment
(def notifiers 
  (atom {:before-add    {}
         :after-add     {}
         :before-remove {}
         :after-remove  {}
         :disposing     {}
         }))

(defn notify [notify-type nodes context]
  (if-let [notifiers (notify-type @notifiers)]
    (doseq [[id notifier] notifiers]
        (notifier notify-type id nodes context))))

(defn add-watch! [notify-type id f]
  (ustate/set-in! notifiers [notify-type id] f))

(defn remove-watch! [notify-type id]
  (ustate/dissoc-in! notifiers [notify-type id]))
)

(defn append! 
  ([parent nodes] (append! parent nodes nil))
  ([parent nodes context]
    (let [nodes (useq/ensure-sequential nodes)]
      (notify :before-add nodes context)
      (doseq [n nodes] (gdom/appendChild parent n))
      (notify :after-add nodes context)
      )))

(defn remove!
  ([nodes] (remove! nodes nil))
  ([nodes context]
    (let [nodes (useq/ensure-sequential nodes)]
      (notify :before-remove nodes context)
      (doseq [n nodes] (gdom/removeNode n))
      (notify :after-remove nodes context)
      )))

(defn disposing! [nodes]
  (notify :disposing nodes))
  
