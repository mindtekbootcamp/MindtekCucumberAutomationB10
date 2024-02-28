@regression @etsy @etsySearch
Feature: Etsy Search Functionality

  # Background runs steps before every scenario
  Background: Etsy setup
    Given user navigates to etsy application
    When user searches for keyword "carpet"

  @etsySearchValidation
  Scenario: Validating search result contains searched item
    Then user validates search result contains
      | carpet  |
      | rug     |
      | turkish |
      | persian |
    # The values between pipes are in DataTable.

  @etsyPriceRange
  Scenario: Validating price range filter for searched item
    And user selects price range over thousand dollars
    Then user validates price range for items over 1000.00
