(ns parenthesin.front-boilerplate.test.panels.wallet.adapters-test
  (:require [cljs.test :refer [deftest is testing]]
            [clojure.string :as str]
            [matcher-combinators.test :refer [match?]]
            [parenthesin.front-boilerplate.panels.wallet.adapters :as adapters]
            [parenthesin.front-boilerplate.test.aux.fixtures.wallet :as fixtures.wallet]))

(deftest ->wallet-entries-test
  (testing "Normal data convertion"
    (let [result (adapters/->wallet-entries fixtures.wallet/unparsed-wallet-entry)
          created-at (-> result :entries first :created-at)]
      (is (match? {:entries [{:id 1
                              :btc-amount 1
                              :usd-amount-at "30000.00"}]
                   :total-btc 1
                   :total-current-usd "30000.00"}
                  result))
      (is (str/includes? created-at "1"))
      (is (str/includes? created-at "2025")))))
