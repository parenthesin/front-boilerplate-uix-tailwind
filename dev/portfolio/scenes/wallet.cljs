(ns portfolio.scenes.wallet
  (:require
   [parenthesin.front-boilerplate.panels.wallet.components :as components]
   [portfolio.react-18 :refer-macros [defscene]]
   [uix.core :refer [$]]))

(defscene loading-spinner
  ($ components/loading-spinner))

(defscene empty-wallet
  ($ components/wallet-entries))

(defscene full-wallet
  ($ components/wallet-entries
     {:entries [{:id (random-uuid) :btc-amount 1 :usd-amount-at 30000M :created-at #inst "2024-03-26T20:59:01Z"}
                {:id (random-uuid) :btc-amount -1 :usd-amount-at 30000M :created-at #inst "2024-03-26T20:59:01Z"}
                {:id (random-uuid) :btc-amount 2 :usd-amount-at 30000M :created-at #inst "2024-03-26T20:59:01Z"}]}))

(defscene empty-refresh-button
  ($ components/refresh-button))

(defscene full-refresh-button
  ($ components/refresh-button
     {:on-click #(js/alert "Boo!")}))

(defscene empty-total-values
  ($ components/total-values))

(defscene full-total-values
  ($ components/total-values
     {:total-btc 2M
      :total-current-usd 60000M}))

(defscene empty-bottom-bar
  ($ components/bottom-bar))

(defscene full-bottom-bar
  ($ components/bottom-bar
     {:on-click #(js/alert "Boo!")
      :wallet-history {:total-btc 2M
                       :total-current-usd 60000M}}))
