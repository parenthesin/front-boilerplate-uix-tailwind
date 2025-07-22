(ns parenthesin.front-boilerplate.test.aux.init
  (:require
   [parenthesin.front-boilerplate.infra.http.component :as http.component]
   [parenthesin.front-boilerplate.infra.system.state :as system.state]
   [parenthesin.front-boilerplate.test.aux.testing-library :as tlr]))

#_{:clj-kondo/ignore [:unresolved-var]}
(defn async-cleanup []
  (tlr/cleanup))

#_{:clj-kondo/ignore [:unresolved-var]}
(defn sync-setup [f]
  (f)
  (tlr/cleanup))

#_{:clj-kondo/ignore [:unresolved-var]}
(defn mock-http-with [mocked-responses]
  (swap! system.state/system
         assoc :http (http.component/new-http-mock mocked-responses)))

#_{:clj-kondo/ignore [:unresolved-var]}
(defn get-mock-http-requests []
  @(-> @system.state/system :http :requests))
