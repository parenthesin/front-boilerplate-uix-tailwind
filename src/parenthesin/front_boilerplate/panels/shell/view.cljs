(ns parenthesin.front-boilerplate.panels.shell.view
  (:require [parenthesin.front-boilerplate.infra.preferences :as preferences]
            [parenthesin.front-boilerplate.infra.system.state :as system.state]
            [parenthesin.front-boilerplate.panels.shell.components :as components]
            [parenthesin.front-boilerplate.panels.wallet.view :refer [app-wallet]]
            [uix.core :as uix :refer [$ defui]]
            [uix.dom]))

(defui app-shell [{:keys [_]}]
  (let [root (.-documentElement js/document)
        [dark-mode set-dark-mode] (uix/use-state (-> @system.state/system :preferences :dark-mode))]
    (uix/use-effect
     (fn []
       (preferences/set-stored-preferences {:dark-mode dark-mode})
       (if dark-mode
         (.setAttribute root "data-theme" "dark")
         (.setAttribute root "data-theme" "light")))
     [dark-mode root])
    ($ :div.container.lg:mx-auto.lg:max-w-screen-lg.px-4.max-w-screen-sm.flex.flex-col.min-h-screen
       ($ components/navbar {:dark-mode dark-mode
                             :set-dark-mode set-dark-mode})
       ($ components/content ($ app-wallet))
       ($ components/footer {:dark-mode dark-mode}))))
