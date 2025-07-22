(ns parenthesin.front-boilerplate.test.aux.fixtures.wallet)

(def total-btc 1)

(def total-current-usd 30000M)

(def unparsed-entry
  {:id 1
   :btc-amount 1
   :usd-amount-at 30000M
   :created-at "2025-01-01T12:00:00.000+00:00"})

(def unparsed-wallet-entry
  {:entries [unparsed-entry]
   :total-btc total-btc
   :total-current-usd total-current-usd})

(def entry
  {:id 1
   :btc-amount 1
   :usd-amount-at 30000M
   :created-at "Wed Jan 01 2025"})

(def wallet-entry
  {:entries [entry]
   :total-btc total-btc
   :total-current-usd total-current-usd})
