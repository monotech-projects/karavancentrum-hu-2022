
(ns app.karavancentrum-hu.frontend.editor.views
    (:require [app.common.frontend.api                     :as common]
              [app.components.frontend.api                 :as components]
              [app.contents.frontend.api                   :as contents]
              [app.karavancentrum-hu.frontend.editor.boxes :as editor.boxes]
              [elements.api                                :as elements]
              [engines.file-editor.api                     :as file-editor]
              [layouts.surface-a.api                       :as surface-a]
              [re-frame.api                                :as r]))

;; ----------------------------------------------------------------------------
;; ----------------------------------------------------------------------------

(defn- basic-data
  []
  [:<> [editor.boxes/website-logo-box]
       [editor.boxes/website-data-box]])

(defn- menus
  []
  [:<> [editor.boxes/header-menu-box]
       [editor.boxes/sidebar-menu-box]
       [editor.boxes/footer-menu-box]])

(defn- about-us
  []
  [:<> [editor.boxes/about-us-section-box]
       [editor.boxes/about-us-page-box]])

(defn- selling
  []
  [:<> [editor.boxes/selling-box]
       [editor.boxes/brand-list]])

;; ----------------------------------------------------------------------------
;; ----------------------------------------------------------------------------

(defn- view-selector
  []
  (let [current-view-id @(r/subscribe [:x.gestures/get-current-view-id :website-content.editor])]
       (case current-view-id :basic-data [basic-data]
                             :menus      [menus]
                             :selling    [selling]
                             :about-us   [about-us])))

(defn- body
  []
  [common/file-editor-body :website-content.editor
                           {:content-path [:website-content :editor/edited-item]
                            :form-element [view-selector]}])

;; ----------------------------------------------------------------------------
;; ----------------------------------------------------------------------------

(defn- header
  []
  [common/file-editor-header :website-content.editor
                             {:label       :website-content
                              :crumbs      [{:label :app-home :route "/@app-home"}
                                            {:label :website-content}]
                              :menu-items  [{:label :basic-data :change-keys [:company-name :company-slogan :company-logo]}
                                            {:label :menus      :change-keys [:header-menu :footer-menu]}
                                            {:label :selling    :change-keys [:brands]}
                                            {:label :about-us   :change-keys [:about-us-section :about-us-page]}]}])

;; ----------------------------------------------------------------------------
;; ----------------------------------------------------------------------------

(defn view
  ; @param (keyword) surface-id
  [surface-id]
  [surface-a/layout surface-id
                    {:content [:<> [header]
                                   [body]]}])
