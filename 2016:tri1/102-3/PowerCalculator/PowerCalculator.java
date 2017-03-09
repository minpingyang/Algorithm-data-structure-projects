package PowerCalculator;

 

// This program is copyright VUW.
// You are granted permission to use it to construct your answer to a COMP102 assignment.
// You may not distribute it in any other way without permission.

/* Code for COMP 102 Assignment 2 
 * Name:Minping Yang
 * Usercode:
 * ID:300364234
 */

import ecs100.*;

/** Program for calculating how much you can save on your power bill */
// http://www.powerwise.co.nz/why-choose-led-light-bulbs/led-light-bulb-facts/

public class PowerCalculator{

    public static final double priceKWh = 20.34;         // cents
    public static final int incandescentLifeSpan = 1000; // hours
    public static final int ledLifeSpan = 25000;         // hours
    public static final double incadescentPrice = .99;   // dollars

    /** Calculates and prints how long it would take you to amortise your cost of switching
     *  to energy saving bulbs
     */
    public void calculateCostSaverCore(){
        double priceKWh$ =priceKWh/100;                                                 //conver from $ to cemts
        double wattageOld=UI.askDouble("what is the wattage of the old bulb?");        //ask the user for the wattage of the old bulb
        double wattageNew=UI.askDouble("what is the wattage of the new bulb?");        //ask the user for the wattage of the new bulb
        int    numberRep=UI.askInt("how many bulbs will be replaced?");               // ask how many bulbs will be replaced
        double totalPowerRed=(wattageOld-wattageNew)*numberRep/1000.0;               //calculate the total power reduction;
        double hoursPDay=UI.askDouble("how many hours the light is on per day?");              //ask how many hours the light is on
        double daysPWeek=UI.askDouble("how many days it's used per week?");          //how many days it's used per week
        double weeksPYear=UI.askDouble("how many weeks it's used per year?");        //ask how many weeks it's used per year
        double costANewLED=UI.askDouble("how many dollars is for each new LED buble"); //ask how many dollars is for each new LED buble
        if(hoursPDay>24||daysPWeek>7||weeksPYear>52){
        UI.println("Please check again if you enter the right number such as, the hours per day <=24, the days per week<=7,and weeks per year<=52");
        }else{
        double totalPowerSavePerYear=totalPowerRed*hoursPDay*daysPWeek*weeksPYear;      //calculate the total power saved per year
        double totalDollasSavedPYear=totalPowerSavePerYear*priceKWh$;                   //calculate the total dollars saved per year     
        double numberOfYearsToBreakEven=numberRep*costANewLED/totalDollasSavedPYear;    //calculate the number of years to break even
        UI.printf("The number of kilowatts they are now saving whenever the lights are on is =%.2f kilowatts\n ",totalPowerRed); //print the number of kilowatts they are now saving whenever the lights are on
        UI.printf("The total kilowatt-hours of energy saved each year is =%.2f kilowatts\n",totalPowerSavePerYear);   //print the total kilowatt-hours of energy saved each year is
        UI.printf("The total dollars saved per year is =%.2f dollars\n ",totalDollasSavedPYear );  //print the total dollars saved per year is:"+totalDollasSavedPYear
        UI.printf("The number of years to break even is =%.2f years\n",numberOfYearsToBreakEven);  //print the number of years to break even is
       }
    }

    /** Calculates and prints how long it would take you to amortise your cost of switching
     *  to energy saving bulbs
     *  For the Completion part consider the life span of each type of bulbs and prints
     *  how much saving will be done after numYears years
     *  
     */
    public void calculateCostSaverCompletion(){
        double priceKWh$ =priceKWh/100;                                                 //conver from $ to cemts
        double incadescentPrice$=incadescentPrice/100;
        double wattageOld=UI.askDouble("what is the wattage of the old bulb?");        //ask the user for the wattage of the old bulb
        double wattageNew=UI.askDouble("what is the wattage of the new bulb?");        //ask the user for the wattage of the new bulb
        int    numberRep=UI.askInt("how many bulbs will be replaced?");               // ask how many bulbs will be replaced
        double totalPowerRed=(wattageOld-wattageNew)*numberRep/1000.0;               //calculate the total power reduction;
        double hoursPDay=UI.askDouble("how many hours the light is on per day?");              //ask how many hours the light is on
        double daysPWeek=UI.askDouble("how many days it's used per week?");          //how many days it's used per week
        double weeksPYear=UI.askDouble("how many weeks it's used per year?");        //ask how many weeks it's used per year
        double years = UI.askDouble(" how many years to calculate the saving over?");
        double costANewLED=UI.askDouble("how many dollars is for each new LED buble"); //ask how many dollars is for each new LED buble
         if(hoursPDay>24||daysPWeek>7||weeksPYear>52){
        UI.println("Please check again if you enter the right number such as, the hours per day <=24, the days per week<=7,and weeks per year<=52");
        }else{
        double totalPowerSavePerYear=totalPowerRed*hoursPDay*daysPWeek*weeksPYear;      //calculate the total power saved per year
        double totalDollasSavedPYear=totalPowerSavePerYear*priceKWh$;                   //calculate the total dollars saved per year     
        double numberOfYearsToBreakEven=numberRep*costANewLED/totalDollasSavedPYear;    //calculate the number of years to break even
        UI.printf("The number of kilowatts they are now saving whenever the lights are on is =%.2f kilowatts\n ",totalPowerRed); //print the number of kilowatts they are now saving whenever the lights are on
        UI.printf("The total kilowatt-hours of energy saved each year is =%.2f kilowatts\n ",totalPowerSavePerYear);   //print the total kilowatt-hours of energy saved each year is
        UI.printf("The total dollars saved per year is=%.2f dollars/n",totalDollasSavedPYear );  //print the total dollars saved per year is:"+totalDollasSavedPYear
        UI.printf("The number of years to break even is =%.2f years/n",numberOfYearsToBreakEven);  //print the number of years to break even is
        double hoursCalculate = hoursPDay*daysPWeek*weeksPYear*years;
        double costOfRuningAndReplacingIncandescentBulbs;
        double costChanginToUsingLEDBulbs;
        if(hoursCalculate<incandescentLifeSpan){
        costOfRuningAndReplacingIncandescentBulbs=hoursCalculate*wattageOld/1000*priceKWh$;  //calculate the cost of running incandescent bulbs.
        UI.printf("The cost of running and replacing incandescent bulbs is=%.2f $ \n ",costOfRuningAndReplacingIncandescentBulbs);
        }
        else{
        costOfRuningAndReplacingIncandescentBulbs= hoursCalculate*wattageOld/1000*priceKWh$+numberRep*incadescentPrice$; //numberRep*(incandescentLifeSpan*wattageOld/1000*priceKWh$+incadescentPrice$);
        UI.printf("The cost of running and replacing incandescent bulbs is=%.2f $ \n ",costOfRuningAndReplacingIncandescentBulbs);
        }
        if(hoursCalculate<ledLifeSpan){
        costChanginToUsingLEDBulbs=hoursCalculate*wattageNew/1000*priceKWh$;// numberRep*(hoursCalculate*wattageOld/1000*priceKWh$+costANewLED); 
        UI.printf("The cost of changing to and then using LED bulbs is =%.2f $ \n ",costChanginToUsingLEDBulbs);
        }else{
        costChanginToUsingLEDBulbs= numberRep*(hoursCalculate*wattageNew/1000*priceKWh$+numberRep*costANewLED); 
        UI.printf("The cost of changing to and then using LED bulbs is =%.2f $ \n ",costChanginToUsingLEDBulbs);
        }
        double savingFromChangingToLEDBulbs=costOfRuningAndReplacingIncandescentBulbs-costChanginToUsingLEDBulbs;
        UI.printf("The savings from changing to LED bulbs is =%.2f $ \n ",savingFromChangingToLEDBulbs);
       }
    }
    public void calculateCostLeavingChallenge(){
       double priceKWh$ =priceKWh/100; 
       double TVAverageWattRemote=6.97, TVAverageWattSwitch=6.6;
       double MicroAverageWatt=3.08;
       String tV="televisions", mIC="microwaves",rE="remote",sW="switch";
       String name=UI.askString("Enter the devic you want to calculate the cost of leaving,either 'televisions' or 'microwaves'?");
       if(name.equals(tV)) {
       String offBy=UI.askString(" Enter the way does turn off the TV by either 'remote'or'switch' ");
       if(offBy.equals(rE)){
           double numbers =UI.askDouble("how many TVs is standby?");
           double hoursPDay=UI.askDouble("how many hours the TV is standby per day?"); 
           double daysPWeek=UI.askDouble("how many days it's standby per week?"); 
           double weeksPYear=UI.askDouble("how many weeks it's standby per year?"); 
           double years = UI.askDouble(" how many years to calculate the cost over?");
                if(hoursPDay>24||daysPWeek>7||weeksPYear>52){
                UI.println("Please check again if you enter the right number such as, the hours per day <=24, the days per week<=7,and weeks per year<=52");
                }else{
                   double costOfLeavingDevices=priceKWh$ *numbers*hoursPDay*daysPWeek*weeksPYear*TVAverageWattRemote/1000;
                   UI.printf("the cost of leaving devices is=%.2f dollars \n",costOfLeavingDevices);
                }
           }
           else if (offBy.equals(sW)){
           double numbers =UI.askDouble("how many TVs is standby?");
           double hoursPDay=UI.askDouble("how many hours the TV is standby per day?"); 
           double daysPWeek=UI.askDouble("how many days it's standby per week?"); 
           double weeksPYear=UI.askDouble("how many weeks it's standby per year?"); 
           double years = UI.askDouble(" how many years to calculate the cost over?");
           if(hoursPDay>24||daysPWeek>7||weeksPYear>52){
                UI.println("Please check again if you enter the right number such as, the hours per day <=24, the days per week<=7,and weeks per year<=52");
                }else{
                       double costOfLeavingDevices=priceKWh$ *numbers*hoursPDay*daysPWeek*weeksPYear*TVAverageWattSwitch/1000;
                       UI.printf("the cost of leaving devices is=%.2f dollars \n",costOfLeavingDevices);
                    }
           }
           else{
           UI.println("You should enter either 'remote'or'switch'");
            }
          
        }
       else if (name.equals(mIC)){
           double numbers =UI.askDouble("how many microwaves is standby?");
           double hoursPDay=UI.askDouble("how many hours the microwaves is standby per day?"); 
           double daysPWeek=UI.askDouble("how many days it's standby per week?"); 
           double weeksPYear=UI.askDouble("how many weeks it's standby per year?"); 
           double years = UI.askDouble(" how many years to calculate the cost over?");
           if(hoursPDay>24||daysPWeek>7||weeksPYear>52){
                UI.println("Please check again if you enter the right number such as, the hours per day <=24, the days per week<=7,and weeks per year<=52");
                }else{
                       double costOfLeavingDevices=priceKWh$ *numbers*hoursPDay*daysPWeek*weeksPYear* MicroAverageWatt/1000;
                       UI.printf("the cost of leaving devices is=%.2f dollars \n",costOfLeavingDevices);
                    }
        }
       else{
          UI.println("You should enter either  'tekevisions'or'microwaves'");
        }
       }
    /** ---------- The code below is already written for you ---------- **/
    /** Constructor, sets up the user interface */
    public PowerCalculator(){
        UI.initialise();
        UI.addButton("Core", this::calculateCostSaverCore); 
        UI.addButton("Completion", this::calculateCostSaverCompletion );
        UI.addButton("Challenge", this::calculateCostLeavingChallenge );
        UI.addButton("Quit", UI::quit);
        UI.setDivider(1);    // Expand the Text pane
    }


}
