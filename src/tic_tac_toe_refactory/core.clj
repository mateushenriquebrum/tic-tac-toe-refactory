(ns tic-tac-toe-refactory.core
  (:gen-class))

;;in a wolrd
(def world
  {:board [0 1 2 3 4 5 6 7 8] ;;there was a booard
   :paths [[0 1 2] [3 4 5] [6 7 8] [0 3 6] [1 4 7] [2 5 8] [0 4 8] [2 4 6]] ;;with strict paths for the glory
   :turn-of [:x :o :x :o :x :o :x :o :x] ;;but you have enimies and a multual conduct code to follow
   :winner :nope ;;until a winner rise
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

;;but you need chose your step wisely
(defn take-step-in-board [{:keys [board turn-of] :as world} step]
  (if (number? (board step))
    (let [player (first turn-of)
          step-in-board (assoc board step player)]
      (-> world
          (assoc :board step-in-board)
          (assoc :turn-of (rest turn-of))
          (#(assoc % :winner (winner? %)))))
    world))

;;until a winner arise in glorious
(defn winners-in-world [world]
  (cond
    (= :x (:winner world)) true
    (= :o (:winner world)) true
    :else false))

;;but one stepe every time
(defn players-taking-steps [world steps show]
   (some
    winners-in-world
    (map #(-> %
              (doto show))
         (reductions take-step-in-board world steps))))

(def play (partial players-taking-steps world))
