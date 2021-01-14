
import java.util.*;

public class Assignment {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        CoffeeProductBST coffeeProducts = new CoffeeProductBST();//records
        Stack<Command> actionListStack = new Stack<Command>();//undolist
        Stack<Command> reAtionListStack = new Stack<Command>();//redo list
        Caretaker ct=new Caretaker();
        CommandFactory cf = new CommandFactory();       
        while (true) {
            askCommand();
            try {
                Command command = cf.selectCommand(sc.next(), sc, coffeeProducts, reAtionListStack, actionListStack,ct);//create and run command
                if (command == null) {//error input insides command
                    System.out.println("Try again");
                    continue;
                }
                if ((command.getClass() == AddProduct.class || command.getClass() == ReceiveProduct.class || command.getClass() == DeliverProduct.class)) {//for undo and redo
                    reAtionListStack.clear();
                    actionListStack.push(command);
                }
                System.out.println("");
            }catch(Exception e){}
    }}
    
    public static void askCommand() {
        System.out.println("Coffee Inventory Management System");
        System.out.println("Please enter command: [a | v | c | s | u | r | sl | x]");
        System.out.println("a = add product,  v = view products,  c = collect product,  s = ship product, ");
        System.out.println("u = undo,  r = redo,  sl = show list undo/redo,  x = exit system\n");
    }
}

    

