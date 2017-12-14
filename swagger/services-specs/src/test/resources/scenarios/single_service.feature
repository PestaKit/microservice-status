#Feature: Single service get and deletion
#
#  Background:
#    Given there is a Services server
#
#  Scenario: list a service with an existing id
#    Given I have a valid service id
#    When I GET on /services/{id} endpoint
#    Then I receive a services with the correct id
#    Then I receive a 200 status code
#
#  Scenario: list a service with an invalid id
#    Given I have an invalid service id
#    When I GET on /services/{id} endpoint
#    Then I receive a 400 status code
#
#  Scenario: delete the service with an existing id
#    Given I have a valid service id
#    When I DELETE on /services/{id} endpoint
#    Then I receive a 200 status code
#
#  Scenario: delete a service with an invalid id
#    Given I have an invalid service id
#    When I DELETE on /services/{id} endpoint
#    Then I receive a 400 status code
