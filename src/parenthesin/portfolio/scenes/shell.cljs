(ns parenthesin.portfolio.scenes.shell
  (:require [uix.core :as uix :refer [defui $]]
            ; [main.component :as c]
            ; [main.lib :refer [defnc]]
            [portfolio.react-18 :refer-macros [defscene]]))

(defui counter []
  (let [[count set-count] (uix/use-state 0)]
    ($ :div
     ($ :p "Count: " count)
     ($ :button {:on-click #(set-count inc)} "Increase"))))

(defscene helix-counter
  :title "Counter with React Hooks"
  ($ counter))
;
; (defscene component-boolean-true
;   (d/div (d/h1 "helix-jsdom")
;          ($ c/component-boolean {:value true})))
;
; (defscene component-boolean-false
;   ($ c/component-boolean {:value false}))
