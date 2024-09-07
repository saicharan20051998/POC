Feature: Amazon products

Background:
    Given the user is logged in
    And goes to landing page
    
    Scenario: User add the products to the cart
        Given User search for the product "the bhagavad gita english"
        When User click on add
        Then User validate the product is added to cart or not	