(ns portfolio.scenes.shell
  (:require [parenthesin.front-boilerplate.panels.shell.components :as components]
            [portfolio.react-18 :refer-macros [defscene]]
            [uix.core :refer [$]]))

(defscene shell-navbar-with-dark-mode
  ($ components/navbar {:dark-mode true}))

(defscene shell-navbar-with-light-mode
  ($ components/navbar {:dark-mode false}))

(defscene shell-content
  ($ components/content
     ($ :div "This is the content of the shell scene! :monza:")))

(defscene shell-footer-with-dark-mode
  ($ components/footer {:dark-mode true}))
