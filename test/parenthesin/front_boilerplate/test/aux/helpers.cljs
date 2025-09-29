(ns parenthesin.front-boilerplate.test.aux.helpers
  (:require
   [parenthesin.front-boilerplate.test.aux.testing-library :as tl]
   [promesa.core :as p]))

(defn wait-for
  "This is a simple wrapper around testing-library's wait-for function.
   If you're using tl/wait-for directly, consider using this instead
   or even just call wait-for one time.
   After the component being rendered, you can just call a simple
   `.findByTestId` on the rendered component -- it should work."
  [component {:keys [test-id]}]
  (tl/wait-for
   #(-> component
        (.findByTestId test-id))))

(defn perform
  [{:keys [click save]
    {:keys [input value]} :input}]
  (p/do
    (tl/click click)
    (tl/change input value)
    (tl/click save)))
