import java.util.List;

public class TerminalNode extends Node{
    TerminalNode(String _value){
        sValue = _value;

    }
    public String getValue(String[] obj, Toolkit tk, List<Node> roots, int numCalls, int maxCalls) {
        if (numCalls> maxCalls){
            return "F"; // Tree failure
        }
        switch (sValue){ //Added the max of 5. Not all 5 will always be relevant
            case "Func1":
                return roots.get(1).getValue(obj, tk, roots, numCalls+1, maxCalls);
            case "Func2":
                return roots.get(2).getValue(obj, tk, roots, numCalls+1, maxCalls);
            case "Func3":
                return roots.get(3).getValue(obj, tk, roots, numCalls+1, maxCalls);
            case "Func4":
                return roots.get(4).getValue(obj, tk, roots, numCalls+1, maxCalls);
            case "Func5":
                return roots.get(5).getValue(obj, tk, roots, numCalls+1, maxCalls);
            default:
                return sValue;
        }
    }
}
