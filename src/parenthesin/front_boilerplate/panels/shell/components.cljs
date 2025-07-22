(ns parenthesin.front-boilerplate.panels.shell.components
  (:require
   [parenthesin.front-boilerplate.components.icon :refer [moon parenthesin sun]]
   [uix.core :as uix :refer [$ defui]]
   [uix.dom]))

(defui theme-toggle [{:keys [dark-mode set-dark-mode]}]
  ($ :label {:className "swap swap-rotate"}
     ($ :input {:type "checkbox"
                :className "theme-controller"
                :checked (not dark-mode)
                :onChange #(set-dark-mode (not dark-mode))
                :value "light"})
     ($ sun)
     ($ moon)))

(defui navbar [{:keys [dark-mode set-dark-mode]}]
  ($ :div.navbar.bg-base-100.shadow-sm
     ($ :div.flex-1
        ($ :a.btn.btn-ghost.text-xl "front-boilerplate-uix-tailwind"))
     ($ :div.flex.gap-2
        ($ theme-toggle {:dark-mode dark-mode
                         :set-dark-mode set-dark-mode})
        ($ :div {:role "button"
                 :className "btn btn-ghost btn-circle avatar"}
           ($ :div.w-10.rounded-full
              ($ :img {:alt "Meme Avatar"
                       :src "https://i.redd.it/8ch0hraejyhe1.jpeg"}))))))

(defui content [{:keys [children]}]
  ($ :div.flex-grow
     children))

(defui footer [{:keys [dark-mode]}]
  ($ :footer.footer.footer-horizontal.footer-center.p-10
     ($ :aside
        ($ parenthesin {:dark-mode dark-mode})
        ($ :p.font-bold
           "Providing reliable parentheses since 2019")
        ($ :p "Public Domain - No rights reserved - "
           ($ :a {:className "link"
                  :href "https://github.com/parenthesin/front-boilerplate-uix-tailwind"
                  :target "_blank"}
              "Source Code")))))
