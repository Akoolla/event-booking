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
    (let [booking (booking/new "email" 2 2 )
          screening (:screening (s/make-booking booking (s/new 32 2)))]

      (testing "screening-with-one-booking"
        (is (= 30 (s/free-seats screening))))
      
      (let [screening (:screening (s/make-booking
                       (booking/new "email2" 2 0)
                       screening))]

        (testing "screening-with-2-bookings"
          (is (= 28 (s/free-seats screening)))))))

(deftest making-a-booking-should-return-tuple-of-bookingref-and-screening
  (let [booking-result (s/make-booking
                        (booking/new "email1" 2 0)
                        (s/new 32 2))]
  (testing "booking result has booking-id"
    (some? (:booking-id booking-result)))))
  
  
