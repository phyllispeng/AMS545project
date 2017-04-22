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
    private double angle;
    
 public MPoint(){

    } 

    /**
     *
     * @param x
     * @param y
     */
  
    public MPoint(double x, double y,double angle) {
        this.x = x;
        this.y = y;
        this.angle = angle;
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
    
    public double getA(){
        return angle;
    }
    public void setAngle(double angle){
        this.angle = angle;
    }
    public String toString(){
        return "< "+ x +" , "+ y + " , "+angle+" >" ;
    }
    
}
