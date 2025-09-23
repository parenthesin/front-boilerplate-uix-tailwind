(ns parenthesin.front-boilerplate.test.panels.wallet.adapters-test
  (:require
   [cljs.test :refer [deftest is testing]]
   [matcher-combinators.test :refer [match?]]
   [parenthesin.front-boilerplate.panels.wallet.adapters :as wallet.adapters]
   [parenthesin.front-boilerplate.test.aux.fixtures.wallet :as fixtures.wallet]))

(deftest ->wallet-entries-test
  (testing "Normal data convertion"
    (let [language {:locale "en-US"
                    :time-zone "America/Sao_Paulo"}
          result (wallet.adapters/->wallet-entries fixtures.wallet/unparsed-wallet-entry language)]
      (is (match? {:entries [{:id #uuid "a541ea75-fe82-4416-9e5c-8f15d6c03739"
                              :btc-amount 1
                              :usd-amount-at "30000.00"
                              :created-at "1/1/2025, 9:00:00 AM"}]
                   :total-btc 1
                   :total-current-usd "30000.00"}
                  result)))))
