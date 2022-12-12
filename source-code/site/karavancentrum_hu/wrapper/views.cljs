
(ns site.karavancentrum-hu.wrapper.views
    (:require [re-frame.api                 :as r]
              [site.components.frontend.api :as components]
              [uri.api                      :as uri]))

;; ----------------------------------------------------------------------------
;; ----------------------------------------------------------------------------

(defn page-bottom
  []
  [:div {:style {:padding "100px 0 50px 0" :display "flex" :justify-content "center"}}
        [:a {:class :kc-content-button :href "/"} "Vissza a főoldalra"]])

(defn credits
  []
  [:div {:style {:padding "15px 0 15px 0" :color "#9ec3fb"}}
        [components/created-by-link ::created-by-link {:theme :dark}]])

(defn footer
  []
  (let [footer-menu @(r/subscribe [:x.db/get-item [:website-content :handler/downloaded-content :footer-menu]])]
       [:div#kc-footer
         [components/menu ::footer-menu {:menu-link footer-menu}]
         [credits]]))

;; ----------------------------------------------------------------------------
;; ----------------------------------------------------------------------------

(defn sidebar-menu
  []
  (let [header-menu @(r/subscribe [:x.db/get-item [:website-content :handler/downloaded-content :header-menu]])]
       [components/menu ::sidebar-menu
                        {:menu-link header-menu}]))

(defn sidebar
  []
  [components/sidebar {:content #'sidebar-menu}])

(defn company-name-and-slogan
  []
  (let [company-name   @(r/subscribe [:x.db/get-item [:website-content :handler/downloaded-content :website-name]])
        company-slogan @(r/subscribe [:x.db/get-item [:website-content :handler/downloaded-content :website-slogan]])]
       [:a {:href "/" :style {:text-decoration "none"}}
           [:div#kc-navbar--company-name-and-slogan [:div#kc-navbar--company-name   company-name]
                                                    [:div#kc-navbar--company-slogan company-slogan]]]))

(defn header
  []
  (let [header-menu @(r/subscribe [:x.db/get-item [:website-content :handler/downloaded-content :header-menu]])]
       [components/navbar {:logo #'company-name-and-slogan
                           :menu-link header-menu
                           :on-menu [:components.sidebar/show-sidebar!]
                           :threshold 800}]))

;; ----------------------------------------------------------------------------
;; ----------------------------------------------------------------------------

(defn wrapper
  [ui-structure]
  (let [route-id @(r/subscribe [:x.router/get-current-route-id])]
       [:div#kc [ui-structure]
                [:div {:class :kc-page-spacer :style {:flex-grow 1}}]
                (if @(r/subscribe [:website-pages.handler/on-page?])
                     [page-bottom])
                (if (= route-id :vehicle-page/route)
                    [page-bottom])
                [footer]
                [header]
                [sidebar]]))

(defn view
  [ui-structure]
  [wrapper ui-structure])
