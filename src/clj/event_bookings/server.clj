(ns event-bookings.server
  (:require [ring.adapter.jetty :as jetty]
            [ring.middleware.resource :as resources]
            [ring.util.response :as response]

            [event-bookings.handler :refer :all])
  (:gen-class))

(defn -main [& args]
  (jetty/run-jetty app {:port 3000}))

