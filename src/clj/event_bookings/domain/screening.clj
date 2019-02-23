(ns event-bookings.domain.screening)

(defn new [seats wheelchairs]
  {:max-seats seats
   :max-wheelchairs wheelchairs
   :bookings {}})

(defn free-seats [screening]
  ;;TODO: Needs to iterate through bookings
  (- (:max-seats screening) 0))

(defn free-wheelchairs [screening]
  (- (:max-wheelchairs screening) 0))

(defn make-booking [booking screening]
  ;; TODO: Add booking to list
  ;; TODO: Loging to test when adding booking there are enough seats
  screening)
