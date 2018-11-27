(ns event-bookings.view.booking
  (:require [hiccup.page :as h]
            [event-bookings.view.common :refer :all]))

(defn booking []
  (bootstrap-page
   {:title "Make booking"}
   [:div
    [:h1 "Make Booking"]]))
