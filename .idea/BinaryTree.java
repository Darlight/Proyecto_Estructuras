
// Necesito ayida aqui

public class BinaryTree{
    Node root = new Node();

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

    public void add(E value){

    }

    public boolean isEmpty(){
        if(current.right == null && current.left == null){
            return true;
        }
        else if(!(current.right == null) || !(current.left == null)){
            return false;
        }
    }

}