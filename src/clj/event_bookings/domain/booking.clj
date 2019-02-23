(ns event-bookings.domain.booking)

(defn new [email seats wheelchairs]
  {:seats seats
   :wheelchairs wheelchairs})
