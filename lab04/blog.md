Q1: What is the code smell present in this snippet? What is the design problem causing the smell? Write your answer in your blog.

all numbers present are number or string. it doesnt define a final and static constant to control the variable
string compare should use equal
if sentence is conflict with the return sentence he can just write
return grade != null && grade.getMark() >= 50 && grade.getGrade() != "FL" && grade.getGrade() != "UF";

Q2: Find this code, and in your blog note down the code smells you detected which led you to your conclusion.
violation of the Law of Demeter / Principle of Least Knowledge.
hasPassedCourse() function call lots of grade's function. This should be deal with the grade class instead of enrolment class . high coupling

Q3:In the enrolments codebase, there is a violation of the Liskov Substitution Principle.
CourseOffering class creat a private Course course. and it also extends Course. this will change the Course class's private member to public alternatively


Explain why the code is consistent with the preconditions and postconditions.
Explain why balance >= 0 is a class invariant for both classes. I.e., give a small informal proof of why this is always true if your preconditions are met.
Are your class definitions consistent with the Liskov Substitution Principle?

Through deposit and withdraw and the constructor throws an exception once the current balance becomes a negative number, it is determined that the balance is always >=0.
The subclass LoggedBankAccount can seamlessly replace the parent class BankAccount without violating the preconditions and postconditions of the parent class method.