(ns parenthesin.front-boilerplate.panels.wallet.components
  (:require [uix.core :as uix :refer [defui $]]
            [uix.dom]))

(defui loading-spinner [{:keys [_]}]
  ($ :div {:className "flex justify-center items-center h-32"
           :data-testid "loading-spinner-component"}
     ($ :span.loading.loading-spinner.loading-xl)))

(defui wallet-entries [{:keys [entries]}]
  ($ :div {:className "rounded-box border border-base-content bg-base-100"
           :data-testid "wallet-entries-component"}
     ($ :table.table
        ($ :thead
           ($ :tr
              ($ :th "ID")
              ($ :th "BTC Amount")
              ($ :th "USD Amount At")
              ($ :th "Created At")))
        ($ :tbody
           (for [{:keys [id btc-amount usd-amount-at created-at]} entries]
             (let [withdrawal (> 0 btc-amount)]
               ($ :tr {:key id
                       :className (when withdrawal "bg-base-300")}
                  ($ :td ($ :div {:className "tooltip"
                                  :data-tip id}
                            ($ :button {:className "btn"
                                        :on-click #(js/navigator.clipboard.writeText id)}
                               ($ :span "ID"))))
                  ($ :td (str btc-amount " BTC"))
                  ($ :td (str "US$ " usd-amount-at))
                  ($ :td (str created-at)))))))))

(defui refresh-button [{:keys [on-click]}]
  ($ :div {:className "p-4"
           :data-testid "refresh-button-component"}
     ($ :button.btn.btn-primary
        {:on-click on-click}
        "Refresh")))

(defui total-values [{:keys [total-btc total-current-usd]}]
  ($ :div {:className "p-4"
           :data-testid "total-values-component"}
     ($ :span.text-lg.font-bold
        (str "Total Values: BTC " total-btc
             "| US$ " total-current-usd))))

(defui bottom-bar [{:keys [on-click wallet-history]}]
  ($ :div {:className "flex justify-between"
           :data-testid "bottom-bar-component"}
     ($ refresh-button {:on-click on-click})
     ($ total-values wallet-history)))
