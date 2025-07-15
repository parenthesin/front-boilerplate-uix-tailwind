(ns parenthesin.front-boilerplate.panels.wallet.view
  (:require
   [parenthesin.front-boilerplate.infra.http :as http]
   [parenthesin.front-boilerplate.panels.wallet.components :refer [wallet-entries]]
   [uix.core :as uix :refer [$ defui]]
   [uix.dom]))

;; todo: arrumar essa abobra
(defn parse-data-blablabla [data]
  (let [entries (map (fn [entry]
                       {:id (:id entry)
                        :btc-amount (.-rep ^js (:btc-amount entry))
                        :usd-amount-at (.-rep ^js (:usd-amount-at entry))
                        :created-at (.toDateString (new js/Date (:created-at entry)))})
                     (:entries data))]
    (assoc data :entries entries)))

(defn get-wallet-history [set-wallet-history]
  (-> (http/request! {:path "wallet/history"
                      :method :get})
      (.then (fn [e] (set-wallet-history (parse-data-blablabla (:body e)))))
      (.catch #(prn "request to get entries! catch: " %))))

(defui app-wallet [_]
  (let [[wallet-history set-wallet-history] (uix/use-state nil)
        _ (get-wallet-history set-wallet-history)]
    ($ wallet-entries {:entries (:entries wallet-history)})))
