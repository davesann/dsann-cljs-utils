(ns dsann.cljs-utils.dom.fx
  (:require 
    ;[dsann.utils.x.core :as u]
    
    [goog.style :as gstyle]
    
    [goog.fx :as gfx]
    [goog.fx.dom :as gfxdom]
    )
  )

(defn fadeout-and-hide 
  ([elem] (fadeout-and-hide elem 100 nil))
  ([elem time-ms] (fadeout-and-hide elem time-ms nil))
  ([elem time-ms accelerator-fn]
    (if (gstyle/isElementShown elem)
      (let [anim (gfxdom/FadeOutAndHide. elem time-ms)]; accelerator-fn)]
        (. anim (play))))))

(defn show-and-fadein 
  ([elem] (show-and-fadein elem 100 nil))
  ([elem time-ms] (show-and-fadein elem time-ms nil))
  ([elem time-ms accelerator-fn]
    (if-not (gstyle/isElementShown elem)
      (let [anim (gfxdom/FadeInAndShow. elem time-ms)]; accelerator-fn)]
        (. anim (play))))))
