(ns parenthesin.front-boilerplate.panels.shell.view
  (:require [parenthesin.front-boilerplate.infra.system.state :as system.state]
            [parenthesin.front-boilerplate.panels.shell.components :as components]
            [parenthesin.front-boilerplate.panels.wallet.view :refer [app-wallet]]
            [uix.core :as uix :refer [$ defui]]
            [uix.dom]))

(defui app-shell [{:keys [_]}]
  (let [{{:keys [dark-mode]} :preferences} (uix/use-atom system.state/system)]
    ($ :div.container.lg:mx-auto.lg:max-w-screen-lg.px-4.max-w-screen-sm.flex.flex-col.min-h-screen
       ($ components/navbar {:dark-mode dark-mode
                             :set-dark-mode (fn [dark-mode] 
                                              (swap! system.state/system assoc-in [:preferences :dark-mode] dark-mode))})
       ($ components/content ($ app-wallet))
       ($ components/footer {:dark-mode dark-mode}))))
