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
       #_($ :.flex.flex-col.justify-center.items-center.h-screen
            ($ :h1.text-6xl.mb-4
               {:style {:transform (str "rotate(" (* n 15) "deg)")}}
               "👋")
            ($ :h2.text-3xl.font-semibold "Hello UIx")
            ($ :.p-6
               ($ button {:on-click #(set-n dec)} "-")
               ($ :span.mx-4.text-xl {} n)
               ($ button {:on-click #(set-n inc)} "+")))
       ($ app-wallet)
       ($ components/footer))))
