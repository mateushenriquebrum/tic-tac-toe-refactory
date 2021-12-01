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

