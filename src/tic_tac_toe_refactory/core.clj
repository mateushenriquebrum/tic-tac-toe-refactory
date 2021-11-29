(ns tic-tac-toe-refactory.core
  (:gen-class))

(defn -main
  "I don't do a whole lot ... yet."
  [& args]
  (println "Hello, World!"))

;;in a wolrd
(def world
  {:board [0 1 2 3 4 5 6 7 8] ;;there was a booard
   :paths [[0 1 2] [3 4 5] [6 7 8] [0 3 6] [1 4 7] [2 5 8] [0 4 8] [2 4 6]] ;;with strict paths for the glory
   :turn-of [:x :o :x :o :x :o :x :o :x] ;;but you have enimies and a multual conduct code to follow
   :winner :nope ;;until a winner rise
   })

(defn path [board cords]
  (mapv board cords))

(defn board-from-paths [{:keys [board paths]}]
  (map (partial path board) paths))

(defn search-winner [world player]
  (map (partial every? player) (board-from-paths world)))

(defn winner? [world]
  (let [x-winner? (some true? (search-winner world {:x true}))
        o-winner? (some true? (search-winner world {:o true}))]
    (cond
      x-winner? :x
      o-winner? :o
      :else :nope)))

;;you need chose your step wisely
(defn take-step-in-board [{:keys [board turn-of] :as world} step]
  (let [player (first turn-of)
        step-in-board (assoc board step player)
        next-turn-of (rest turn-of)
        winner (winner? world)]
    (assoc world
           :board step-in-board
           :turn-of next-turn-of
           :winner winner)))

;;until a winner arise in glorious
(defn a-winner-arises [world]
  (cond
    (= :x (:winner world)) true
    (= :o (:winner world)) true
    :else false))

;;but one step each time until the end
(defn a-winner-arises-terminator [world step]
   (if (a-winner-arises (take-step-in-board world step))
      (reduced (take-step-in-board world step))
      (take-step-in-board world step)))

(defn players-taking-steps [world steps]
  (reduce
   a-winner-arises-terminator
   world
   steps))

(players-taking-steps world [0 4 1 5 2])