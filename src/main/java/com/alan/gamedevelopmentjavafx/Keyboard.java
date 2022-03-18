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

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;

public class Keyboard extends Application {

    class Score {
        String nombre;
        int puntuacion;

        public Score(String nombre, int puntuacion) {
            this.nombre = nombre;
            this.puntuacion = puntuacion;
        }
    }

    List<Score> scoreList = new ArrayList<>();
    List<Score> ordenador = new ArrayList<>();

    double refresh = 20;//ms
    double addBallDuration = 2500;//ms
    double addinvulnerabilidad = 5000;//ms
    double temp = 0;
    double temp2 = 0;
    boolean invulnerabilidad = false;
    int hearthcounter = 3;
    char letra = 65;
    char letra2 = 65;
    char letra3 = 65;
    int arcadeTurno = 1;
    boolean infernaldoor = false;
    int opacity = 1;
    int velocity = 5;

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
        Image background2 = new Image( "Zerg.jpg" );


        Image left = new Image( "Garrosh.gif" );
        Image right = new Image( "Garroshinvert.gif" );
        Image axe = new Image( "Gorehowl.png", 375 , 201 , false,false);
        Image axe2 = new Image( "Gorehowl2.png", 375 , 201 , false,false);
        Image hearth = new Image("hearth.png");
        Image earth = new Image("bubble.png");
        Sprite garrosh = new Sprite();
        garrosh.setImage(left);



        Sprite weapon = new Sprite();
        weapon.setImage(axe);

        Sprite weapon2 = new Sprite();
        weapon2.setImage(axe2);


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
            int axex2= 0;
            int xrandom = 0;
            int yrandom = 0;
            boolean rightleft = true;
            boolean topdown = true;
            boolean vivo = true;


            //Rotate rotate;
            public void handle(long currentNanoTime)
            {

                // Clear the canvas
                gc.clearRect(0, 0, 1980,1080);
                gc2.clearRect(0, 0, 1980,1080);

                if(infernaldoor){
                    gc.drawImage( background2, 0, 0,1980,1080);
                }else {
                    gc.drawImage( background, 0, 0,1980,1080);
                }



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
                        if(velocity == 20){

                        }else {
                            velocity ++;
                        }

                        if(addBallDuration == 200){

                            if(infernaldoor){
                                infernaldoor= false;
                            }else{
                                infernaldoor= true;
                            }
                            addBallDuration = 2500;


                        }

                        addBallDuration-=100;

                        points[0]++;
                    }

                    if (weapon2.intersects(s)){
                        spawner.remove(s);

                        points[0]++;
                    }

                    if(garrosh.intersects(s) && !invulnerabilidad){

                        if(hearthcounter !=0){

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

                //if(false){
                if(hearthcounter != 0){

                    if(input.contains("G")){
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

                    if(input.contains("F")){
                        //gc.drawImage( axe, x + axex, y + 10 );

                        //gc.rotate(weapon.getBoundary().getHeight()+x);

                        //weapon.getTransforms().addAll(rotate);

                        weapon2.setPosition(x + axex2,y + 10);
                        weapon2.render(gc);

                        if(axex2 == -100)axex2=0;
                        axex2-=5;

                   /* if (weapon.intersects(badguy)){
                        muerto = false;
                        points[0]++;
                    }*/

                    }else{
                        weapon2.setPositionX(5000);
                        weapon2.setPositionY(5000);


                    }


                    if(x == 1600 ) x-=5;
                else if(y == 725) y-=5;
                else if(x == -50 ) x+=5;
                else if(y == -50) y+=5;


                    //System.out.println("X: " + x + "Y: " + y);

                if (input.contains("RIGHT") && input.contains("UP")){
                    x+=velocity;
                    y-=velocity;
                    gc.drawImage( right, x, y );
                    derechaizquietda = true;
                }else
                if (input.contains("RIGHT") && input.contains("DOWN")){
                    x+=velocity;
                    y+=velocity;
                    gc.drawImage( right, x, y );
                    derechaizquietda = true;
                }else
                if (input.contains("LEFT") && input.contains("UP")){
                    x-=velocity;
                    y-=velocity;
                    gc.drawImage( left, x, y );
                    derechaizquietda = false;
                }else
                if (input.contains("LEFT") && input.contains("DOWN")){
                    x-=velocity;
                    y+=velocity;
                    gc.drawImage( left, x, y );
                    derechaizquietda = false;
                }else
                if (input.contains("RIGHT")){
                    x+=velocity;
                    gc.drawImage( right, x, y );
                    derechaizquietda = true;
                }else
                if (input.contains("UP")){
                    y-=velocity;
                    if(derechaizquietda){
                        gc.drawImage( right, x, y );
                    }
                    else{
                        gc.drawImage( left, x, y );
                    }
                }else
                if (input.contains("DOWN")){
                    y+=velocity;
                    if(derechaizquietda){
                        gc.drawImage( right, x, y );
                    }
                    else{
                        gc.drawImage( left, x, y );
                    }
                }else
                if (input.contains("LEFT")){
                    x-=velocity;
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
                        opacity=4;
                    }else {
                        opacity=1;
                    }

                }else{
                    weapon.setPositionX(5000);
                    weapon.setPositionY(5000);
                    weapon2.setPositionX(5000);
                    weapon2.setPositionY(5000);
                    opacity = 100;

                    gc2.setFill( Color.BLUE );
                    String pointsText = "Points: " + points[0];
                    //String pointsText = "Points: " + points[0];
                    gc2.fillText( pointsText, 900, 500 );
                    gc2.strokeText( pointsText, 900, 500 );


                    if (input.contains("ENTER")){
                        if(arcadeBoolean)arcadeTurno++;
                        if(arcadeTurno == 20){
                            defaultoptions();
                            defaultoptions2();
                        }
                        //arcadeBoolean = false;
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

                    if(arcadeTurno == 3){
                        if (input.contains("UP")){
                            if(letra3 ==90)letra3-=26;
                            letra3 += 1;
                        }

                        if (input.contains("DOWN")){
                            if(letra3 ==65)letra3+=26;
                            letra3 --;
                        }
                    }

                    if(arcadeTurno >= 4){
                        if(arcadeBoolean)guardarPuntuacion(points[0]);
                        //arcadeBoolean=false;

                        for (int i = 2; i >= 0; i--) {
                            gc2.fillText( scoreList.get(i).nombre + " " + scoreList.get(i).puntuacion, 1300, 360 + ((i+1) * 60) );
                        }

                    }


                    String arcadeTextLETRA1 = Character.toString(letra) ;
                    String arcadeTextLETRA2 = Character.toString(letra2) ;
                    String arcadeTextLETRA3 = Character.toString(letra3) ;

                    gc2.setFill( Color.WHITE );
                    gc2.fillText( arcadeTextLETRA1, 880, 560 );
                    gc2.strokeText( arcadeTextLETRA1, 880, 560 );
                    gc2.fillText( arcadeTextLETRA2, 990, 560 );
                    gc2.strokeText( arcadeTextLETRA2, 990, 560 );
                    gc2.fillText( arcadeTextLETRA3, 1100, 560 );
                    gc2.strokeText( arcadeTextLETRA3, 1100, 560 );

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

            void defaultoptions2(){
               derechaizquietda = false;
                 x = 0;
                 y = 0;
                 axex= 0;
                 axex2= 0;
                 xrandom = 0;
                 yrandom = 0;
                 rightleft = true;
                 topdown = true;
                 vivo = true;
                 points[0] = 0;

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
            if(infernaldoor){
                addVillain2();
            }

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

    private void addVillain2(){
        if(spawner.size() % 2 == 0){
            Sprite sprite = new Sprite(1);
            sprite.setImage("space2.png");
            double x = (Math.random() * 1750)-1600;
            double y = (Math.random() * 725)+1;
            sprite.setPosition(x,y);
            if(hearthcounter  !=0){
                spawner.add(sprite);
            }
        }else {
            Sprite sprite = new Sprite("1");
            sprite.setImage("space2.png");
            double x = (Math.random() * 1750)-1600;
            double y = (Math.random() * 725)+1;
            sprite.setPosition(x,y);
            spawner.add(sprite);
        }
    }

    void guardarPuntuacion(int puntuacion) {
        try {
            FileWriter fileWriter = new FileWriter("scores.txt", true);
            fileWriter.write(""+ letra + letra2+ letra3 + "," + puntuacion + "\n");
            fileWriter.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
        leerPuntuaciones();
    }
    void leerPuntuaciones() {

        try {
            Scanner scanner = new Scanner(new File("scores.txt"));
            scanner.useDelimiter(",|\n");

            while (scanner.hasNext()) {
                String nombre = scanner.next();
                int puntos = scanner.nextInt();
                //scoreList.add(new Score(nombre, puntos));
                ordenador.add(new Score(nombre, puntos));
            }


            while(ordenador.size() > 0){
                int posNotaMax = 0;
                for (int i = 0; i < ordenador.size(); i++) {
                    if(ordenador.get(i).puntuacion > ordenador.get(posNotaMax).puntuacion){
                        posNotaMax = i;
                    }

                }
                scoreList.add(ordenador.get(posNotaMax));
                ordenador.remove(posNotaMax);

            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    void defaultoptions(){
        refresh = 20;//ms
        addBallDuration = 5000;//ms
        addinvulnerabilidad = 5000;//ms
        temp = 0;
        temp2 = 0;
        invulnerabilidad = false;
        hearthcounter = 3;
        letra = 65;
        letra2 = 65;
        letra3 = 65;
        arcadeTurno = 1;
        infernaldoor = false;
        opacity = 1;
        velocity = 5;

        scoreList = new ArrayList<>();
        ordenador = new ArrayList<>();
        arcadeBoolean = true;
        spawner = new ArrayList<>();
    }

}
