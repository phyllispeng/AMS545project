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
import java.util.concurrent.TimeUnit;
import javafx.animation.Animation;
import javafx.animation.FadeTransition;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.PauseTransition;
import javafx.animation.SequentialTransition;
import javafx.animation.Timeline;
import javafx.concurrent.Task;
import javafx.concurrent.WorkerStateEvent;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.util.Duration;

/**
 *
 * @author Phyllis Peng
 */
public class CHAlgo {

    private CGproject cg;

    /**
     *
     * @param L
     * @param n
     * @param m
     * @return
     */
    public static void computeAndDrawGS(ArrayList<MPoint> L, int m) {
        ArrayList<ArrayList<MPoint>> PList = split(L, m);

        ArrayList<ArrayList<MPoint>> Polygon = new ArrayList();
        //compute convex Hall
        for (int j = 0; j < PList.size(); j++) {
            int cellSize = PList.get(j).size();
            switch (cellSize) {
                case 1:
                    Polygon.add(PList.get(j));
                    break;
                case 2:
                    Polygon.add(PList.get(j));
                    break;
                default:
                    ArrayList<MPoint> FindLow = FindLowest((ArrayList<MPoint>) PList.get(j));
                    TreeSet<MPoint> L1 = sortAngle(FindLow);
                    ArrayList<MPoint> CG = new ArrayList();
                    CG.addAll(L1);
                    //Polygon is an array with all the comvex hulls
                    // Polygon = [ [hull_1],[hull_2],[hull_3],...]
                    Polygon.add(ComputeHull(CG));
                    break;
            }
        }

        //draw polygons here
        for (int ply = 0; ply < Polygon.size(); ply++) {
            ArrayList<MPoint> temp = new ArrayList();
            temp = Polygon.get(ply);
            temp.add(temp.get(0));

            for (int t = 0; t < temp.size() - 1; t++) {

                MPoint Ps = temp.get(t);
                MPoint Pe = temp.get(t + 1);
                Line line = drawSingleLine(Ps, Pe);

                CGproject.c.getChildren().add(line);

            }

        }
    }

    public static ArrayList<MPoint> ChanAlgoComplete(ArrayList<MPoint> L) {
        ArrayList<MPoint> CH = new ArrayList();
        for (int t = 1; t < L.size(); t++) {
            System.out.print(t);
            int a = (int) Math.pow(2, Math.pow(2, t));
            int m = Math.min(a, L.size());
            CH = ChanALGO(L, m);
            if (CH.isEmpty()) {
                continue;
            }
        }
        return CH;
    }

    public static ArrayList<MPoint> ChanALGO(ArrayList<MPoint> L, int m) {

        ArrayList<MPoint> Lcopy = new ArrayList();

        Lcopy.addAll(L);

        ArrayList<ArrayList<MPoint>> PList = split(L, m);

        ArrayList<ArrayList<MPoint>> Polygon = new ArrayList();
        //compute convex Hall
        for (int j = 0; j < PList.size(); j++) {
            int cellSize = PList.get(j).size();
            switch (cellSize) {
                case 1:
                    Polygon.add(PList.get(j));
                    break;
                case 2:
                    Polygon.add(PList.get(j));
                    break;
                default:
                    ArrayList<MPoint> FindLow = FindLowest((ArrayList<MPoint>) PList.get(j));
                    TreeSet<MPoint> L1 = sortAngle(FindLow);
                    ArrayList<MPoint> CG = new ArrayList();
                    CG.addAll(L1);
                    //Polygon is an array with all the comvex hulls
                    // Polygon = [ [hull_1],[hull_2],[hull_3],...]
                    Polygon.add(ComputeHull(CG));
                    break;
            }
        }

        //draw polygons here
        for (int ply = 0; ply < Polygon.size(); ply++) {
            ArrayList<MPoint> temp = new ArrayList();
            temp = Polygon.get(ply);
            temp.add(temp.get(0));

            for (int t = 0; t < temp.size() - 1; t++) {

                MPoint Ps = temp.get(t);
                MPoint Pe = temp.get(t + 1);
                Line line = drawSingleLine(Ps, Pe);

                CGproject.c.getChildren().add(line);

            }

        }
        //polygon is the graham scan result
        // initial p0 and p1 
        //p is the convex hull out put

        System.out.println("CHull arraylist \n" + Polygon);

        ArrayList<MPoint> P = new ArrayList();

        ArrayList<MPoint> ATemp = new ArrayList();
        MPoint p1 = FindLowest(Lcopy).get(0);
        MPoint p0 = new MPoint(p1.getX() - 10, 0);
        MPoint pn1 = new MPoint();
        MPoint pn = new MPoint();
        P.add(0, p0);
        P.add(1, p1);

        for (int k = 1; k < m; k++) {

//            System.out.println("K is "+k+"\n==================================================");
            for (int i = 0; i < PList.size(); i++) {

                // test the largest angle in the sub convex hull
                pn1 = MAXAngle(P.get(k), P.get(k - 1), Polygon.get(i));
                ATemp.add(pn1);
//                 System.out.println("\n i is "+i +"the returned angle is "+pn1);
            }
            //System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~\n" + ATemp + "\n~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~\n");
            //draw lines between angles in this loop
            pn = MAXAngle(P.get(k), P.get(k - 1), ATemp);
            //draw lines between angles in this loop
            for (int a = 0; a < ATemp.size(); a++) {

                Line EDGE = drawSingleLine(P.get(k), ATemp.get(a));
                EDGE.setStroke(Color.CRIMSON);
                EDGE.setFill(Color.CRIMSON);
                FadeTransition ft = new FadeTransition(Duration.seconds(2), EDGE);
                ft.setFromValue(0.0);
                ft.setToValue(1.0);
                ft.setCycleCount(k);

                CGproject.c.getChildren().add(EDGE);
                ft.pause();
                ft.play();

                // 6 minutes data per frame
                if (pn != ATemp.get(a)) {

                    final Timeline timeline = new Timeline();
                    timeline.setCycleCount(a);

                    final KeyFrame kf = new KeyFrame(Duration.millis(1000), new EventHandler<ActionEvent>() {
                        @Override
                        public void handle(ActionEvent actionEvent) {
                            EDGE.setVisible(true);
                            timeline.play();
                            EDGE.setVisible(false);
                            timeline.pause();
                            timeline.play();
                        }
                    });
                    timeline.getKeyFrames().add(kf);
                    timeline.play();

                } else {

                    EDGE.setStroke(Color.DARKCYAN);
                    EDGE.setFill(Color.DARKCYAN);
                    FadeTransition ft1 = new FadeTransition(Duration.seconds(2), EDGE);
                    ft1.setFromValue(0.0);
                    ft1.setToValue(1.0);
                    ft1.setCycleCount(k);
                    ft1.play();
                    EDGE.setVisible(true);

                }

            }
            P.add(pn);

            if (pn == P.get(1)) {
                return P;
            }

        }

        P.clear();
        return P;
    }

    public static int largestIndex(ArrayList<Double> A) {
        int index = 0;
        double max = A.get(0);
        for (int i = 0; i < A.size(); i++) {
            if (!Double.isNaN(A.get(i))) {
                if (max < A.get(i)) {
                    max = A.get(i);
                    index = i;

                }
            }
        }
//        System.out.println("\n~~index here is" + index);
        return index;
    }

    /**
     *
     * @param a
     * @param b
     * @param c
     * @return Method to test out if c is strictly on the left of ab
     */
    public static boolean Left(MPoint a, MPoint b, MPoint c) {
        if (PointArea(a, b, c) > 0) {
            return true;
        }
        return false;
    }

    /**
     *
     * @param a
     * @param b
     * @param c
     * @return Method to test out if c is on the left or on of ab
     */
    public static boolean OnLeft(MPoint a, MPoint b, MPoint c) {
        if (PointArea(a, b, c) >= 0) {
            return true;
        }
        return false;
    }

    /**
     *
     * @param p1 the center point
     * @param p2 the pre point
     * @param Q list of Qs
     * @return
     */
    public static MPoint MAXAngle(MPoint p1, MPoint p2, ArrayList<MPoint> Q) {
        ArrayList<Double> Angles = new ArrayList();
//       System.out.println("the Q in the funcntion is "+Q);
        for (int i = 0; i < Q.size(); i++) {
            if (p1 == Q.get(i)) {
                Angles.add(0.0);
            } else {
                //test with every single point in the point list of q
                double dis12 = PointDistance(p1, p2);
                double dis13 = PointDistance(p1, Q.get(i));
                double dis23 = PointDistance(p2, Q.get(i));
                double result = (dis12 * dis12 + dis13 * dis13 - dis23 * dis23) / (2 * dis12 * dis13);
                double angle = Math.acos(result);
                Angles.add(Math.toDegrees(angle));

            }

        }
//        System.out.println("!!"+Angles);

//        System.out.println("angles are \n"+ Angles);
        int index = largestIndex(Angles);

//        System.out.println("the index is  " + index);
//         System.out.println("Q is"+Q);
        return Q.get(index);
    }

    /**
     *
     * @param a
     * @param b
     * @param c
     * @return Method to test out if c is collinear with ab
     */
    public static boolean Colinear(MPoint a, MPoint b, MPoint c) {
        if (PointArea(a, b, c) == 0) {
            return true;
        }
        return false;
    }

    /**
     *
     * @param a
     * @param b
     * @param c
     * @return Compute to the area between a,b,c
     */
    public static double PointArea(MPoint a, MPoint b, MPoint c) {
        double A = (b.getX() - a.getX()) * (c.getY() - a.getY()) - (c.getX() - a.getX()) * (b.getY() - a.getY());
        return A;
    }

    /**
     *
     * @param a
     * @param b
     * @return Compute the distance between point a and b
     */
    public static double PointDistance(MPoint a, MPoint b) {
        double Xdirection = a.getX() - b.getX();
        double Ydirection = a.getY() - b.getY();
        double D = Math.sqrt(Math.pow(Xdirection, 2) + Math.pow(Ydirection, 2));
        return D;
    }

    public static ArrayList<MPoint> FindLowest(ArrayList<MPoint> P) {

        int c = 0;
        int n = P.size();
        for (int i = 1; i < n; i++) {
            if ((P.get(i).getY() < P.get(c).getY()) || ((P.get(i).getY() == P.get(c).getY()) && (P.get(i).getX() > P.get(c).getX()))) {
                c = i;
            }
        }

        Collections.swap(P, 0, c);
        return P;
    }

    public static ArrayList<MPoint> loadCSVFile(String fn) throws FileNotFoundException {
        ArrayList<MPoint> MP = new ArrayList();

        String csvFilePath = "src/cgproject/";

        String fileName = csvFilePath + fn;
        Scanner fromFile = new Scanner(new BufferedReader(new FileReader(fileName)));
        while (fromFile.hasNextLine()) {
            String Input = fromFile.nextLine();
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

    public static double MAngle(MPoint p0, MPoint pi) {
        double X = pi.getX() - p0.getX();
        double Y = pi.getY() - p0.getY();

        double MA = Math.atan2(Y, X);
        return MA;
    }

    public static TreeSet<MPoint> sortAngle(ArrayList<MPoint> L) {
        MPoint low = L.get(0);
        TreeSet<MPoint> SortedSet = new TreeSet<MPoint>(new Comparator<MPoint>() {
            @Override
            public int compare(MPoint o1, MPoint o2) {
                if (o1 == o2 || o1.equals(o2)) {

                    return 0;
                }
                double theta1 = MAngle(low, o1);
                double theta2 = MAngle(low, o2);

                if (theta1 < theta2) {
                    return -1;
                } else if (theta1 > theta2) {
                    return 1;
                } else {
                    double d1 = PointDistance(low, o1);
                    double d2 = PointDistance(low, o2);

                    if (d1 < d2) {

                        return -1;
                    } else {

                        return 1;
                    }
                }
            }

        });
        SortedSet.addAll(L);
        return SortedSet;
    }

    public static ArrayList<MPoint> ComputeHull(ArrayList<MPoint> L) {
        Stack<MPoint> st = new Stack();
        ArrayList Pgons = new ArrayList();
        MPoint sP = L.get(0); //lowest point is the starting point
        st.push(sP);
        st.push(L.get(1));
        // bottom two whill never be removed
        int i = 2;
        while (i < L.size()) {
            MPoint temp = (MPoint) st.pop();
            MPoint p0 = (MPoint) st.peek();
            st.push(temp);
            MPoint p1 = (MPoint) st.peek();

            if (Left(p0, p1, L.get(i))) {
                st.push(L.get(i));
                i++;
            } else {
                st.pop();
            }

        }

        while (!st.isEmpty()) {
            Pgons.add(0, st.pop());
        }

        return Pgons;
    }

    private static ArrayList<ArrayList<MPoint>> split(ArrayList<MPoint> L, double m) {
        double n = L.size();

        double r = Math.ceil(n / m);

        ArrayList PList = new ArrayList();

        int i = 0;
        while (i < r) {

            if (L.size() < m) {
                PList.add(L);
            } else {
                ArrayList<MPoint> temp = new ArrayList();

                for (int j = 0; j < m; j++) {

                    temp.add(L.get(0));
                    L.remove(0);
                }
                PList.add(temp);
                //      System.out.println(L);
            }

            i++;
        }

        return PList;
    }

    public static void animateLine(Line l, int k) {
        Timeline tl = new Timeline(new KeyFrame(Duration.seconds(3), event -> l.setVisible(true)));
        tl.setCycleCount(k);
        tl.play();

    }

    public static void removeLine(Line l, int k) {
        Timeline tl = new Timeline(new KeyFrame(Duration.seconds(3), event -> l.setVisible(false)));
        tl.setCycleCount(k);
        tl.play();

    }

    public static Line drawSingleLine(MPoint Ps, MPoint Pe) {
        Line tempL = new Line();
        tempL.setStartX(Ps.getX());
        tempL.setStartY(Ps.getY());
        tempL.setEndX(Pe.getX());
        tempL.setEndY(Pe.getY());
        tempL.setStrokeWidth(2);
        tempL.setStroke(Color.GOLD);
        tempL.setFill(Color.GOLD);
        return tempL;
    }
//

    public static void main(String[] args) throws FileNotFoundException {

        ArrayList<MPoint> aaa = loadCSVFile("points.csv");
        System.out.println(ChanALGO(aaa, 4));
//       ArrayList<Double> a = new ArrayList();
//       a.add(1.2/0);
//       a.add(1.0);
//       System.out.println(a);
//       System.out.println(largestIndex(a));
    }
}
