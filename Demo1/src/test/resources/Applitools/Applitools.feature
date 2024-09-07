Feature: Applitools

  Scenario Outline: User sums the total transaction amount
    Given the user navigates to the page with username "<username>" and password "<password>"
    Then the user verifies the page title
    And the user calculates the total profit
    And the user calculates the total loss
    And the user sums the total profit and total loss
    
    Examples:
    |username|password|
    |Admin   |Password@123|