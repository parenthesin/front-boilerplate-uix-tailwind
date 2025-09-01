(ns parenthesin.front-boilerplate.panels.wallet.state
  (:require
   [parenthesin.front-boilerplate.infra.http :as http]
   [parenthesin.front-boilerplate.panels.wallet.adapters :as adapters]))

(def db
  (atom {:result nil
         :error nil
         :loading false}))

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

(defn wallet-deposit [{:keys [language]} {:keys [value]}]
  (swap! db assoc :error nil :loading true)
  (-> (http/request! {:path "wallet/deposit"
                      :method :post
                      :accept :json
                      :content-type :json
                      :body {:btc value}})
      (.then (fn [e]
               (swap! db update-in [:result :entries] conj (adapters/->wallet-entry (:body e) language))
               (swap! db assoc :loading false)))
      (.catch (fn [err]
                (swap! db assoc
                       :error err
                       :loading false)
                (js/console.error "request to deposit entries! catch: " (clj->js err))))))

(defn wallet-withdrawal [{:keys [language]} {:keys [value]}]
  (swap! db assoc :error nil :loading true)
  (-> (http/request! {:path "wallet/withdrawal"
                      :method :post
                      :accept :json
                      :content-type :json
                      :body {:btc value}})
      (.then (fn [e]
               (swap! db update-in [:result :entries] conj (adapters/->wallet-entry (:body e) language))
               (swap! db assoc :loading false)))
      (.catch (fn [err]
                (swap! db assoc
                       :error err
                       :loading false)
                (js/console.error "request to withdrawal entries! catch: " (clj->js err))))))
