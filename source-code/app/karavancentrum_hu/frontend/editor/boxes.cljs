
(ns app.karavancentrum-hu.frontend.editor.boxes
    (:require [app.components.frontend.api    :as components]
              [app.contents.frontend.api      :as contents]
              [app.storage.frontend.api       :as storage]
              [app.website-menus.frontend.api :as website-menus]
              [elements.api                   :as elements]
              [forms.api                      :as forms]
              [re-frame.api                   :as r]
              [vector.api                     :as vector]))

;; ----------------------------------------------------------------------------
;; ----------------------------------------------------------------------------

(defn- about-us-section-picker
  []
  (let [editor-disabled? @(r/subscribe [:file-editor/editor-disabled? :website-content.editor])]
       [contents/content-picker ::about-us-section-picker
                                {:autosave?     true
                                 :color         :muted
                                 :disabled?     editor-disabled?
                                 :indent        {:vertical :s}
                                 :multi-select? false
                                 :placeholder   "n/a"
                                 :value-path    [:website-content :editor/edited-item :about-us-section]}]))

(defn- about-us-section-box
  []
  (let [editor-disabled? @(r/subscribe [:file-editor/editor-disabled? :website-content.editor])]
       [components/surface-box ::about-us-section-box
                               {:content [:<> [:div (forms/form-row-attributes)
                                                    [:div (forms/form-block-attributes {:ratio 100})
                                                          [about-us-section-picker]]]
                                              [elements/horizontal-separator {:height :s}]]
                                :disabled? editor-disabled?
                                :label     :about-us-section}]))

;; ----------------------------------------------------------------------------
;; ----------------------------------------------------------------------------

(defn- about-us-page-picker
  []
  (let [editor-disabled? @(r/subscribe [:file-editor/editor-disabled? :website-content.editor])]
       [contents/content-picker ::about-us-page-picker
                                {:autosave?     true
                                 :color         :muted
                                 :disabled?     editor-disabled?
                                 :indent        {:vertical :s}
                                 :multi-select? false
                                 :placeholder   "n/a"
                                 :value-path    [:website-content :editor/edited-item :about-us-page]}]))

(defn- about-us-page-box
  []
  (let [editor-disabled? @(r/subscribe [:file-editor/editor-disabled? :website-content.editor])]
       [components/surface-box ::about-us-page-box
                               {:content [:<> [:div (forms/form-row-attributes)
                                                    [:div (forms/form-block-attributes {:ratio 100})
                                                          [about-us-page-picker]]]
                                              [elements/horizontal-separator {:height :s}]]
                                :disabled? editor-disabled?
                                :indent    {:top :m}
                                :label     :about-us-page}]))

;; ----------------------------------------------------------------------------
;; ----------------------------------------------------------------------------

(defn- duplicate-brand-button
  [brand-dex _]
  (let [editor-disabled? @(r/subscribe [:file-editor/editor-disabled? :website-content.editor])]
       [elements/button {:color     :primary
                         :disabled? editor-disabled?
                         :font-size :xs
                         :indent    {:right :s :top :m}
                         :label     :duplicate!
                         :on-click  [:x.db/apply-item! [:website-content :editor/edited-item :brands]
                                                     vector/duplicate-nth-item brand-dex]}]))

(defn- delete-brand-button
  [brand-dex _]
  (let [editor-disabled? @(r/subscribe [:file-editor/editor-disabled? :website-content.editor])]
       [elements/button {:color     :warning
                         :disabled? editor-disabled?
                         :font-size :xs
                         :indent    {:right :s :top :m}
                         :label     :delete!
                         :on-click  [:x.db/apply-item! [:website-content :editor/edited-item :brands]
                                                     vector/remove-nth-item brand-dex]}]))

(defn- brand-description-field
  [brand-dex _]
  (let [editor-disabled? @(r/subscribe [:file-editor/editor-disabled? :website-content.editor])]
       [elements/multiline-field {:disabled?   editor-disabled?
                                  :label       :description
                                  :indent      {:top :m :vertical :s}
                                  :placeholder :brand-description-placeholder
                                  :value-path  [:website-content :editor/edited-item :brands brand-dex :description]}]))

(defn- brand-title-field
  [brand-dex _]
  (let [editor-disabled? @(r/subscribe [:file-editor/editor-disabled? :website-content.editor])]
       [elements/text-field {:disabled?   editor-disabled?
                             :label       :section-title
                             :indent      {:top :m :vertical :s}
                             :placeholder :section-title-placeholder
                             :value-path  [:website-content :editor/edited-item :brands brand-dex :title]}]))

(defn- brand-link-label-field
  [brand-dex _]
  (let [editor-disabled? @(r/subscribe [:file-editor/editor-disabled? :website-content.editor])]
       [elements/text-field {:disabled?   editor-disabled?
                             :label       :link-label
                             :indent      {:top :m :vertical :s}
                             :placeholder :brand-name
                             :value-path  [:website-content :editor/edited-item :brands brand-dex :link-label]}]))

(defn- brand-link-field
  [brand-dex _]
  (let [editor-disabled? @(r/subscribe [:file-editor/editor-disabled? :website-content.editor])]
       [elements/text-field {:disabled?   editor-disabled?
                             :label       :link
                             :indent      {:top :m :vertical :s}
                             :placeholder :website-link-placeholder
                             :value-path  [:website-content :editor/edited-item :brands brand-dex :link]}]))

(defn- brand-box
  [brand-dex brand-props]
  [components/surface-box {:content [:<> [:div (forms/form-row-attributes)
                                               [:div (forms/form-block-attributes {:ratio 100})
                                                     [brand-title-field      brand-dex brand-props]
                                                     [brand-link-label-field brand-dex brand-props]
                                                     [brand-link-field       brand-dex brand-props]]]
                                         [:div (forms/form-row-attributes)
                                               [:div (forms/form-block-attributes {:ratio 100})
                                                     [brand-description-field brand-dex brand-props]]]
                                         [:div {:style {:display :flex :justify-content :flex-end}}
                                               [duplicate-brand-button brand-dex brand-props]
                                               [delete-brand-button    brand-dex brand-props]]
                                         [elements/horizontal-separator {:height :xs}]]
                           :indent  {:top :m}}])

;; ----------------------------------------------------------------------------
;; ----------------------------------------------------------------------------

(defn- brand-list
  []
  (letfn [(f [%1 %2 %3] (conj %1 [brand-box %2 %3]))]
         (let [brands @(r/subscribe [:x.db/get-item [:website-content :editor/edited-item :brands]])]
              (reduce-kv f [:<>] brands))))

;; ----------------------------------------------------------------------------
;; ----------------------------------------------------------------------------

(defn- brand-controls-action-bar
  []
  (let [editor-disabled? @(r/subscribe [:file-editor/editor-disabled? :website-content.editor])
        on-click [:x.db/apply-item! [:website-content :editor/edited-item :brands] vector/cons-item {}]]
       [components/action-bar ::brand-controls-action-bar
                              {:disabled? editor-disabled?
                               :label     :add-brand!
                               :on-click  on-click}]))

(defn- selling-box
  []
  (let [editor-disabled? @(r/subscribe [:file-editor/editor-disabled? :website-content.editor])]
       [components/surface-box ::selling-box
                               {:content [:<> [brand-controls-action-bar]
                                              [elements/horizontal-separator {:height :xs}]]
                                :disabled? editor-disabled?
                                :label     :selling}]))
