package com.alan.gamedevelopmentjavafx;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;

import java.util.ArrayList;

public class Keyboard extends Application {

    public void start(Stage theStage)
    {
        theStage.setTitle( "World of Warcraft" );

        Group root = new Group();
        Scene theScene = new Scene( root );
        theStage.setScene( theScene );

        Canvas canvas = new Canvas( 1980, 1080 );
        root.getChildren().add( canvas );

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
        Image axe = new Image( "Gorehowl.png" );



        new AnimationTimer()
        {
            int x = 0;
            int y = 0;

            public void handle(long currentNanoTime)
            {

                // Clear the canvas
                gc.clearRect(0, 0, 1980,1080);

                gc.drawImage( background, 0, 0,1980,1080);


                if(input.contains("F")){
                    gc.drawImage( axe, x + 10, y + 10 );
                }

                if(x == 1600 ) {
                    x-=5;
                    gc.drawImage( right, x, y );
                    //System.out.println("X: " + x + "Y: " + y);

                }else
                if(y == 725){
                    y-=5;
                    gc.drawImage( left, x, y );
                }else
                if(x == -50 ) {
                    x+=5;
                    gc.drawImage( left, x, y );
                }else
                if(y == -50){
                    y+=5;
                    gc.drawImage( left, x, y );
                }
                    //System.out.println("X: " + x + "Y: " + y);

                if (input.contains("RIGHT") && input.contains("UP")){
                    x+=5;
                    y-=5;
                    gc.drawImage( right, x, y );
                }else
                if (input.contains("RIGHT") && input.contains("DOWN")){
                    x+=5;
                    y+=5;
                    gc.drawImage( right, x, y );
                }else
                if (input.contains("LEFT") && input.contains("UP")){
                    x-=5;
                    y-=5;
                    gc.drawImage( left, x, y );
                }else
                if (input.contains("LEFT") && input.contains("DOWN")){
                    x-=5;
                    y+=5;
                    gc.drawImage( left, x, y );
                }else
                if (input.contains("RIGHT")){
                    x+=5;
                    gc.drawImage( right, x, y );
                }else
                if (input.contains("UP")){
                    y-=5;
                    gc.drawImage( left, x, y );
                }else
                if (input.contains("DOWN")){
                    y+=5;
                    gc.drawImage( left, x, y );
                }else
                if (input.contains("LEFT")){
                    x-=5;
                    gc.drawImage( left, x, y );
                }
                else gc.drawImage( left, x, y );

                }

        }.start();

        theStage.show();
    }
}
