(ns parenthesin.front-boilerplate.adapters)

(defn format-amount [amount]
  (.toFixed amount 2))
