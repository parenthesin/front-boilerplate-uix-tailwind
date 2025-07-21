(ns parenthesin.front-boilerplate.panels.wallet.state
  (:require [parenthesin.front-boilerplate.infra.http :as http]
            [parenthesin.front-boilerplate.panels.wallet.adapters :as adapters]))

(def db
  (atom {:result nil
         :error nil
         :loading false}))

(defn get-wallet-history []
  (swap! db assoc :error nil :loading true)
  (-> (http/request! {:path "wallet/history"
                      :method :get
                      :accept :json
                      :content-type :json})
      (.then (fn [e]
               (swap! db assoc
                      :result (adapters/->wallet-entries (:body e))
                      :loading false)))
      (.catch (fn [err]
                (swap! db assoc
                       :error err
                       :loading false)
                (js/console.error "request to get entries! catch: " (clj->js err))))))
