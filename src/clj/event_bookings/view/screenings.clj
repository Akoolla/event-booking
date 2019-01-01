(ns event-bookings.view.screenings
  (:require [hiccup.page :as h]
            [event-bookings.view.common :refer :all]
            [clojure.string :refer [join]]
            [java-time :as t]))

(defn- screening-date
  [screening-date]
  (join ", " [(.getDisplayName
               (t/day-of-week screening-date)
               (java.time.format.TextStyle/FULL)
               (java.util.Locale/UK))

              (join "." [(.getDisplayName
                          (t/month screening-date)
                          (java.time.format.TextStyle/FULL)
                          (java.util.Locale/UK))

                         (t/as screening-date :day-of-week)])

              (t/format "h:mm a" screening-date)]))

(defn seats-available-style
  "Style should be danger if not seats free, amber of 20% available or green for over 20%"
  [booked-spaces max-spaces]
  (join " " ["label" "label-success"]))

(defn sc
  [screening]
  (let [film (:film screening)
        wheel-chair-style (seats-available-style 10 10)
        seats-style (seats-available-style 10 10)]
    [:li.list-group-item
     [:h4.list-group-item-heading
      (join " " [(:name film)
                 (join "" ["(" (:rating film) ")"])
                 (:country film)
                 (t/as (:date film) :year)
                 (:length film) "mins"])]
     [:h5.list-group-item-heading (screening-date (:date screening))]
     [:p
      [:span {:class seats-style} "Seats Left&nbsp;&nbsp;" [:span.badge (:max-seats screening)]]
      "&nbsp;"
      [:span.label.label-success "Wheelchair spaces&nbsp;&nbsp;" [:span.badge (:max-wheelchairs screening)]]]
     [:a {:href (join "/" [ "book" (:id screening)])
          :role "button"
          :class "btn btn-primary btn-xs"} "Make Booking"]]))

(defn screening-list
  [screenings]
  (bootstrap-page
   {:title "Screenings"}
   [:div.list-group
    (for [screening screenings]
      (sc screening))]
   [:div
    [:p#clickable "Click me!"]
    [:script {:src "/js/cljs.js"}]]))

(defn add
  []
  (bootstrap-page
   {:title "Add screening"}
   [:div.container
    [:div
     (form
      {:method "POST" :enctype "multipart/form-data" :action "/screening/add"}
      [:div
       [:div.row
        [:div.col-xs-8.col-sm-4
         [:div.form-group
          [:label {:for "name"} "Film Name"]
          [:input.form-control {:id "name" :name "name" :placeholder "Enter film Name" :autofocus "autofocus"}]]]

        [:div.col-xs-4.col-sm-2
         [:div.form-group
          [:label {:for "rating"} "Film Rating"]
          [:input.form-control {:id "rating" :name "rating" :placeholder "e.g PG"}]]]]

       [:div.row
        [:div.col-xs-3.col-sm-2
         [:div.form-group
          [:label {:for "length"} "Length"]
          [:input.form-control {:id "length" :name "length" :type "number" :placeholder "in mins."}]]]

        [:div.col-xs-3.col-sm-2
         [:div.form-group
          [:label {:for "country"} "Country"]
          [:input.form-control {:id "country" :name "country" :placeholder "e.g UK"}]]]

        [:div.col-xs-6.col-sm-2
          [:div.form-group
           [:label {:for "release-date"} "Release Date"]
           [:input.form-control {:id "release-date" :name "release-date" :type "date"}]]]]

       [:div.row
        [:div.col-sm-6
         [:div.form-group
          [:label {:for "description"} "Description"]
          [:textarea.form-control {:id "description" :name "description"}]]]]
        
       [:div.row
        [:div.col-sm-3
         [:div.form-group
          [:label {:for "date"} "Screening Date/Time"]
          [:input.form-control {:id "date" :name "date" :type "datetime-local"}]]]]

       [:div.row
        [:div.col-sm-3
         [:div.form-group
          [:label
           [:input {:id "allowbookings" :name "allowbookings" :type "checkbox"} "&nbsp;Allow Bookings"]]]]]
       
       [:div.row
        [:div.col-xs-6.col-sm-3
         [:div.form-group
          [:label {:for "max-seats"} "Num. of Seats"]
          [:input.form-control {:id "max-seats" :name "max-seats" :type "number" :placeholder "Default is 32" }]]]

        [:div.col-xs-6.col-sm-3
         [:div.form-group
          [:label {:for "max-wheelchairs"} "Num. of Wheel chairs"]
          [:input.form-control {:id "max-wheelchairs" :name "max-wheelchairs" :type "number" :placeholder "Default is 2" }]]]]

       [:div.row
        [:div.col-sm-6
         [:button.btn.btn-primary.pull-right {:type "submit" :id "addBtn"} "Add Screening"]]]

       ])]]))
     
