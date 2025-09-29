(ns parenthesin.front-boilerplate.panels.management.view
  (:require
   [parenthesin.front-boilerplate.adapters :as adapters]
   [parenthesin.front-boilerplate.components.alert :as components.alert]
   [parenthesin.front-boilerplate.panels.management.adapters :as management.adapters]
   [parenthesin.front-boilerplate.panels.management.components :refer [management-form]]
   [parenthesin.front-boilerplate.panels.management.state :refer [db
                                                                  get-current-btc-usd]]
   [uix.core :as uix :refer [$ defui]]
   [uix.dom]))

(defn ^:private modal-action-handler [e btc-price btc-value action-fn close-fn]
  (.preventDefault e)
  (action-fn (management.adapters/->action-values btc-value btc-price))
  (close-fn))

(defn ^:private update-btc
  [btc-val btc-price on-change set-btc-value set-usd-price]
  (on-change)
  (set-btc-value btc-val)
  (set-usd-price (* btc-price btc-val)))

(defui management-modal [{:keys [balance btc-price buy-on-click sell-on-click on-change close-fn]}]
  (let [[btc-value set-btc-value] (uix/use-state 0)
        [usd-price set-usd-price] (uix/use-state 0)]
    ($ :.modal-box
       ($ :h3.m-2 "Management")
       ($ management-form {:balance balance
                           :btc-value btc-value
                           :usd-price usd-price
                           :on-change (fn [e] (update-btc (adapters/event->value e) btc-price on-change set-btc-value set-usd-price))
                           :buy-on-click (fn [e] (modal-action-handler e btc-price btc-value buy-on-click close-fn))
                           :sell-on-click (fn [e] (modal-action-handler e btc-price btc-value sell-on-click close-fn))}))))

(defui app-management [{:keys [balance buy-on-click sell-on-click]}]
  (let [{:keys [result error]} (uix/use-atom db)]
    (uix/use-effect
     #(get-current-btc-usd)
     [])

    ($ :dialog {:id "management-modal"
                :className "modal"}
       (if error
         ($ components.alert/alert-error {:info error})
         ($ management-modal {:balance balance
                              :btc-price (:usd-amount result)
                              :buy-on-click buy-on-click
                              :sell-on-click sell-on-click
                              :on-change get-current-btc-usd
                              :close-fn #(.close (js/document.getElementById "management-modal"))}))
       ($ :form {:method "dialog"
                 :className "modal-backdrop"}
          ($ :button "close")))))
