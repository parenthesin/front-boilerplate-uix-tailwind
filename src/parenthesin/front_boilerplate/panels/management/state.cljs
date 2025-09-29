(ns parenthesin.front-boilerplate.panels.management.state
  (:require
   [parenthesin.front-boilerplate.infra.http :as http]))

(def db
  (atom {:result nil
         :error nil
         :loading false}))

(defn get-current-btc-usd []
  (swap! db assoc :error nil :loading true)
  (-> (http/request! {:path "wallet/current-btc-usd"
                      :method :get
                      :accept :json
                      :content-type :json})
      (.then (fn [e]
               (swap! db assoc
                      :result (:body e)
                      :loading false)))
      (.catch (fn [err]
                (swap! db assoc
                       :error err
                       :loading false)
                (js/console.error "request to get current btc usd! catch: " (clj->js err))))))
