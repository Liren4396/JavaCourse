Bool

Task 1) Composite Pattern

Q1: What are the compound nodes and the leaf nodes in this problem? Write the answer in your blog post.

A leaf node is a terminal node in a Boolean expression tree whose value is Boolean true or false
Compound nodes are those nodes that contain child nodes. They represent the logical combination part of a Boolean expression, often including logical operators such as AND, OR, and NOT to combine child nodes.

Q2: Will you use keep the BooleanNode as an abstract class or use an interface to represent the composite type?
use interface and split into compound class and leaf class.

Q3: In our factory, what are the different types of objects we need to create? What are the different fields they will have?