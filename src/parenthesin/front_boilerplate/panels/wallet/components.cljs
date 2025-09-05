(ns parenthesin.front-boilerplate.panels.wallet.components
  (:require
   [parenthesin.front-boilerplate.components.icon :refer [magnifying-glass]]
   [uix.core :as uix :refer [$ defui]]
   [uix.dom]))

(defui loading-spinner [{:keys [_]}]
  ($ :div {:className "flex justify-center items-center h-32"
           :data-testid "loading-spinner-component"}
     ($ :span.loading.loading-spinner.loading-xl)))

(defui wallet-entries [{:keys [entries]}]
  ($ :div {:className "bg-base-100"
           :data-testid "wallet-entries-component"}
     ($ :table.table
        ($ :thead
           ($ :tr
              ($ :th "BTC Amount")
              ($ :th "USD Amount At")
              ($ :th "Created At")
              ($ :th)))
        ($ :tbody
           (for [{:keys [id btc-amount usd-amount-at created-at]} entries]
             (let [withdrawal (> 0 btc-amount)]
               ($ :tr {:key id
                       :className (str "hover:bg-base-200" (when withdrawal " bg-base-300"))}
                  ($ :td (str btc-amount " BTC"))
                  ($ :td (str "US$ " usd-amount-at))
                  ($ :td (str created-at))
                  ($ :td.text-right ($ :div {:className "tooltip"}
                                       ($ :div.tooltip-content
                                          ($ :div.text-xs.text-justify
                                             ($ :p ($ :b "ID: ") (str id))
                                             ($ :p ($ :b "BTC Amount: ") (str btc-amount))
                                             ($ :p ($ :b "USD Amount At: ") (str usd-amount-at))
                                             ($ :p ($ :b "Created At: ") (str created-at))))
                                       ($ magnifying-glass))))))))))

(defui refresh-button [{:keys [on-click]}]
  ($ :div {:data-testid "refresh-button-component"}
     ($ :button.btn.btn-primary.join-item
        {:on-click on-click}
        "Refresh")))

(defui management-button [{:keys [on-click]}]
  ($ :div {:data-testid "management-button-component"}
     ($ :button.btn.btn-accent.join-item
        {:on-click on-click}
        "Management")))

(defui total-values [{:keys [total-btc total-current-usd]}]
  ($ :div {:className "p-4"
           :data-testid "total-values-component"}
     ($ :span.text-lg.font-bold
        (str "Total Values: BTC " total-btc
             "| US$ " total-current-usd))))

(defui bottom-bar [{:keys [management-on-click refresh-on-click wallet-history]}]
  ($ :div {:className "flex justify-between"
           :data-testid "bottom-bar-component"}
     ($ :.join.p-4
        ($ refresh-button {:on-click refresh-on-click})
        ($ management-button {:on-click management-on-click}))
     ($ total-values wallet-history)))
