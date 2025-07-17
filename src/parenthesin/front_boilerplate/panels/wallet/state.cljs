(ns parenthesin.front-boilerplate.panels.wallet.state
  (:require [parenthesin.front-boilerplate.infra.http :as http]
            [parenthesin.front-boilerplate.panels.wallet.adapters :as adapters]))

(defn get-wallet-history [set-wallet-history]
  (-> (http/request! {:path "wallet/history"
                      :method :get})
      (.then (fn [e] (set-wallet-history (adapters/->wallet-entries (:body e)))))
      (.catch #(prn "request to get entries! catch: " %))))
