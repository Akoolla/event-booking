(ns event-bookings.views.screenings-test
  (:require [clojure.test :refer :all]
            [event-bookings.view.screenings :as view]))


(deftest can-return-correct-style-for-num-of-available-places
  (testing "Style for no available places"
    (let [style (view/seats-available-style 0 32)]
      (is (= "label label-danger" style))))

  (testing "Style for less than 50% places left"
    (let [style (view/seats-available-style 15 32)]
      (is (= "label label-warning" style))))

  (testing "Style for more than 50% places left"
    (let [style (view/seats-available-style 16 32)]
      (is (= "label label-success" style)))))

