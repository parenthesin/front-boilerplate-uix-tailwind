(ns parenthesin.portfolio.core
  (:require [parenthesin.portfolio.scenes.shell]
            [portfolio.ui :as ui]))

#_{:clj-kondo/ignore [:clojure-lsp/unused-public-var]}
(defn init
  []
  (ui/start!))
