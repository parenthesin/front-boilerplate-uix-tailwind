(ns parenthesin.front-boilerplate.test.adapters-test
  (:require
   [cljs.test :refer [deftest is testing]]
   [matcher-combinators.test :refer [match?]]
   [parenthesin.front-boilerplate.adapters :as adapters]))

(deftest format-amount-test
  (testing "Normal data convertion"
    (let [result (adapters/format-amount 123.456789)]
      (is (match? "123.46" result))))

  (testing "Rounding values"
    (let [result (adapters/format-amount 123.451)]
      (is (match? "123.45" result)))))

(deftest event->value-test
  (testing "Normal event"
    (let [event #js {:target #js {:value "test-value"}}
          result (adapters/event->value event)]
      (is (match? "test-value" result))))

  (testing "Empty event"
    (let [event #js {}
          result (adapters/event->value event)]
      (is (match? nil result)))))
