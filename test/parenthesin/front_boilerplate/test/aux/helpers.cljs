(ns parenthesin.front-boilerplate.test.aux.helpers
  (:require [cognitect.transit :as t]
            [parenthesin.front-boilerplate.test.aux.testing-library :as tl]))

(defn ->tagged-decimal [value]
  (t/tagged-value "f" value))

(defn wait-for [component {:keys [test-id]}]
  (tl/wait-for
   #(-> component
        (.findByTestId test-id))))
