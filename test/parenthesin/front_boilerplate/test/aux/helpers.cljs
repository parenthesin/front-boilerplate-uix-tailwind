(ns parenthesin.front-boilerplate.test.aux.helpers
  (:require
   [parenthesin.front-boilerplate.test.aux.testing-library :as tl]))

;; this is a simple wrapper around testing-library's wait-for function
;; if you're using tl/wait-for directly, consider using this instead
;; or event just call wait-for one time.
;; after the component beign rendered, you can just call a simple
;; `.findByTestId` on the rendered component -- it should work
(defn wait-for [component {:keys [test-id]}]
  (tl/wait-for
   #(-> component
        (.findByTestId test-id))))
