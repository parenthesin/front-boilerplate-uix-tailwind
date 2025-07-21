(ns parenthesin.front-boilerplate.test.panels.wallet.view-test
  (:require [cljs.test :refer [async deftest is testing use-fixtures]]
            [clojure.string :as str]
            [matcher-combinators.test :refer [match?]]
            [parenthesin.front-boilerplate.panels.wallet.view :as view]
            [parenthesin.front-boilerplate.test.aux.fixtures.wallet :as fixtures.wallet]
            [parenthesin.front-boilerplate.test.aux.helpers :as helpers]
            [parenthesin.front-boilerplate.test.aux.init :refer [mock-http-with]]
            [parenthesin.front-boilerplate.test.aux.testing-library :as tl]
            [promesa.core :as p]
            [uix.core :refer [$]]))

(use-fixtures :each
  {:after tl/async-cleanup})

(deftest app-wallet-test
  (mock-http-with {"wallet/history"
                   {:lag 0
                    :status 200
                    :body fixtures.wallet/wallet-entry}})

  (async done
         (p/catch
          (p/let [rendered-view (tl/render ($ view/app-wallet nil))
                  title-element (-> rendered-view (.getByText "BTC Wallet"))
                  description-element (-> rendered-view (.getByText "This is the history of your transactions."))
                  wallet-entries-component (helpers/wait-for rendered-view {:test-id "wallet-entries-component"})
                  refresh-button (helpers/wait-for rendered-view {:test-id "refresh-button-component"})
                  total-values (helpers/wait-for rendered-view {:test-id "total-values-component"})
                  refresh-button-component (-> refresh-button (.querySelector "button"))
                  table-element (-> wallet-entries-component (.querySelector "table"))
                  table-headers (-> table-element (.querySelectorAll "th"))
                  table-rows (-> table-element (.querySelectorAll "tbody tr"))
                  total-span (-> total-values (.querySelector "span"))]

            (testing "app wallet view should render with correct structure"
              (is (match? "text-2xl font-bold mb-4" (-> title-element (aget "className"))))
              (is (match? "mb-4" (-> description-element (aget "className"))))
              (is (match? "BTC Wallet" (-> title-element (.-textContent))))
              (is (match? "This is the history of your transactions." (-> description-element (.-textContent)))))

            (testing "wallet entries table should render correctly"
              (is (match? "table" (-> table-element (aget "className"))))
              (is (match? 3 (-> table-headers (aget "length"))))
              (is (match? "BTC Amount" (-> table-headers (aget 0) (.-textContent))))
              (is (match? "USD Amount At" (-> table-headers (aget 1) (.-textContent))))
              (is (match? "Created At" (-> table-headers (aget 2) (.-textContent))))
              (is (< 0 (-> table-rows (aget "length")))))

            (testing "table data should match fixture data"
              (let [first-row (aget table-rows 0)
                    row-cells (-> first-row (.querySelectorAll "td"))]
                (is (str/includes? (-> row-cells (aget 0) (.-textContent)) "BTC"))
                (is (str/includes? (-> row-cells (aget 1) (.-textContent)) "US$"))
                (is (str/includes? (-> row-cells (aget 2) (.-textContent)) "Wed Jan 01 2025"))))

            (testing "bottom bar components should render correctly"
              (is (match? "btn btn-primary" (-> refresh-button-component (aget "className"))))
              (is (match? "Refresh" (-> refresh-button-component (.-textContent))))
              (is (match? "text-lg font-bold" (-> total-span (aget "className"))))
              (is (str/includes? (-> total-span (.-textContent)) "Total Values:"))
              (is (str/includes? (-> total-span (.-textContent)) "BTC"))
              (is (str/includes? (-> total-span (.-textContent)) "US$")))

            (done))
          (fn [err]
            (is (= nil err))
            (done)))))
