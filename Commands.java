
import java.util.*;

interface Command {

    abstract void execute();

    abstract void undo();

    abstract void redo();

    abstract void showCommandInfo();
}

class ShowProduct implements Command {

    CoffeeProductBST records;
    int productID;
    Scanner sc;

    public ShowProduct(Scanner sc, CoffeeProductBST coffeeProducts) {
        this.sc = sc;
        records = coffeeProducts;
        execute();
    }

    public void execute() {
        System.out.println("Enter product id (* to show all):");
        String temp = sc.next();
        if (temp.equals("*")) {
            System.out.println("Coffee Product information");
            System.out.println("ID  \t" + "Namec\t\t\t\t" + "Quantity \t\tOther Info");
            if (records.root != null) {
                records.inOrder(records.root);//loop all the record data
            }
        } else {
            try {
                productID = Integer.parseInt(temp);
                CoffeeProduct product = null;
                if (records.root != null) {
                    
                    product = records.getCoffeeProduct(records.root, productID);//get record
                }
                if (product == null) {
                    System.out.println("record with id " + productID + "do not exist");
                } else {
                    System.out.println("Product information");
                    String par[] = product.toString().split(",");
                    for (int i = 0; i < par.length - 1; i++) {
                        System.out.println(par[i]);
                    }

                }
            } catch (NumberFormatException e) {
                System.out.println("Input should be a int/* \nTry again");
            }
        }

    }

    public void redo() {
    }

    public void undo() {
        System.out.println("Command ShowProduct do not have undo method");
    }

    public void showCommandInfo() {
    }
}

class DisplayUndoOrRedoList implements Command {

    Command temp;
    Stack<Command> reActionList, actionList;

    public DisplayUndoOrRedoList(Stack reActionList, Stack actionList) {
        this.reActionList = reActionList;
        this.actionList = actionList;
        execute();
    }

    public void execute() {
        System.out.println("Undo List: ");
        if (actionList.size() > 0) {//show undo list
            for (int i = 0; i < actionList.size(); i++) {
                temp = actionList.get(i);

                temp.showCommandInfo();
            }

        } else {
            System.out.println("Empty");
        }
        System.out.println("\nRedo List: ");//show redo list
        if (reActionList.size() > 0) {
            for (int i = 0; i < reActionList.size(); i++) {
                temp = reActionList.get(i);
                temp.showCommandInfo();
            }
        } else {
            System.out.println("Empty");
        }
    }

    public void redo() {
    }

    public void undo() {
    }

    public void showCommandInfo() {
    }
}

class AddProduct implements Command {
    CoffeeProductBST records;
    CoffeeProductFactory cf = new CoffeeProductFactory();
    Scanner sc;
    CoffeeProduct cpt;
    public AddProduct(Scanner sc, CoffeeProductBST coffeeProducts) {
        this.sc = sc;
        this.records = coffeeProducts;
        execute();
    }
    public void execute() {
        System.out.println("Enter Coffee type (cc=Coffee Candy/cp=Coffee Powder):");
        switch (sc.next()) {
            case "cc":
                cpt = cf.sf[1].createCoffeeProduct(sc);
                break;
            case "cp":
                cpt = cf.sf[0].createCoffeeProduct(sc);
                break;
            default:
                System.out.println("error input");
                throw new RuntimeException();
        }
        if (records.containsNode(cpt)) {
            System.out.println("productId already exist");

            throw new RuntimeException();
        } else {
            records.add(cpt);
        }
        System.out.println("New product record created.");

    }
    public void undo() {
        records.delete(cpt);//delete record
    }
    public void redo() {
        records.add(cpt);
        System.out.println("Record added");//readd record
    }
    public void showCommandInfo() {//for sl command
        System.out.println("Add " + cpt.getProductID() + " " + cpt.getName());
    }

}

class ReceiveProduct implements Command {
    CoffeeProductBST records;
    CoffeeProduct cp;
    Scanner sc;
    int qReceive;
    Caretaker ct;
    public ReceiveProduct(Scanner sc, CoffeeProductBST coffeeProducts, Caretaker ct) {
        records = coffeeProducts;
        this.sc = sc;
        this.ct = ct;
        execute();
    }
    public void execute() {
        try {
            System.out.println("Enter product id:");
            int code = sc.nextInt();
            cp = records.getCoffeeProduct(records.root, code);//get record
            if (cp == null) {//if can't find
                System.out.println("product with id: " + code + " can not find");
                throw new NullPointerException();
            } else {
                if (cp.getClass() == CoffeeCandy.class) {//ask qty
                    System.out.println("Quantity to receive:");
                } else if (cp.getClass() == CoffeePowder.class) {
                    System.out.println("Quantity to deposit:");
                }
                qReceive = sc.nextInt();
                if (qReceive <= 0) {//nagative number
                    System.out.println("the quantity must bigger than 0");
                    throw new NumberFormatException();
                }
                System.out.print("Received " + qReceive + " packs of " + cp.getName() + ". ");
                ct.saveCoffeeProduct(cp);//save status at mememto
                cp.setQty(cp.getQty() + qReceive);//update record
                System.out.println("Current quantity is " + cp.getQty()+".");
            }
        } catch (InputMismatchException e) {
            String next = sc.next();//skip the error input
            throw e;
        } catch (NumberFormatException e) {
            throw e;
        } catch (NullPointerException e) {
            throw e;
        }
    }
    public void undo() {
        ct.undo();
    }
    public void redo() {
        ct.redo();
    }
    public void showCommandInfo() {
        System.out.println("Received " + this.qReceive + " " + cp.getName() + " (" + cp.getProductID() + ")");
    }
}

class DeliverProduct implements Command {
    CoffeeProductBST records;
    CoffeeProduct cp;
    Caretaker ct;
    Scanner sc;
    int qSend;
    public DeliverProduct(Scanner sc, CoffeeProductBST coffeeProducts, Caretaker ct) {
        records = coffeeProducts;
        this.sc = sc;
        this.ct = ct;
        execute();
    }
    public void execute() {
        try {
            System.out.println("Enter product id");
            int code = sc.nextInt();
            cp = records.getCoffeeProduct(records.root, code);//get record
            if (cp == null) {//if can't find
                System.out.println("product with id:" + code + " can not find");
                throw new RuntimeException();
            } else {
                System.out.println("Quantity to ship:");//ask qty
                qSend = sc.nextInt();
                if (qSend > cp.getQty()) {
                    System.out.println("Invalid quantity (current balance is less than required quantity).");
                    throw new RuntimeException();
                }else if(qSend<0){
                    System.out.println("the quantity must bigger than 0");
                    throw new NumberFormatException();
                }
                ct.saveCoffeeProduct(cp);//save status at mememto
                System.out.print("Shipped " + qSend + " packs of " + cp.getName() + ".");
                cp.setQty(cp.getQty() - qSend);
                System.out.println("Current quantity is " + cp.getQty());
            }
        } catch (InputMismatchException e) {
           String next = sc.next();//skip the error input
            throw e;
        } catch (RuntimeException e) {
            throw e;
        }
    }

    public void undo() {
        ct.undo();
    }

    public void redo() {
        ct.redo();
    }

    public void showCommandInfo() {
        System.out.println("Shipped " + this.qSend + " " + cp.getName() + " (" + cp.getProductID() + ")");
    }
}

class Undo implements Command {

    Command undoCommend;

    public Undo(Stack<Command> reActionList, Stack<Command> actionList) {
        if (actionList.size() < 1) {
            System.out.println("no action can be undo");
            return;
        }
        undoCommend = actionList.pop();
        reActionList.push(undoCommend);

        execute();
        System.out.println("undo completed.");

    }

    public void execute() {
        undoCommend.undo();//run the Command undo method
    }

    public void undo() {
        System.out.println("Command ShowProduct do not have undo method");
    }

    public void redo() {
        System.out.println("Command ShowProduct do not have redo method");
    }

    public void showCommandInfo() {
    }
}

class Redo implements Command {

    Command undoCommend;

    public Redo(Stack<Command> reActionList, Stack<Command> actionList) {
        if (reActionList.size() < 1) {
            System.out.println("no action can be redo");
            return;
        }
        undoCommend = reActionList.pop();
        actionList.push(undoCommend);
        execute();
        System.out.println("redo completed.");
    }

    public void execute() {
        undoCommend.redo();//run  Command redo method
    }

    public void undo() {
        System.out.println("Command Redo do not have undo method");
    }

    public void redo() {
        System.out.println("Command Rndo do not have redo method");
    }

    public void showCommandInfo() {
    }
}

class Exit implements Command {

    public Exit() {
        execute();
    }

    public void execute() {
        System.out.println("Thanks for using Coffee Inventory Management System!!");
        System.exit(0);
    }

    public void undo() {
    }

    public void redo() {
    }

    public void showCommandInfo() {
    }
}

class Caretaker {

    private Stack<Memento> undoList;
    private Stack<Memento> redoList;

    public Caretaker() {
        undoList = new Stack<Memento>();
        redoList = new Stack<Memento>();
    }

    public void saveCoffeeProduct(CoffeeProduct mc) {
        Memento amemento = new Memento(mc);
        undoList.push(amemento);
        redoList.clear();
    }

    public void undo() {
        if (!undoList.isEmpty()) {
            Memento m = undoList.pop();//get old status at memento
            Memento rm = new Memento(m);//back up old status at memento
            redoList.push(rm);
            m.restore();//recover
        } else {
            System.out.println("Nothing to undo.");
        }
    }

    public void redo() {
        if (!redoList.isEmpty()) {
            Memento m = redoList.pop();//get old status at memento
            undoList.push(new Memento(m));//back up old status at memento
            m.restore();//recover
        } else {
            System.out.println("Nothing to redo.");
        }
    }
}
