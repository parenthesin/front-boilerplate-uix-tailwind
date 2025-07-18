(ns parenthesin.front-boilerplate.test.panels.wallet.adapters-test
  (:require [cljs.test :refer [deftest is testing]]
            [parenthesin.front-boilerplate.panels.wallet.adapters :as adapters]
            [parenthesin.front-boilerplate.test.aux.fixtures.wallet :as fixtures.wallet]))

(deftest ->wallet-entry-test
  (testing "Normal data convertion"
    (is (= {:entries [{:id 1
                       :btc-amount 1
                       :usd-amount-at 30000M
                       :created-at "Wed Jan 01 2025"}]
            :total-btc 1
            :total-current-usd 30000M}
           (adapters/->wallet-entries fixtures.wallet/unparsed-wallet-entry)))))
