## Week 5
- Read over spec, look af code repo and familiarise 
## Week 6
- start planning 
- looking at task 1 questions and starting code analysing 
## Week 7
- completed parts of task 1: 
    - did parts of 1)iii)
    - did some parts of c)ii)
    - completed d)ii)

- started thinking about choice for task 2, will probably choose buildables 
- started planning (notes): 
    sun stone: 
- can be used interchangeably with keys or treasure when building 
    - need to create new collectable 
    - change buildables to hold this 
- can be used as a key 
    - need to hold this in door and in key??
    - can be used to open any door???
- existing keys or treasures should be used first before using this
- counts towards treasure goal 
- when used to open door or building, it is not destroyed 
- when used as a listed ingredient in crafting it is destroyed (when used as sunstone rather than used as key or treasure)

    sceptre: 
- buildable: (1 wood OR 2 arrows) + (1 key OR 1 treasure) + (1 sun stone).
- mind controls mercenaries and assassins 
- no distance constraint 
- lasts for certain num of ticks 
    - starts on tick when it is used
    - eg: 2 ticks:
        - Tick 1 - player's turn: Player mind controls enemy.
        - Tick 1 - enemy's turn: Enemy is mind controlled.
        - Tick 2 - player's turn: Player moves.
        - Tick 2 - enemy's turn: Enemy is mind controlled.
        - Tick 3 - player's turn: Player moves.
        - Tick 3 - enemy's turn: Enemy is no longer mind controlled.

    midnight armour: 
- buildable: sword + sun stone 
- only buildable if no zombies in dungeon 
- extra attack and protection 
- lasts forever 

## Week 8
- completed task 2)a) 
    - did the zombie part of the goal 
    - tested zombie spawner behaviour and enemy goal behaviour
- started task 2 implementation 
## Week 9
- finished task 2 implemetation
- tested and trialled behaviour
- linted whole database (all code)
- completed task 3 open refactoring 
    - zombie spawn bug 
    - fixed player not being able to pick up more than one key bug 
    - fixed mercenary should be bribed within manhattan distance 
