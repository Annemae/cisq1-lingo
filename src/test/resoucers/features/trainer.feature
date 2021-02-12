Feature: Training for Lingo
  As a player,
  I want to practice guessing 5, 6 and 7 letter words,
  in order to prepare for Lingo.

#  START A NEW GAME

  Scenario: Start a new game
    When I start a new game
    Then the word to guess has "5" letters
    And I should see the first letter
    And my score is "0"

#  START A NEW ROUND

  Scenario Outline: Start a new round
    Given I am playing a game
    And the round was won
    And the last word had "<previous length>" letters
    When I start a new round
    Then the word to guess has "<next length>" letters

    Examples:
      | previous length | next length |
      | 5               | 6           |
      | 6               | 7           |
      | 7               | 5           |

  #Failure path
  Scenario: Cannot start a new round if round is lost
    Given I am playing a game
    And the round was lost
    Then I cannot start a new round

  #Failure path
  Scenario: Cannot start a new round if still playing
    Given I am playing a game
    And I am still guessing a word
    Then I cannot start a new round

  #Failure path
  Scenario: Cannot start a new round if eliminated
    Given I start a new round
    When I do not have a game
    Then I cannot start a new round

#  GUESSING A WORD

  Scenario Outline: Guessing a word
    Given the round is still ongoing
    When I take a "<guess>" for the "<word>"
    Then I will receive "<feedback>" on what I just entered

    Examples:
      | word  | guess  | feedback                                    |
      | BROOD | BINGO  | CORRECT, ABSENT, ABSENT, ABSENT, PRESENT    |
      | BROOD | BROOD  | CORRECT, CORRECT, CORRECT, CORRECT, CORRECT |
      | BROOD | BOOR   | INVALID, INVALID, INVALID, INVALID          |

  #Failure path
  Scenario: Cannot take a guess if word length is invalid
    Given the round is still ongoing
    When the word I fill in has the wrong amount of letters
    Then I will receive feedback that my guess is invalid

  #Failure path
  Scenario: Cannot take a guess if word already guessed
    Given the word has already been guessed
    When I take a guess for the word
    Then I will receive feedback that I cannot take a guess

  #Failure path
  Scenario: Cannot take a guess if game already over
    Given I lost the game
    When I take a guess for the word
    Then I will receive feedback that I cannot take a guess

  Scenario: Game is lost after five wrong attempts
    Given the round is still ongoing
    When I take my fifth guess
    And the guess is wrong
    Then I lost the game

#  SCORE INCREASES BASED ON NUMBER OF ATTEMPTS

  Scenario Outline: Score increases based on number of attempts
    Given I am playing a game
    And the score is "<current score>"
    And the word to guess is "BROOD"
    When I guess "BROOD" in "<attempts>"
    Then the score is "<new score>"

    Examples:
    | current score | attempts | new score |
    | 0             | 1        | 25        |
    | 5             | 1        | 30        |
    | 0             | 2        | 20        |
    | 5             | 2        | 25        |
    | 0             | 3        | 15        |
    | 5             | 3        | 20        |
    | 0             | 4        | 10        |
    | 5             | 4        | 15        |
    | 0             | 5        | 5         |
    | 5             | 5        | 10        |