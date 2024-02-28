Feature: Validating Yards api calls

  @regression @smoke @api
  Scenario: Validating POST yard api call
    Given user creates yard with post yard api call with data
      | name    | Arslan Yard 5 |
      | address | 321 xyz st.   |
      | city    | Miami         |
      | state   | FL            |
      | zipCode | 60173         |
      | spots   | 1123          |
    When user gets created yard with get api call
    Then user validates get call response having right yard details

  @regression @smoke @api @TC-110
  Scenario: Validating PATCH yard api call
    Given user creates yard with post yard api call with data
      | name    | Mindtek Yard 5 |
      | address | 321 xyz st.    |
      | city    | Miami          |
      | state   | FL             |
      | zipCode | 60173          |
      | spots   | 1123           |
    When user updates created yard with Patch api call with data
      | name  | Mindtek Yard 10 |
      | city  | Chicago         |
      | state | IL              |
    When user gets created yard with get api call
    Then user validates get call response having updated yard details
    And user validated data is updated in DB

  @regression @smoke @api @TC-111
  Scenario Outline: Validating POST yard api call - negative scenario
    Given user creates yard with post yard api call with data
      | name    | <name>      |
      | address | 321 xyz st. |
      | city    | Miami       |
      | state   | FL          |
      | zipCode | 60173       |
      | spots   | 1123        |
    Then user validates status code 400
    And user validates "<errorMessage>" error message
    And user validates that yard is not persisted in DB
    Examples:
      | name                                                                   | errorMessage                                                     |
      | abc!                                                                   | Enter the correct data (leters, digits and '- & \| . ()' symbols |
      | aandfnfsnfoinnvndvubnwuobnuvnoifnwoidsnvffiowdsnviownvionwviowicwdsnvi | Ensure this field has no more than 50 characters.                |


  @regression @api @TC-112
  Scenario Outline: Validating query parameters of Get Yards api call
    Given user creates 20 yards if there is less than 20 yards in DB
    When user gets yards with get api call with query parameters
      | status   | <status>   |
      | ordering | <ordering> |
      | offset   | <offset>   |
      | limit    | <limit>    |
    Then user validates api response having right data
    And user validates yards data with DB
    Examples:
      | status     | ordering | offset | limit |
      | active     | -id      | 15     | 2     |
      | active     | -id      | 1      | 20    |
      | active     | id       | 3      | 4     |
      | non-active | -id      | 0      | 1     |

    Scenario: Validating query parameters of Get Yards api call - negative scenario (offset is more than number of yards)
      Given user gets total numbers of yards from DB
      When user gets yards with query param offset more than total yards
      Then user validates 0 numbers of yards in response

    Scenario: Validating query parameters of Get Yards api call - negative scenario (with offset negative number)
      Given user creates 20 yards if there is less than 20 yards in DB
      When user sends get yards api call with negative number -5 for query param offset
      Then user validates "offset can not be negative number" query parameter error message

      #{
      #	{ “errorMessage” : [ “offset can not be negative number” ] }
      #}

    Scenario: Validating get yards api call - negative scenario (getting non existing yard)
      Given user checks what yard id doesn't exist in DB
      When user sends get call for non existing yard
      Then user validates status code 404
    


