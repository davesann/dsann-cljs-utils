(ns dsann.cljs-utils.dom.find
  (:require  
    [goog.dom :as gdom]
    [goog.dom.dataset :as gdata]
    [goog.dom.classes :as gcls]
    
    [dsann.utils.x.core  :as u]
    [dsann.cljs-utils.js :as ujs]
    
    [dsann.utils.protocols.identifiable :as p-id]
    
    ))


(defn by-id [id node]
  (gdom/getElement id node))

(defn by-class [class node]
  (ujs/array->coll 
    (gdom/getElementsByClass class node)))

(defn by-class-inclusive [class node]
  (let [nodes (by-class class node)] 
    (if (gcls/has node class)
      (cons node nodes)
      nodes)))

(defn first-by-class [class node]
  (gdom/getElementByClass class node)) 

(defn first-by-class-inclusive [class node]
   (if (gcls/has node class)
     node
     (gdom/getElementByClass class node))) 

(defn- bind-id [dom-node]
  (gdata/get dom-node "bindId"))

(defn bindings [dom-node]
  (gdom/getElementsByClass "bind" dom-node))

(defn bindings-map [dom-node]
  (ujs/into-by bind-id (bindings dom-node)))

(defn by-bind-id [a-bind-id dom-node]
  (let [b-array (bindings dom-node)]
    (ujs/find-first #(= a-bind-id (bind-id %)) b-array)))

(defn find-child-index 
  ([child] (find-child-index child 0))
  ([child i]
    (if-let [pc (.-previousSibling child)]
      (recur pc (inc i))
      i)))