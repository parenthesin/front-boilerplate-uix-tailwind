(ns parenthesin.front-boilerplate.config)

(goog-define DEBUG true)
(goog-define PROTOCOL "http")
(goog-define HOST "localhost")
(goog-define PORT "5000")
(goog-define BASE_URL "https://your-prod-url.com/")

(def config
  (let [debug? goog.DEBUG]
    {:debug DEBUG
     :protocol PROTOCOL
     :host HOST
     :port PORT
     :language (if debug? "en-US" (.-language js/navigator))
     :base-url (if debug?
                 "http://localhost:3001/"
                 BASE_URL)
     :resources-base-url (-> (if debug?
                               "http://localhost:3001/"
                               BASE_URL)
                             (str "resources/"))}))
