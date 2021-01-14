
import java.util.Scanner;

class CoffeeProduct {
    private String name;
    private int productID;
    private int qty;
    public CoffeeProduct(String name, int productID) {
        this.name = name;
        this.productID = productID;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public int getProductID() {
        return productID;
    }
    public void setProductID(int productID) {
        this.productID = productID;
    }
    public int getQty() {
        return qty;
    }
    public void setQty(int qty) {
        this.qty = qty;
    }
    @Override
    public String toString() {//return the product data
        return  "ID: "+productID +",Name: " + name + ",Quantity: " + qty;
    }



}

class CoffeeCandy extends CoffeeProduct {

    private int noOfCandy;
    private int caloriesPerCandy;

    public CoffeeCandy(String name, int productID, int noOfCandy, int caloriesPerCandy) {
        super(name, productID);
        this.noOfCandy = noOfCandy;
        this.caloriesPerCandy = caloriesPerCandy;
    }

    @Override
    public String toString() {//return the product data
        return super.toString() + ",Number of candies per package: "+ noOfCandy + ",Calories Per candy: "+ caloriesPerCandy+","+noOfCandy+" candy per package ("+caloriesPerCandy+"calories each)";
    }



    public int getNoOfCandy() {
        return noOfCandy;
    }

    public void setNoOfCandy(int noOfCandy) {
        this.noOfCandy = noOfCandy;
    }

    public int getCaloriesPerCandy() {
        return caloriesPerCandy;
    }

    public void setCaloriesPerCandy(int caloriesPerCandy) {
        this.caloriesPerCandy = caloriesPerCandy;
    }

}

class CoffeePowder extends CoffeeProduct {

    private double weight;

  
    public CoffeePowder(String name, int productID, double weight) {
        super(name, productID);
        this.weight = weight;
    }

    public double getWeight() {
        return weight;
    }

    public void setWeight(double weight) {
        this.weight = weight;
    }

    @Override
    public String toString() {//return the product data
        return super.toString() + ",weight: " + weight+","+weight+"g";
    }

}

interface AbstractCoffeeProductFactory {//CoffeeProduct 

    public abstract CoffeeProduct createCoffeeProduct(Scanner sc);

}

class CoffeeProductFactory {//Factory provider

    public AbstractCoffeeProductFactory[] sf;

    public CoffeeProductFactory() {
        sf = new AbstractCoffeeProductFactory[2];
        sf[0] = new CoffeePowderFactory();
        sf[1] = new CoffeeCandyFactory();
    }
}

class CoffeeCandyFactory implements AbstractCoffeeProductFactory {

    public CoffeeProduct createCoffeeProduct(int productID, String name, int noOfCandy, int caloriesPerCandy) {
        return new CoffeeCandy(name, productID, noOfCandy, caloriesPerCandy);
    }

    public CoffeeProduct createCoffeeProduct(Scanner sc) {//
        System.out.println("Enter product id, name, number of candy and calories per candy: ");

        sc.nextLine();
        String requestString = sc.nextLine();// get the data string
        String[] par = requestString.split(", ");//String to array
        try {
            return createCoffeeProduct(Integer.parseInt(par[0]), par[1], Integer.parseInt(par[2]), Integer.parseInt(par[3]));//creat object
        } catch (IndexOutOfBoundsException e) {// handle error input type
            System.out.println("Error input,the input should mutch format of \'Id, name, number of candy and calories per candy\'");//case of count(input) !=3
            throw e;
        }catch (NumberFormatException e) {// handle error input type
            System.out.println("Error input,the input type should be int, String, int, int ");//case of wroung input type
            throw e;
        }
    }

  
}

class CoffeePowderFactory implements AbstractCoffeeProductFactory {

    public CoffeeProduct createCoffeeProduct(String name, int productID, double weight) {
        return new CoffeePowder(name, productID, weight);
    }

    public CoffeeProduct createCoffeeProduct(Scanner sc) {
        
        System.out.println("Enter product id , name and weight(g): ");
        sc.nextLine();
        String requestString = sc.nextLine();
        String[] par = requestString.split(", ");//String to array
        try {
        return createCoffeeProduct(par[1], Integer.parseInt(par[0]), Double.parseDouble(par[2]));//create object
        }catch (NumberFormatException e) {// handle error input type
            System.out.println("Error input,the input type should be int, int, double ");//case of count(input) !=3
            throw e;
        }catch(IndexOutOfBoundsException e){
            System.out.println("Error input,the input should mutch format of \'Id, name, weight\' ");//case of wroung input type
            throw e;
        }
    }



}
class Memento {// back up for change status of CoffeeProduct

    private CoffeeProduct cp;
    private int mQty;

    public Memento(CoffeeProduct mc) {
        cp = mc; //reference linking
        mQty=cp.getQty();
    }
    public Memento(Memento m) {
        cp =m.getCoffeeProduct(); 
        mQty = m.getCoffeeProduct().getQty();
    }

    public CoffeeProduct getCoffeeProduct() {
        return cp;
    }

    public void setCoffeeProduct(CoffeeProduct CoffeeProduct) {
        cp = CoffeeProduct;
    }

    public int getmQty() {
        return mQty;
    }

    public void setmState(int qty) {
        this.mQty = qty;
    }
    public void restore() {
        cp.setQty(mQty);
    }
}



