import javax.swing.tree.TreeNode;
import java.util.*;

/**
 * Created by ssundaram on 3/11/18.
 */
public class Trees {

    private static class BSTIterator{
        Stack<Node> nodeStack;
        public BSTIterator(Node root){
            nodeStack = new Stack<>();
            pushAll(root);
        }

        private int next(){
            Node node = nodeStack.pop();
            if(node.right != null){
                pushAll(node.right);
            }
            return node.data;
        }

        private boolean hasNext(){
            return !nodeStack.isEmpty();
        }

        private void pushAll(Node root) {
            while(root != null){
                nodeStack.push(root);
                root = root.left;
            }
        }
    }

        public Node trimBST(Node root, int L, int R) {
            if (root == null) return null;

            if (root.getData() < L) return trimBST(root.right, L, R);
            if (root.getData() > R) return trimBST(root.left, L, R);

            root.left = trimBST(root.left, L, R);
            root.right = trimBST(root.right, L, R);

            return root;
        }

    public List<Double> averageOfLevels(Node root) {
        List<Double> result = new ArrayList<>();
        Queue<Node> q = new LinkedList<>();

        if(root == null) return result;
        q.add(root);
        while(!q.isEmpty()) {
            int n = q.size();
            double sum = 0.0;
            for(int i = 0; i < n; i++) {
                Node node = q.poll();
                sum += node.getData();
                if(node.left != null) q.offer(node.left);
                if(node.right != null) q.offer(node.right);
            }
            result.add(sum / n);
        }
        return result;
    }

    private static boolean uniVal(Node node, int[] count){
        if(node == null){
            return true;
        }
        boolean isLeft = uniVal(node.left, count);
        boolean isRight = uniVal(node.right, count);
        if(!isLeft || !isRight){
            return false;
        }
        if(node.left != null && node.left.data != node.data){
            return false;
        }

        if(node.right != null && node.right.data != node.data){
            return false;
        }
        count[0]++;
        return true;
    }
    private static void rob(Node node){



    }


//
//              6
//           /      \
//           3          5
//       /   \          \
//       2     5          4
//           /   \
//           7     4
//    There are 4 leaves, hence 4 root to leaf paths:
//    Path                    Number
//  6->3->2                   632
//          6->3->5->7               6357
//          6->3->5->4               6354
//          6->5>4                    654
//    Answer = 632 + 6357 + 6354 + 654 = 13997

    public static int sumNumbers(Node root) {
        return sum(root, 0);
    }

    public static int sum(Node n, int s){
        if (n == null) return 0;
        if (n.right == null && n.left == null) return s*10 + n.data;
        return sum(n.left, s*10 + n.data) + sum(n.right, s*10 + n.data);
    }

    public static int sumNumbers1(Node root) {
        if (root == null)
            return 0;
        return sumR(root, 0);
    }
    public static int sumR(Node root, int x) {
        if (root.right == null && root.left == null)
            return 10 * x + root.data;
        int val = 0;
        if (root.left != null)
            val += sumR(root.left, 10 * x + root.data);
        if (root.right != null)
            val += sumR(root.right, 10 * x + root.data);
        return val;
    }

    int treePathsSumUtil(Node node, int val)
    {
        // Base case
        if (node == null)
            return 0;

        // Update val
        val = (val * 10 + node.data);

        // if current node is leaf, return the current value of val
        if (node.left == null && node.right == null)
            return val;

        // recur sum of values for left and right subtree
        return treePathsSumUtil(node.left, val)
                + treePathsSumUtil(node.right, val);
    }

    // A wrapper function over treePathsSumUtil()
    int treePathsSum(Node node)
    {
        // Pass the initial value as 0 as there is nothing above root
        return treePathsSumUtil(node, 0);
    }

    private static Node flipTree(Node curr, Node prev){
        //left most become root
        if(curr == null){
            return null;
        }
        if(curr.left == null && curr.right == null){
            return curr;
        }
        Node flippedRoot = flipTree(curr.left, prev);
        curr.left.left = curr.right;
        curr.left.right = curr;
        curr.left = null;
        curr.right = null;
        return flippedRoot;
    }

    private static Node addRow(Node node, int v, int d, int level){
        if(node == null  && level != 1){
            return null;
        }
        if(node == null  && level == 1){
            return new Node(v);
        }
        //create the node as new root node for the tree
        if(d == 1){
            Node newRoot = new Node(v);
            newRoot.left = node;
            return newRoot;
        }
        //for each non null node at d-1
            addRow(node.left, v, d, level+1);
            addRow(node.right, v, d, level+1);

        if(level == d -1){
            if(node != null){
                Node leftSub = node.left;
                Node rightSub = node.right;
                Node newleftSub = new Node(v);
                Node newRightSub = new Node(v);
                node.left = newleftSub;
                node.right = newRightSub;
                newleftSub.left = leftSub;
                newRightSub.right = rightSub;

            }
            return node;
        }
        return null;

    }



    private static void printLevelOrder(Node node) {
        Queue<Node> queue = new LinkedList<Node>();
        queue.add(node);
        while (!queue.isEmpty()) {

            /* poll() removes the present head.
            For more information on poll() visit
            http://www.tutorialspoint.com/java/util/linkedlist_poll.htm */
            Node tempNode = queue.poll();
            System.out.print(tempNode.data + " ");

            /*Enqueue left child */
            if (tempNode.left != null) {
                queue.add(tempNode.left);
            }

            /*Enqueue right child */
            if (tempNode.right != null) {
                queue.add(tempNode.right);
            }
        }
    }

    private static Node invert(Node node){
        if(node == null){
            return null;
        }
        Node temp = node.getLeft();
        node.left = invert(node.right);
        node.right = invert(temp);
        System.out.println("After Inverting tree");
        return node;

    }


    private static Node invert1(Node node){
        if(node == null){
            return null;
        }
        Node invertedLeft = invert(node.left);
        Node invertedRight = invert(node.right);
        node.left = invertedRight;
        node.right = invertedLeft;

        System.out.println("After Inverting tree");
        return node;

    }

    private static void printLevelOrder1(Node node){
        if(node != null){
            Queue<Node> q = new LinkedList<>();
            q.offer(node);
            while(!q.isEmpty()){
                int n = q.size();
                for(int i=0; i< n ; i++){
                    Node curr = q.poll();
                    if(curr.getLeft() != null){
                        q.offer(curr.getLeft());
                    }
                    if(curr.getRight() != null){
                        q.offer(curr.getRight());
                    }
                    System.out.print(curr.getData()+ " ");
                }
                System.out.println("");
                System.out.println("==============================");


            }

        }

    }

    private static void printLevelOrderHelper(){

    }


    private void deserialize(String s){

        Queue<String> nodes = new LinkedList<>();
        nodes.addAll(Arrays.asList(s.split(",")));
        desirializer(nodes);

    }

    private Node desirializer(Queue q){

        if("N".equals(q.remove())){
            return null;
        }
        Node node = new Node(Integer.parseInt((String)q.remove()));
        node.left = desirializer(q);
        node.right = desirializer(q);
        return node;
    }

    private static void serialize(Node node, StringBuilder stringBuilder){
        if(node == null){
            stringBuilder.append("N").append(",");
            return;
        }
        stringBuilder.append(node.data).append(",");
        serialize(node.getLeft(), stringBuilder);
        serialize(node.getRight(), stringBuilder);
    }




    private static int minDepth(Node node){
        if(node == null){
            return 0;
        }
        return 1+ Math.min(minDepth(node.getLeft()), minDepth(node.getRight()));

    }




    static class Node {
            private int data;
            private Node left, right;

            public Node(int data) {
                this.data = data;
            }

            public int getData() {
                return data;
            }

            public void setData(int data) {
                this.data = data;
            }

            public Node getLeft() {
                return left;
            }

            public void setLeft(Node left) {
                this.left = left;
            }

            public Node getRight() {
                return right;
            }

            public void setRight(Node right) {
                this.right = right;
            }

            @Override
            public String toString(){
                return ""+data;
            }

        }



        // https://articles.leetcode.com/print-edge-nodes-boundary-of-binary/
        private static void boundaryTraversal(Node root) {
            if (root != null) {
                System.out.println(root.getData());
            }

            printLeftBoundaryInTopDownOrder(root.getLeft());

            printLeafNodesInLeftToRightOrder(root.getLeft());
            printLeafNodesInLeftToRightOrder(root.getRight());

            printRightBoundaryInBottomUpOrder(root.getRight());
        }

        private static void printLeftBoundaryInTopDownOrder(Node root) {
            if (root == null || isLeafNode(root)) {
                return;
            }

            System.out.println(root.getData());

            printLeftBoundaryInTopDownOrder(root.getLeft());
        }

        private static void printLeafNodesInLeftToRightOrder(Node root) {
            if (root == null) {
                return;
            }

            if (isLeafNode(root)) {
                System.out.println(root.getData());
                return;
            }

            printLeafNodesInLeftToRightOrder(root.getLeft());

            printLeafNodesInLeftToRightOrder(root.getRight());
        }

        private static void printRightBoundaryInBottomUpOrder(Node root) {
            if (root == null || isLeafNode(root)) {
                return;
            }

            printRightBoundaryInBottomUpOrder(root.getRight());

            System.out.println(root.getData());
        }

        private static boolean isLeafNode(Node root) {
            return root.getLeft() == null && root.getRight() == null;
        }


//    public Node trimBST(Node root, int L, int R) {
//        if (root == null) return null;
//
//        if (root.data < L) return trimBST(root.right, L, R);
//        if (root.data > R) return trimBST(root.left, L, R);
//
//        root.left = trimBST(root.left, L, R);
//        root.right = trimBST(root.right, L, R);
//
//        return root;
//    }

//    private static int widthOfBinTree(Node root){
//
//    }
//
//    private static int widthOfBinTreeHelper(int level, List<Integer> start, List<Integer> end, int index){
//
//    }

    private static Node trim(int low, int high, Node node){
        if(node == null){
            return null;
        }
        if(node.data > high){
            return trim(low, high, node.left);
        }
        if(node.data < low){
            return trim(low, high, node.right);
        }
        node.left = trim(low, high, node.left);
        node.right = trim(low, high, node.right);
        return node;
    }

    private static Node buildTree1() {
        final Node root = new Node(2);
        final Node l1 = new Node(1);
        final Node r1 = new Node(3);


        root.setLeft(l1);
        root.setRight(r1);
        return root;
    }

    //            1
    //            / \
    //            2   3
    //            / \
    //            4   5
    //    Print: [4 5 3], [2], [1]
    private static Node buildTreeImbal() {
        final Node root = new Node(1);
        final Node l1 = new Node(2);
        final Node r1 = new Node(3);
        final Node ll2 = new Node(4);
        final Node lr2 = new Node(5);


        root.setLeft(l1);
        root.setRight(r1);
        l1.setLeft(ll2);
        l1.setRight(lr2);
        return root;
    }

    private static Node buildTree() {
        final Node root = new Node(1);
        final Node l1 = new Node(2);
        final Node r1 = new Node(3);
        final Node ll2 = new Node(4);
        final Node lr2 = new Node(5);
        final Node rl2 = new Node(6);
        final Node rr2 = new Node(7);

        root.setLeft(l1);
        root.setRight(r1);
        l1.setLeft(ll2);
        l1.setRight(lr2);
        r1.setLeft(rl2);
        r1.setRight(rr2);
        return root;
    }

    private static Node buildTree3() {
        final Node root = new Node(5);
        final Node l1 = new Node(4);
        final Node r1 = new Node(5);
        final Node ll2 = new Node(1);
        final Node lr2 = new Node(1);
        final Node rl2 = new Node(6);
        final Node rr2 = new Node(5);

        root.setLeft(l1);
        root.setRight(r1);
        l1.setLeft(ll2);
        l1.setRight(lr2);
        r1.setLeft(rl2);
        r1.setRight(rr2);
        return root;
    }

    private static Node buildBST() {
         Node root = new Node(4);
        insert( root, 2);
        insert( root, 3);
        insert( root, 1);
        insert( root, 6);
        insert( root, 5);
        insert( root, 7);


        return root;
    }
//
//                 5
//                /  \
//               2    4
//             /  \
//            1    3


    private static Node buildBST1() {
        final Node root = new Node(5);
        final Node l1 = new Node(2);
        final Node r1 = new Node(4);
        final Node ll2 = new Node(1);
       final Node lr2 = new Node(3);
//        final Node rl2 = new Node(6);
//        final Node rr2 = new Node(5);

        root.setLeft(l1);
        root.setRight(r1);
        l1.setLeft(ll2);
        l1.setRight(lr2);
//        r1.setLeft(rl2);
//        r1.setRight(rr2);
        return root;
    }
//                      50
    //                /     \
    //              30       60
    //              /  \     /  \
    //              5   20   45   70
   //                              / \
    //                             65  80

    private static Node buildBST2() {
        final Node root = new Node(50);
        final Node l1 = new Node(30);
        final Node r1 = new Node(60);
        final Node ll2 = new Node(5);
        final Node lr2 = new Node(20);
        final Node rl2 = new Node(45);
        final Node rr2 = new Node(70);
        final Node rrl3 = new Node(65);
        final Node rrr3 = new Node(80);

        root.setLeft(l1);
        root.setRight(r1);
        l1.setLeft(ll2);
        l1.setRight(lr2);
        r1.setLeft(rl2);
        r1.setRight(rr2);
        rr2.setLeft(rrl3);
        rr2.setRight(rrr3);
        return root;
    }


    private static Node insert(Node node, int data) {
        if (node == null) {
            node = new Node(data);
        } else {
            if (data <= node.data) {
                node.left = insert(node.left, data);
            } else {
                node.right = insert(node.right, data);
            }
        }
        return node;
    }



    private static boolean isBST1(Node node, int MIN, int MAX) {
        if (node == null)
            return true;
        boolean isLeft = isBST1(node.left, MIN, node.data);
        boolean isRight = isBST1(node.right,node.data, MAX );
        boolean isBST = node.data > MIN && node.data < MAX && isLeft && isRight;
        return isBST;

    }

    private static boolean isBST(Node node, int MIN, int MAX) {
        if (node == null)
            return true;
        return(node.data > MIN && node.data < MAX && isBST(node.left, MIN, node.data) && isBST(node.right, node.data, MAX));

    }

    private static class Info{
        private int size;
        private  int min;
        private int max;
        private  boolean isBST;

        private Info(int size, int min, int max, boolean isBST ){
            this.size = size;
            this.min = min;
            this.max = max;
            this.isBST = isBST;
        }

        private static class Builder{
            private int size = 0;
            private  int min = Integer.MAX_VALUE;
            private int max= Integer.MIN_VALUE;
            private  boolean isBST = true;

            public Builder setSize(int size) {
                this.size = size;
                return this;
            }

            public Builder setMin(int min) {
                this.min = min;
                return this;
            }

            public Builder setMax(int max) {
                this.max = max;return this;
            }

            public Builder setBST(boolean BST) {
                isBST = BST;return this;
            }

            public  Info build(){
                return new Info(size, min, max, isBST);

            }

        }
    }

    private static int largestBST(Node node){
        return largestBSTHelper(node).size;
    }

    private static Info largestBSTHelper(Node node){
        if(node == null){
            return new Info.Builder().build();
        }
        Info leftInfo = largestBSTHelper(node.left);
        Info rightInfo = largestBSTHelper(node.right);
        int min = Math.min(node.data, Math.min(rightInfo.min,leftInfo.min));
        int max = Math.max(node.data, Math.max(leftInfo.max, rightInfo.max));
        boolean isBst = false;
        int size = Math.max(leftInfo.size, rightInfo.size);
        if(node.data >= leftInfo.max && node.data <= rightInfo.min && leftInfo.isBST && rightInfo.isBST){
             isBst = true;
             size = leftInfo.size + rightInfo.size +1;
        }
        Info info = new Info.Builder().setMin(min).setMax(max).setBST(isBst).setSize(size).build();
        return info;

    }


    public boolean isSymmetric(Node root) {
        if(root==null) return true;
        return isMirror(root.left,root.right);
    }
    public boolean isMirror(Node p, Node q) {
        if(p==null || q==null) return p == q;
        return (p.data==q.data) && isMirror(p.left,q.right) && isMirror(p.right,q.left);
    }


    private  static String toString(Node node){
        //pre order
        if(node == null){
            return "";
        }
        String temp = ""+node.data;
        String left = ""+toString(node.left);
        String right = ""+toString(node.right);
        if(left.equals("") && right.equals("")){
            return temp;
        }
        String result = temp + (left.equals("") && !right.equals("")? "()":"("+left+")") + (!right.equals("")? "("+right+")": "") ;
        return result;

    }

//    Q: Write a function named "dump", which
//    takes a reference to a binary tree node,
//    returns a string representation of the tree.
//            (1) For a null reference returns "".
//            (2) For a leaf node returns the integer number in string like "1234".
//            (3) For a non-leaf node returns string like: "T(1234,T(5,12,),T(12,,3))"
//    T(<int-value>,<LeftTree>,<RightTree>)

    private static String dump(Node node){
        if(node == null){
            return "";
        }
        String temp;
        if(isLeafNode(node)){
            temp = ""+node.data;
        }else {
             temp = "T(" + node.data ;
        }
        String left = dump(node.left);
        String right = dump(node.right);
        String result = temp+ "," + left +","+ right + ")";
        return result;
    }

    //https://stackoverflow.com/questions/25588041/building-a-tree-from-string-input
    //http://math.hws.edu/javanotes/c9/s5.html
    private static Node parseHelper(String tree){
        parse(tree, 0, tree.length() -1 );
        return null;
    }

    private static Node parse(String tree, int startIndex, int endIndex){
        if(startIndex > endIndex){
            return null;
        }
        if(startIndex == endIndex){
            return new Node(tree.charAt(startIndex));
        }
        //parse root
        int rootIndex = tree.indexOf('T', startIndex) < 0 ?  tree.charAt(0): tree.indexOf('T') + 2;
        Node root = new Node(Character.getNumericValue(tree.charAt(rootIndex)));
        int rightIndex = findRightIndex(tree, rootIndex+2) +1;
        root.left = parse(tree, rootIndex+2, rightIndex -2 );
        root.right = parse(tree, rightIndex, endIndex );
        return root;
    }

    private static int findRightIndex(String tree, int startIndex) {
        //after every T push ( and pop )
        if(tree.charAt(startIndex) == 'T'){
            Stack<Character> stack = new Stack<>();
            int index = tree.indexOf('(', startIndex);
            stack.push( (tree.charAt(index)));
            index++;
            while(!stack.isEmpty()){
                if(tree.charAt(index) == '('){
                    stack.push(tree.charAt(index));
                }
                if(tree.charAt(index) == ')'){
                    stack.pop();
                }
                index++;

            }
            return index;
        }

        if(Character.isDigit(tree.charAt(startIndex))){
            return startIndex +1;
        }
        return -1;
    }

    private static int sum = 0;
    public static void BSTtoGreater(Node node){
        if(node == null){
            return ;
        }
        BSTtoGreater(node.right);
        node.data+= sum;
        sum += node.data;
        BSTtoGreater(node.left);
    }
    public static void BSTtoGreater(Node node, int sum){
        if(node == null){
            return;
        }
         BSTtoGreater(node.right, sum);
        node.data+= sum;
        sum += node.data;
        BSTtoGreater(node.left, sum);
    }


    private static boolean isSameTree(Node node1, Node node2){
        if(node1 == null || node2== null){
            return node1 == node2;
        }
        boolean left = isSameTree(node1.left, node2.left);
        boolean right = isSameTree(node1.right, node2.right);
        boolean isNodeSame = node1.data == node2.data;
        return left && right && isNodeSame;

    }


    private static int sumOfLeftLeaves(Node node){
        if(node == null){
            return 0;
        }
        int sum = 0;
        if(node.left != null){
            if(node.left.left ==null && node.left.right == null){
                sum+= node.data;
            }else {
                sum += sumOfLeftLeaves(node.getLeft());
            }

        }

        sum += sumOfLeftLeaves(node.right);
        return sum;

    }

    private static int longestUniValPath(Node node, Node prev, int[] maxLen){
        if(node == null){
            return 0;
        }
        //get max left len
        int leftLen = longestUniValPath(node.getLeft(), node, maxLen);
        int rightLen = longestUniValPath(node.getRight(), node, maxLen);
        maxLen[0] = Math.max(maxLen[0], (leftLen+rightLen));
        return node.getData() == prev.getData()? Math.max(leftLen,rightLen) +1 :0;

        //get max right len

        //return 1+ max of len

    }

    private static int diameter(Node node, int[] diameter){
        if(node == null){
            return 0;
        }
        //get max left len
        int leftDepth = diameter(node.getLeft(), diameter);
        int rightDepth = diameter(node.getRight(), diameter);
        diameter[0] = Math.max(diameter[0], (leftDepth+rightDepth));
        return  Math.max(leftDepth,rightDepth) +1  ;

        //get max right len

        //return 1+ max of len

    }


    private static Integer tiltHelper(Node node){
        //Integer sum = new Integer(0);
        int[] sum = new int[1];
         tilt(node, sum);
         return sum[0];
    }

    //tilt = sum of left sub - right sub
    private static int tilt(Node node, int[] sum){
        if(node == null){
            return 0;
        }
        int leftSum  = tilt(node.left, sum);
        int rightSum = tilt(node.right, sum);
        sum[0] +=  Math.abs(leftSum - rightSum);
        return leftSum + rightSum + node.data;


    }

    private static int maxSum(Node node){
        Map<Integer, Integer> sumToFreq = new HashMap<>();
        List<Integer> sums = new ArrayList<>();
        int[] sum = new int[1];
        maxSum(node, sum, sumToFreq, sums);
        return sum[0];

    }


    private static int maxSum(Node node, int[] sum, Map<Integer, Integer> sumToFreq, List<Integer> sums){
        if(node == null){
            return 0;
        }
        int leftSum  = maxSum(node.left, sum, sumToFreq, sums);
        int rightSum = maxSum(node.left, sum, sumToFreq, sums);
        Integer key = leftSum+rightSum;
        sumToFreq.put(key, sumToFreq.getOrDefault(key,0)+1);
        if(sumToFreq.get(key) == sum[0]){
            sums.add(key);
        }else if (sumToFreq.get(key) > sum[0]){
            sums.clear();
            sums.add(key);
        }
        sum[0] =  Math.max(sum[0], key);
        return leftSum + rightSum + node.data;


    }



   private static boolean isBalanaced(Node node){
       return isBalanaced(node.left) && isBalanaced(node.right) && Math.abs(depth(node.left)- depth(node.right)) <= 1;
   }

    private static int depth(Node node) {
       if(node == null){
           return 0;
       }
       return Math.max(depth(node.left), depth(node.right))+1;
    }

    private static class Relation{
        Integer parent;
        Integer child;
        boolean isLeft;
        Relation(Integer child, Integer parent, boolean isLeft){
            this.parent = parent;
            this.child = child;
            this.isLeft = isLeft;
        }
    }

    //    Given the following relationships:
//
//       Child Parent IsLeft
//        15 20 true
//        19 80 true
//        17 20 false
//        16 80 false
//        80 50 false
//        50 null false
//        20 50 true
//
//    You should return the following tree:
//
//            50
//            /  \
//            20   80
//            / \   / \
//            15 17 19 16
//    Function Signature
//

    private static Node buildTreeFromRelationList(List<Relation> relations){
       Map<Integer, List<Relation>> parentToRelationMap = new HashMap<>();
       Node root = null;
       for(Relation relation: relations){
           if(relation.parent == null ){
                root = new Node(relation.child);
           }else{
               List<Relation> relationList =  parentToRelationMap.getOrDefault(relation.parent, new ArrayList<>());
               relationList.add(relation);
               parentToRelationMap.put(relation.parent, relationList);
           }
       }
       root = buildTreeFromMap(root, parentToRelationMap);
       return root;
    }

    private static Node buildTreeFromMap(Node root, Map<Integer, List<Relation>> parentToRelationMap) {

        List<Relation> relations = parentToRelationMap.getOrDefault(root.data, new ArrayList<>());
        //leaf node
        if(relations.isEmpty()){
            return root;
        }
        for(Relation relation:relations){
            if(relation.isLeft) {
                root.left = buildTreeFromMap(new Node(relation.child), parentToRelationMap);
            }else{
                root.right = buildTreeFromMap(new Node(relation.child), parentToRelationMap);
            }
        }
        return root;
    }

    //TODO: Print and remove leaf nodes until root node left
//    Given a imbalance binary tree, print the leaf nodes then remove those leaf node, print the new leaf nodes until only root node left.
//
//    For example:
//
//            1
//            / \
//            2   3
//            / \
//            4   5
//    Print: [4 5 3], [2], [1]

    static int dropByLevel(Node root,Map<Integer,ArrayList<Node>> map) {
        if (root != null) {
            int l = dropByLevel(root.left, map);
            int r = dropByLevel(root.right, map);
            int h = Math.max(l, r) + 1;
            ArrayList<Node> arr = map.containsKey(h) ? map.get(h)  : new ArrayList<>();
            arr.add(root);
            map.put(h, arr);
            return h;
        }
        return 0;
    }
    //    private static Node printandRemoveLeaves(Node root, List<List<Node>> nodes){
//        printandRemoveLeaves(root.left, nodes);
//        printandRemoveLeaves(root.right, nodes);
//        if(root.left != null && root.left.left == null && root.left.right == null){
//
//        }
//        return printLevelOrder1(root);
//
//    }



//      20
//              /     \
//              10      30
//              /  \    /  \
//              5     15 25   35




    /**
     * Given two nodes of a tree,
     * method should return the deepest common ancestor of those nodes.
     *
     *          A
     *         / \
     *        B   C
     *       / \
     *      D   E
     *         / \
     *        G   F
     *
     *  commonAncestor(D, F) = B
     *  commonAncestor(C, G) = A
     *  commonAncestor(E, B) = B
     */

//        class Node {
//            final Node parent;
//            final Node left;
//            final Node right;
//
//            public Node(Node parent, Node left, Node right) {
//                this.parent = parent;
//                this.left = left;
//                this.right = right;
//            }
//            boolean isRoot() {
//                return parent == null;
//            }

            //     IMPLEMENT THIS FUNCTION
//            Node commonAncestor(Node one, Node two);

//        while t1.parent != None and t2.parent != None:
//                if t1.parent == t2:
//                return t2
//        elif t2.parent == t1:
//                return t1
//        elif t1.parent == t2.parent:
//                return t1.parent
//                t1  = t1.parent
//        t2  = t2.parent
//    if t1.parent == None:
//                return t1
//    else:
//            return t2


    //
//
//    public static void flatten(Node root) {
//        if (root == null)
//            return;
//        flatten(root.right);
//        flatten(root.left);
//        root.right = prev;
//        root.left = null;
//        prev = root;
//    }




    public static void main(String[] args) {
        Node root = buildTree();

        boundaryTraversal(root);
        StringBuilder sb = new StringBuilder();
        serialize(root, sb);
        System.out.println(sb.toString());
        //printLevelOrder(root);
        printLevelOrder1(root);
        invert1(root);
        printLevelOrder1(root);
        root = buildTree();
        printLevelOrder1(root);
        root = trim(3, 6, root);
        System.out.println("Trimmed tree");
        printLevelOrder1(root);
        root = buildTree();
        System.out.println("String version: " + toString(root));
        System.out.println("Dump string version: " + dump(root));
        root = buildTree1();
        BSTtoGreater(root);
        printLevelOrder1(root);
        root = buildTree1();
        BSTtoGreater(root, 0);
        printLevelOrder1(root);
        root = buildTree1();
        System.out.println(" Tilt of tree is:" + tiltHelper(root));
        printLevelOrder1(root);
        root = buildTree3();
        int[] len = new int[1];
        longestUniValPath(root, root,len);
        System.out.println("longest unival length: "+len[0]);
        root = buildBST();
        isBST1(root);
        BSTIterator bstIterator = new BSTIterator(root);
        printLevelOrder1(root);
        System.out.println("run the iterator");
        while(bstIterator.hasNext()){
            System.out.println(bstIterator.next());
        }

        root = buildTree();
        printLevelOrder1(root);
        addRow(root, 16, 3, 1);
        System.out.println(" post adding row");
        printLevelOrder1(root);
        root = buildTree1();
        sumNumbers1(root);
        root = buildBST();
        System.out.println(" BST is a BST: " + isBST(root, Integer.MIN_VALUE, Integer.MAX_VALUE));
        printLevelOrder1(root);
        root = buildBST1();
        printLevelOrder1(root);
        System.out.println(" largest BST : "+largestBST(root));
        root = buildBST2();
        printLevelOrder1(root);
        System.out.println(" largest BST : "+largestBST(root));
        //       Child Parent IsLeft
        //        15 20 true
        //        19 80 true
        //        17 20 false
        //        16 80 false
        //        80 50 false
        //        50 null false
        //        20 50 true
        root = buildTreeFromRelationList(Arrays.asList(
               new Relation(15, 20, true),
                new Relation(19, 80, true),
                new Relation(17, 20, false),
                new Relation(16, 80, false),
                new Relation(80, 50, false),
                new Relation(50, null, false),
                new Relation(20, 50, true)
                ));
            System.out.println(" Tree from relations ");
            printLevelOrder1(root);

            //            1
            //            / \
            //            2   3
            //            / \
            //            4   5
            //    Print: [4 5 3], [2], [1]

            Map<Integer,ArrayList<Node>> map  =  new HashMap<>();
            root = buildTreeImbal();
            dropByLevel(root,  map) ;
            for (Map.Entry<Integer, ArrayList<Node>> entry  :  map.entrySet())  {
                System.out.println("leaves");
                System.out.println(entry.getValue());
            }
        root = buildTreeNew();
        System.out.println("Max sum from alt levels "+ getMaxSumForAlternateLevel(root));
    }

    private static int getMaxSumForAlternateLevel(Node root) {
        if(root == null){
            return 0;
        }
        int[] levelSum =new int[2];
        Queue<Node> q = new LinkedList<>();
        q.add(root);
        int level =0;
        while(!q.isEmpty()){
            int size = q.size();
            for(int i=0; i< size; i++){
                Node node = q.remove();
                if(node.left != null){
                    q.add(node.left);
                }
                if(node.right != null){
                    q.add(node.right);
                }
                if(level % 2 ==0){
                    levelSum[0]+= node.data;
                }else{
                    levelSum[1]+= node.data;
                }
            }
            level++;
        }
        return Math.max(levelSum[0], levelSum[1]);
    }

    private static Node buildTreeNew() {
        Node root = new Node(1);
        root.left = new Node(2);
        root.right = new Node(3);
        root.right.left = new Node(4);
        root.right.left.right = new Node(5);
        root.right.left.right.left = new Node(6);
        return root;
    }

    void printkdistanceNodeDown(Node node, int k)
    {
        // Base Case
        if (node == null || k < 0)
            return;

        // If we reach a k distant node, print it
        if (k == 0)
        {
            System.out.print(node.data);
            System.out.println("");
            return;
        }

        // Recur for left and right subtrees
        printkdistanceNodeDown(node.left, k - 1);
        printkdistanceNodeDown(node.right, k - 1);
    }

    // Prints all nodes at distance k from a given target node.
    // The k distant nodes may be upward or downward.This function
    // Returns distance of root from target node, it returns -1
    // if target node is not present in tree rooted with root.
    int printkdistanceNode(Node node, Node target, int k)
    {
        // Base Case 1: If tree is empty, return -1
        if (node == null)
            return -1;

        // If target is same as root.  Use the downward function
        // to print all nodes at distance k in subtree rooted with
        // target or root
        if (node == target)
        {
            printkdistanceNodeDown(node, k);
            return 0;
        }

        // Recur for left subtree
        int dl = printkdistanceNode(node.left, target, k);

        // Check if target node was found in left subtree
        if (dl != -1)
        {

            // If root is at distance k from target, print root
            // Note that dl is Distance of root's left child from
            // target
            if (dl + 1 == k)
            {
                System.out.print(node.data);
                System.out.println("");
            }

            // Else go to right subtree and print all k-dl-2 distant nodes
            // Note that the right child is 2 edges away from left child
            else
                printkdistanceNodeDown(node.right, k - dl - 2);

            // Add 1 to the distance and return value for parent calls
            return 1 + dl;
        }

        // MIRROR OF ABOVE CODE FOR RIGHT SUBTREE
        // Note that we reach here only when node was not found in left
        // subtree
        int dr = printkdistanceNode(node.right, target, k);
        if (dr != -1)
        {
            if (dr + 1 == k)
            {
                System.out.print(node.data);
                System.out.println("");
            }
            else
                printkdistanceNodeDown(node.left, k - dr - 2);
            return 1 + dr;
        }

        // If target was neither present in left nor in right subtree
        return -1;
    }

    private static boolean isBST1(Node root) {
       return isBST1(root, Integer.MIN_VALUE, Integer.MAX_VALUE);
    }
}




