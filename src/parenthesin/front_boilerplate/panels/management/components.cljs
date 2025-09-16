(ns parenthesin.front-boilerplate.panels.management.components
  (:require
   [parenthesin.front-boilerplate.adapters :refer [format-amount]]
   [uix.core :as uix :refer [$ defui]]
   [uix.dom]))

(defn ^:private modal-action-handler [e action-fn close-fn]
  (.preventDefault e)
  (action-fn)
  (close-fn))

(defui management-form [{:keys [btc-price buy-on-click sell-on-click on-change close-fn]}]
  (let [[btc-value set-btc-value] (uix/use-state 0)
        [usd-price set-usd-price] (uix/use-state 0)]
    ($ :form {:className "flex flex-col"
              :data-testid "management-form-component"}
       ($ :.flex
          ($ :label.input.m-2.w-full
             ($ :input {:type "number"
                        :className "grow"
                        :data-testid "management-form-btc-input"
                        :min "0"
                        :step "any"
                        :value btc-value
                        :onChange (fn [element]
                                    (let [btc-val (-> element .-target .-value)]
                                      (on-change)
                                      (set-btc-value btc-val)
                                      (set-usd-price (* btc-price btc-val))))})
             ($ :label "BTC")))
       ($ :.flex
          ($ :label.m-2.w-full.text-right
             ($ :label "This transaction will represent ")
             ($ :label.font-bold "₿ ")
             ($ :label (if (empty? (str btc-value)) "0" btc-value))
             ($ :label " ≈ ")
             ($ :label.font-bold "$ ")
             ($ :label (format-amount usd-price))))
       ($ :.flex.justify-end
          ($ :button {:className "btn btn-primary m-2"
                      :data-testid "management-form-buy-button"
                      :on-click (fn [e]
                                  (modal-action-handler e
                                                        #(buy-on-click {:value (js/parseFloat btc-value)
                                                                        :btc-price btc-price})
                                                        close-fn))}
             "Buy")
          ($ :button {:className "btn btn-secondary m-2"
                      :data-testid "management-form-sell-button"
                      :on-click (fn [e]
                                  (modal-action-handler e
                                                        #(sell-on-click {:value (js/parseFloat (* btc-value -1))
                                                                         :btc-price btc-price})
                                                        close-fn))}
             "Sell")))))
