### Requirements Assumptions

- The `startGame` method should initialize the game score. This means that the scores for both the home team and away team should be set to `0` at the start of the game.

- The `startGame` method should add the game to the list of all registered games. In a real-world scenario, this action would likely be done before the game starts, but for the sake of this implementation, it will be handled within the `startGame` method.

- The `updateScore` method receives a pair of new scores for the game, not the differences from the previous score synchronization.

- The  `getGamesSummary` method returns results for all games, including active games.

- The  `getGamesSummary` removes from summary not started games.