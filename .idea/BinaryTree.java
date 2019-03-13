
// Necesito ayida aqui

public class BinaryTree{
    Node root = new Node();

    //Recursive method to add. If the value is null, then there's no value, which means that we've reached a leaf, and something can be added.
    private Node addRecursive(Node current, E value){
        //If there is no previous branch
        if(current == null){
            return new Node(value);
        }
        if(value < current.value){
            current.left = addRecursive(current.left, value);

        }
        else if (value > current.value){
            current.right = addRecursive(current.right, value);

        }
        return current;
    }

    public void add(){
    root = addRecursive(root, 0)
    }

    public boolean isEmpty(){

        //Last value has been reached
        if(current.right == null && current.left == null){
            return true;
        }
        //Last value has been reached
        else if(!(current.right == null) || !(current.left == null)){
            return false;
        }
    }

    public BinaryTree createTree(){
        BinaryTree tree = new BinaryTree();

        // Create a FOR method here that goes across the expression to create a binary tree.
        for()

    }
}