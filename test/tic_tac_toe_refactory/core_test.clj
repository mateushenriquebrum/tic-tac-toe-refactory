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

(deftest about-winners-and-lossers 
  (let []
    (testing "Winners can arise with they play fair and wisely"
      (is (= true (winners-in-world {:winner :x}))))
    (testing "Or even exists"
      (is (= false (winners-in-world {:winner :nope}))))))

(deftest about-take-steps-in-life
  (let [world {:board [0 1 2] :paths [[1]] :turn-of [:x :o :x]}]
    (testing "You can take one step each time in the world"
      (is (= [0 :x 2] (:board (take-step-in-board world 1)))))
    (testing "And thouse steps define you"
      (is (= :x (:winner (take-step-in-board world 1)))))))

(deftest about-competing-to-win
  (let [world {:board [0 1 2] :paths [[1]] :turn-of [:x :o :x] :winner :nope}]
    (testing "With the right steps you will win"
      (is (= true (players-taking-steps world [0 2 1] identity))))
    (testing "But if you do not pay attention certainly loose"
      (is (= true (players-taking-steps world [0 1] identity))))
    (testing "Or even tie"
      (is (not= true (players-taking-steps world [0] identity))))))