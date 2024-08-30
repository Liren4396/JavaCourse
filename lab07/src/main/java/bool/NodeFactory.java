package bool;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.json.JSONObject;

public class NodeFactory {
    public static BooleanNode createFromJson(JSONObject json) {
        String nodeType = (String) json.get("node");
        
        if ("and".equals(nodeType) || "or".equals(nodeType) || "not".equals(nodeType)) {
            nodeType = nodeType.toUpperCase();
            // If it's a compound expression (AND, OR, or NOT), create a CompoundNode.
            BooleanNode subnode1 = createFromJson((JSONObject) json.get("subnode1"));
            BooleanNode subnode2 = createFromJson((JSONObject) json.get("subnode2"));
            return new CompoundNode(subnode1, subnode2, nodeType);
        } else if ("value".equals(nodeType)) {
            // If it's a leaf node representing true or false, create a LeafNode.
            boolean value = (boolean) json.get("value");
            return new LeafNode(value);
        }
        return null;
    }
    public static void main(String[] args) {
        testCreateFromJson();
    }
    public static void testCreateFromJson() {
        JSONObject json = new JSONObject();
        json.put("node", "and");
        
        JSONObject subnode1 = new JSONObject();
        subnode1.put("node", "or");
        
        JSONObject subnode2 = new JSONObject();
        subnode2.put("node", "value");
        subnode2.put("value", true);
        
        subnode1.put("subnode1", subnode2);
        
        subnode2 = new JSONObject();
        subnode2.put("node", "value");
        subnode2.put("value", false);
        
        subnode1.put("subnode2", subnode2);
        
        json.put("subnode1", subnode1);
        
        subnode2 = new JSONObject();
        subnode2.put("node", "value");
        subnode2.put("value", true);
        
        json.put("subnode2", subnode2);

        BooleanNode node = createFromJson(json);
        
        System.out.println(node.prettyPrint());
        System.out.println(node.evaluate());
    }
}
