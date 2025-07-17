(ns parenthesin.front-boilerplate.panels.wallet.view
  (:require [parenthesin.front-boilerplate.panels.wallet.components :refer [bottom-bar wallet-entries]]
            [parenthesin.front-boilerplate.panels.wallet.state :refer [get-wallet-history]]
            [uix.core :as uix :refer [$ defui]]
            [uix.dom]))

(defui app-wallet [_]
  (let [[wallet-history set-wallet-history] (uix/use-state nil)]
    (get-wallet-history set-wallet-history)
    ($ :div
       ($ :h1.text-2xl.font-bold.mb-4 "BTC Wallet")
       ($ :p.mb-4 "This is the history of your transactions.")
       ($ wallet-entries wallet-history)
       ($ bottom-bar {:on-click #(get-wallet-history set-wallet-history)
                      :wallet-history wallet-history}))))
