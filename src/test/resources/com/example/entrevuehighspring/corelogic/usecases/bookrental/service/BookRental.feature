Feature: The renter must have the minimum age required for the book
   Scenario Outline: Le locataire qui est tros juene requis par le livre fait un demand
   Given a known book
     And a known underaged renter
     And a location with the known book available
    When the renter requests to rent the book
    Then an exception is thrown
     And the exception is of type TooYoungForRentalException