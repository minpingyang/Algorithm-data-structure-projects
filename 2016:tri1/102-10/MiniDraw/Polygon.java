// This program is copyright VUW.
// You are granted permission to use it to construct your answer to a COMP102 assignment.
// You may not distribute it in any other way without permission.

/* Code for COMP 102 Assignment 10
 * Name:
 * Usercode:
 * ID:
 */

import java.util.*;
import ecs100.*;
import java.awt.Color;
import java.io.*;

/** Polygon represents a polygon made of a sequence of straight lines.
 *  Implements the Shape interface.
 *  Has a field to record the colour of the line and two fields to store
 *  lists of the x coordinates and the y coordinates of all the vertices
 */

public class Polygon implements Shape{
    private ArrayList<Double> x=new ArrayList<Double>();
    private ArrayList<Double> y=new ArrayList<Double>();
    private Color col;
    public Polygon (double x, double y, Color col){
        this.x.add(x);
        this.y.add(y);
        this.col = col;
    }

    public void addXY(double x,double y){
        this.x.add(x);

        this.y.add(y);
    }

    public Polygon (String description){
        Scanner data = new Scanner(description);
        int red = data.nextInt();
        int green = data.nextInt();
        int blue = data.nextInt();
        this.col = new Color(red, green, blue);
        int i=0;
        while(data.hasNext()){
            this.x.add(i,data.nextDouble());
            this.y.add(i,data.nextDouble());

            i++;
        }
       
    }

    public String toString(){
        String s="";
        for(int i=0;i<this.x.size();i++){
            s+=" "+this.x.get(i)+" "+this.y.get(i)+" ";
        }

        return ("Polygon "+col.getRed()+" "+col.getGreen()+" "+col.getBlue()+" "+s);
    }

    /**
     * 
     */
    public boolean on(double u, double v){
        double threshold = 3;
        boolean check=false;
        // first check if it is past the ends of the line...
        for(int i=0;i<this.x.size()-1;i++){

            // then check the distance from the point to the line
            double wd = this.x.get(i+1)-this.x.get(i);
            double ht = this.y.get(i+1)-this.y.get(i);
            double lastLineWD=this.x.get(this.x.size()-1)-this.x.get(0);
            double lastLineHT=this.y.get(this.y.size()-1)-this.y.get(0);
            if(Math.abs(((v-this.y.get(i))*wd - (u-this.x.get(i))*ht)/Math.hypot(wd, ht)) <= threshold
            ||Math.abs(((v-this.y.get(0))*lastLineWD - (u-this.x.get(0))*lastLineHT)/Math.hypot(lastLineWD, lastLineHT)) <= threshold
            ){
                return true;
            }
            // distance of a point from a line, from linear algebra
        }

        return false;
    }

    public void moveBy(double dx, double dy){
        for(int i=0;i<this.x.size();i++){
            this.x.set(i,this.x.get(i)+dx);
            this.y.set(i,this.y.get(i)+dy);

        }
    } 

    public void resize(double changeWd, double changeHt){
        double sumX=0,sumY=0;
        for(int i=0;i<this.x.size();i++){
            sumX+=this.x.get(i);
            sumY+=this.y.get(i);
        }
        double centralX=sumX/this.x.size();
        double centralY=sumY/this.y.size();
        for(int i=0;i<this.x.size();i++){
            double x=this.x.get(i);
            double y=this.y.get(i);
            if(x>centralX){
                this.x.set(i,x-changeWd/2.0);                
            }else{
               this.x.set(i,x+changeWd/2.0);  
            }
            if(y>centralY){
                this.y.set(i,y-changeHt/2.0);                
            }else{
               this.y.set(i,y+changeHt/2.0);  
            }
        }

    }

    public void redraw(){
        UI.setColor(this.col);

        int length=this.x.size();
        double[]x=new double[length];
        double[]y=new double[length];
        for(int i=0;i<length;i++){
            x[i]=this.x.get(i);
            y[i]=this.y.get(i);
        }

        UI.drawPolygon(x,y,length);

    }

}
