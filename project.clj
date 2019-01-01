(defproject example "0.1.0-SNAPSHOT"
  :description "FIXME: write this!"
  :url "http://example.com/FIXME"
  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v10.html"}

  :min-lein-version "2.7.1"

  :dependencies [[org.clojure/clojure "1.9.0"]
                 [org.clojure/clojurescript "1.9.229"]
                 [org.clojure/core.async "0.2.395"
                  :exclusions [org.clojure/tools.reader]]

                 ;; NOTE: common clojure server side libraries
                 [ring "1.7.1"]
                 [ring/ring-defaults "0.3.2"]
                 [compojure "1.6.1"]

                 [ring-webjars "0.1.1"]
                 [hiccup "1.0.5"]
                 [org.webjars/bootstrap "3.3.6"]
                 [org.webjars/bootswatch-paper "3.3.5+4"]

                 [alandipert/enduro "1.2.0"]
                 [slugger "1.0.1"]
                 [clojure.java-time "0.3.1"]
                 [environ "1.1.0"]]

  :plugins [[lein-figwheel "0.5.10-SNAPSHOT"]
            [lein-cljsbuild "1.1.5" :exclusions [[org.clojure/clojure]]]
            [lein-ring "0.9.7"]]

  :source-paths ["src/clj/", "src/cljs"]
  :ring {:handler event-bookings.handler/app}

  :cljsbuild {:builds
              [{:id "dev"
                :source-paths ["src/clj", "src/cljs"]
                :figwheel {:on-jsload "event-bookings.client/on-js-reload"
                           :open-urls ["http://localhost:3449"]}

                :compiler {:main event-bookings.client
                           :asset-path "js/compiled/out"
                           :output-to "resources/public/js/compiled/example.js"
                           :output-dir "resources/public/js/compiled/out"
                           :source-map-timestamp true
                           :preloads [devtools.preload]}}
               {:id "min"
                :source-paths ["src/clj", "src/cljs"]
                :compiler {:output-to "resources/public/js/compiled/example.js"
                           :main event-bookings.client
                           :optimizations :advanced
                           :pretty-print false}}]}

  :figwheel {;; NOTE: configure figwheel to embed your ring-handler
             ;;:ring-handler example.server-handler/dev-app
             :css-dirs ["resources/public/css"]}

  ;; setting up nREPL for Figwheel and ClojureScript dev
  ;; Please see:
  ;; https://github.com/bhauman/lein-figwheel/wiki/Using-the-Figwheel-REPL-within-NRepl
  :profiles {:dev {:dependencies [[binaryage/devtools "0.9.0"]
                                  [figwheel-sidecar "0.5.10-SNAPSHOT"]
                                  [com.cemerick/piggieback "0.2.1"]]
                   :clean-targets ^{:protect false} ["resources/public/js/compiled" "target"]

                   ;; need to add dev source path here to get user.clj loaded
                   :source-paths ["src" "dev"]
                   ;; for CIDER
                   ;; :plugins [[cider/cider-nrepl "0.12.0"]]
                   :repl-options {:nrepl-middleware [cemerick.piggieback/wrap-cljs-repl]}}})
