(ns parenthesin.front-boilerplate.test.aux.fixtures.wallet
  (:require [parenthesin.front-boilerplate.test.aux.helpers :as helpers]))

(def unparsed-entry
  {:id 1
   :btc-amount (helpers/->tagged-decimal 1)
   :usd-amount-at (helpers/->tagged-decimal 30000M)
   :created-at "2025-01-01T12:00:00Z"})

(def unparsed-total-btc
  (helpers/->tagged-decimal 1))

(def unparsed-total-current-usd
  (helpers/->tagged-decimal 30000M))

(def unparsed-wallet-entry
  {:entries [unparsed-entry]
   :total-btc unparsed-total-btc
   :total-current-usd unparsed-total-current-usd})

(def entry
  {:id 1
   :btc-amount 1
   :usd-amount-at 30000M
   :created-at "Wed Jan 01 2025"})

(def total-btc 1)

(def total-current-usd 30000M)

(def wallet-entry
  {:entries [entry]
   :total-btc total-btc
   :total-current-usd total-current-usd})
