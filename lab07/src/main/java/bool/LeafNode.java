package bool;

public class LeafNode implements BooleanNode {
    private boolean expression;

    public LeafNode(boolean value) {
        this.expression = value;
    }

    @Override
    public boolean evaluate() {
        return expression;
    }

    @Override
    public String prettyPrint() {
        return Boolean.toString(expression);
    }
    
}
