package bool;

import javax.swing.text.StyledEditorKit.BoldAction;

public class BooleanEvaluator {
    public static boolean evaluate(BooleanNode expression) {
        return expression.evaluate();
    }

    public static String prettyPrint(BooleanNode expression) {
        // Pretty print the expression
        return expression.prettyPrint();
    }

    public static void main(String[] args) {
        BooleanNode leafTrue = new LeafNode(true);
        BooleanNode leafFalse = new LeafNode(false);

        BooleanNode node1 = new CompoundNode(leafTrue, leafFalse, "AND");
        System.out.println(node1.evaluate());
        System.out.println(node1.prettyPrint());
        
        BooleanNode notNode = new CompoundNode(leafFalse, "NOT");
        BooleanNode node2 = new CompoundNode(leafFalse, notNode, "OR");
        System.out.println(node2.evaluate());
        System.out.println(node2.prettyPrint());

        BooleanNode orNode = new CompoundNode(leafTrue, leafFalse, "OR");
        BooleanNode andNode = new CompoundNode(leafFalse, orNode, "AND");
        BooleanNode notNode1 = new CompoundNode(andNode, "NOT");
        BooleanNode node3 = new CompoundNode(leafTrue, notNode1, "OR");

        System.out.println(node3.evaluate());
        System.out.println(node3.prettyPrint());
        // BooleanEvaluator.evaluate(...)
        // BooleanEvaluator.evaluate(...)
    }

}
