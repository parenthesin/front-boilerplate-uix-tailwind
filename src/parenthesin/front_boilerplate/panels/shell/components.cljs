(ns parenthesin.front-boilerplate.panels.shell.components
  (:require [uix.core :as uix :refer [defui $]]
            [uix.dom]))

(defui navbar [{:keys [_]}]
  ($ :div.navbar.bg-base-100.shadow-sm
     ($ :div.flex-1
        ($ :a.btn.btn-ghost.text-xl "daisyUI"))
     ($ :div.flex.gap-2
        ($ :div.dropdown.dropdown-end
           ($ :div {:tabIndex 0
                    :role "button"
                    :className "btn btn-ghost btn-circle avatar"}
              ($ :div.w-10.rounded-full
                 ($ :img {:alt "Meme Avatar"
                          :src "https://i.redd.it/8ch0hraejyhe1.jpeg"})))
           ($ :ul {:tabIndex 0
                   :className "menu menu-sm dropdown-content bg-base-100 rounded-box z-1 mt-3 w-52 p-2 shadow"}
              ($ :li
                 ($ :a {:href "https://uix-cljs.dev" :target "_blank"}
                    "UIx"
                    ($ :span.badge "React!")))
              ($ :li
                 ($ :a {:href "https://github.com/parenthesin" :target "_blank"} "Source Code")))))))
