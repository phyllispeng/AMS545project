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
/**
 *
 * @author Phyllis Peng
 */
public class CGproject extends Application {

    private CHAlgo chalgo;

//        public RadioButton AnimeOn;  
//        public RadioButton AnimeOff;  
    public ArrayList<MPoint> mp;
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
        loadFile.setText("Import");
        tf.setText("m");

        c.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent e) {
                MPoint me = respondToClick(e);
                mp.add(me);
              
                drawCircle(me);
            }

        });

        loadFile.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {

                try {
                    mp = chalgo.loadCSVFile();
                    for (int i = 0; i < mp.size(); i++) {
                        MPoint dot = mp.get(i);
                        Circle circle = new Circle(5.0);
                        circle.setFill(Color.BLACK);
                        double x = (dot.getX() * 25) + 400;
                        double y = (dot.getY() * 25) + 400;
                        System.out.println("x is " + x + "y is " + y);
                        circle.setCenterX(x);
                        circle.setCenterY(y);
                        // circle.relocate(x,y);
                        c.getChildren().add(circle);
                    }
                } catch (FileNotFoundException ex) {
                    System.out.print(ex);
                }
            }
        });

        clearBtn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                c.getChildren().clear();
                mp.clear();
                m=0;
                System.out.println("Clear");
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
                if (m!=0){
               ch= chalgo.ChanALGO( mp ,  m);
                System.out.println("chis \n"+ch);
                }
                
                // L.add(mp.get(0));
               
                for(int k=0; k<ch.size();k++){
                    drawLines(ch);
                    
                }
            }
        });
        StackPane root = new StackPane();

        root.getChildren().add(mainPane);
        mainPane.setBottom(OptionBox);
        mainPane.setCenter(c);

        Scene scene = new Scene(root, 800, 800);
        OptionBox.getChildren().addAll(tf, computeBtn, loadFile, clearBtn);
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
    public static void drawCircle(MPoint P){
          Circle circle = new Circle(5.0);
                
                circle.setFill(Color.BLACK);
                //circle.relocate(me.getX(),me.getY());
                circle.setCenterX(P.getX());
                circle.setCenterY(P.getY());

                c.getChildren().add(circle);
    }
    public static void drawLines(ArrayList<MPoint>  L) {
        ArrayList<MPoint> newL = new ArrayList(); 
        newL= L;
        newL.add(L.get(0));
        
        for (int i = 0; i < newL.size() - 1; i++) {
            Line lineA = new Line();
            MPoint start = newL.get(i);
            MPoint end = newL.get(i+1);
            lineA.setStartX(start.getX());
            lineA.setStartY(start.getY());
            lineA.setEndX(end.getX());
            lineA.setEndY(end.getY());
       
            lineA.setStrokeWidth(3);
            
            c.getChildren().add(lineA);
        }
    }

}
