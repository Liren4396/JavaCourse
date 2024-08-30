# Assignment II Pair Blog Template

## Task 1) Code Analysis and Refactoring ⛏️

### a) From DRY to Design Patterns

[Links to your merge requests](/put/links/here)

> i. Look inside src/main/java/dungeonmania/entities/enemies. Where can you notice an instance of repeated code? Note down the particular offending lines/methods/fields.

In Mercenary.java, the method -- 'interact' has the same logic as move method (it checks whether it's adjacent to the player).
The logic for choosing the next position to move to in the move method is duplicated in two different places. It first checks for invincibility and then for other cases. The same logic can be found in the Mercenary class as well. It would be better to extract this logic into a common method to eliminate code repetition.
ZombieToast.java, Spider.java and Mercenary.java all have DEFAULT_ATTACK and DEFAULT_HEALTH which could be moved to Enemy. 

> ii. What Design Pattern could be used to improve the quality of the code and avoid repetition? Justify your choice by relating the scenario to the key characteristics of your chosen Design Pattern.

To improve the quality of the code and avoid repetition in this scenario, the "Strategy" design pattern could be utilized. The Strategy pattern is suitable because it allows defining a family of algorithms, encapsulates each one of them, and makes them interchangeable. This pattern fits well in situations where you have multiple variations of behavior encapsulated in classes and need to switch between them at runtime.
Strategy mode will help encapsulate these different movement strategies such as player status, potion effects, etc.
[Answer]

> iii. Using your chosen Design Pattern, refactor the code to remove the repetition..


TODO: ....
[Briefly explain what you did]


b) Observer Pattern (5 marks)
Identify one place where the Observer Pattern is present in the codebase, and outline how the implementation relates to the key characteristics of the Observer Pattern.

ZombieToastSpawner 
In the onDestroy method in Enemy.java, the game.unsubscribe method is called, implementing the Observer method. 
TODO: .... need more sentence
.........

c) Inheritance Design (6 marks)
i. Name the code smell present in the above code. Identify all subclasses of Entity which have similar code smells that point towards the same root cause.

Empty Implementation
There is no specific logic code in the method, but just returns a null value.

Boulder --- onMovedAway, onDestroy
Door -- onMovedAway, onDestroy
Exit -- onOverlap, onMovedAway, onDestroy
Player -- onMovedAway, onDestroy
Portal -- onMovedAway, onDestroy
Switch -- onDestroy
Wall -- onOverlap, onMovedAway, onDestroy

ii. Redesign the inheritance structure to solve the problem, in doing so remove the smells.

Remove all the abstract attributes in Entity so that For Exit and other subclasses, override methods that require specific behavior. For those subclasses that do not require specific behavior, they will inherit the default behavior from the Entity class.

d) More Code Smells (6 marks)
The previous engineering team has left you with the following note:

Collectable entities are a big problem. We tried to change the way picking up items is handled, to be done at the player level instead of within the entity itself but found that we had to start making changes in heaps of different places for it to work, so we abandoned it.

i. What code smell is present in the above description?

the violation of the Single Responsibility Principle (SRP)
The Collectable class directly handles the player's pickUp operation. This is a bad design because it will cause the Collectable class to be tightly coupled with the Player class, increasing the fragility of the code and reducing maintainability.

ii. Refactor the code to resolve the smell and the underlying problem causing it.

Lots of repeated code among collectables, common methods were collected and put into a new class Collectable. These methods come from the classes Arrow, Key, Potion, Wood, Sword and Treasure. The canMoveTo method for these classes were the same as well as the method onOverlap.
This new class Collectable extends Entity as the classes did and implements Inventory Item. The onOverlap method was changed to call the onOverlap method for Player which then handles the picking up of items that was previously in the Collectable items. This way the responsibility is on Player rather than the Collectable. 

e) Open-Closed Goals (6 marks)
Look inside the goals package at the code provided.
i. Do you think the design is of good quality here? Do you think it complies with the open-closed principle? Do you think the design should be changed?

low quality.
Does not comply with the open-closed principle, which states that software entities should be open for extension, but closed for modification. Although it provides a mechanism to create targets of different types, every time a new target type is introduced or an existing type needs to be modified, changes need to be made directly in the class. This violates the principle and need to be changed

ii. If you think the design is sufficient as it is, justify your decision. If you think the answer is no, pick a suitable Design Pattern that would improve the quality of the code and refactor the code accordingly.

Use strategic design patterns and composite patterns. first pattern allows behavior to be encapsulated in a separate class and allows behavior to be selected at runtime.Second pattern allows you to combine objects into a tree structure to represent a part-whole hierarchy.



f) Open Refactoring (10 marks)
Make any other refactoring improvements you see fit to the codebase. This can include resolving Design Smells, using Design Patterns discussed or any other general improvements to the health and quality of the code.

Improve the state pattern of PlayerState.


Task 2

question A:
something add : 
    goals dir --> EnemyGoal.java && one case in GoalFactory.java

    entities dir --> Player.java: private int defeatedEnemy = 0; and functions with it.

    dungeonmania dir --> GameBuilder.java --> buildMap function: FILE if (jsonEntity.getString("type").equals("zombie_toast_spawner")) {
        game.increaseSpawners();
    }

    dungeonmania dir --> Game.java: private int spawners = 0; and some functions with it.

    enemies dir --> ZombieToastSpawner.java :   @Override
    public void onDestroy(GameMap map) {
        g.decreaseSpawners();
    }

Created Test Files:
    src/test/resources/dungeons/d_basicGoalsTest_enemy.json
    src/test/resources/configs/c_enemyGoalsTest.json
    src/test/java/dungeonmania/mvp/task2/EnemyGoalsTest.java
    src/main/resources/configs/enemyGoalTest_c.json
    src/main/resources/dungeons/enemyTest.json

Task 3  (kushi)
While testing i found that the functionality for zombietoastspawner was not being testing correctly. In the provided test for the zombie toast spawnerm the spawner is destroyed but the test does not account for this, instead, it asserts that the spawner is still there. interact(player, game) was taking in a weapon but wasnot destroying the spawner. the code has been changed to fix this and reflect the actual required implementation. That is, the spawner is destroyed when interacted with a weapon. 

the spec says that the player can only carry one key at a time, however the current implementation allows the player to pick up multiple. The code was refactored to reflect this requirement, the player can now only hold one key. 

the spec also specifies that the mercenaries should be bribed if they are within manhattan distance to the player, while the current implementation bribes the merc when briberadius >= 0. the code was refactored to reflect this behaviour.  