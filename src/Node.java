import java.util.ArrayList;
import java.util.List;

public class Node {
    Node(){
        children = new ArrayList<Node>();
    }

    String sValue;
    List<Node> children;

    public String getValue(String [] obj, Toolkit tk)
    {
        return sValue;
    }
}
