Feature: Tests for Exchange rates API  
User want to test the response of rates API for current and previous dates.

  Scenario: Test Rates API for Latest Foreign Exchange rates
    Given User set "valid" API endpoint
     When User set the "latest" date
      And Send GET HTTP request  
     Then API call gets success with status code as "200" 
      And "base" in response body is "EUR"
      And "date" in response body is "latest date"
      And currencies exists in "rates"
  
  Scenario: Test Rates API for Invalid login
    Given User set "valid" API endpoint
     When User set the "incorrect format" date
      And Send GET HTTP request  
     Then API call gets success with status code as "400" 
      And "error" in response body is "time data 'late' does not match format '%Y-%m-%d'"
  
  Scenario: Test Rates API for Specific Date Foreign Exchange rates
    Given User set "valid" API endpoint
     When User set the "specific" date as year"2020" month"01" date"01"
      And Send GET HTTP request  
     Then API call gets success with status code as "200" 
      And "base" in response body is "EUR"
      And "date" in response body is "specific date"
      And currencies exists in "rates"
