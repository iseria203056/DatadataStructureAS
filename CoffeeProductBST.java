
import java.util.Vector;

class Node {
    CoffeeProduct product;
    Node left;
    Node right;
    Node(CoffeeProduct product) {
        this.product = product;
        right = null;
        left = null;
    }
}
public class CoffeeProductBST {
    Node root;    
    public CoffeeProductBST() {
        this.root = null;        
    }
    
    private Node addRecursive(Node current, CoffeeProduct value) {//add product record
        if (current == null) {
            return new Node(value);
        }
        if (value.getProductID() < current.product.getProductID()) {//save at left hand side if productid < current.productid
            current.left = addRecursive(current.left, value);
        } else if (value.getProductID() > current.product.getProductID()) {//save at right hand side if productid > current.productid
            current.right = addRecursive(current.right, value);
        }        
        return current;
    }
    
    public void add(CoffeeProduct value) {
        root = addRecursive(root, value);
    }
    
    private boolean containsNodeRecursive(Node current, CoffeeProduct value) {
        if (current == null) {
            return false;
        }
        if (value.getProductID() == current.product.getProductID()) {
            return true;
        }
        return value.getProductID() < current.product.getProductID()
                ? containsNodeRecursive(current.left, value)
                : containsNodeRecursive(current.right, value);
    }
    
    public boolean containsNode(CoffeeProduct value) {
        return containsNodeRecursive(root, value);
    }
    
    private Node deleteRecursive(Node current, CoffeeProduct value) {
        if (current == null) {
            return null;
        }
        if (value.getProductID() == current.product.getProductID()) {//delete target product
            
            if (root == current) {//if product=root
                root=null;
            }
            current = null;
            return current;
        }
        if (value.getProductID() < current.product.getProductID()) {////find the product that id less then current node's product id
            current.left = deleteRecursive(current.left, value);
            return current;
        } else if (value.getProductID() > current.product.getProductID()) {////find the product that id bigger then current node's product id
            current.right = deleteRecursive(current.right, value);
            return current;
        }
        return current;
    }
    
    public void delete(CoffeeProduct value) {
        deleteRecursive(root, value);
        buildTree(root);
    }
    
    void inOrder(Node node) {// show all product data by assending order
        if (node == null) {
            return;
        }
        inOrder(node.left);
        String par[] = node.product.toString().split(",");

        for (int i = 0; i < 3; i++) {
            if (i == 2) {
                System.out.print("\t");
            }
            System.out.print(par[i].split(": ")[1] + "\t");
        }
        System.out.println("\t"+ par[par.length - 1]);
        
        inOrder(node.right);
    }
    
    void storeBSTNodes(Node root, Vector nodes) {
        // Base case 
        if (root == null) {
            return;
        }

        // Store nodes in Inorder (which is sorted 
        // order for BST) 
        storeBSTNodes(root.left, nodes);
        nodes.add(root);
        //System.out.print(nodes);
        storeBSTNodes(root.right, nodes);
    }

    /* Recursive function to construct binary tree */
    Node buildTreeUtil(Vector<Node> nodes, int start,
            int end) {
        if (start > end) {// base case 
            return null;
        }
        int mid = (start + end) / 2;
        Node node = nodes.get(mid);

        /* Using index in Inorder traversal, construct 
           left and right subtress */
        node.left = buildTreeUtil(nodes, start, mid - 1);
        node.right = buildTreeUtil(nodes, mid + 1, end);
        return node;
    }

    // This functions converts an unbalanced BST to 
    // a balanced BST 
    Node buildTree(Node root) {
        // Store nodes of given BST in sorted order 
        Vector<Node> nodes = new Vector<Node>();
        storeBSTNodes(root, nodes);

        // Constucts BST from nodes[] 
        int n = nodes.size();
        return buildTreeUtil(nodes, 0, n - 1);
    }
    
    public CoffeeProduct getCoffeeProduct(Node node, int productID) {// get the coffee product
        if (node == null) {
            return null;
        }
        if (node.product.getProductID() == productID) {//find the product which is same productid
            return node.product;
        } else if (node.left != null && node.product.getProductID() > productID) {//find the product that id less then current node's product id
            return getCoffeeProduct(node.left, productID);
        } else if (node.right != null && node.product.getProductID() < productID) {//find the product that id less then current node's product id
            return getCoffeeProduct(node.right, productID);
        } else {
            return null;
        }
    }
}
