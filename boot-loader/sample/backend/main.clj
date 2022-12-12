
(ns sample.backend.main
    (:gen-class)
    (:require ; monotech-hq/monoset
              [elements.api]
              [fonts.api]
              [forms.api]
              [icons.api]
              [layouts.popup-a.api]
              [layouts.surface-a.api]
              ; monotech-hq/project-developer
              [project-developer.api]
              ; monotech-hq/project-kit
              [app.common.backend.api]
              [app.common.iso.api]
              [app.contents.backend.api]
              [app.home.backend.api]
              [app.settings.backend.api]
              [app.storage.backend.api]
              [app.user.backend.api]
              [app.views.backend.api]
              [app.website-config.backend.api]
              [app.website-contacts.backend.api]
              [app.website-content.backend.api]
              [app.website-menus.backend.api]
              [app.website-pages.backend.api]
              ; monotech-projects/karavancentrum-hu-2022
              [app.rental-vehicles.backend.api]
              [app.sample.backend.api]
              [project.sample.backend.api]
              [site.karavancentrum-hu.backend.api]
              [site.rental-vehicles.backend.api]
              [site.website-config.backend.api]
              [site.website-contacts.backend.api]
              [site.website-content.backend.api]
              [site.website-menus.backend.api]
              [site.website-pages.backend.api]
              ; ...
              [pattern.api       :as p]
              [shadow-cljs.api   :as shadow-cljs]
              [x.boot-loader.api :as x.boot-loader]
              [x.core.api        :as x.core]))

;; ----------------------------------------------------------------------------
;; ----------------------------------------------------------------------------

(defn start-server!
  ; @param (map) server-props
  ; {:port (integer or string)(opt)}
  [{:keys [port] :as server-props}]
  (println "boot-loader - Starting server on port:" (or port "@default"))
  (x.boot-loader/start-server! server-props))

;; ----------------------------------------------------------------------------
;; ----------------------------------------------------------------------------

(defn -main
  ; @param (list of *) args
  ; [(integer)(opt) port]
  ;
  ; @usage
  ; (-main 3000)
  ;
  ; @usage
  ; java -jar {{namespace}}.jar 3000
  [& [port :as args]]
  ; In product releases the pattern.api validator should be turned off.
  ; Excepts when you trying to debug the application.
  (p/ignore!) ; <- Turning off the pattern.api validator
  (start-server! {:port port}))

(defn dev
  ; @param (map) options
  ; {:port (integer)
  ;  :shadow-build (keyword)}
  ;
  ; @usage
  ; (dev {:shadow-build :my-build})
  [{:keys [port shadow-build]}]
  (letfn [(callback-f [] (shadow-cljs/use-build! shadow-build))]
         (start-server! {:callback-f callback-f
                         :port       port
                         :dev-mode?  true})))
