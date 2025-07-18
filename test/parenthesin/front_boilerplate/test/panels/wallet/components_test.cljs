(ns parenthesin.front-boilerplate.test.panels.wallet.components-test
  (:require [cljs.test :refer [async deftest is testing use-fixtures]]
            [parenthesin.front-boilerplate.panels.wallet.components :as components]
            [parenthesin.front-boilerplate.test.aux.testing-library :as tl]
            [parenthesin.front-boilerplate.test.aux.fixtures.wallet :as fixtures.wallet]
            [matcher-combinators.test :refer [match?]]
            [promesa.core :as p]
            [uix.core :refer [$]]))

(use-fixtures :each
  {:after tl/async-cleanup})

(deftest wallet-entries-test
  (async done
         (p/catch
          (p/let [rendered-component (tl/render ($ components/wallet-entries fixtures.wallet/wallet-entry))
                  wallet-entries-component (tl/wait-for
                                            #(-> rendered-component
                                                 (.findByTestId "wallet-entries-component")))
                  btc-amount-item (-> rendered-component (.getByText "1 BTC"))
                  usd-amount-at-item (-> rendered-component (.getByText "US$ 30000"))
                  created-at-item (-> rendered-component (.getByText "Wed Jan 01 2025"))]

            (testing "wallet entries component should render with correct classes"
              (is (match? "overflow-x-auto rounded-box border border-base-content bg-base-100"
                          (-> wallet-entries-component
                              (aget "className")))))

            (testing "wallet entries component should render table content with correct values"
              (is (match? "1 BTC" (-> btc-amount-item (.-textContent))))
              (is (match? "US$ 30000" (-> usd-amount-at-item (.-textContent))))
              (is (match? "Wed Jan 01 2025" (-> created-at-item (.-textContent)))))

            (done))
          (fn [err] (is (= nil err))
            (done)))))
