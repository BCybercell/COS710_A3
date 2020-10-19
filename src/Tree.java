import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Tree {
    Tree(int type, int _maxDepth, Toolkit _tk , int _maxCalls, int _numFunctionTrees){
        maxDepth = _maxDepth;
        tk = _tk;
        numCalls = 0;
        maxCalls = _maxCalls;
        numFunctionTrees = _numFunctionTrees;
        rawFitness = 0.0;
        standardizedFitness = 0.0;
        adjustedFitness = 0.0;
        normalizedFitness = 0.10; // set to 0.10 so that new children don't get replaced as easily
        hitsRatio = 0;
        trainCE = 0;
        testCE = 0;
        avTrainCE = 0;

        root = new ArrayList<>();

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
        Tree toReturn = new Tree(1, maxDepth ,tk, maxCalls, numFunctionTrees );
        int count = 0;
        for (Node r:root
             ) {
            Node newRoot = new FunctionNode(r.sValue);
            toReturn.root.set(count++, newRoot);
            cloneRecursive(newRoot, r);
        }

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

        for (int i = -1; i < numFunctionTrees; i++) { // -1 is for the main
            String f = tk.getRandomFunction();
            root.add(new FunctionNode(f)); //main?
            int currHeight = 0;
            boolean isMain = false;
            if (i == -1){
                isMain = true;
            }
            AddNodesRandom(root.get(i+1), currHeight, isMain);
        }


    }

    void Full(){
        for (int i = -1; i < numFunctionTrees; i++) { // -1 is for the main
            String f = tk.getRandomFunction();
            root.add(new FunctionNode(f)); //main?
            int currHeight = 1; // modified to reduce heap overflow?

            AddNodesFull(root.get(i+1), currHeight, false); //Does not matter since full is not used
        }

    }
    void AddNodesRandom(Node node, int currentDepth, boolean isMain){
        if (Arrays.asList(tk.functionSet).contains(node.sValue) ){
            int numChildren = tk.getArity(node.sValue);
           // System.out.println(node.sValue + " "+ numChildren);
            for (int i = 0; i < numChildren; i++) {
                if (tk.rand.nextDouble()>0.5 || currentDepth >= maxDepth){
                    node.children.add(new TerminalNode(tk.getRandomTerminal(isMain, numFunctionTrees)));
                }
                else{
                    Node tmp = new FunctionNode(tk.getRandomFunction());
                    node.children.add(tmp);
                    AddNodesRandom(tmp, currentDepth + 1, isMain);
                }
            }

        }
    }

    void AddNodesFull(Node node, int currentDepth, boolean isMain){
        if (Arrays.asList(tk.functionSet).contains(node.sValue) ){
            int numChildren = tk.getArity(node.sValue);
            //System.out.println(node.sValue + " "+ numChildren);

            for (int i = 0; i < numChildren; i++) {
                if (currentDepth >= maxDepth) {
                    node.children.add(new TerminalNode(tk.getRandomTerminal(isMain, numFunctionTrees)));
                } else {
                    Node tmp = new FunctionNode(tk.getRandomFunction());
                    node.children.add(tmp);
                    //System.out.println(tmp.sValue);
                    AddNodesFull(tmp, currentDepth + 1, isMain);
                }
            }

        }
    }

    String getTreeValue(String [] obj)
    {
        return root.get(0).getValue(obj, tk, root, 0, maxCalls);

    }

    int getNumNodes(){
        int total = 0;
        for (Node r:root
             ) {
            total += getNumNodesRecursive(r);
        }
        return total;
    }

    int getNumNodes(int randSubTree){

        return getNumNodesRecursive(root.get(randSubTree));
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
   // TODO: adding tree index keeps tree typing. Since no ARG present no node typing needed
    Node getSubtree(int nodeNum, int treeIndex){ //Tree index for selecting main of function
        nodeNumberStorage = -1;
        if (nodeNum != 0){
            return getSubtreeRecursive(root.get(treeIndex),nodeNum);
        }
        else return root.get(treeIndex);
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

    void setSubtree(int nodeNum, Node newSubTree, int treeIndex){
        Node tempRoot = root.get(treeIndex);
        nodeNumberStorage = -1;
        if (nodeNum != 0){
            nodeNumberStorage++;
            for (Node child:tempRoot.children
            ) {
                boolean tmp = setSubtreeRecursive(child, nodeNum, newSubTree);
                if (tmp){
                    tempRoot.children.set(tempRoot.children.indexOf(child), newSubTree);
                }
            }
        }
        else {
            root.set(treeIndex, newSubTree);
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
            if (tmp){
                n.children.set(n.children.indexOf(child), newSubTree);
            }
        }
        return false;
    }

    int getDepth(){
        int highest = 0;
        for (Node r: root
             ) {
            int temp = getDepthRecursive(r);
            if (temp > highest){
                highest = temp;
            }
        }
        return highest;
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

    List<Node> root;
    int maxDepth;
    Toolkit tk;
    double rawFitness, standardizedFitness,adjustedFitness, normalizedFitness, accuracy;
    int hitsRatio, nodeNumberStorage, numCalls, maxCalls, numFunctionTrees;
    long trainCE, testCE, avTrainCE;
}
