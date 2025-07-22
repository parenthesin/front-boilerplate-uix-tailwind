(ns parenthesin.front-boilerplate.panels.wallet.adapters)

(defn- format-amount [amount]
  (.toFixed amount 2))

(defn- ->wallet-entry [{:keys [id btc-amount usd-amount-at created-at]} language]
  {:id id
   :btc-amount btc-amount
   :usd-amount-at (format-amount usd-amount-at)
   :created-at (-> (new js/Date created-at) (.toLocaleString language))})

(defn ->wallet-entries [{:keys [entries total-btc total-current-usd]} language]
  (let [entries (->> entries
                     (map #(->wallet-entry % language))
                     (sort :created-at))]
    {:entries entries
     :total-btc total-btc
     :total-current-usd (format-amount total-current-usd)}))
