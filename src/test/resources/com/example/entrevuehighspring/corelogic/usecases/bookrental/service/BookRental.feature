Feature: Book Rental

  As a rental chain
  I want to track clients, books, locations, and inventory 
  So that I can rent books to clients when it is safe to do so.


  Scenario: Le locataire qui est tros juene requis par le livre fait un demand
  Given a known book
    And a known underaged renter
    And a location with the known book available
   When the renter requests to rent the book
   Then an exception is thrown
    And the exception is of type TooYoungForRentalException


  Scenario: BookNotAvailableException quand le livre n'est pas disponible (déjà loué)
  Given a known book
    And a known renter
    And a location with the known book unavailable
   When the renter requests to rent the book
   Then an exception is thrown
    And the exception is of type BookNotAvailableException


  Scenario: Le livre doit etre disponible (pas déjà loué)
  Given a known book
    And a known renter
    And a known second renter
    And a location with the known book available
   When the renter requests to rent the book
    And the second renter requests to rent the book
   Then an exception is thrown
    And the exception is of type BookNotAvailableException


  Scenario: Locataire éligible + livre disponible --> Location créée
  Given a known book
    And a known renter
    And a location with the known book available
   When the renter requests to rent the book
   Then there was no exception
    And The book was rented successfully

  Scenario: Locataire éligible + livre disponible -> Email Envoyée
  Given a known book
    And a known renter
    And a location with the known book available
   When the renter requests to rent the book
   Then there was no exception
    And The book was rented successfully
    And an email was queued