(ns event-bookings.handler
  (:import java.net.URI)
  (:require  [compojure.core :refer :all]
             [compojure.core :as compojure :refer (GET POST ANY defroutes)]
             [compojure.route :as route]
             [ring.util.response :as response]
             [ring.middleware.webjars :refer [wrap-webjars]]
             [ring.middleware.defaults :refer [wrap-defaults site-defaults]]

             [event-bookings.view.screenings :as screenings]
             [event-bookings.store.screenings :as store]
             [event-bookings.view.booking :as booking]
             
             [java-time :as t]))

(def datetime-fmt "yyyy-MM-dd'T'HH:mm")
(def date-fmt "yyyy-MM-dd")

(defn format-date-time
  "String date in format yyyy-dd-MM'T'HH:mm to java-time/zoned--date-time (UTC)"
  [str-date]
  (-> (t/local-date-time datetime-fmt str-date)
      (t/zoned-date-time 0)
      (t/with-zone "UTC")))

(defn format-date
  "String date in format 2020-01-23"
  [str-date]
  (t/zoned-date-time (t/local-date date-fmt str-date) 0))

(defroutes admin-routes
  (GET "/screening/add" [] (screenings/add))
  (POST "/screening/add" {params :params}
        (let [screening {:film {:name (:name params)
                                :rating (:rating params)
                                :length (:length params)
                                :country (:country params)
                                :date (format-date (:release-date params))
                                :description (:description params)}
                         :date (format-date-time (:date params))
                         :allow-bookings (:allowbookings params)
                         :max-seats (:max-seats params)
                         :max-wheelchairs (:max-wheelchairs params)}]
          (store/create-screening screening)
          (screenings/screening-list (vals (store/list-all-screenings))))))

(defroutes screening-routes
  (GET "/" [] (screenings/screening-list (vals (store/list-all-screenings))))
  (GET "/booking" [] (booking/booking)))

(defroutes app-routes
  admin-routes
  screening-routes)

(def app
  (->
   app-routes
   wrap-webjars
   (wrap-defaults site-defaults)))

