(ns event-bookings.domain.make-booking-test
  (:require [clojure.test :refer :all]
            [event-bookings.domain.screening :as s]
            [event-bookings.domain.booking :as booking]))

(deftest setting-spaces-for-screening
  (testing "number-of-seats"
    (let [screening (s/new 28 2)]
      (is (= 28 (s/free-seats screening)))
      (is (= 2 (s/free-wheelchairs screening))))))

(deftest can-get-calculate-free-seats
  (testing "something"
    (let [booking (booking/new "email" 2 2 )
          screening (s/make-booking booking (s/new 32 2))]
      (is (= 30 (s/free-seats screening))))))
