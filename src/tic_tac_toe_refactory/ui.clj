(ns tic-tac-toe-refactory.ui
  (:require
   [clojure.string :refer [join]]
   [tic-tac-toe-refactory.core :refer :all]))

(def input-from-keyboard
  (repeatedly #(Integer/parseInt (read-line))))

(defn grid [ps]
  (join "\n" (map #(join " | " %) (partition 3 ps))))

(defn pretty-out [world]
  (println (grid (map #(if (keyword? %) (name %) %) (:board world))))  
  (if (not= :nope (:winner world)) 
    (println (str "Player " (name (:winner world)) " won!!!"))
    (println (str "Player " (name (first (:turn-of world))) " make your turn!"))))
  
(play input-from-keyboard pretty-out)
