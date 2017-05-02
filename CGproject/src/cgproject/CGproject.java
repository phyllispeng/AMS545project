/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cgproject;

//import java.awt.event.MouseEvent;
import java.io.FileNotFoundException;
import java.util.*;
import javafx.scene.input.MouseEvent;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.stage.Stage;
import javafx.animation.*;
import javafx.scene.control.Label;

/**
 *
 * @author Phyllis Peng
 */
public class CGproject extends Application {

    private CHAlgo chalgo;

//        public RadioButton AnimeOn;  
//        public RadioButton AnimeOff;  
    public ArrayList<MPoint> mp;
    public Stage s;
    public int m;
    public static Pane c;

    @Override
    public void start(Stage primaryStage) {
        mp = new ArrayList<MPoint>();
        Insets marginlessInsets = new Insets(5, 5, 5, 5);
        Button btn = new Button();
        HBox OptionBox = new HBox();
        Button clearBtn = new Button();
        Button computeBtn = new Button();
        Button loadFile = new Button();
        Button ComputeGS = new Button();
        BorderPane mainPane = new BorderPane();
        TextField tf = new TextField();
        c = new Pane();
        c.resize(800, 600);
        c.setStyle("-fx-background-color #BDC0BA");
        OptionBox.setStyle("-fx-background-color:#A8D8B9 ");
        OptionBox.setAlignment(Pos.BOTTOM_CENTER);
        OptionBox.setPadding(marginlessInsets);
        OptionBox.setSpacing(50.0);

        clearBtn.setText("Clear");
        computeBtn.setText("Compute");
        ComputeGS.setText("Compute and Draw GS");
        loadFile.setText("Import");
        tf.setText("m");

        c.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent e) {
                MPoint me = respondToClick(e);
                mp.add(me);

                drawCircle(me);
                System.out.println("mp list is" + mp);
            }

        });

        loadFile.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {

                try {
                    ArrayList<MPoint> temp = new ArrayList();
                    temp = chalgo.loadCSVFile();
                    for (int i = 0; i < temp.size(); i++) {
                        MPoint dot = temp.get(i);
                        Circle circle = new Circle(5.0);
                        circle.setFill(Color.BLACK);
                        double x = (dot.getX() * 30) + 300;
                        double y = 500-(dot.getY() * 30) ;

                        MPoint ScalePoint = new MPoint(x, y);
                        mp.add(ScalePoint);

                        System.out.println("x is " + x + "y is " + y);
                        circle.setCenterX(x);
                        circle.setCenterY(y);
                        // circle.relocate(x,y);
                        c.getChildren().add(circle);
                    }
                } catch (FileNotFoundException ex) {
                    System.out.print(ex);
                }

                System.out.println("mp list is" + mp);
            }
        });

        clearBtn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                c.getChildren().clear();
                mp.clear();
                tf.setText("m");
                m = 0;
                System.out.println("Clear");
            }
        });
        
        ComputeGS.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                System.out.print("gs"); 
                m = Integer.parseInt(tf.getText());
                CHAlgo.computeAndDrawGS(mp, m);
                System.out.println("Compute GS");
        }
        });
        computeBtn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                try {
                    tf.setStyle("-fx-background-color :white");
                    m = Integer.parseInt(tf.getText());

                } catch (NumberFormatException ex) { // handle your exception
                    tf.setStyle("-fx-background-color : #B5495B");
                    // tf.setStyle("-fx-text-fill :white");
                }
                ArrayList<MPoint> ch = new ArrayList();
                System.out.println(" m is " + m);

                // the convex hull arraylist
                if (m != 0) {
                    ch = chalgo.ChanALGO(mp, m);
                    System.out.println("chis \n" + ch);
                }

                // L.add(mp.get(0));
                if (ch.isEmpty()) {
                    c.getChildren().clear();
                    mp.clear();
                    m = 0;
                    Label fail = new Label();
                    fail.setText("Fail m was too small, click clear to try again");
                    fail.setLayoutX(280);
                    fail.setLayoutY(200);
                    fail.setScaleX(2);
                    fail.setScaleY(2);
                  //  fail.setAlignment(Pos.TOP_CENTER);
                    c.getChildren().add(fail);
                } else {
                 //   drawLines(ch);

                }

            }
        });
        StackPane root = new StackPane();

        root.getChildren().add(mainPane);
        mainPane.setBottom(OptionBox);
        mainPane.setCenter(c);

        Scene scene = new Scene(root, 800, 800);
        OptionBox.getChildren().addAll(tf, computeBtn, ComputeGS,loadFile, clearBtn);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static MPoint respondToClick(MouseEvent me) {
        MPoint mp = new MPoint();
        double x = (double) me.getX();
        double y = (double) me.getY();
        mp.setX(x);
        mp.setY(y);

        return mp;
    }

    public static void drawCircle(MPoint P) {
        Circle circle = new Circle(5.0);

        circle.setFill(Color.BLACK);
        //circle.relocate(me.getX(),me.getY());
        circle.setCenterX(P.getX());
        circle.setCenterY(P.getY());

        c.getChildren().add(circle);
    }

    public static void drawLines(ArrayList<MPoint> L) {

        for (int i = 1; i < L.size() - 1; i++) {
            Line lineA = new Line();
            MPoint start = L.get(i);
            MPoint end = L.get(i + 1);
            lineA.setStartX(start.getX());
            lineA.setStartY(start.getY());
            lineA.setEndX(end.getX());
            lineA.setEndY(end.getY());
            
            lineA.setStrokeWidth(3);
            lineA.setStroke(Color.DARKCYAN);
            lineA.setFill(Color.DARKCYAN);
            c.getChildren().add(lineA);
        }
    }

}
