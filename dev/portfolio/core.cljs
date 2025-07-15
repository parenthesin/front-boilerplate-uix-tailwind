(ns portfolio.core
  (:require [portfolio.scenes.shell]
            [portfolio.ui :as ui]))

#_{:clj-kondo/ignore [:clojure-lsp/unused-public-var]}
(defn init
  []
  (ui/start! {:config {:css-paths ["/css/bundle.css"]}}))
