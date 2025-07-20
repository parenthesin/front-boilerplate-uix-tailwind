(ns parenthesin.front-boilerplate.panels.wallet.adapters)

(defn- format-amount [amount]
  (.toFixed amount 2))

(defn- ->wallet-entry [{:keys [id btc-amount usd-amount-at created-at]}]
  {:id id
   :btc-amount btc-amount
   :usd-amount-at (format-amount usd-amount-at)
   :created-at (->> created-at (new js/Date) .toDateString)})

(defn ->wallet-entries [{:keys [entries total-btc total-current-usd]}]
  (let [entries (map ->wallet-entry entries)]
    {:entries entries
     :total-btc total-btc
     :total-current-usd (format-amount total-current-usd)}))
