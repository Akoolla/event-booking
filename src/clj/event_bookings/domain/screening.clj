(ns event-bookings.domain.screening)

(defn new [seats wheelchairs]
  {:max-seats seats
   :max-wheelchairs wheelchairs
   :bookings {}})

(defn make-booking-id [booking]
  (.toString (java.util.UUID/randomUUID)))

(defn free-seats [screening]
  (- (:max-seats screening)
     (reduce (fn [seats booking]
               (+ seats (:seats booking)))
             0
             (vals (:bookings screening)))))

(defn free-wheelchairs [screening]
  (- (:max-wheelchairs screening) 0))

(defn make-booking [booking screening]
  (let [booking-id (make-booking-id booking)
        screening (assoc screening :bookings
                         (assoc (:bookings screening)
                                booking-id booking))]
    {:booking-id booking-id
     :screening screening}))
;; TODO: Logic to test when adding booking there are enough seats)
