(ns portfolio.scenes.management
  (:require
   [parenthesin.front-boilerplate.panels.management.components :as components]
   [portfolio.react-18 :refer-macros [defscene]]
   [uix.core :refer [$]]))

(defscene management-form-without-btc-price
  ($ components/management-form))

(defscene management-form-with-btc-price
  ($ components/management-form {:btc-price 30000}))
