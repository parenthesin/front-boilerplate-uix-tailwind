(ns portfolio.scenes.shell
  (:require [parenthesin.front-boilerplate.panels.shell.components :as components]
            [portfolio.react-18 :refer-macros [defscene]]
            [uix.core :as uix :refer [$ defui]]))

(defui counter []
  (let [[count set-count] (uix/use-state 0)]
    ($ :div
       ($ :p "Count: " count)
       ($ :button.btn.btn-warning {:on-click #(set-count inc)} "Increase"))))

(defscene uix-counter
  :title "Counter with React Hooks"
  ($ counter))

(defscene shell-navbar-with-dark-mode
  ($ components/navbar {:dark-mode true}))

(defscene shell-navbar-with-light-mode
  ($ components/navbar {:dark-mode false}))

(defscene shell-footer-with-dark-mode
  ($ components/footer {:dark-mode true}))
