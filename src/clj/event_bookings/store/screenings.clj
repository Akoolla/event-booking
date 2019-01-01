(ns event-bookings.store.screenings
  (:require [alandipert.enduro :as enduro]
            [event-bookings.store.env :as env]
            [event-bookings.slugs :as slugger]
            [java-time :as t]))
   
(def storage
  ;; a map from user-id to user details, each being a map of attributes
  (enduro/file-atom
   {}
   (str env/storage-directory "screenings.edn")))

(defn- format-date
  "Enduro uses the standard tags to read in data so we format date to #inst"
  [expiry]
  (if-let [expiry expiry]
    (t/to-java-date expiry)
    expiry))

(defn- read-dates-concern
  [screening]
  (let [film (:film screening)]
    (assoc screening
         :date (t/zoned-date-time (:date screening) 0)
         :film (assoc film :date (t/zoned-date-time (:date film) 0)))))

(defn get-by-id
  "Check out make id function for id format"
  [id]
  (let [screening (get @storage id)
        screening (assoc screening
                         :date (t/zoned-date-time (:date screening) 0))]
   screening))

(defn create-screening
  [screening]
  (enduro/swap!
     storage
     (fn [screenings]
       (let [id (slugger/make-screening-slug (:name (:film screening)) (:date screening))
             screening (assoc screening
                              :date (format-date (:date screening))
                              :film (let [film (:film screening)]
                                          (assoc film :date (format-date (:date film))))
                              :id id)]
         (assoc screenings id screening)))))

(defn list-all-screenings
  []
  (let [screenings {}]
    (reduce (fn [accum screening]
              (let [id (first screening)
                    screening (read-dates-concern (second screening))]
                (assoc accum id screening)))
            screenings
            (deref storage))))
