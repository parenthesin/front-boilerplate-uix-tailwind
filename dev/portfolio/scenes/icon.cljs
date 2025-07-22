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

(defscene parenthesin-icon
  ($ components/parenthesin))

(defscene error-icon
  ($ components/error))
