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
  (when (number? (board step))
    (as-> world w
      (assoc-in w [:board step] (first turn-of))
      (update w :turn-of rest)
      (assoc w :game-on? (game-is-on? w))
      (assoc w :winner (winner? w)))))

;;untill you wont be able to play anymore
(defn continue-playing? [world]
  (and (= :nope (:winner world)) (:game-on? world)))

;; as lazy
(defn players-taking-steps [world steps]
  (if (continue-playing? world)
    (cons world
          (lazy-seq (players-taking-steps
                     (take-step-in-board world (first steps))
                     (rest steps))))
    (cons world '())))

(def play (partial players-taking-steps world))
