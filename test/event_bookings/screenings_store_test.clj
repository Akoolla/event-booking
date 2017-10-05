(ns event-bookings.screenings-store-test
  (:require [clojure.test :refer :all]
            [java-time :as t]
            [event-bookings.store.screenings :as store]
            [event-bookings.slugs :as slugs]))

(deftest can-store-and-retrieve
  (let [screening {:film {:name "The Levelling"
                          :rating "PG"
                          :length 120
                          :country "USA"
                          :date (t/zoned-date-time (t/local-date) 0)
                          :description "Some longer text"}
                   :date (t/zoned-date-time (t/local-date-time "yyyy-MM-dd'T'HH:mm" "2020-02-26T19:30") 0)
                   :allow-bookings true
                   :max-seats 32
                   :max-wheelchairs 2}
        screening (store/create-screening screening)
        screening (store/get-by-id "the-levelling-2020-26-02-19-30")
        screenigns (store/list-all-screenings)]
    (testing "List of screenigns"
      (is (not(nil? screenigns))))

    (testing "Screening created"
      (is (not(nil? screening)))
      (is (= (type (:date screening)) java.time.ZonedDateTime)))))

(deftest can-make-screeing-slug
  (let [screening-date (t/zoned-date-time (t/local-date-time "yyyy-MM-dd'T'HH:mm" "2020-02-26T19:30") 0)
        film-name "The Levelling"
        slug (slugs/make-screening-slug film-name screening-date)]
    (testing "Slug from film name and screening date"
      (is (not(nil? slug)))
      (is (= "the-levelling-2020-26-02-19-30" slug)))))
