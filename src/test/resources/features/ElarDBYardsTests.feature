Feature: Yards database validation

  Background:
    Given user navigates to elar application
    When user logs in to Elar app
    And user clicks on Yards tab
    And user clicks on add yard button

  @regression @smoke @db @TC-100
  Scenario Outline: Add yard database validation
    And user creates yard with data
      | Name     | random      |
      | Street   | 123 abc st. |
      | City     | <City>      |
      | State    | <State>     |
      | Zip code | 60173       |
      | spots    | 1123        |
    Then user validates success message "successfully created"
    And user validates yard is persisted in DB
    Examples:
      | City        | State |
      | Chicago     | IL    |
      | New York    | NY    |
      | Los Angeles | CA    |

  Scenario Outline: Edit yard database validation
    And user creates yard with data
      | Name     | random      |
      | Street   | 123 abc st. |
      | City     | <City>      |
      | State    | <State>     |
      | Zip code | 60173       |
      | spots    | 1123        |
    And user goes to created yard page and clicks edit button
    And user updates name with "random"
    Then user validates success message "successfully changed"
    And user validates yard is persisted in DB
    Examples:
      | City        | State |
      | Chicago     | NY    |
      | Los Angeles | CA    |











