(ns parenthesin.front-boilerplate.panels.management.adapters)

;; TODO unit test
(defn ->action-values [btc-value btc-price]
  {:value (js/parseFloat btc-value)
   :btc-price btc-price})
