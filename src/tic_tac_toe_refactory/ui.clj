(ns tic-tac-toe-refactory.ui
  (:require
   [tic-tac-toe-refactory.core :refer :all]))

(def input-from-keyboard
  (repeatedly #(Integer/parseInt (read-line))))

(play input-from-keyboard println)