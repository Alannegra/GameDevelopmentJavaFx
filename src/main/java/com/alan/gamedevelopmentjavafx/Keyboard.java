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
import javafx.scene.effect.BoxBlur;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
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
    double addinvulnerabilidad = 5000;//ms
    double temp = 0;
    double temp2 = 0;
    boolean invulnerabilidad = false;
    int hearthcounter = 3;
    char letra = 65;
    char letra2 = 65;
    int arcadeTurno = 1;
    boolean arcadeBoolean = true;
    ArrayList<Sprite> spawner = new ArrayList<>();

    public void start(Stage theStage)
    {
        theStage.setTitle( "World of Warcraft" );

        Timeline timeline = new Timeline(new KeyFrame(Duration.millis(refresh), e->moveBalls()));
        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.play();

        //if(invulnerabilidad){
        Timeline timeline2 = new Timeline(new KeyFrame(Duration.millis(refresh), e->TimeInvulnerabilidad()));
        timeline2.setCycleCount(Timeline.INDEFINITE);
        timeline2.play();
        //}

        Group root = new Group();
        Scene theScene = new Scene( root );
        theStage.setScene( theScene );

        Canvas canvas = new Canvas( 1980, 1080 );
        Canvas canvas2 = new Canvas(1980, 1080);

        root.getChildren().add( canvas );
        root.getChildren().add( canvas2 );

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
        GraphicsContext gc2 = canvas2.getGraphicsContext2D();

        Image background = new Image( "BackgroundOrgrimmar.jpg" );



        Image left = new Image( "Garrosh.gif" );
        Image right = new Image( "Garroshinvert.gif" );
        Image axe = new Image( "Gorehowl.png", 375 , 201 , false,false);
        Image hearth = new Image("hearth.png");
        Image earth = new Image("bubble.png");
        Sprite garrosh = new Sprite();
        garrosh.setImage(left);



        Sprite weapon = new Sprite();
        weapon.setImage(axe);


        Font theFont = Font.font( "Helvetica", FontWeight.BOLD, 24 );
        gc.setFont( theFont );
        gc.setStroke( Color.BLACK );
        gc.setLineWidth(1);

        Font theFont2 = Font.font( "Helvetica", FontWeight.BOLD, 54 );
        gc2.setFont( theFont2 );
        gc2.setStroke( Color.BLACK );
        gc2.setLineWidth(1);

        /*Rotate rotate = new Rotate(180,100,100);

        rotate.setAngle(20);
        rotate.setPivotX(150);
        rotate.setPivotY(225);*/

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
            boolean vivo = true;

            int opacity = 1;
            //Rotate rotate;
            public void handle(long currentNanoTime)
            {

                // Clear the canvas
                gc.clearRect(0, 0, 1980,1080);
                gc2.clearRect(0, 0, 1980,1080);

                gc.drawImage( background, 0, 0,1980,1080);


                gc.setEffect(new BoxBlur(opacity, opacity, 3));



                if(hearthcounter == 3){
                    gc.drawImage(hearth, 25,0,100,100);
                    gc.drawImage(hearth, 105,0,100,100);
                    gc.drawImage(hearth, 185,0,100,100);
                }else if (hearthcounter == 2){
                    gc.drawImage(hearth, 25,0,100,100);
                    gc.drawImage(hearth, 105,0,100,100);
                }else if (hearthcounter == 1){
                    gc.drawImage(hearth, 25,0,100,100);
                }else{

                }

                if(hearthcounter != 0){
                    garrosh.setPosition(x,y);
                }

                xrandom = randomx();
                yrandom = randomy();


                for (Sprite s:spawner) {

                    if (weapon.intersects(s)){
                        spawner.remove(s);
                        addBallDuration-=100;
                        points[0]++;
                    }

                    if(garrosh.intersects(s) && !invulnerabilidad){

                        if(hearthcounter !=0){
                            opacity+=1.5*opacity;
                            hearthcounter--;
                            invulnerabilidad = true;
                            TimeInvulnerabilidad();
                        }else{
                            garrosh.setPosition(5000,5000);
                        }

                    }



                    s.setPosition(randomxSpawner(s),randomySpawner(s));

                    s.render(gc);
                }

                /*if(muerto){
                    badguy.setPosition(xrandom ,yrandom);
                    badguy.render(gc);
                    //gc.drawImage(dehaka, xrandom ,yrandom);
                }*/

                if(false){
                //if(hearthcounter != 0){

                    if(input.contains("F")){
                        //gc.drawImage( axe, x + axex, y + 10 );

                        //gc.rotate(weapon.getBoundary().getHeight()+x);

                        //weapon.getTransforms().addAll(rotate);

                        weapon.setPosition(x + axex,y + 10);
                        weapon.render(gc);

                        if(axex == 100)axex=0;
                        axex+=5;

                   /* if (weapon.intersects(badguy)){
                        muerto = false;
                        points[0]++;
                    }*/

                    }else{
                        weapon.setPositionX(5000);
                        weapon.setPositionY(5000);


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


                    if(invulnerabilidad){
                        gc.drawImage(earth,x + 100,y +100,200,200);
                    }

                }else{
                    weapon.setPositionX(5000);
                    weapon.setPositionY(5000);
                    opacity = 100;

                    gc2.setFill( Color.BLUE );
                    String pointsText = "Points: " + (int)arcadeTurno;
                    //String pointsText = "Points: " + points[0];
                    gc2.fillText( pointsText, 900, 500 );
                    gc2.strokeText( pointsText, 900, 500 );


                    if (input.contains("ENTER")){
                        if(arcadeBoolean)arcadeTurno++;
                        arcadeBoolean = false;
                    }

                    if(arcadeTurno == 1){
                        if (input.contains("UP")){
                            if(letra ==90)letra-=26;
                            letra += 1;
                        }

                        if (input.contains("DOWN")){
                            if(letra ==65)letra+=26;
                            letra --;
                        }
                    }

                    if(arcadeTurno == 2){
                        if (input.contains("UP")){
                            if(letra2 ==90)letra2-=26;
                            letra2 += 1;
                        }

                        if (input.contains("DOWN")){
                            if(letra2 ==65)letra2+=26;
                            letra2 --;
                        }
                    }




                    String arcadeTextLETRA1 = Character.toString(letra) ;
                    String arcadeTextLETRA2 = Character.toString(letra2) ;

                    gc2.setFill( Color.WHITE );
                    gc2.fillText( arcadeTextLETRA1, 880, 560 );
                    gc2.strokeText( arcadeTextLETRA1, 880, 560 );
                    gc2.fillText( arcadeTextLETRA2, 900, 560 );
                    gc2.strokeText( arcadeTextLETRA2, 900, 560 );


                }



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

            int randomxSpawner (Sprite sprite){

                if(sprite.getPositionX() >= 1750 ){
                    sprite.setPositionX(1750);
                    sprite.setLeftrigt(false);
                }
                else if(sprite.getPositionX() <= -50 ){
                    sprite.setPositionX(-50);
                    sprite.setLeftrigt(true);
                }
                if(sprite.isLeftrigt())return (int) (sprite.getPositionX()+5);
                else return (int) (sprite.getPositionX()-5);

            }
            int randomySpawner (Sprite sprite){
                if(sprite.getPositionY() >= 725 ){
                    sprite.setPositionX(725);
                    sprite.setTopdown(false);
                }
                else if(sprite.getPositionY() <= -50 ){
                    sprite.setPositionY(-50);
                    sprite.setTopdown(true);
                }
                if(sprite.isTopdown())return (int) (sprite.getPositionY()+5);
                else return (int) (sprite.getPositionY()-5);

            }



        }.start();

        theStage.show();
    }

    private void moveBalls() {
        temp = temp + refresh;
        if (temp > addBallDuration) {
            temp = 0;
            //System.out.println("Hola");
            addVillain();

        }
    }

    private void TimeInvulnerabilidad() {
        temp2 = temp2 + refresh;
        if (temp2 > addinvulnerabilidad) {
            temp2 = 0;
            //System.out.println("Hola");
            invulnerabilidad = false;
        }
    }

    private void addVillain(){

        if(spawner.size() % 2 == 0){
            Sprite sprite = new Sprite(1);
            sprite.setImage("space.png");
            double x = (Math.random() * 1750)+1600;
            double y = (Math.random() * 725)+1;
            sprite.setPosition(x,y);
        if(hearthcounter  !=0){
            spawner.add(sprite);
            }


        }else {
            Sprite sprite = new Sprite("1");
            sprite.setImage("space.png");
            double x = (Math.random() * 1750)+1600;
            double y = (Math.random() * 725)+1;
            sprite.setPosition(x,y);
            spawner.add(sprite);
        }




    }

}
