/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package cgproject;
import java.lang.Math.*;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;

//import cgproject.MPoint;
import java.util.*;
/**
 *
 * @author Phyllis Peng
 */
//C:\Users\Phyllis Peng\Documents\AMS 545\ams545project\CGproject\src\cgproject

public class CHAlgo {
    public MPoint mp;
    
    /**
     *
     * @param n
     * @param m
     * @return
     */
    
    
    public static double ChanALGO(int n , int m){
        double p = n/m;
        double r = Math.ceil(p);
        
    return r;
    }

    /**
     *
     */
    public static void ComputeHull( ){
    
    }
    
    /**
     *
     * @param a
     * @param b
     * @param c
     * @return
     * Method to test out if c is strictly on the left of ab 
     */
    public static boolean Left(MPoint a, MPoint b, MPoint c){
        if (PointArea(a,b,c)>0){
            return true;
        }
        return false;
    }
    
    /**
     *
     * @param a
     * @param b
     * @param c
     * @return
     * Method to test out if c is on the left or on of ab 
     */
    public static boolean OnLeft(MPoint a, MPoint b, MPoint c){
        if (PointArea(a,b,c)>=0){
            return true;
        }
        return false;
    }
    
    /**
     *
     * @param a
     * @param b
     * @param c
     * @return
     * Method to test out if c is collinear with ab 
     */
    public static boolean Colinear(MPoint a, MPoint b, MPoint c){
        if (PointArea(a,b,c)==0){
            return true;
        }
        return false;
    }
    
    /**
     *
     * @param a
     * @param b
     * @param c
     * @return
     * Compute to the area between a,b,c
     */
    public static double PointArea(MPoint a, MPoint b, MPoint c){
        double A = (b.getX()-a.getX())*(c.getY()-a.getY())-(c.getX()-a.getX())*(b.getY()-a.getY());
        return A;
    }
    
    /**
     *
     * @param a
     * @param b
     * @return
     * Compute the distance between point a and b
     */
    public static double PointDistance(MPoint a, MPoint b){
        double Xdirection = a.getX()-b.getX();
        double Ydirection = a.getY()-b.getY();
        double D = Math.sqrt(Math.pow(Xdirection,2)+Math.pow(Ydirection,2));
        return D;
    }
    public static ArrayList<MPoint> FindLowest(ArrayList<MPoint> P){
        
         
        int c =0;
        int n = P.size();
        for (int i = 1; i<n;i++){
            if ( (P.get(i).getY() < P.get(c).getY()) || ((P.get(i).getY() == P.get(c).getY() ) && (P.get(i).getX() > P.get(c).getX())) ){
                c = i;
            }
        }
        MPoint temp = P.get(0);
        P.remove(0);
        P.add(0, P.get(c-1)); // put the lowest point at the P0 position
        P.remove(c);
        P.add(c,temp);
        return P;
    }
    
    public static ArrayList<MPoint> loadCSVFile() throws FileNotFoundException{
        ArrayList<MPoint> MP = new ArrayList();
        
        String csvFilePath = "src/cgproject/";
         
        String fileName =csvFilePath+"points.csv";
        Scanner fromFile = new Scanner(new BufferedReader(new FileReader(fileName)));
        /*
        MPoint p0 = new MPoint(0,0);
        MP.add(p0);
        */        
        while(fromFile.hasNextLine()){
            String Input =  fromFile.nextLine();
            String[] p = Input.split(",");
            double Px = Double.parseDouble(p[0]);
            double Py = Double.parseDouble(p[1]);
            
            MPoint P = new MPoint();
            P.setX(Px);
            P.setY(Py);
            MP.add(P);
        }
        
        return MP;
    }
    public static double MAngle (MPoint p0, MPoint pi ){
        double MA = Math.atan2(pi.getY(),pi.getX()) - Math.atan2(p0.getY(),p0.getX());
        return MA;
    }
    /**
     * compare the angle value in the MPoing Object
     */
    public static class CompAngle implements Comparator<MPoint>{
        @Override
         public int compare(MPoint a, MPoint b){
             return (int) (a.getA() - b.getA());
         }       
    }
    
    public static ArrayList<MPoint> sortAngle(ArrayList<MPoint> SortedList){
        MPoint temp = SortedList.get(0);
        SortedList.remove(0);
        Collections.sort(SortedList, new CHAlgo.CompAngle());

        SortedList.add(0,temp);
        return SortedList;
    }
    
    
    public static void main(String[] args) throws FileNotFoundException {
    
     
    
    MPoint a = new MPoint();
    a.setX(9);
    a.setY(7);
    System.out.println(a + "a x is" + a.getX() +" a y is "+a.getY());
    
    ArrayList<MPoint> mpoints = loadCSVFile();
    System.out.println(mpoints);
    System.out.println("=============================================");
    ArrayList<MPoint> L =  FindLowest(mpoints);
    System.out.println(L);
    
    for (int j =1 ; j<L.size();j++){
        
        MPoint P0 = L.get(0);
        P0.setAngle(0);
        MPoint Pj = L.get(j);
       // System.out.println(Pj);
        double A = MAngle(P0,Pj);
        Pj.setAngle(A);
       // System.out.println(A);
       
       
    }
    System.out.println("L before sorting\n"+L);
    ArrayList<MPoint> L1 = sortAngle(L);
    System.out.println(L1);
    }
}
