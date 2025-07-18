(ns parenthesin.front-boilerplate.test.aux.helpers
  (:require [cognitect.transit :as t]))

(defn ->tagged-decimal [value]
  (t/tagged-value "f" value))
