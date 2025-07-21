(ns portfolio.scenes.alert
  (:require [parenthesin.front-boilerplate.components.alert :as components]
            [portfolio.react-18 :refer-macros [defscene]]
            [uix.core :refer [$]]))

(defscene alert-error
  ($ components/alert-error {:info #js {:message "Error message"
                                        :data "Error data"}}))
