Task 1
Q1: Why is this approach considered good design? What principles does it makes use of?

Ensure code is loosely coupled and highly cohesive.
Encapsulation and abstraction can effectively hide the complexity of implementation, isolate the variability of implementation, provide stable and easy-to-use abstract interfaces for dependent modules, and increase readability.

Q2: What is meant by the term "black-box"? How are the tests inside BookingSystemTest black-box?

1. Black box means treating the program as a black box that cannot be opened, and testing is performed at the program interface without considering the internal structure and internal characteristics of the program.
2. Black box testing is conducted from the user's perspective, starting from the corresponding relationship between input data and output data.

Task 2
Q3: What does this method do? What does it return, and what side effects does it have?
1.This method is checking whether there is a empty room if anyone wants to make a revservation. 
2.return type is boolen and true if there is a room for the given preferences or false if there is no room for the given preferences.
3.If the last room is left and two people are booking it, and the first person is already booking, the system will directly tell the second person that the room is full, even though the first person may not have brought enough money because Instead, choose a hotel on other websites to book.


Q4: In your opinion, which is better quality code, Code A or B? Justify your answer.

Code B
1.Readable
2.Extensibility: Code B is easier to expand if new room types need to be added in the future.
3.In code A if the room type is not one of the the provided three, it will still add a null room into the whole rooms.This will make error when we do other methods like makepoint 

Task 3
Q5: What are some code smells (features of the code that make it poor quality) present in this method?

The whole thing looks too messy. Java provides the instanceof interface to divide the logic very clearly.

Task 4
Q6: Note down all of the code smells you can see.

the three child classes have the same code part. And the better way to
modify is remove the same to their parent class(more readable and clear.
better for people to modify in the future)

Q7: Reflect on your thought process, the steps you took to refactor and how you have improved the design of the system.
1. change room from interface to class because there is only one parent class.
2. copy the book() and to tojson() to room class. delete them in three child class(we dont need to write three same code. we can just call the parent class to approach the same method.)
3. change the printwelcome to abstract because this is the only one different is the three child class. each child class must print their own print method

Reflections
When writing code, at least you should be able to understand the code you write when you read it in the future, right?