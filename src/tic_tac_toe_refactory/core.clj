(ns tic-tac-toe-refactory.core
  (:gen-class))

(defn -main
  "I don't do a whole lot ... yet."
  [& args]
  (println "Hello, World!"))

;;in a wolrd
(def world
  {:board [1 2 3 4 5 6 7 8 9] ;;there was a booard
   :paths [[1 2 3] [4 5 6] [7 8 9] [1 4 7] [2 5 8] [3 6 9] [1 5 9] [3 5 7]] ;;with strict paths for the glory
   :turn-of [:x :o :x :o :x :o :x :o :x] ;;but you have enimies and a multual conduct code to follow
   })

;;you need chose your step wisely
(defn take-step-in-board [{:keys [board turn-of] :as world} step]
  (let [player (first turn-of)
        step-in-board (assoc board step player)
        next-turn-of (rest turn-of)]
    (assoc world
           :board step-in-board
           :turn-of next-turn-of)))

;;until a winner arise in glorious

;;but one step each time
(defn players-taking-steps [world steps]
  (reduce take-step-in-board world steps))