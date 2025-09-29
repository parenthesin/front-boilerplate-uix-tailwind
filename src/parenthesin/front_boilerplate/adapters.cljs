(ns parenthesin.front-boilerplate.adapters)

(defn format-amount [amount]
  (.toFixed amount 2))

(defn event->value [event]
  (some-> event .-target .-value))
