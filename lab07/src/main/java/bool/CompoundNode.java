package bool;

public class CompoundNode implements BooleanNode {
    private BooleanNode left;
    private BooleanNode right;
    private String expression;

    public CompoundNode(BooleanNode right, String operator) {
        if (operator == "NOT") {
            this.right = right;
            this.expression = operator;
        }
    }
    public CompoundNode(BooleanNode left, BooleanNode right, String operator) {
        this.left = left;
        this.right = right;
        this.expression = operator;
    }

    @Override
    public boolean evaluate() {
        if (expression.equals("AND")) {
            return left.evaluate() && right.evaluate();
        } else if (expression.equals("OR")) {
            return left.evaluate() || right.evaluate();
        } else if (expression.equals("NOT")) {
            return !right.evaluate();
        }
        return false;
    }

    @Override
    public String prettyPrint() {
        if (expression.equals("NOT")) {
            return "(" + expression + " " + right.prettyPrint() + ")";
        } else {
            return "(" + expression + " " + left.prettyPrint() + " " + right.prettyPrint() + ")";
        }
    }

}
