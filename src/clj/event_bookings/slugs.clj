(ns event-bookings.slugs
  (:require [slugger.core :as slugger]
            [java-time :as t]
            [clojure.string :refer [join]]))

(defn make-screening-slug
  "Film name and java-time/zoned-date-time"
  [film-name screening-date]
  (let [slug (slugger/->slug film-name)
        slug (join "-" [slug
                        (t/format "yyyy-dd-MM-HH-mm" screening-date)])]
    slug))
