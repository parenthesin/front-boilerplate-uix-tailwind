(ns parenthesin.front-boilerplate.test.panels.management.view-test
  (:require
   [cljs.test :refer [async deftest is testing use-fixtures]]
   [matcher-combinators.test :refer [match?]]
   [parenthesin.front-boilerplate.panels.management.view :as view]
   [parenthesin.front-boilerplate.test.aux.helpers :as helpers]
   [parenthesin.front-boilerplate.test.aux.init :refer [mock-http-with]]
   [parenthesin.front-boilerplate.test.aux.testing-library :as tl]
   [promesa.core :as p]
   [uix.core :refer [$]]))

(use-fixtures :each
  {:after tl/async-cleanup})

(def buy-state (atom false))
(def sell-state (atom false))

(def app-management-props
  {:buy-on-click (fn [{:keys [value]}] (reset! buy-state value))
   :sell-on-click (fn [{:keys [value]}] (reset! sell-state value))})

(deftest app-management-test
  (mock-http-with {"wallet/current-btc-usd"
                   {:lag 0
                    :status 200
                    :body {:btc-amount 1
                           :usd-amount 30000M}}})

  (async done
         (p/catch
          (p/let [rendered-view (tl/wait-for #(tl/render ($ view/app-management app-management-props)))
                  title-element (-> rendered-view (.getByText "Management"))
                  management-form-component (helpers/wait-for rendered-view {:test-id "management-form-component"})
                  btc-input (helpers/wait-for rendered-view {:test-id "management-form-btc-input"})
                  buy-button (helpers/wait-for rendered-view {:test-id "management-form-buy-button"})
                  sell-button (helpers/wait-for rendered-view {:test-id "management-form-sell-button"})]

            (testing "app management view should render with correct structure"
              (is (match? "H3" (-> title-element .-tagName)))
              (is (match? "flex flex-col" (-> management-form-component (aget "className")))))

            (testing "simulating button click should call on-click function with correct values"
              (testing "buy operation"
                (tl/change btc-input 2)
                (tl/click buy-button)
                (is (match? 2 @buy-state)))

              (testing "sell operation"
                (tl/change btc-input 5)
                (tl/click sell-button)
                (is (match? -5 @sell-state))))

            (done))
          (fn [err]
            (is (= nil err))
            (done)))))

(deftest app-management-with-error-test
  (mock-http-with {"wallet/current-btc-usd"
                   {:lag 0
                    :status 500
                    :body {:error "Internal Server Error"}}})
  (async done
         (p/catch
          (p/let [rendered-view (tl/wait-for #(tl/render ($ view/app-management app-management-props)))
                  alert-error-component (helpers/wait-for rendered-view {:test-id "alert-error-component"})
                  alert-error-component-info (helpers/wait-for rendered-view {:test-id "alert-error-component-info"})
                  alert-error-component-message (-> alert-error-component (.querySelector "div"))]

            (testing "app management view should render with alert error component"
              (is (match? "alert alert-error alert-vertical text-left" (-> alert-error-component (aget "className")))))

            (testing "alert error component should render with correct error message"
              (is (match? "Response error" (-> alert-error-component-message (.-textContent))))
              (is (match? "{:lag 0, :status 500, :body {:error \"Internal Server Error\"}}" (-> alert-error-component-info (.-textContent)))))

            (done))
          (fn [err]
            (is (= nil err))
            (done)))))
