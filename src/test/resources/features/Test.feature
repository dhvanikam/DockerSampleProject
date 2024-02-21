@BDDSTORY-BANKAUTO-206
Feature: Deposit
  Create a feature file for Deposit functionality

  Background: 
    The user is logged in to banking website

    Given The user is on banking website login page
    When The user enter valid "mngr502686" and "pArUhud"
    And The user click on login button
    Then The user redirected to homepage

  @BDDTEST-BANKAUTO-215
  Scenario Outline: Checking with valid data
    Given User enter valid "<AccountNo>","<Amount>","<Description>"
    When Click on submit button
    Then User should see "Account does not exist" alert message

    Examples: 
      | AccountNo | Amount | Description |
      |    122823 |   1000 | personal    |

  @BDDTEST-BANKAUTO-218
  Scenario Outline: Checking with characters in "<AccountNo>"
    Given User enter characters in "<AccountNo>" field
    Then user should see "Characters are not allowed" message

    Examples: 
      | AccountNo |
      | 1228ab    |

  @BDDTEST-BANKAUTO-220
  Scenario Outline: Checking with empty space in "<AccountNo>"
    Given User enter blank space in "<AccountNo>" field
    Then User should be presented "Account Number must not be blank" message

    Examples: 
      | AccountNo |
      |           |

  @BDDTEST-BANKAUTO-223
  Scenario Outline: Checking with negetive number in "<AccountNo>"
    Given User enter negetive number in "<AccountNo>" field
    Then User should be presented "Negative numbers not allowed" message

    Examples: 
      | AccountNo |
      |      -123 |

  @BDDTEST-BANKAUTO-224
  Scenario Outline: Checking with more than 10 digits in "<AccountNo>"
    Given User enter more than 10 digits in "<AccountNo>" field
    Then User should be blocked from entering any data

    Examples: 
      | AccountNo  |
      | 1234567891 |

  @BDDTEST-BANKAUTO-225
  Scenario Outline: Checking with space in between "<AccountNo>"
    Given User enter space in between "<AccountNo>" field
    Then User should be presented "Characters are not allowed" message

    Examples: 
      | AccountNo |
      | 12 285    |

  @BDDTEST-BANKAUTO-226
  Scenario Outline: Checking with special character in "<AccountNo>"
    Given User enter special Charater in "<AccountNo>" Field
    Then User should be presented "Special characters are not allowed" message

    Examples: 
      | AccountNo |
      | 12%285    |

  @BDDTEST-BANKAUTO-252
  Scenario Outline: Checking outline with valid data
    Given User enter valid "<AccountNo>","<Amount>","<Description>"
    When Click on submit button
    Then User should see "Account does not exist" alert message

    Examples: 
      | AccountNo | Amount | Description |
      |    122823 |   1000 | personal    |
