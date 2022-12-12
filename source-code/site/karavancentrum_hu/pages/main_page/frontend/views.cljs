
(ns site.karavancentrum-hu.pages.main-page.frontend.views
    (:require [re-frame.api                                                      :as r]
              [reagent.api                                                       :as reagent]
              [site.components.frontend.api                                      :as components]
              [site.karavancentrum-hu.pages.main-page.frontend.sections.about-us :as about-us]
              [site.karavancentrum-hu.pages.main-page.frontend.sections.brands   :as brands]
              [site.karavancentrum-hu.pages.main-page.frontend.sections.contacts :as contacts]
              [site.karavancentrum-hu.pages.main-page.frontend.sections.hero     :as hero]
              [site.karavancentrum-hu.pages.main-page.frontend.sections.renting  :as renting]
              [tools.image-preloader.api                                         :as image-preloader]))

;; ----------------------------------------------------------------------------
;; ----------------------------------------------------------------------------

(defn main-page
  [props]
  [:main#main-page--content [hero/view]
                            [renting/view]
                            [brands/view]
                            [about-us/view]
                            [contacts/view]
                            [image-preloader/component {:uri "/site/images/hero-1920.jpg"}]])

;; ----------------------------------------------------------------------------
;; ----------------------------------------------------------------------------

(defn view
  []
  [main-page])
