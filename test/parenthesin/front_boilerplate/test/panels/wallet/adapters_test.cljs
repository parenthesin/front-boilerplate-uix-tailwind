(ns parenthesin.front-boilerplate.test.panels.wallet.adapters-test
  (:require [cljs.test :refer [deftest is testing]]
            [matcher-combinators.test :refer [match?]]
            [parenthesin.front-boilerplate.panels.wallet.adapters :as adapters]
            [parenthesin.front-boilerplate.test.aux.fixtures.wallet :as fixtures.wallet]))

(deftest ->wallet-entries-test
  (testing "Normal data convertion"
    (with-redefs [adapters/get-local-language (constantly "en-US")]
      (let [result (adapters/->wallet-entries fixtures.wallet/unparsed-wallet-entry)]
        (is (match? {:entries [{:id 1
                                :btc-amount 1
                                :usd-amount-at "30000.00"
                                :created-at "1/1/2025, 9:00:00 AM"}]
                     :total-btc 1
                     :total-current-usd "30000.00"}
                    result))))))
