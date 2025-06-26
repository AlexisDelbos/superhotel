Feature: Ajouter un hotel

  Scenario: L'utilisateur ajoute plusieurs hotels
    Given l'hotel est sauvé
    And un autre hotel est aussi sauvé
    When l'utilisateur consulte la liste des hôtels
    Then la page est renvoyée avec un statut 200
    And la réponse contient les noms des hôtels
    And la réponse contient l'adresse de l'hôtel
    And la réponse contient les numéros de téléphone des hôtels

