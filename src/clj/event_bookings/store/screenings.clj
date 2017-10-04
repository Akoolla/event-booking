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
  [expiry]
  (if-let [expiry expiry]
    (t/to-java-date expiry)
    expiry))

(defn- read-dates-concern
  [screening]
  (assoc screening
         :date (t/zoned-date-time (:date screening) 0)
         :film-date (t/zoned-date-time (:film-date screening) 0)))

(defn create-id
  "Creats a unique id for the screening based on film.name and screening date"
  [screening])

(defn get-by-id
  "Check out make id function for id format"
  [id]
  (let [screening (get @storage id)
        screening (assoc screening
                         :date (t/zoned-date-time (:date screening) 0)
                         :film-date (t/zoned-date-time (:film-date screening) 0))]
   screening))

(defn create-screening
  [screening]
  (enduro/swap!
     storage
     (fn [screenings]
       (let [id (slugger/make-screening-slug (:film-name screening) (:date screening))
             screening (assoc screening
                              :date (format-date (:date screening))
                              :film-date (format-date (:film-date screening))
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
