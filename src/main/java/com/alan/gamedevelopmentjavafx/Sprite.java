package com.alan.gamedevelopmentjavafx;

import javafx.geometry.Rectangle2D;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.input.RotateEvent;
import javafx.scene.shape.Rectangle;

public class Sprite extends Rectangle {
    private Image image;
    private double positionX;
    private double positionY;
    private double velocityX;
    private double velocityY;
    private double width;
    private double height;
    private boolean topdown;
    private boolean leftrigt;

    public Sprite()
    {
        positionX = 0;
        positionY = 0;
        velocityX = 0;
        velocityY = 0;
        topdown = false;
        leftrigt = true;
    }
    public Sprite(String a)
    {
        positionX = 0;
        positionY = 0;
        velocityX = 0;
        velocityY = 0;
        topdown = false;
        leftrigt = true;
    }

    public Sprite(int sa)
    {
        positionX = 0;
        positionY = 0;
        velocityX = 0;
        velocityY = 0;
        topdown = true;
        leftrigt = true;
    }

    public void setImage(Image i)
    {
        image = i;
        width = i.getWidth();
        height = i.getHeight();
    }

    public void setImage(String filename)
    {
        Image i = new Image(filename);
        setImage(i);
    }

    public void setImage(String filename,int w, int h)
    {
        Image i = new Image(filename ,w , h , false,false);
        setImage(i);
    }

    public double getPositionX() {
        return positionX;
    }

    public double getPositionY() {
        return positionY;
    }

    public void setPositionX(double positionX) {
        this.positionX = positionX;
    }

    public void setPositionY(double positionY) {
        this.positionY = positionY;
    }

    public void setPosition(double x, double y)
    {
        positionX = x;
        positionY = y;
    }

    public boolean isTopdown() {
        return topdown;
    }

    public void setTopdown(boolean topdown) {
        this.topdown = topdown;
    }

    public boolean isLeftrigt() {
        return leftrigt;
    }

    public void setLeftrigt(boolean leftrigt) {
        this.leftrigt = leftrigt;
    }

    public void setVelocity(double x, double y)
    {
        velocityX = x;
        velocityY = y;
    }

    public void addVelocity(double x, double y)
    {
        velocityX += x;
        velocityY += y;
    }

    public void update(double time)
    {
        positionX += velocityX * time;
        positionY += velocityY * time;
    }


    public void render(GraphicsContext gc)
    {
        gc.drawImage( image, positionX, positionY );
    }

    public Rectangle2D getBoundary()
    {
        return new Rectangle2D(positionX,positionY,width,height);
    }

    public boolean intersects(Sprite s)
    {
        return s.getBoundary().intersects( this.getBoundary() );
    }

    public String toString()
    {
        return " Position: [" + positionX + "," + positionY + "]"
                + " Velocity: [" + velocityX + "," + velocityY + "]";
    }


}
