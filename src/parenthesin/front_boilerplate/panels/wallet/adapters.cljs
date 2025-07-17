(ns parenthesin.front-boilerplate.panels.wallet.adapters)

(defn- ->wallet-entry [{:keys [id btc-amount usd-amount-at created-at]}]
  {:id id
   :btc-amount (.-rep ^js btc-amount)
   :usd-amount-at (.-rep ^js usd-amount-at)
   :created-at (->> created-at (new js/Date) .toDateString)})

(defn ->wallet-entries [{:keys [entries total-btc total-current-usd]}]
  (let [entries (map ->wallet-entry entries)]
    {:entries entries
     :total-btc (.-rep ^js total-btc)
     :total-current-usd (.-rep ^js total-current-usd)}))
