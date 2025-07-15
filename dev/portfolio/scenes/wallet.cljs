(ns portfolio.scenes.wallet
  (:require [parenthesin.front-boilerplate.panels.wallet.components :as components]
            [portfolio.react-18 :refer-macros [defscene]]
            [uix.core :refer [$]]))

(defscene empty-wallet
  ($ components/wallet-entries))

(defscene full-wallet
  ($ components/wallet-entries
     {:entries [{:id (random-uuid) :btc-amount 1 :usd-amount-at 30000M :created-at #inst "2024-03-26T20:59:01Z"}
                {:id (random-uuid) :btc-amount -1 :usd-amount-at 30000M :created-at #inst "2024-03-26T20:59:01Z"}
                {:id (random-uuid) :btc-amount 2 :usd-amount-at 30000M :created-at #inst "2024-03-26T20:59:01Z"}]}))

