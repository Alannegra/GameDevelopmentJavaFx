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

        Image left = new Image( "Garroshinvert.gif" );
        Image leftG = new Image( "Garrosh spray.png" );

        Image right = new Image( "Garrosh.gif" );
        Image rightG = new Image( "sun.png" );

        new AnimationTimer()
        {
            int x = 0;
            int y = 0;

            public void handle(long currentNanoTime)
            {

                // Clear the canvas
                gc.clearRect(0, 0, 1980,1080);

                gc.drawImage( background, 0, 0,1980,1080);

                if (input.contains("LEFT"))
                    gc.drawImage( leftG, 64, 64 );
                else
                    gc.drawImage( left, 64, 64 );

                if (input.contains("RIGHT")){
                    x+=1;
                    gc.drawImage( rightG, x, y );
                }else
                    gc.drawImage( right, 256, 64 );
                if (input.contains("UP")){
                    y+=1;
                    gc.drawImage( rightG, x, y );
                }
                if (input.contains("DOWN")){
                    y-=1;
                    gc.drawImage( rightG, x, y );
                }


            }
        }.start();

        theStage.show();
    }
}
