/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cgproject;

//import java.awt.event.MouseEvent;
import java.awt.Graphics;
import java.awt.Graphics2D;
import javafx.scene.input.MouseEvent;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.RadioButton;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Label;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
/**
 *
 * @author Phyllis Peng
 */
public class CGproject extends Application {
    
        
//        public RadioButton AnimeOn;  
//        public RadioButton AnimeOff;  
 
        
  
        @Override
    public void start(Stage primaryStage) {
            Insets marginlessInsets= new Insets(5, 5, 5, 5);
            Button btn = new Button(); 
            HBox OptionBox = new HBox();
            Button clearBtn = new Button(); 
            Button computeBtn = new Button(); 
            BorderPane mainPane = new BorderPane();
            ImageView iv = new ImageView();
            StackPane SP = new StackPane();
            Group g = new Group();
            Label l = new Label();
           
            OptionBox.setStyle("-fx-background-color: green ");
            OptionBox.setAlignment(Pos.BOTTOM_CENTER);
            OptionBox.setPadding(marginlessInsets);
            OptionBox.setSpacing(50.0);
            btn.setText("Say 'Hello World'");
            clearBtn.setText("Clear");
            computeBtn.setText("Compute");
           
            SP.setOnMouseClicked(new EventHandler<MouseEvent>(){
                @Override
                public void handle(MouseEvent e){
                    MPoint me = respondToClick(e);
                    Circle circle = new Circle( 5.0);
                   System.out.println(me);
                
                   
                    
                    circle.setFill(Color.BLACK);
                    circle.setCenterX(me.getX()-300);
                    circle.setCenterY(me.getY()-400);
                    
                     g.getChildren().add(circle);
                    g.relocate(me.getX()-350, me.getY()-450);
                    SP.getChildren().add(g);
                    
                }
                
            });
            
            btn.setOnAction(new EventHandler<ActionEvent>() {

                @Override
                public void handle(ActionEvent event) {
                    System.out.println("Hello World!");
                }
            });
            
            clearBtn.setOnAction(new EventHandler<ActionEvent>(){
                @Override
                public void handle(ActionEvent event){
                    System.out.println("Clear");
                }
            });
            computeBtn.setOnAction(new EventHandler<ActionEvent>(){
                @Override
                public void handle(ActionEvent event){
                    System.out.println("Compute");
                }
            });
        StackPane root = new StackPane();
        //root.getChildren().add(btn);
        root.getChildren().add(mainPane);
        mainPane.setBottom(OptionBox);
        mainPane.setCenter(SP);
      //  SP.getChildren().add(btn);
 
        
        Scene scene = new Scene(root, 800, 600);
        OptionBox.getChildren().addAll(clearBtn,computeBtn);
        primaryStage.setTitle("Hello World!");
        primaryStage.setScene(scene);
        primaryStage.show();
    }
  
    public static MPoint respondToClick(MouseEvent me){
        MPoint mp = new MPoint();
        double x = (double) me.getX();
        double y = (double) me.getY();
        mp.setX(x);
        mp.setY(y);
        
        return mp;
    }

    public void paintCOmponet(Graphics g, MPoint mp){
        g.setColor(java.awt.Color.black);
        int x = (int)mp.getX();
        int y = (int)mp.getY();
        g.fillOval(x, y, 10, 10);
        
    }
    /**
     * @param args the command line arguments
     */
    /*public static void main(String[] args) {
        launch(args);
    }*/
    
}
