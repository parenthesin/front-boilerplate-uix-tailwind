(ns parenthesin.front-boilerplate.panels.wallet.view
  (:require [parenthesin.front-boilerplate.components.alert :as components.alert]
            [parenthesin.front-boilerplate.infra.system.state :as system.state]
            [parenthesin.front-boilerplate.panels.wallet.components :refer [bottom-bar loading-spinner wallet-entries]]
            [parenthesin.front-boilerplate.panels.wallet.state :refer [db get-wallet-history]]
            [uix.core :as uix :refer [$ defui]]
            [uix.dom]))

(defui app-wallet [_]
  (let [{:keys [config]} (uix/use-atom system.state/system)
        {:keys [result error loading]} (uix/use-atom db)]
    (uix/use-effect
     #(get-wallet-history config)
     [config])
    ($ :div {:className "px-6 py-10"
             :data-testid "app-wallet-view"}
       (if error
         ($ components.alert/alert-error {:info error})
         ($ :<>
            ($ :h1.text-2xl.font-bold.mb-4 "BTC Wallet")
            ($ :p.mb-4 "This is the history of your transactions.")
            (if loading
              ($ loading-spinner)
              ($ :<>
                 ($ wallet-entries result)
                 ($ bottom-bar {:on-click #(get-wallet-history config)
                                :wallet-history result}))))))))
