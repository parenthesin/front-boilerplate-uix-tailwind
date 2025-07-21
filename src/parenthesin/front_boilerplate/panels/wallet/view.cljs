(ns parenthesin.front-boilerplate.panels.wallet.view
  (:require [parenthesin.front-boilerplate.panels.wallet.components :refer [bottom-bar loading-spinner wallet-entries]]
            [parenthesin.front-boilerplate.panels.wallet.state :refer [get-wallet-history]]
            [uix.core :as uix :refer [$ defui]]
            [uix.dom]))

(defui app-wallet [_]
  (let [[loading set-loading] (uix/use-state true)
        [wallet-history set-wallet-history] (uix/use-state nil)]
    (get-wallet-history set-wallet-history set-loading)
    ($ :div {:data-testid "app-wallet-view"}
       ($ :h1.text-2xl.font-bold.mb-4.mt-6 "BTC Wallet")
       ($ :p.mb-4 "This is the history of your transactions.")
       (if loading
         ($ loading-spinner)
         ($ :<>
            ($ wallet-entries wallet-history)
            ($ bottom-bar {:on-click #(get-wallet-history set-wallet-history set-loading)
                           :wallet-history wallet-history}))))))
