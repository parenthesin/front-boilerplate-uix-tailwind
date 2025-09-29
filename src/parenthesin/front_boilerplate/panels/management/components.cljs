(ns parenthesin.front-boilerplate.panels.management.components
  (:require
   [parenthesin.front-boilerplate.adapters :refer [format-amount]]
   [uix.core :as uix :refer [$ defui]]
   [uix.dom]))

(defui management-form [{:keys [balance btc-value buy-on-click sell-on-click on-change usd-price]}]
  (let [has-balance? (> btc-value balance)]
    ($ :form {:className "flex flex-col"
              :data-testid "management-form-component"}
       ($ :fieldset
          ($ :.flex
             ($ :label.input.m-2.w-full
                ($ :input {:type "number"
                           :className "grow"
                           :data-testid "management-form-btc-input"
                           :min "0"
                           :step "any"
                           :value btc-value
                           :onChange on-change})
                ($ :label "BTC")))

          ($ :p {:className "label mx-2 w-full text-xs text-error"
                 :data-testid "management-form-error"}
             (when has-balance? (str "Your total balance is ₿ " balance))))
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
                      :on-click buy-on-click}
             "Buy")
          ($ :button {:className "btn btn-secondary m-2"
                      :data-testid "management-form-sell-button"
                      :disabled has-balance?
                      :on-click sell-on-click}
             "Sell")))))
