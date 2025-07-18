(ns parenthesin.front-boilerplate.infra.system.state
  (:require [parenthesin.front-boilerplate.config :as config]
            [parenthesin.front-boilerplate.infra.http.component :as http]))

(def system
  (atom {:config config/config
         :http (http/new-http config/config)}))
