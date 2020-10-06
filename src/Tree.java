import java.util.Arrays;

public class Tree {
    Tree(int type, int _maxDepth, Toolkit _tk){
        maxDepth = _maxDepth;
        tk = _tk;
        rawFitness = 0.0;
        standardizedFitness = 0.0;
        adjustedFitness = 0.0;
        normalizedFitness = 0.10; // set to 0.10 so that new children don't get replaced as easily
        hitsRatio = 0;

        if (type == 0){
            // Full
            Full();
            //System.out.println("Full Tree created.");
        }
        else if (type == 1){
            // Grow
            Grow();
            //System.out.println("Grow Tree created.");
        }
    }
    public Tree clone(){
        Tree toReturn = new Tree(1,maxDepth,tk);
        Node r = new FunctionNode(root.sValue);
        toReturn.root = r;
        cloneRecursive(r, root);
        return toReturn;
    }
    void cloneRecursive(Node n, Node thisTree){
        for (Node child:thisTree.children
             ) {
            Node tmp;
            if (Arrays.asList(tk.functionSet).contains(child.sValue)){
               tmp = new FunctionNode(child.sValue);
            }
            else
            {
                tmp = new TerminalNode(child.sValue);
            }

            n.children.add(tmp);
            cloneRecursive(tmp,child);
        }
    }
    void Grow(){
        String f = tk.getRandomFunction();
        root = new FunctionNode(f);
        int currHeight = 0;
        AddNodesRandom(root, currHeight);
    }

    void Full(){
        String f = tk.getRandomFunction();
        root = new FunctionNode(f);
        int currHeight = 1; // modified to reduce heap overflow?
        AddNodesFull(root, currHeight);
    }
    void AddNodesRandom(Node node, int currentDepth){
        if (Arrays.asList(tk.functionSet).contains(node.sValue) ){
            int numChildren = tk.getArity(node.sValue);
           // System.out.println(node.sValue + " "+ numChildren);
            for (int i = 0; i < numChildren; i++) {
                if (tk.rand.nextDouble()>0.5 || currentDepth >= maxDepth){
                    node.children.add(new TerminalNode(tk.getRandomTerminal()));
                }
                else{
                    Node tmp = new FunctionNode(tk.getRandomFunction());
                    node.children.add(tmp);
                    AddNodesRandom(tmp, currentDepth + 1);
                }
            }

        }
    }

    void AddNodesFull(Node node, int currentDepth){
        if (Arrays.asList(tk.functionSet).contains(node.sValue) ){
            int numChildren = tk.getArity(node.sValue);
            //System.out.println(node.sValue + " "+ numChildren);

            for (int i = 0; i < numChildren; i++) {
                if (currentDepth >= maxDepth) {
                    node.children.add(new TerminalNode(tk.getRandomTerminal()));
                } else {
                    Node tmp = new FunctionNode(tk.getRandomFunction());
                    node.children.add(tmp);
                    //System.out.println(tmp.sValue);
                    AddNodesFull(tmp, currentDepth + 1);
                }
            }

        }
    }

    String getTreeValue(String [] obj)
    {
        return root.getValue(obj, tk);

    }

    int getNumNodes(){
        return getNumNodesRecursive(root);
    }
    int getNumNodesRecursive(Node n){
        if (n.children.size() == 0){
            return 1;
        }
        int tmp = 1;
        for (Node child:n.children
             ) {
            tmp += getNumNodesRecursive(child);
        }
        return tmp;
    }
    Node getSubtree(int nodeNum){
        nodeNumberStorage = -1;
        if (nodeNum != 0){
            return getSubtreeRecursive(root,nodeNum);
        }
        else return root;
    }
    Node getSubtreeRecursive(Node n,int nodeNum){
        nodeNumberStorage++;
        if (nodeNumberStorage == nodeNum){
            return n;
        }
        for (Node child:n.children
        ) {
            Node tmp = getSubtreeRecursive(child, nodeNum);
            if (tmp != null){
                return tmp;
            }
        }
        return null;
    }

    void setSubtree(int nodeNum, Node newSubTree){
        nodeNumberStorage = -1;
        if (nodeNum != 0){
            nodeNumberStorage++;
            for (Node child:root.children
            ) {
                boolean tmp = setSubtreeRecursive(child, nodeNum, newSubTree);
                if (tmp == true){
                    root.children.set(root.children.indexOf(child), newSubTree);
                }
            }
        }
        else {
            root = newSubTree;
        }
    }
    boolean setSubtreeRecursive(Node n,int nodeNum,Node newSubTree){
        nodeNumberStorage++;
        if (nodeNumberStorage == nodeNum){
            return true;
        }
        for (Node child:n.children
        ) {
            boolean tmp = setSubtreeRecursive(child, nodeNum, newSubTree);
            if (tmp == true){
                n.children.set(n.children.indexOf(child), newSubTree);
            }
        }
        return false;
    }
    int getDepth(){
        return getDepthRecursive(root);
    }
    int getDepthRecursive(Node n){
        int highest = 0;
        for (Node c: n.children
             ) {
            int temp = getDepthRecursive(c);
            if (temp > highest){
                highest = temp;
            }
        }
        return highest+1;
    }

    Node root;
    int maxDepth;
    Toolkit tk;
    double rawFitness, standardizedFitness,adjustedFitness, normalizedFitness, accuracy;
    int hitsRatio;
    int nodeNumberStorage;
}
