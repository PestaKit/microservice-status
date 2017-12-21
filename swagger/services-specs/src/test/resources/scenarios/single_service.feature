Feature: Single service get and deletion

  Background:
    Given There is a Services server
    Given I have a flushed database
    Given I populate it with some services

  Scenario: list a service with an existing uid
    Given I have a valid service uid
    When I GET on /services/{uid} endpoint
    Then I receive a services with the correct uid
    Then I receive a 200 status code

  Scenario: list a service with an invalid uid
    Given I have an invalid service uid
    When I GET on /services/{uid} endpoint
    Then I receive a 404 status code

  Scenario: delete the service with an existing uid
    Given I have a valid service uid
    When I DELETE on /services/{uid} endpoint
    Then I receive a 200 status code

  Scenario: delete a service with an invalid uid
    Given I have an invalid service uid
    When I DELETE on /services/{uid} endpoint
    Then I receive a 404 status code
