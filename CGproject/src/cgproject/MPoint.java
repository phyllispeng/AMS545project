/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cgproject;

/**
 *
 * @author Phyllis Peng
 */
public class MPoint {
    private double x;
    private double y;
    
/*    public MPoint(){
    x=0.0;
    y=0.0;
    }
  */  

    /**
     *
     * @param x
     * @param y
     */
  
    public MPoint(double x, double y) {
        this.x = x;
        this.y = y;
    }
    
    /**
     *
     * @return
     */
    public double getX() {
        return x;
    }

    /**
     *
     * @param x
     */
    public void setX(double x) {
        this.x = x;
    }

    /**
     *
     * @return
     */
    public double getY() {
        return y;
    }

    /**
     *
     * @param y
     */
    public void setY(double y) {
        this.y = y;
    }
    public String toString(){
        return "< "+ x +" , "+ y +" >";
    }
    
}
