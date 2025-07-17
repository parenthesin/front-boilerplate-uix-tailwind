(ns parenthesin.front-boilerplate.panels.shell.view
  (:require [parenthesin.front-boilerplate.panels.shell.components :as components]
            [parenthesin.front-boilerplate.panels.wallet.view :refer [app-wallet]]
            [uix.core :as uix :refer [$ defui]]
            [uix.dom]))

(defui button [props]
  ($ :button.btn.btn-circle.w-10.h-10
     props))

(defui app-shell [{:keys [_]}]
  (let [[n set-n] (uix/use-state 0)]
    ($ :div.container.lg:mx-auto.lg:max-w-screen-lg.px-4.max-w-screen-sm.flex.flex-col.min-h-screen
       ($ components/navbar)
       ($ app-wallet)
       ($ components/footer))))
