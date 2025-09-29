(ns portfolio.scenes.icon
  (:require
   [parenthesin.front-boilerplate.components.icon :as components]
   [portfolio.react-18 :refer-macros [defscene]]
   [uix.core :refer [$]]))

(defscene sun-icon
  ($ components/sun))

(defscene moon-icon
  ($ components/moon))

(defscene magnifying-glass
  ($ components/magnifying-glass))

(defscene parenthesin-icon-dark-mode
  ($ components/parenthesin {:dark-mode true}))

(defscene parenthesin-icon-light-mode
  ($ components/parenthesin {:dark-mode false}))

(defscene error-icon
  ($ components/error))
