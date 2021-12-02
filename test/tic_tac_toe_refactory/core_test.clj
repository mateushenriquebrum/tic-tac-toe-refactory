(ns tic-tac-toe-refactory.core-test
  (:require [clojure.test :refer :all]
            [tic-tac-toe-refactory.core :refer :all]))

(deftest about-winners
  (let [paths [[0 1 2] [3 4 5] [6 7 8] [0 3 6] [1 4 7] [2 5 8] [0 4 8] [2 4 6]]]
    (testing "There is not winners or lossers in the begining"
      (is (= :nope (winner? {:board [0 1 2 3 4 5 6 7 8] :paths paths}))))
    (testing "Neither after some random steps"
      (is (= :nope (winner? {:board [0 :x 2 3 4 :o 6 7 :o] :paths paths}))))
    (testing "But surely after some consistence and direction"
      (is (= :x (winner? {:board [:x :x :x 3 4 :o 6 7 :o] :paths paths}))))
    (testing "But can happens for both sides"
      (is (= :o (winner? {:board [:o 2 :x :o :x 5 :o 7 :x] :paths paths}))))))

(deftest about-take-steps-in-life
  (let [world {:board [0 1 2] :paths [[1]] :turn-of [:x :o :x]}]
    (testing "You can take one step each time in the world"
      (is (= [0 :x 2] (:board (take-step-in-board world 1)))))
    (testing "And thouse steps define you"
      (is (= :x (:winner (take-step-in-board world 1)))))))

(deftest about-no-more-chances
  (testing "Sometimes you have a plenty"
    (is (= true (game-is-on? {:board [1 2 3]}))))
  (testing "Sometimes just few"
    (is (= true (game-is-on? {:board [:x :o 3]}))))
  (testing "But it always go eventually"
    (is (= false (game-is-on? {:board [:x :o :x]})))))

(deftest about-competing-to-win
  (testing "With the right steps you will win"
    (is (= :x (:winner (players-taking-steps world [0 3 1 4 2] identity)))))
  (testing "Or loose if you do not pay enouth attention"
    (is (= :o (:winner (players-taking-steps world [8 3 1 4 2 5] identity)))))
  (testing "But there is always a tied to restart again"
    (is (= :nope (:winner (players-taking-steps world [4 0 8 5 2 1 3 6 7] identity))))))