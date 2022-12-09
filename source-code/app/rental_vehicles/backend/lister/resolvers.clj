
(ns app.rental-vehicles.backend.lister.resolvers
    (:require [app.common.backend.api                :as common]
              [com.wsscode.pathom3.connect.operation :refer [defresolver]]
              [engines.item-lister.api               :as item-lister]
              [mongo-db.api                          :as mongo-db]
              [pathom.api                            :as pathom]
              [x.user.api                            :as x.user]))

;; ----------------------------------------------------------------------------
;; ----------------------------------------------------------------------------

(defn get-items-f
  ; @param (map) env
  ; {:request (map)}
  ; @param (map) resolver-props
  ;
  ; @return (map)
  ; {:document-count (integer)
  ;  :documents (namespaced maps in vector)}
  [{:keys [request] :as env} _]
  (if (x.user/request->authenticated? request)
      (let [get-pipeline   (item-lister/env->get-pipeline   env :rental-vehicles.lister)
            count-pipeline (item-lister/env->count-pipeline env :rental-vehicles.lister)]
           {:items          (mongo-db/get-documents-by-pipeline   "rental_vehicles" get-pipeline)
            :all-item-count (mongo-db/count-documents-by-pipeline "rental_vehicles" count-pipeline)})))

(defresolver get-items
             ; @param (map) env
             ; @param (map) resolver-props
             ;
             ; @return (namespaced map)
             ;  {:rental-vehicles.lister/get-items (map)
             ;    {:document-count (integer)
             ;     :documents (namespaced maps in vector)}}
             [env resolver-props]
             {:rental-vehicles.lister/get-items (get-items-f env resolver-props)})

;; ----------------------------------------------------------------------------
;; ----------------------------------------------------------------------------

; @constant (functions in vector)
(def HANDLERS [get-items])

(pathom/reg-handlers! ::handlers HANDLERS)
