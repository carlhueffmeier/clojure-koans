(ns koans.10-runtime-polymorphism
  (:require [koan-engine.core :refer :all]))

(defn hello
  ([] "Hello World!")
  ([a] (str "Hello, you silly " a "."))
  ([a & more] (str "Hello to this group: "
                   (apply str
                          (interpose ", " (cons a more)))
                   "!")))

(def get-name (fn [a] (get a :name)))
(defmulti diet (fn [x] (:eater x)))
(defmethod diet :herbivore [a] (str (get-name a) " eats veggies."))
(defmethod diet :carnivore [a] (str (get-name a) " eats animals."))
(defmethod diet :default [a] "I don't know what Rich Hickey eats.")

(meditations
  "Some functions can be used in different ways - with no arguments"
  (= "Hello World!" (hello))

  "With one argument"
  (= "Hello, you silly world." (hello "world"))

  "Or with many arguments"
  (= "Hello to this group: Peter, Paul, Mary!"
     (hello "Peter" "Paul" "Mary"))

  "Multimethods allow more complex dispatching"
  (= "Bambi eats veggies."
     (diet {:species "deer" :name "Bambi" :age 1 :eater :herbivore}))

  "Animals have different names"
  (= "Thumper eats veggies."
     (diet {:species "rabbit" :name "Thumper" :age 1 :eater :herbivore}))

  "Different methods are used depending on the dispatch function result"
  (= "Simba eats animals."
     (diet {:species "lion" :name "Simba" :age 1 :eater :carnivore}))

  "You may use a default method when no others match"
  (= "I don't know what Rich Hickey eats."
     (diet {:name "Rich Hickey"})))
