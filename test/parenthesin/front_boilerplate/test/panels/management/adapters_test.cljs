(ns parenthesin.front-boilerplate.test.panels.management.adapters-test
  (:require
   [cljs.test :refer [deftest is testing]]
   [matcher-combinators.test :refer [match?]]
   [parenthesin.front-boilerplate.panels.management.adapters :as management.adapters]))

(deftest ->action-values-test
  (testing "Normal data convertion"
    (let [result (management.adapters/->action-values "1.5" 30000M)]
      (is (match? {:value 1.5
                   :btc-price 30000M}
                  result)))))
