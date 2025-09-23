(ns parenthesin.front-boilerplate.adapters)

(defn format-amount [amount]
  (.toFixed amount 2))

;; TODO unit test
(defn event->value [event]
  (-> event .-target .-value))
