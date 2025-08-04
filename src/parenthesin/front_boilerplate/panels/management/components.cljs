(ns parenthesin.front-boilerplate.panels.management.components
  (:require
   [parenthesin.front-boilerplate.adapters :refer [format-amount]]
   [uix.core :as uix :refer [$ defui]]
   [uix.dom]))

(defui management-form [{:keys [btc-price]}]
  (let [[btc-value set-btc-value] (uix/use-state 0)
        [usd-price set-usd-price] (uix/use-state 0)]
    ($ :form.flex.flex-col
       ($ :.flex
          ($ :label.input.m-2.w-full
             ($ :input {:type "number"
                        :className "grow"
                        :min "0"
                        :step "any"
                        :value btc-value
                        :onChange (fn [element]
                                    (let [btc-val (-> element .-target .-value)]
                                      (set-btc-value btc-val)
                                      (set-usd-price (* btc-price btc-val))))})
             ($ :label "BTC")))
       ($ :.flex
          ($ :label.m-2.w-full.text-right
             ($ :label "This transaction will represent ")
             ($ :label.font-bold "₿ ")
             ($ :label (if (empty? (str btc-value)) "0" btc-value))
             ($ :label " ~= ")
             ($ :label.font-bold "$ ")
             ($ :label (format-amount usd-price))))
       ($ :.flex.justify-end
          ($ :button {:className "btn btn-primary m-2"}
             "Buy")
          ($ :button {:className "btn btn-secondary m-2"}
             "Sell")))))
