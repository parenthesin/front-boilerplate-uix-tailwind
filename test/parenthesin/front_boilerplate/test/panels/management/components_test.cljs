(ns parenthesin.front-boilerplate.test.panels.management.components-test
  (:require
   [cljs.test :refer [async deftest is testing use-fixtures]]
   [matcher-combinators.test :refer [match?]]
   [parenthesin.front-boilerplate.panels.management.components :as components]
   [parenthesin.front-boilerplate.test.aux.helpers :as helpers]
   [parenthesin.front-boilerplate.test.aux.testing-library :as tl]
   [promesa.core :as p]
   [uix.core :refer [$]]))

(use-fixtures :each
  {:after tl/async-cleanup})

(def management-form-props
  {:balance 10M
   :btc-value 1
   :buy-on-click (fn [])
   :sell-on-click (fn [])
   :on-change (fn [])
   :usd-price 30000})

(deftest management-form-test
  (async done
         (p/catch
          (p/let [rendered-component (tl/render ($ components/management-form management-form-props))
                  management-form-component (helpers/wait-for rendered-component {:test-id "management-form-component"})
                  btc-input (helpers/wait-for rendered-component {:test-id "management-form-btc-input"})
                  buy-button (helpers/wait-for rendered-component {:test-id "management-form-buy-button"})
                  sell-button (helpers/wait-for rendered-component {:test-id "management-form-sell-button"})]

            (testing "management form component should render with correct classes"
              (is (match? "flex flex-col"
                          (aget management-form-component "className"))))

            (testing "btc input should render with correct classes and attributes"
              (is (match? "grow" (aget btc-input "className")))
              (is (match? "number" (aget btc-input "type")))
              (is (match? "0" (aget btc-input "min")))
              (is (match? "any" (aget btc-input "step"))))

            (testing "buy button should render with correct classes and text"
              (is (match? "btn btn-primary m-2" (aget buy-button "className")))
              (is (match? "Buy" (.-textContent buy-button))))

            (testing "sell button should render with correct classes and text"
              (is (match? "btn btn-secondary m-2" (aget sell-button "className")))
              (is (match? "Sell" (.-textContent sell-button))))

            (done))
          (fn [err] (is (= nil err))
            (done)))))
