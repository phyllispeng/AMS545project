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


public class CHAlgo {
    
    /**
     *
     * @param n
     * @param m
     * @return
     */
    
    
    public static ArrayList ChanALGO(ArrayList<MPoint> L , int m){
        int n = L.size();
        int p =  n/m;
        int  r = (int) Math.ceil(p);
        int i = 0;
       ArrayList  PList =split(L,m);
        
        
        ArrayList Polygon = new ArrayList();
        
        //compute convex Hall
        for (int j =0; j<PList.size();j++){
             
            Stack st = GS((ArrayList<MPoint>) PList.get(j));
            Polygon.add(st);
        }
    return Polygon;
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
        double X = pi.getX() - p0.getX();
        double Y = pi.getY() - p0.getY();
        
        double MA = Math.atan2(Y,X);
        return MA;
    }
    /**
     * compare the angle value in the MPoing Object
     * public static class CompAngle implements Comparator<MPoint>{
        @Override
         public int compare(MPoint a, MPoint b){
             return (int) (a.getA() - b.getA());
         }       
    }
    
     *  */
     
   
    
    public static TreeSet<MPoint> sortAngle(ArrayList<MPoint>  L){
        MPoint low =  L.get(0);
    TreeSet<MPoint> SortedSet = new TreeSet<MPoint>(new Comparator<MPoint>(){
            @Override
            public int compare(MPoint o1, MPoint o2) {
                if (o1 == o2 || o1.equals(o2)){
                    
                    return 0;
                }
                double theta1 = MAngle(low,o1);
                double theta2 = MAngle(low,o2);
              
                if (theta1 < theta2){
                    return -1;
                }else if (theta1 > theta2){
                    return 1;
                }
                else{
                    double d1 = PointDistance(low,o1);
                    double d2 = PointDistance(low,o2);
                    
                    if (d1<d2){
                        
                        return -1;
                    }else{
                        
                        return 1;
                        }
                }
            }
            
        }); 
        SortedSet.addAll(L);
        return SortedSet;
    }
    
    public static Stack <MPoint> GS ( ArrayList<MPoint> L){
        //intialize the stack
        
        Stack st =  new Stack();
        MPoint sP = L.get(0); //lowest point is the starting point
        st.push(sP);
        st.push(L.get(1));
        // bottom two whill never be removed
        int i =2;
        while (i<L.size() ){
            MPoint temp = (MPoint) st.pop();
            MPoint p0 = (MPoint) st.peek();
            st.push(temp);
            MPoint p1 = (MPoint) st.peek();
            
            if (Left(p0,p1,L.get(i))){
                st.push(L.get(i));
                i++;
            }else{
                st.pop();
            }
            
        }
        
        
        return st;
        
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
     //   P0.setAngle(0);
        MPoint Pj = L.get(j);        
      //  Pj.setAngle(MAngle(P0,Pj));
        
       
       
    }
    
    TreeSet<MPoint> L1 = sortAngle(L);
   
    ArrayList<MPoint> CG = new ArrayList();
    CG.addAll(L1);
    System.out.println("=============================================");
    Stack aa = new Stack();
    aa = GS(CG);
    System.out.println("stack is"+aa);
     List b =ChanALGO(CG ,  5);
     System.out.println(b);
    }

    private static ArrayList<MPoint> split(ArrayList<MPoint> L, int m) {
        int n = L.size();
        int p =  n/m;
        int  r = (int) Math.ceil(p);
        ArrayList<MPoint> temp = new ArrayList<MPoint>();
        ArrayList P = new ArrayList<MPoint>();
        for (int i =0; i< L.size();i+=m){
            temp.clear();
            System.out.println("i is "+i+"\n");
             for (int j = i; j< i+m;j++){
                 System.out.println("j is "+j+"\n");
                 temp.add(L.get(j));
             }
             P.add(temp);
        }
        return P;
        }
}
