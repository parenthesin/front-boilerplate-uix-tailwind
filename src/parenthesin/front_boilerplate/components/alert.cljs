(ns parenthesin.front-boilerplate.components.alert
  (:require
   [parenthesin.front-boilerplate.components.icon :refer [error]]
   [uix.core :refer [$ defui]]))

(defui alert-error [{:keys [info]}]
  ($ :div {:className "alert alert-error alert-vertical text-left"
           :data-testid "alert-error-component"
           :role "alert"}
     ($ :div.flex.items-center
        ($ error)
        ($ :div {:className "font-bold text-xl"
                 :data-testid "alert-error-component-message"}
           (str (.-message info))))
     ($ :div {:data-testid "alert-error-component-info"}
        (str (.-data info)))))
