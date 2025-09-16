(ns parenthesin.front-boilerplate.panels.management.view
  (:require
   [parenthesin.front-boilerplate.components.alert :as components.alert]
   [parenthesin.front-boilerplate.panels.management.components :refer [management-form]]
   [parenthesin.front-boilerplate.panels.management.state :refer [db
                                                                  get-current-btc-usd]]
   [uix.core :as uix :refer [$ defui]]
   [uix.dom]))

(defui app-management [{:keys [buy-on-click sell-on-click]}]
  (let [{:keys [result error]} (uix/use-atom db)]
    (uix/use-effect
     #(get-current-btc-usd)
     [])

    ($ :dialog {:id "management-modal"
                :className "modal"}
       (if error
         ($ components.alert/alert-error {:info error})
         ($ :.modal-box
            ($ :h3 "Management")
            ($ management-form {:btc-price (:usd-amount result)
                                :buy-on-click buy-on-click
                                :sell-on-click sell-on-click
                                :on-change get-current-btc-usd
                                :close-fn #(.close (js/document.getElementById "management-modal"))})))
       ($ :form {:method "dialog"
                 :className "modal-backdrop"}
          ($ :button "close")))))
