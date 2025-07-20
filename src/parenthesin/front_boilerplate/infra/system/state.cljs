(ns parenthesin.front-boilerplate.infra.system.state
  (:require [parenthesin.front-boilerplate.config :as config]
            [parenthesin.front-boilerplate.infra.http.component :as http]
            [parenthesin.front-boilerplate.infra.preferences :as preferences]))

(def system
  (atom {:config config/config
         :http (http/new-http config/config)
         :preferences (preferences/get-stored-preferences)}))
