Feature: Single service put and patch

  Background:
    Given There is a Services server
    Given I have a flushed database
    Then I populate it with some services

  Scenario: patch an existing service with a valid status
    Given I have a valid service uid
    Given I have a valid status
    When I PATCH on /services/{uid} endpoint
    Then I receive a 200 status code
    When I GET on /services/{uid} endpoint
    Then The status has changed
    Then I receive a 200 status code

  Scenario: try to patch an existing service with an invalid status
    Given I have a valid service uid
    Given I have an invalid status
    When I PATCH on /services/{uid} endpoint
    Then I receive a 400 status code

  Scenario: try to patch a missing service with a valid status
    Given I have an invalid service uid
    Given I have a valid status
    When I PATCH on /services/{uid} endpoint
    Then I receive a 404 status code

  Scenario: try to patch a missing service with an invalid status
    Given I have an invalid service uid
    Given I have an invalid status
    When I PATCH on /services/{uid} endpoint
    Then I receive a 400 status code

  Scenario: put an existing service with a valid update
    Given I have a valid service uid
    Given I have a service payload
    When I PUT on /services/{uid} endpoint
    Then I receive a 200 status code
    When I GET on /services/{uid} endpoint
    Then The service has changed
    Then I receive a 200 status code

  Scenario: try to put an existing service with an invalid update
    Given I have a valid service uid
    Given I have an invalid service payload (address is null)
    When I PUT on /services/{uid} endpoint
    Then I receive a 400 status code

  Scenario: try to put a missing service with a valid update
    Given I have an invalid service uid
    Given I have a service payload
    When I PUT on /services/{uid} endpoint
    Then I receive a 404 status code

  Scenario: try to put a missing service with an invalid update
    Given I have an invalid service uid
    Given I have an invalid service payload (address is null)
    When I PUT on /services/{uid} endpoint
    Then I receive a 400 status code
