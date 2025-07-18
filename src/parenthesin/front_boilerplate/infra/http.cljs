(ns parenthesin.front-boilerplate.infra.http
  (:require [parenthesin.front-boilerplate.infra.http.component :as http.component]
            [parenthesin.front-boilerplate.infra.system.state :as system.state]))

(defn request! [request-input]
  (http.component/request (:http @system.state/system) request-input))

(comment
  ;; base test component request
  (-> (http.component/request
       (http.component/new-http {:base-url "http://localhost:3001"})
       {:path "wallet/history"
        :method :get})
      (.then #(prn "then" (:body %)))
      (.catch  #(prn "catch" %)))

  ;; using `request!` function
  (-> (request! {:path "wallet/history"
                 :method :get})
      (.then #(prn "request! then: " (:body %)))
      (.catch #(prn "request! catch: " %))))
