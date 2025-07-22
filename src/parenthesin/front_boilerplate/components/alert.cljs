(ns parenthesin.front-boilerplate.components.alert
  (:require
   [parenthesin.front-boilerplate.components.icon :refer [error]]
   [uix.core :refer [$ defui]]))

(defui alert-error [{:keys [info]}]
  ($ :div {:className "alert alert-error alert-vertical text-left"
           :role "alert"}
     ($ :div.flex.items-center
        ($ error)
        ($ :div.font-bold.text-xl (str (.-message info))))
     ($ :div (str (.-data info)))))
