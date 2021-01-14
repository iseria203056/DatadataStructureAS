
import java.util.InputMismatchException;
import java.util.Scanner;
import java.util.Stack;

interface AbstractCommandFactory {

    public abstract Command createCommand(Scanner sc, CoffeeProductBST coffeeProducts, Stack<Command> reActionList, Stack<Command> actionList, Caretaker ct);//make sure all command factory have this method

}

class CommandFactory {

    AbstractCommandFactory[] acf;

    public CommandFactory() {
        acf = new AbstractCommandFactory[10];
        acf[0] = new ShowProductFactory();
        acf[1] = new AddProductFactory();
        acf[2] = new ReceiveProductFactory();
        acf[3] = new DeliverProductFactory();
        acf[4] = new DisplayUndoOrRedoListFactory();
        acf[5] = new UndoFactory();
        acf[6] = new RedoFactory();
        acf[7] = new ExitFactory();

    }

    public Command selectCommand(String request, Scanner sc, CoffeeProductBST coffeeProducts, Stack<Command> reActionList, Stack<Command> actionList, Caretaker ct) {//return Command object
        switch (request) {
            case "a":
                try {
                    return acf[1].createCommand(sc, coffeeProducts, reActionList, actionList, ct);
                } catch (IndexOutOfBoundsException e) {
                    return null;
                } catch (NumberFormatException e) {
                    return null;
                } catch (RuntimeException e) {
                    return null;
                }
            case "v":
                
                return acf[0].createCommand(sc, coffeeProducts, reActionList, actionList, ct);
              
            case "c":
                try {
                    return acf[2].createCommand(sc, coffeeProducts, reActionList, actionList, ct);
                } catch (InputMismatchException e) {
                    System.out.println("C:Error:The input should be integer and less than 1*10^32");
                    return null;
                } catch (NumberFormatException e) {
                    return null;
                } catch (NullPointerException e) {
                    return null;
                }

            case "s":
                try {
                    return acf[3].createCommand(sc, coffeeProducts, reActionList, actionList, ct);
                } catch (InputMismatchException e) {
                    System.out.println("Error:The input should be integer and less than 1*10^32");
                    return null;
                } catch (NumberFormatException e) {
                    return null;
                } catch (RuntimeException e) {
                    return null;
                }
            case "sl":
                return acf[4].createCommand(sc, coffeeProducts, reActionList, actionList, ct);
            case "u":

                return acf[5].createCommand(sc, coffeeProducts, reActionList, actionList, ct);

            case "r":
                return acf[6].createCommand(sc, coffeeProducts, reActionList, actionList, ct);
            case "x":
                return acf[7].createCommand(sc, coffeeProducts, reActionList, actionList, ct);
            default:
                return null;
        }
    }
}

class ShowProductFactory implements AbstractCommandFactory {

    public Command createCommand(Scanner sc, CoffeeProductBST coffeeProducts, Stack<Command> reActionList, Stack<Command> actionList, Caretaker ct) {
        return new ShowProduct(sc, coffeeProducts);
    }
}

class AddProductFactory implements AbstractCommandFactory {

    public Command createCommand(Scanner sc, CoffeeProductBST coffeeProducts, Stack<Command> reActionList, Stack<Command> actionList, Caretaker ct) {
        return new AddProduct(sc, coffeeProducts);
    }
}

class UndoFactory implements AbstractCommandFactory {

    public Command createCommand(Scanner sc, CoffeeProductBST coffeeProducts, Stack<Command> reActionList, Stack<Command> actionList, Caretaker ct) {

        return new Undo(reActionList, actionList);
    }
}

class RedoFactory implements AbstractCommandFactory {

    public Command createCommand(Scanner sc, CoffeeProductBST coffeeProducts, Stack<Command> reActionList, Stack<Command> actionList, Caretaker ct) {
        return new Redo(reActionList, actionList);
    }
}

class ReceiveProductFactory implements AbstractCommandFactory {

    public Command createCommand(Scanner sc, CoffeeProductBST coffeeProducts, Stack<Command> reActionList, Stack<Command> actionList, Caretaker ct) {
        return new ReceiveProduct(sc, coffeeProducts, ct);
    }
}

class DeliverProductFactory implements AbstractCommandFactory {

    public Command createCommand(Scanner sc, CoffeeProductBST coffeeProducts, Stack<Command> reActionList, Stack<Command> actionList, Caretaker ct) {
        return new DeliverProduct(sc, coffeeProducts, ct);
    }
}

class DisplayUndoOrRedoListFactory implements AbstractCommandFactory {

    public Command createCommand(Scanner sc, CoffeeProductBST coffeeProducts, Stack<Command> reActionList, Stack<Command> actionList, Caretaker ct) {
        return new DisplayUndoOrRedoList(reActionList, actionList);
    }
}

class ExitFactory implements AbstractCommandFactory {

    public Command createCommand(Scanner sc, CoffeeProductBST coffeeProducts, Stack<Command> reActionList, Stack<Command> actionList, Caretaker ct) {
        return new Exit();
    }
}
