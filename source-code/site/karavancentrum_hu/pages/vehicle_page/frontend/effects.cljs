
(ns site.karavancentrum-hu.pages.vehicle-page.frontend.effects
    (:require [re-frame.api                                             :as r]
              [site.karavancentrum-hu.pages.vehicle-page.frontend.views :as views]))

;; ----------------------------------------------------------------------------
;; ----------------------------------------------------------------------------

(r/reg-event-fx :vehicle-page/load-page!
  {:dispatch-n [[:vehicle-page/render-page!]]})
               ;[:ui/set-title! ...]
               
(r/reg-event-fx :vehicle-page/render-page!
  [:x.ui/render-surface! :vehicle-page/view
                         {:on-unmount [:vehicle-page/clear-selected-vehicle!]
                          :content    #'views/view}])
