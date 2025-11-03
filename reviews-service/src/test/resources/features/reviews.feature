Feature: Gestión de Reseñas

  Background:
    Given existen reseñas registradas en el sistema

  Scenario: Listar todas las reseñas
    When el cliente solicita la lista de reseñas
    Then el sistema devuelve una lista con todas las reseñas registradas

  Scenario: Listar reseñas de un psicólogo específico
    Given el psicólogo con id 1 tiene reseñas registradas
    When el cliente solicita las reseñas del psicólogo con id 1
    Then el sistema devuelve solo las reseñas asociadas a ese psicólogo

  Scenario: Buscar reseña específica
    Given existe una reseña con id 5
    When el cliente solicita la reseña con id 5
    Then el sistema devuelve la reseña correspondiente
