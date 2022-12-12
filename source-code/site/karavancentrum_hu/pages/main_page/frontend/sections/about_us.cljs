
(ns site.karavancentrum-hu.pages.main-page.frontend.sections.about-us
    (:require [css.api      :as css]
              [re-frame.api :as r]
              [reagent.api  :refer [ratom]]))

;; ----------------------------------------------------------------------------
;; ----------------------------------------------------------------------------

(defn about-us-page
  []
  (let [page-visible? (ratom false)]
       (fn []
           (if-let [about-us-page @(r/subscribe [:contents.handler/get-parsed-content [:website-content :handler/transfered-content :about-us-page]])]
                   [:<> [:div {:id :kc-about-us--section :style {:display (if-not @page-visible? "none" "block")}}
                              about-us-page]
                        [:div {:class :kc-content-button :on-click #(swap! page-visible? not)}
                              (if @page-visible? "Kevesebb tartalom" "Tovább olvasom")]]))))

(defn about-us-section
  []
  (if-let [about-us-section @(r/subscribe [:contents.handler/get-parsed-content [:website-content :handler/transfered-content :about-us-section]])]
          [:div {:id :kc-about-us--section}
                about-us-section]))

(defn about-us
  []
  [:div {:id :kc-about-us}
        [:div {:class :kc-section-title} "Magunkról"]
        [about-us-section]
        [about-us-page]])

(defn view
  []
  [:section {:id :about-us}
            [about-us]])
