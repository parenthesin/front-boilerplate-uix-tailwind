(ns parenthesin.front-boilerplate.infra.preferences
  (:require
   [clojure.edn :as edn]))

(def default-preferences
  {:dark-mode true})

(defn get-stored-preferences []
  (let [stored-preferences (js/localStorage.getItem "preferences")]
    (if stored-preferences
      (edn/read-string stored-preferences)
      default-preferences)))

(defn set-stored-preferences [preferences]
  (js/localStorage.setItem "preferences" (pr-str preferences)))
