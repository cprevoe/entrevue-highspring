Feature:  Le locataire demande à louer un livre
  
Scenario: Un adult locataire demande à louer un livre disponible à un endroit connu
  Given un adult locataire connu
    And un location connu
    And un livre connu
    And le livre est disponible
   When le locataire demande à louer le livre à l'endroit indiqué
   Then le livre n'est plus disponible
    And la réponse indique que le demande était accordée

Scenario: Un locataire trop jeune demande à louer un livre pour les adults disponible à un endroit connu
  Given un enfant locataire connu
    And un location connu
    And un livre pour les adults connu
    And le livre est disponible
   When le locataire demande à louer le livre à l'endroit indiqué
   Then il y a un exception
    And l'exception est un TooYoungForRentalException

Scenario: Un adult locataire demande à louer un livre pas disponible à un endroit connu
  Given un adult locataire connu
    And un location connu
    And un livre connu
    And le livre n'est pas disponible
   When le locataire demande à louer le livre à l'endroit indiqué
   Then le livre n'est plus disponible
    And la réponse indique que le demande était accordée
