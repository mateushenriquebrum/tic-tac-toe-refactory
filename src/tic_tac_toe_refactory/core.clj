(ns tic-tac-toe-refactory.core
  (:gen-class))

;;in a wolrd
(def world
  {:board [0 1 2 3 4 5 6 7 8] ;;there was a booard
   :paths [[0 1 2] [3 4 5] [6 7 8] [0 3 6] [1 4 7] [2 5 8] [0 4 8] [2 4 6]] ;;with strict paths for the glory
   :turn-of [:x :o :x :o :x :o :x :o :x] ;;but you have enimies and a multual conduct code to follow
   :winner :nope ;;until a winner rise
   :game-on? true ;;or untill it finish
   })

(def path mapv)

(defn board-from-paths [{:keys [board paths]}]
  (map (partial path board) paths))

(defn search-winner [world player]
  (map (partial every? player) (board-from-paths world)))

;;you can be a winner
(defn winner? [world]
  (let [x-winner? (some true? (search-winner world {:x true}))
        o-winner? (some true? (search-winner world {:o true}))]
    (cond
      x-winner? :x
      o-winner? :o
      :else :nope)))

;;and and keep the game on
(defn game-is-on? [world]
  (not (every? keyword? (:board world))))

;;but you need chose your step wisely
(defn take-step-in-board [{:keys [board turn-of] :as world} step]
  (if (number? (board step))
    (let [player (first turn-of)
          step-in-board (assoc board step player)]
      (-> world          
          (assoc :board step-in-board)
          (assoc :turn-of (rest turn-of))
          (#(assoc % :game-on? (game-is-on? %)))
          (#(assoc % :winner (winner? %)))))
    world))
    
;;untill you wont be able to play anymore
(defn continue-playing? [world]
  (and (= :nope (:winner world)) (:game-on? world)))

;;but one stepe every time
(defn players-taking-steps [empty-world some-steps show]
  (show empty-world)
  (loop [world empty-world
         steps some-steps]
    (if (continue-playing? world)
      (let [new-world (take-step-in-board world (first steps))]
        (show new-world)
        (recur new-world (rest steps)))
      world)))

(def play (partial players-taking-steps world))
