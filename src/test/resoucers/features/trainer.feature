Feature: Training for Lingo
  As a player,
  I want to practice guessing 5, 6 and 7 letter words,
  in order to prepare for Lingo.

  Scenario: Start a new game
    When I start a new game
    Then the word to guess has "5" letters
    And I should see the first letter
    And my score is "0"

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
    Given I am playing a game
    And the round was lost
    Then I cannot start a new round

  Scenario Outline: Guessing a word
    Given the round is still ongoing
    When I take a "<guess>" for the "<word>"
    Then I will receive "<feedback>" on what I just entered

    Examples:
      | word  | guess  | feedback                                    |
      | BROOD | BINGO  | CORRECT, ABSENT, ABSENT, ABSENT, PRESENT    |
      | BROOD | BROOD  | CORRECT, CORRECT, CORRECT, CORRECT, CORRECT |

    #Failure path
    Given the round is still ongoing
    When the word I fill in has the wrong amount of letters
    Then I will receive feedback that my guess is invalid