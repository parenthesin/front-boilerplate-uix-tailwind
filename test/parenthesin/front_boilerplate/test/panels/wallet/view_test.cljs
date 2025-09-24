(ns parenthesin.front-boilerplate.test.panels.wallet.view-test
  (:require
   [cljs.test :refer [async deftest is testing use-fixtures]]
   [clojure.string :as str]
   [matcher-combinators.matchers :as matchers]
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
  (mock-http-with {"wallet/current-btc-usd"
                   {:lag 0
                    :status 200
                    :body {:btc-amount 1
                           :usd-amount 30000M}}

                   "wallet/deposit"
                   {:lag 0
                    :status 201
                    :body {:id #uuid "3fa85f64-5717-4562-b3fc-2c963f66afa6"
                           :btc-amount 1
                           :usd-amount-at 30000M
                           :created-at "2025-09-15T19:36:25.930Z"}}

                   "wallet/history"
                   {:lag 0
                    :status 200
                    :body fixtures.wallet/unparsed-wallet-entry}

                   "wallet/withdrawal"
                   {:lag 0
                    :status 201
                    :body {:id #uuid "1b2e6b86-a7fd-4556-b379-118f7dbfb742"
                           :btc-amount -1
                           :usd-amount-at 30000M
                           :created-at "2025-09-15T20:36:25.930Z"}}})

  (async done
         (p/catch
          (p/let [rendered-view (tl/wait-for #(tl/render ($ view/app-wallet nil)))
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
              (is (match? "text-2xl font-bold mb-4" (aget title-element "className")))
              (is (match? "mb-4" (aget description-element "className")))
              (is (match? "BTC Wallet" (.-textContent title-element)))
              (is (match? "This is the history of your transactions." (.-textContent description-element))))

            (testing "wallet entries table should render correctly"
              (is (match? "table" (aget table-element "className")))
              (is (match? 4 (aget table-headers "length")))
              (is (match? "BTC Amount" (-> table-headers (aget 0) (.-textContent))))
              (is (match? "USD Amount At" (-> table-headers (aget 1) (.-textContent))))
              (is (match? "Created At" (-> table-headers (aget 2) (.-textContent))))
              (is (< 0 (aget table-rows "length"))))

            (testing "table data should match fixture data"
              (let [first-row (aget table-rows 0)
                    row-cells (-> first-row (.querySelectorAll "td"))]
                (is (str/includes? (-> row-cells (aget 0) (.-textContent)) "BTC"))
                (is (str/includes? (-> row-cells (aget 1) (.-textContent)) "US$"))
                (is (str/includes? (-> row-cells (aget 2) (.-textContent)) "1/1/2025, 9:00:00 AM"))))

            (testing "bottom bar components should render correctly"
              (is (match? "btn btn-primary join-item" (aget refresh-button-component "className")))
              (is (match? "Refresh" (.-textContent refresh-button-component)))
              (is (match? "text-lg font-bold" (aget total-span "className")))
              (is (str/includes? (.-textContent total-span) "Total Values:"))
              (is (str/includes? (.-textContent total-span) "BTC"))
              (is (str/includes? (.-textContent total-span) "US$")))

            (testing "table data should update when buying"
              (p/let [management-button (helpers/wait-for rendered-view {:test-id "management-button-component"})
                      btc-input (helpers/wait-for rendered-view {:test-id "management-form-btc-input"})
                      buy-button (helpers/wait-for rendered-view {:test-id "management-form-buy-button"})
                      ;; here we're emulating a click, input, and saving operations
                      ;; as they're async operations we've to add a simple `tl/wait-for`
                      ;; to properly wait for these operations to finish
                      _perform (tl/wait-for #(helpers/perform {:click management-button
                                                               :input {:input btc-input :value 1}
                                                               :save buy-button}))
                      wallet-entries-component (helpers/wait-for rendered-view {:test-id "wallet-entries-component"})
                      wallet-entries (-> wallet-entries-component (.querySelector "table") (.querySelectorAll "tbody tr"))
                      total-values (helpers/wait-for rendered-view {:test-id "total-values-component"})
                      total-span (-> total-values (.querySelector "span"))]

                (is (match? (matchers/in-any-order ["a541ea75-fe82-4416-9e5c-8f15d6c03739"
                                                    "3fa85f64-5717-4562-b3fc-2c963f66afa6"])
                            (map #(.-id %) wallet-entries)))

                (is (match? "Total Values: BTC 2| US$ 60000.00"
                            (.-textContent total-span)))))

            (testing "table data should update when selling"
              (p/let [management-button (helpers/wait-for rendered-view {:test-id "management-button-component"})
                      btc-input (helpers/wait-for rendered-view {:test-id "management-form-btc-input"})
                      sell-button (helpers/wait-for rendered-view {:test-id "management-form-sell-button"})
                      _perform (tl/wait-for #(helpers/perform {:click management-button
                                                               :input {:input btc-input :value 1}
                                                               :save sell-button}))
                      wallet-entries-component (helpers/wait-for rendered-view {:test-id "wallet-entries-component"})
                      wallet-entries (-> wallet-entries-component (.querySelector "table") (.querySelectorAll "tbody tr"))
                      total-values (helpers/wait-for rendered-view {:test-id "total-values-component"})
                      total-span (-> total-values (.querySelector "span"))]

                (is (match? (matchers/in-any-order ["a541ea75-fe82-4416-9e5c-8f15d6c03739"
                                                    "3fa85f64-5717-4562-b3fc-2c963f66afa6"
                                                    "1b2e6b86-a7fd-4556-b379-118f7dbfb742"])
                            (map #(.-id %) wallet-entries)))

                (is (match? "Total Values: BTC 1| US$ 30000.00"
                            (.-textContent total-span)))))

            #_(testing "should not allow selling more than owned"
                (p/let [management-button (helpers/wait-for rendered-view {:test-id "management-button-component"})
                        btc-input (helpers/wait-for rendered-view {:test-id "management-form-btc-input"})
                        sell-button (helpers/wait-for rendered-view {:test-id "management-form-sell-button"})
                        _perform (tl/wait-for #(helpers/perform {:click management-button
                                                                 :input {:input btc-input :value 5}
                                                                 :save sell-button}))
                        alert-message (helpers/wait-for rendered-view {:test-id "alert-error-component-message"})]

                  (is (match? "You cannot withdraw more than your current balance."
                              (.-textContent alert-message)))))

            (done))
          (fn [err]
            (is (= nil err))
            (done)))))
