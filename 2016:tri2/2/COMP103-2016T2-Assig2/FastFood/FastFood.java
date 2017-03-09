// This program is copyright VUW.
// You are granted permission to use it to construct your answer to a COMP103 assignment.
// You may not distribute it in any other way without permission.

/* Code for COMP103 Assignment 2
 * Name:
 * Usercode:
 * ID:
 */

import ecs100.*;
import java.util.*;
import java.awt.*;
/** The FastFood game involves customers who generate orders, and the player
 *  who has to fulfill the orders by assembling the right collection of food items.
 *  The goal of the game is to make as much money as possible before
 *  the player gets too far behind the customers and is forced to give up.
 *
 *  The game presents the player with a queue of orders in a fast food outlet.
 *  The player has to fullfill the customer orders by adding the correct items to
 *  the order at the head of the queue.  
 *  When the order is ready, the player can deliver the order, which will
 *  take it off the queue, and will add the price of the order to the balance.
 *  Whenever the player adds an item to the order that doesn't belong in the
 *  order, the price of the item is subtracted from the balance.
 *  The player can practice by generating orders using the Practice button.
 *  Once the game is started, the orders are generated automatically.
 */
public class FastFood{

    private Queue<Order> orders;
    private double balance;
    public static Map<String,Double>priceMap;
    private Map<String,String> keyToAction;
    private Timer timer;
    public FastFood() {
        UI.setDivider(0.26);
        timer=new Timer();
       // this.timer.setTime(60000);
        orders = new ArrayDeque<Order>();
        balance=0;

        priceMap=new HashMap<String,Double>();
        priceMap.put("Fish",2.50);
        priceMap.put("Chips",1.50);
        priceMap.put("Burger",5.00);

        initialiseMappings();
        UI.addButton("Practice Order", () -> {generateOrder(); drawOrders();});
        UI.addButton("Add Fish",       () -> {addItem("Fish"); drawOrders();});
        UI.addButton("Add Chips",      () -> {addItem("Chips"); drawOrders();});
        UI.addButton("Add Burger",     () -> {addItem("Burger"); drawOrders();});
        UI.addButton("Deliver Order",  () -> {deliverOrder(); drawOrders();});
        UI.addButton("Start Game",     () -> {startGame(); drawOrders();});
        UI.setKeyListener(this::doKey);
        
        drawOrders();
        this.run();
        
        

        //UI.repaintGraphics();
    }

    public void doKey(String key){

        String s=keyToAction.get(key);        
        if(s!=null){
            if(key.equals("Enter")){
                startGame(); 
                drawOrders();
            }
            if(s.equals("Deliver")){
                deliverOrder();
                drawOrders();
            }else{
                addItem(s);
                drawOrders();
            }

        }
    }

    public void initialiseMappings(){
        keyToAction = new HashMap<String,String>();
        keyToAction.put("f", "Fish");     keyToAction.put("F", "Fish");  
        keyToAction.put("c", "Chips");   keyToAction.put("C", "Chips");  
        keyToAction.put("b", "Burger");   keyToAction.put("B", "Burger");
        keyToAction.put(" ", "Deliver");  

    }

    /** Creates a new order and puts it on the queue to be processed */
    public void generateOrder() {
        Order or=new Order();

        this.orders.add(or);
    }

    /** As long as there is an order in the queue, adds the specified
     *  item to the order at the head of the queue,
     *  If adding the item fails (i.e., it isn't one of the items
     *  that are wanted by the order) then the price
     *  of the item is deducted from the current balance.
     */
    public void addItem(String item) {

        if(!this.orders.isEmpty()){
            Order headOrder=this.orders.peek();
            double price=priceMap.get(item);
            if(!headOrder.isReady()&&!headOrder.addItemToOrder(item)){

                this.balance-=price;
                UI.println("Oh no! You added an unwanted "+item);
                UI.println(price+" deducted from your balance :-(");
                if(timer!=null&&timer.diffTime()>=1000){
                    this.timer.subTime(1000);
                    UI.println("1 second deducted!!!");
                }

                UI.println("(Balance: $"+this.balance+")");
            }
        }

    }

    /** As long as there is an order at the front of the queue and it is ready,
     *  take the first order off the queue, compute the price of the order,
     *  and update the total balance by adding the order price.
     *  If there is not a ready order on the queue, it prints a warning message.
     */
    public void deliverOrder() {
        Order headOrder= this.orders.peek();
        if(!this.orders.isEmpty()){
            Order headOrder1= this.orders.peek();
            if(headOrder1.isReady()){
                double price=headOrder1.getPrice();
                this.balance+=price;
                this.orders.remove();
                if(timer!=null&&timer.diffTime()>0){
                    this.timer.addTime(1000);
                    UI.println("1 second is rewarded !");
                }

                UI.println("That will be $"+price+" . please.");
                UI.println("Thanks! Enjoy your meal");
                UI.println("(Balance: $"+this.balance+")");
            }else{
                UI.println("Can't deliver order: it's not ready");
            }

        }

    }

    /** Draws the queue of orders on the Graphics pane.
     *  Also draws the current balance in the top left corner
     */
    public void drawOrders() {
        UI.clearGraphics();
        String bala=Double.toString(this.balance);
        UI.setColor(Color.black);
        UI.drawString("$ "+bala,20,20);
        drawInstructions();
        drawTimer();
        int size=orders.size();
        Order[] temOrders=new Order[size];
        int count=0;
        for (Iterator<Order> i = orders.iterator(); i.hasNext();) {
            temOrders[count++] = i.next();
        }
        for(int i=0;i<size;i++){
            Order or1=temOrders[i];
            or1.draw(50+30*i);
            
        }

    }

    private void drawTimer(){
        String time;
        if (this.timer.diffTime()<=0){
            time = "00:00.00";
        } else {
            time = this.timer.convertTime(this.timer.diffTime());
        }
        UI.setFontSize(14);
        if (this.timer.diffTime()<=10000) { //turn red after 10sec
            UI.setColor(Color.red.darker());
        } else {
            UI.setColor(Color.blue);
        }
        UI.drawString(time,50,40);
        UI.drawString("Timer:",0,40);
    }

    // In the game version, the orders must be automatically generated.
    // The methods below will reset the queue and the current balance,
    // and will then set the gameRunning field to true. This will make
    // the run method start generating orders.
    // The run method is called from the main method, and therefore is in the main
    // thread, which executes concurrently with all the GUI buttons.
    // run  does nothing until the gameRunning field is set to be true
    // Once the gameRunning field is true, then it will generate orders automatically,
    // every timeBetweenOrders milliseconds. It will also makde the games speed up
    // gradually, by steadily reducing the timeBetweenOrders.
    // You do not need to write these methods code.
    public void drawInstructions(){
        UI.setFontSize(14);
        UI.setColor(Color.red);
        UI.drawString("Welcome to FastFood",100,0);
        UI.drawString("<press F to add Fish || press B to add Burger|| press C to add Chips>",120,20);
        UI.drawString("<press Space to deliver order>",120,40);
        //UI.drawString("<press Enter to start>",280,200);

    }
    private boolean gameRunning = false;
    private long timeBetweenOrders = 5000;

    private void startGame(){
        UI.clearGraphics();
        UI.clearText();
        orders.clear();
        balance = 0;
        timeBetweenOrders = 5000;
        gameRunning = true;
        this.timer.setTime(60000);
    }

    public void run() {
        long timeBetweenSpeedups = 2000;
        long timeNextOrder = 0;
        long timeNextSpeedup = 0;
        
        while (true) { // forever...
            UI.sleep(100); // Wait at least 100 milliseconds between actions.
            long now = System.currentTimeMillis();

            if (!gameRunning) 
                continue;  // if gameRunning is false, then don't generate orders

            // add another order, if the time has come
            if (now >= timeNextOrder) {
                timeNextOrder = now + timeBetweenOrders;
                generateOrder();
                drawOrders();
            }

            // speed up order generation, if the time has come
            if (now >= timeNextSpeedup) {   
                if (timeBetweenOrders> 200)    // as long as maximum speed has not been reached...
                    timeBetweenOrders -= 100;  // reduce interval

                timeNextSpeedup = now + timeBetweenSpeedups;
            }
            if(timer!=null&&timer.diffTime()<=0){
                UI.println("Oh no! You run out of time! Game over...");
                orders.clear();
                gameRunning = false;
                break;
            }
            if (orders.size() > 20) {
                UI.println("Oh no! You have too many orders waiting! Game over...");
                orders.clear();
                gameRunning = false;
                break;
            }

        }
    }

    public static void main(String args[]) {
        FastFood ff = new FastFood();
    }
}
