(ns parenthesin.front-boilerplate.panels.wallet.state
  (:require
   [parenthesin.front-boilerplate.adapters :refer [format-amount]]
   [parenthesin.front-boilerplate.infra.http :as http]
   [parenthesin.front-boilerplate.panels.wallet.adapters :as adapters]))

(def db
  (atom {:result nil
         :error nil
         :loading false}))

(defn update-db [entry btc-price value language]
  (let [current-btc (get-in @db [:result :total-btc])
        total-btc (+ value current-btc)]
    (swap! db update-in [:result :entries] conj (adapters/->wallet-entry entry language))
    (swap! db update-in [:result :total-btc] + value)
    (swap! db assoc-in [:result :total-current-usd] (format-amount (* total-btc btc-price)))
    (swap! db assoc :loading false)))

(defn get-wallet-history [{:keys [language]}]
  (swap! db assoc :error nil :loading true)
  (-> (http/request! {:path "wallet/history"
                      :method :get
                      :accept :json
                      :content-type :json})
      (.then (fn [e]
               (swap! db assoc
                      :result (adapters/->wallet-entries (:body e) language)
                      :loading false)))
      (.catch (fn [err]
                (swap! db assoc
                       :error err
                       :loading false)
                (js/console.error "request to get entries! catch: " (clj->js err))))))

(defn wallet-deposit [{:keys [language]} {:keys [btc-price value]}]
  (swap! db assoc :error nil :loading true)
  (-> (http/request! {:path "wallet/deposit"
                      :method :post
                      :accept :json
                      :content-type :json
                      :body {:btc value}})
      (.then (fn [e] (update-db (:body e) btc-price value language)))
      (.catch (fn [err]
                (swap! db assoc
                       :error err
                       :loading false)
                (js/console.error "request to deposit entries! catch: " (clj->js err))))))

(defn wallet-withdrawal [{:keys [language]} {:keys [btc-price value]}]
  (swap! db assoc :error nil :loading true)
  (let [current-btc (get-in @db [:result :total-btc])
        value-to-withdraw (* value -1)]
    (if (> value current-btc)
      (swap! db assoc
             :error #js {:message "You cannot withdraw more than your current balance."}
             :loading false)
      (-> (http/request! {:path "wallet/withdrawal"
                          :method :post
                          :accept :json
                          :content-type :json
                          :body {:btc value-to-withdraw}})
          (.then (fn [e] (update-db (:body e) btc-price value-to-withdraw language)))
          (.catch (fn [err]
                    (swap! db assoc
                           :error err
                           :loading false)
                    (js/console.error "request to withdrawal entries! catch: " (clj->js err))))))))
