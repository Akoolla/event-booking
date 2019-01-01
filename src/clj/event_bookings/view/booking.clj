(ns event-bookings.view.booking
  (:require [hiccup.page :as h]
            [event-bookings.view.common :refer :all]))

(defn booking [event-slug]
  (bootstrap-page
   {:title event-slug}
   [:div.container
    [:div.row
     [:div.col-md-4
      (form
       [:div
        [:div.form-group
         [:label "Booking for:"]
         [:p.form-control-static {:id "filmInfo"} event-slug]]

        [:div.form-group.has-feedback {:id "bookedNAmeGrp"}
         [:label.control-label {:for "bookedName"} "Name"]
         [:input.form-control {:type "text"
                               :placeholder "Please enter a name for booking"
                               :id "bookedName"}]]

        [:div.form-group.has-feedback {:id "emailGrp"}
         [:label.control-label {:for "email"} "Email"]
         [:input.form-control {:type "text"
                               :placeholder "Please enter an email address"
                               :id "email"
                               :name "email"}]]

        [:div.form-group.has-feedback {:id "stdSeatsGrp"}
         [:label.control-label {:for "stdSeats"} "Number of seats (remaining 3)"]
         [:input.form-control {:type "number"
                               :placeholder "0"
                               :id "stdSeats" :name "stdSeats"}]]

        [:div.form-group.has-feedback {:id "wheelChairsGrp"}
         [:label.control-label {:for "wheelChairs"} "Number of Wheelchairs (remaining 2)"]
         [:input.form-control {:type "number"
                               :id "wheelChairs" :name "wheelChairs"
                               :placeholder "0"}]]
        [:div.form-group
         [:button.btn.btn-primary.disabled {:id "btnBook"} "Book Seats"]]
        ])
      ]
     ]
    ]))
