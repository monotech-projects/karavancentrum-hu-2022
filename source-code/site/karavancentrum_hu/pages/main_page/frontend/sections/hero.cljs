
(ns site.karavancentrum-hu.pages.main-page.frontend.sections.hero
    (:require [re-frame.api                 :as r]
              [site.components.frontend.api :as components]
              [x.environment.api            :as x.environment]))

;; ----------------------------------------------------------------------------
;; ----------------------------------------------------------------------------

(defn hero
  []
  (let [website-name   @(r/subscribe [:x.db/get-item [:website-content :handler/transfered-content :website-name]])
        website-slogan @(r/subscribe [:x.db/get-item [:website-content :handler/transfered-content :website-slogan]])]
       [:div#kc-hero [:div#kc-hero--website-name-and-slogan
                       (letfn [(scroll-f [intersecting?]
                                         (x.environment/set-element-attribute! "kc-navbar--website-name-and-slogan" "data-hidden"      intersecting?)
                                         (x.environment/set-element-attribute! "kc-hero--website-name-and-slogan"   "data-hidden" (not intersecting?)))]
                              [components/scroll-sensor ::scroll-sensor {:callback-f scroll-f
                                                                         :style {:position "absolute" :top "0" :left "0"}}])
                       [:div#kc-hero--website-name   website-name]
                       [:div#kc-hero--website-slogan website-slogan]]
                     [components/scroll-icon {:style {:position :absolute :bottom 0 :left 0}}]]))

(defn view
  []
  [:section {:id :hero}
            [hero]])
