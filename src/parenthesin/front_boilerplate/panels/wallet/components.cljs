(ns parenthesin.front-boilerplate.panels.wallet.components
  (:require [uix.core :as uix :refer [defui $]]
            [uix.dom]))

(defui wallet-entries [{:keys [entries]}]
  ($ :.overflow-x-auto.rounded-box.border.border-base-content.bg-base-100
     ($ :table.table
        ($ :thead
           ($ :tr
              ($ :th "ID")
              ($ :th "BTC Amount")
              ($ :th "USD Amount At")
              ($ :th "Created At")))
        ($ :tbody
           (for [{:keys [id btc-amount usd-amount-at created-at]} entries]
             ($ :tr {:key id}
                ($ :td (str id))
                ($ :td (str btc-amount " BTC"))
                ($ :td (str "US$ " usd-amount-at))
                ($ :td (str created-at))))))))
