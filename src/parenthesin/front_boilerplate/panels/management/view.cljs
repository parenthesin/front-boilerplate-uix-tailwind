(ns parenthesin.front-boilerplate.panels.management.view
  (:require
   [parenthesin.front-boilerplate.components.alert :as components.alert]
   [parenthesin.front-boilerplate.infra.system.state :as system.state]
   [parenthesin.front-boilerplate.panels.management.components :refer [management-form]]
   [parenthesin.front-boilerplate.panels.management.state :refer [db
                                                                  get-current-btc-usd]]
   [parenthesin.front-boilerplate.panels.wallet.components :refer [loading-spinner]]
   [uix.core :as uix :refer [$ defui]]
   [uix.dom]))

(defui app-management [{:keys [_]}]
  (let [{:keys [config]} (uix/use-atom system.state/system)
        {:keys [result error loading]} (uix/use-atom db)]
    (uix/use-effect
     #(get-current-btc-usd config)
     [config])

    ($ :dialog {:id "management-modal"
                :className "modal"}
       (if error
         ($ components.alert/alert-error {:info error})
         (if loading
           ($ loading-spinner)
           ($ :.modal-box
              ($ :h3 "Management")
              ($ management-form {:btc-price (:usd-amount result)
                                  :buy-on-click #(js/alert "aaa")
                                  :sell-on-click #(js/alert "aaa")}))))
       ($ :form {:method "dialog"
                 :className "modal-backdrop"}
          ($ :button "close")))))
