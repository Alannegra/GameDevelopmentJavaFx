package com.alan.gamedevelopmentjavafx;

import javafx.animation.AnimationTimer;
import javafx.animation.KeyFrame;
import javafx.animation.RotateTransition;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.geometry.Rectangle2D;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.input.KeyEvent;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.transform.Rotate;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.util.ArrayList;
import java.util.Collection;
import java.util.concurrent.TimeUnit;

public class Keyboard extends Application {

    double refresh = 20;//ms
    double addBallDuration = 5000;//ms
    double temp = 0;

    ArrayList<Sprite> spawner = new ArrayList<>();

    public void start(Stage theStage)
    {
        theStage.setTitle( "World of Warcraft" );

        Timeline timeline = new Timeline(new KeyFrame(Duration.millis(refresh), e->moveBalls()));
        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.play();

        Group root = new Group();
        Scene theScene = new Scene( root );
        theStage.setScene( theScene );

        Canvas canvas = new Canvas( 1980, 1080 );
        root.getChildren().add( canvas );

        final int[] points = {0};

        ArrayList<String> input = new ArrayList<String>();

        theScene.setOnKeyPressed(
                new EventHandler<KeyEvent>()
                {
                    public void handle(KeyEvent e)
                    {
                        String code = e.getCode().toString();

                        // only add once... prevent duplicates
                        if ( !input.contains(code) )
                            input.add( code );
                    }
                });

        theScene.setOnKeyReleased(
                new EventHandler<KeyEvent>()
                {
                    public void handle(KeyEvent e)
                    {
                        String code = e.getCode().toString();
                        input.remove( code );
                    }
                });

        GraphicsContext gc = canvas.getGraphicsContext2D();

        Image background = new Image( "BackgroundOrgrimmar.jpg" );

        Image left = new Image( "Garrosh.gif" );
        Image right = new Image( "Garroshinvert.gif" );
        Image axe = new Image( "Gorehowl.png", 375 , 201 , false,false);

        Image dehaka = new Image("space.png");
        Sprite badguy = new Sprite();

        Sprite weapon = new Sprite();
        weapon.setImage(axe);
        badguy.setImage(dehaka);


        Font theFont = Font.font( "Helvetica", FontWeight.BOLD, 24 );
        gc.setFont( theFont );
        gc.setStroke( Color.BLACK );
        gc.setLineWidth(1);


        new AnimationTimer()
        {
            boolean derechaizquietda = false;

            int x = 0;
            int y = 0;
            int axex= 0;
            int xrandom = 0;
            int yrandom = 0;
            boolean rightleft = true;
            boolean topdown = true;
            boolean muerto = true;
            Rotate rotate;

            public void handle(long currentNanoTime)
            {

                // Clear the canvas
                gc.clearRect(0, 0, 1980,1080);

                gc.drawImage( background, 0, 0,1980,1080);


                if(input.contains("F")){
                    //gc.drawImage( axe, x + axex, y + 10 );

                    //gc.rotate(weapon.getBoundary().getHeight()+x);

                    Rotate rotate = new Rotate(180,x,y);
                    //rotate.setAngle(20);
                    //rotate.setPivotX(150);
                    //rotate.setPivotY(225);
                    //weapon.getTransforms().add(rotate);

                    weapon.setPosition(x + axex,y + 10);
                    weapon.render(gc);

                    if(axex == 100)axex=0;
                    axex+=5;
                    if (weapon.intersects(badguy)){
                        muerto = false;
                        points[0]++;
                    }
                }

                xrandom = randomx();
                yrandom = randomy();


                for (Sprite s:spawner) {
                    //System.out.println(s.getLayoutX() + " " + s.getLayoutY());

                    s.setPosition(randomxSpawner((int) s.getPositionX()),randomySpawner((int) s.getPositionY()));

                    s.render(gc);
                }
                if(muerto){
                    badguy.setPosition(xrandom ,yrandom);
                    badguy.render(gc);
                    //gc.drawImage(dehaka, xrandom ,yrandom);
                }



                if(x == 1600 ) x-=5;
                else if(y == 725) y-=5;
                else if(x == -50 ) x+=5;
                else if(y == -50) y+=5;


                    //System.out.println("X: " + x + "Y: " + y);

                if (input.contains("RIGHT") && input.contains("UP")){
                    x+=5;
                    y-=5;
                    gc.drawImage( right, x, y );
                    derechaizquietda = true;
                }else
                if (input.contains("RIGHT") && input.contains("DOWN")){
                    x+=5;
                    y+=5;
                    gc.drawImage( right, x, y );
                    derechaizquietda = true;
                }else
                if (input.contains("LEFT") && input.contains("UP")){
                    x-=5;
                    y-=5;
                    gc.drawImage( left, x, y );
                    derechaizquietda = false;
                }else
                if (input.contains("LEFT") && input.contains("DOWN")){
                    x-=5;
                    y+=5;
                    gc.drawImage( left, x, y );
                    derechaizquietda = false;
                }else
                if (input.contains("RIGHT")){
                    x+=5;
                    gc.drawImage( right, x, y );
                    derechaizquietda = true;
                }else
                if (input.contains("UP")){
                    y-=5;
                    if(derechaizquietda){
                        gc.drawImage( right, x, y );
                    }
                    else{
                        gc.drawImage( left, x, y );
                    }
                }else
                if (input.contains("DOWN")){
                    y+=5;
                    if(derechaizquietda){
                        gc.drawImage( right, x, y );
                    }
                    else{
                        gc.drawImage( left, x, y );
                    }
                }else
                if (input.contains("LEFT")){
                    x-=5;
                    gc.drawImage( left, x, y );
                    derechaizquietda = false;
                }
                /*else{
                    gc.drawImage( left, x, y );
                    derechaizquietda = false;
                }*/

                if(derechaizquietda){
                    gc.drawImage( right, x, y );
                }
                else{
                    gc.drawImage( left, x, y );
                }

                gc.setFill( Color.RED );
                String pointsText = "Points: " + points[0];
                gc.fillText( pointsText, 360, 36 );

                gc.strokeText( pointsText, 360, 36 );

                }

                int randomx (){
                if(xrandom == 1750 ){
                    rightleft=false;
                }
                 else if(xrandom == -50 )rightleft=true;
                if(rightleft)return xrandom+5;
                else return xrandom-5;

                }
            int randomy (){
                if(yrandom == 725 ){
                    topdown=false;
                }
                else if(yrandom == -50 )topdown=true;
                if(topdown)return yrandom+5;
                else return yrandom-5;

            }

            int randomxSpawner (int x){
                if(x == 1750 ){
                    rightleft=false;
                }
                else if(x == -50 )rightleft=true;
                if(rightleft)return x+5;
                else return x-5;

            }
            int randomySpawner (int y){
                if(y == 725 ){
                    topdown=false;
                }
                else if(yrandom == -50 )topdown=true;
                if(topdown)return y+5;
                else return y-5;

            }


        }.start();

        theStage.show();
    }

    private void moveBalls() {
        temp = temp + refresh;
        if (temp > addBallDuration) {
            temp = 0;
            System.out.println("Hola");

            addVillain();
        }
    }
    private void addVillain(){
        Sprite sprite = new Sprite();
        sprite.setImage("space.png");
        double x = (Math.random() * 1750)+1;
        double y = (Math.random() * 725)+1;
        sprite.setPosition(x,y);
        spawner.add(sprite);
    }

}
