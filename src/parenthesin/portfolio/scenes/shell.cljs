(ns parenthesin.portfolio.scenes.shell
  (:require [parenthesin.front-boilerplate.panels.shell.components :as components]
            [portfolio.react-18 :refer-macros [defscene]]
            [uix.core :as uix :refer [$ defui]]))

(defui counter []
  (let [[count set-count] (uix/use-state 0)]
    ($ :div
       ($ :p "Count: " count)
       ($ :button.btn.btn-warning {:on-click #(set-count inc)} "Increase"))))

(defscene helix-counter
  :title "Counter with React Hooks"
  ($ counter))

(defscene shell-navbar
  ($ components/navbar))

;
; (defscene component-boolean-true
;   (d/div (d/h1 "helix-jsdom")
;          ($ c/component-boolean {:value true})))
;
; (defscene component-boolean-false
;   ($ c/component-boolean {:value false}))
