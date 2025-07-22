(ns parenthesin.front-boilerplate.infra.system.state
  (:require
   [parenthesin.front-boilerplate.config :as config]
   [parenthesin.front-boilerplate.infra.http.component :as http]
   [parenthesin.front-boilerplate.infra.preferences :as preferences]))

(def system
  (atom {:config config/config
         :http (http/new-http config/config)
         :preferences (preferences/get-stored-preferences)}))

(add-watch system :sync-local-storage
           (fn [_key system-atom _old-state _new-state]
             (let [root (.-documentElement js/document)
                   dark-mode (get-in @system-atom [:preferences :dark-mode] true)]
               (preferences/set-stored-preferences {:dark-mode dark-mode})
               (if dark-mode
                 (.setAttribute root "data-theme" "dark")
                 (.setAttribute root "data-theme" "light")))))
