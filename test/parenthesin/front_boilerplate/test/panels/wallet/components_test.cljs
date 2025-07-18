(ns parenthesin.front-boilerplate.test.panels.wallet.components-test
  (:require [cljs.test :refer [async deftest is testing use-fixtures]]
            [matcher-combinators.test :refer [match?]]
            [parenthesin.front-boilerplate.panels.wallet.components :as components]
            [parenthesin.front-boilerplate.test.aux.fixtures.wallet :as fixtures.wallet]
            [parenthesin.front-boilerplate.test.aux.helpers :as helpers]
            [parenthesin.front-boilerplate.test.aux.testing-library :as tl]
            [promesa.core :as p]
            [uix.core :refer [$]]))

(use-fixtures :each
  {:after tl/async-cleanup})

(deftest wallet-entries-test
  (async done
         (p/catch
          (p/let [rendered-component (tl/render ($ components/wallet-entries fixtures.wallet/wallet-entry))
                  wallet-entries-component (helpers/wait-for rendered-component {:test-id "wallet-entries-component"})
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

(deftest refresh-button-test
  (async done
         (p/catch
          (p/let [clicked-state (atom false)
                  on-click-fn (fn [] (reset! clicked-state true))
                  rendered-component (tl/render ($ components/refresh-button {:on-click on-click-fn}))
                  refresh-button-component (helpers/wait-for rendered-component {:test-id "refresh-button-component"})
                  button-element (-> rendered-component (.getByText "Refresh"))]

            (testing "refresh button component should render with correct class for div"
              (is (match? "p-4"
                          (-> refresh-button-component
                              (aget "className")))))

            (testing "refresh button should render with correct classes"
              (is (match? "btn btn-primary" (-> button-element (aget "className")))))

            (testing "refresh button should call on-click function when clicked"
              (is (= false @clicked-state))

              (.click button-element)

              (is (= true @clicked-state)))

            (done))
          (fn [err] (is (= nil err))
            (done)))))

(deftest total-values-test
  (async done
         (p/catch
          (p/let [rendered-component (tl/render ($ components/total-values fixtures.wallet/wallet-entry))
                  total-values-component (helpers/wait-for rendered-component {:test-id "total-values-component"})
                  total-values-item (-> rendered-component (.getByText "Total Values: BTC 1| US$ 30000"))]

            (testing "total values component should render with correct class for div"
              (is (match? "p-4"
                          (-> total-values-component
                              (aget "className")))))

            (testing "total values component should render with correct values"
              (is (match? "Total Values: BTC 1| US$ 30000" (-> total-values-item (.-textContent)))))

            (done))
          (fn [err]
            (is (= nil err))
            (done)))))

(deftest bottom-bar-test
  (async done
         (p/catch
          (p/let [clicked-state (atom false)
                  on-click-fn (fn [] (reset! clicked-state true))
                  rendered-component (tl/render ($ components/bottom-bar
                                                   {:on-click on-click-fn
                                                    :wallet-history fixtures.wallet/wallet-entry}))
                  bottom-bar-component (helpers/wait-for rendered-component {:test-id "bottom-bar-component"})
                  refresh-button-component (-> rendered-component (.getByText "Refresh"))
                  total-values-item (-> rendered-component (.getByText "Total Values: BTC 1| US$ 30000"))]

            (testing "bottom bar component should render with correct class for flex container"
              (is (match? "flex justify-between"
                          (-> bottom-bar-component
                              (aget "className")))))

            (testing "refresh button should render with correct classes"
              (is (match? "btn btn-primary" (-> refresh-button-component (aget "className")))))

            (testing "total values should render with correct values"
              (is (= "Total Values: BTC 1| US$ 30000" (-> total-values-item (.-textContent)))))

            (testing "refresh button should call on-click function when clicked"
              (is (= false @clicked-state))

              (.click refresh-button-component)

              (is (= true @clicked-state)))

            (done))
          (fn [err] (is (= nil err))
            (done)))))
