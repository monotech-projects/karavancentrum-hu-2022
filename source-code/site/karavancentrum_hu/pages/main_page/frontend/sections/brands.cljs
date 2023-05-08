
(ns site.karavancentrum-hu.pages.main-page.frontend.sections.brands
    (:require [css.api      :as css]
              [re-frame.api :as r]
              [uri.api      :as uri]
              [hiccup.api   :as hiccup]
              [string.api   :as string]
              [vector.api   :as vector]))

;; WARNING
;; Fixing line break issues
;; /n not breaking as intented

(defn gap-items
  ; @param () n
  ; @param () delimiter
  ;
  ; @usage
  ; (gap-items [:A :B] :x)
  ;
  ; @example
  ; (gap-items [:A :B :C :D] :x)
  ; =>
  ; [:A :x :B :x :C :x :D]
  ;
  ; @return (vector)
  [n delimiter]
  (letfn [(f [result dex x]
            (if (= 0 dex)
              [x]
              (conj result delimiter x)))]
    (reduce-kv f [] n)))

(defn parse-newlines
  ; @param (hiccup) n
  ;
  ; @example
  ; (parse-newlines [:p "Hello world!\nIt's me!"])
  ; =>
  ; [:p "Hello world!" [:br] "It's me!"]
  ;
  ; @return (hiccup)
  [n]
  (letfn [(f0 [element] (reduce f1 [] element))
          (f1 [element content] (if (string/contains-part? content "\n")
                                  (as-> content % (string/split % #"\n")
                                        (gap-items % [:br])
                                        (vector/concat-items element %))
                                  (vector/conj-item element content)))]
    (hiccup/walk n f0)))


;; ----------------------------------------------------------------------------
;; ----------------------------------------------------------------------------

(defn brand
  [brand-dex {:keys [link-label link description title]}]
  [:div.kc-brand [:p.kc-brand--title         title]
                 (parse-newlines [:div.kc-brand--description description])
                 [:div {:class :kc-brand--link-box}
                      ;;  [:div {}]
                            ;;  [:div.kc-brand--label link-label]]
                            ;;  [:div.kc-brand--link  link]]
                       [:a.kc-brand--goto {:href (uri/valid-uri link) :title "Hivatkozás megnyitása" :target "_blank"}
                                          [:span "Megtekintés"]
                                          [:i.fas.fa-arrow-right]]]])

(defn brands
  []
  (let [brands @(r/subscribe [:x.db/get-item [:website-content :handler/transfered-content :brands]])]
       [:div#kc-brands [:div [:p.kc-section-title "Értékesítés"]]
                       [:div#kc-brands--brand-list (letfn [(f [%1 %2 %3] (conj %1 [brand %2 %3]))]
                                                          (reduce-kv f [:<>] brands))]]))

(defn view
  []
  [:section {:id :brands}
            [brands]])
