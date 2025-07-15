(ns parenthesin.front-boilerplate.core
  (:require [parenthesin.front-boilerplate.panels.shell.view :refer [app-shell]]
            [uix.core :as uix :refer [defui $]]
            [uix.dom]))

(defui app []
  ($ app-shell {}))

(defonce root
  (uix.dom/create-root (js/document.getElementById "app")))

(defn render []
  (uix.dom/render-root
   ($ uix/strict-mode
      ($ app))
   root))

(defn ^:export init []
  (render))
