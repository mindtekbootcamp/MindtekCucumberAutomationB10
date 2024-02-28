@regression @smartbear @orderCreation
Feature: Validating Order  Creation

  Background: Order creation setup
    Given user navigates to smartbear application
    And user logs in with username "Tester" and password "test"
    And user clicks on Order tab

  @discountCalculator
  Scenario Outline: Applying discount to the total amount
    When user selects product "<Product>" and quantity <Quantity>
    Then user validates the price is calculated correctly for <Quantity>
    Examples:
      | Product     | Quantity |
      | MyMoney     | 9        |
      | FamilyAlbum | 10       |
      | ScreenSaver | 11       |

  @placeOrder
  Scenario: Placing an order and validating
    When user places an order with data and validates with success message "New order has been successfully added."
      | PRODUCT     | QUANTITY | CUSTOMER NAME | STREET     | CITY     | STATE | ZIP   | CARD   | CARD NUM  | EXP DATE |
      | MyMoney     | 5        | John Doe      | 123 Lee St | New York | NY    | 12345 | Visa   | 123456789 | 12/25    |
      | FamilyAlbum | 10       | Harsh Patel   | 321 Dee St | Chicago  | IL    | 54321 | Amex   | 987654321 | 03/26    |
      | ScreenSaver | 12       | Lisa Morgan   | 456 Zee St | Boston   | MA    | 67890 | Master | 192837465 | 10/25    |
    Then user validates the created order is in the list of all orders

