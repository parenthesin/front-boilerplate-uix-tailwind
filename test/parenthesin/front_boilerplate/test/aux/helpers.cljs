(ns parenthesin.front-boilerplate.test.aux.helpers
  (:require
   [parenthesin.front-boilerplate.test.aux.testing-library :as tl]))

(defn wait-for [component {:keys [test-id]}]
  (tl/wait-for
   #(-> component
        (.findByTestId test-id))))
