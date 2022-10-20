package vendingMachineSystem;

public class Product {
    private int id;
    private String name;
    private String category;
    private int quantity;
    private float price;

    public void setId(int id){
        this.id = id;
    }
    public void setName(String name){
        this.name = name;
    }
    public void setCategory(String category){
        this.category = category;
    }
    public void setQuantity(int quantity){
        this.quantity = quantity;
    }
    public void setPrice(float price){
        this.price = price;
    }

    public int getId(){
        return id;
    }
    public int getQuantity(){
        return quantity;
    }
    public float getPrice() {
        return price;
    }
    public String getName(){
        return name;
    }
    public String getCategory() {
        return category;
    }
}
