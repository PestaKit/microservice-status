Feature: Creation of fruits

  Background:
    Given there is a Fruits server

  Scenario: create a fruit
    Given I have a fruit payload
    When I POST it to the /fruits endpoint
    Then I receive a 201 status code

  Scenario: delete a fruit
    Given there is a fruit available
    When I POST to the /deletefruit endpoint
    Then I reveive a 200 status code