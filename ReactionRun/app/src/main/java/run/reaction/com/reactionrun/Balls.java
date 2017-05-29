package run.reaction.com.reactionrun;

import android.graphics.Rect;
import android.graphics.RectF;

import java.util.Random;

/**
 * Created by layfon on 16/07/2016.
 */
public class Balls {
    RectF rect;
    float xVelocity ;
    float yVelocity;
    float ballWidth =20;
    float ballHeight = 20;

    public Balls(int screenX, int screenY){
        xVelocity = 200;
        yVelocity = -400;
        rect = new RectF();
    }

    public RectF getRect(){
        return rect;
    }

    public void update(long fps){
        rect.left = rect.left +(xVelocity /fps);
        rect.top = rect.top +(yVelocity/fps);
        rect.right = rect.left + ballWidth;
        rect.bottom = rect.top -ballHeight;
    }

    public void reverseYVelocity(){
        yVelocity = - yVelocity;
    }

    public void reverseXVelocity(){
        xVelocity = - xVelocity;
    }

    public float setRandomXVelocity() {
        Random generator = new Random();
        int answer = generator.nextInt(200);
        if (answer == 0) {
            reverseXVelocity();
        }
        return answer;
    }
        public float setRandomYVelocity(){
        Random generator = new Random();
        int answer = generator.nextInt(200);
        if(answer == 0){
            reverseYVelocity();
        }
            return answer;
        }


    public void reset(int x, int y){
        xVelocity =setRandomXVelocity();
        xVelocity = setRandomYVelocity();
        Random corner = new Random();
        int luck = corner.nextInt(4)+1;
        if (luck==4){
        rect.left = x -(ballWidth+10);
        rect.top = y - 40;}
        else if(luck==3){
            rect.left = 0 +(ballWidth+10);
            rect.top = y - 40;}
        else if(luck==1){
            rect.left = 0 +(ballWidth+10);
        rect.top = 0 + 40;}
    else if( luck==2){
        rect.left = x -(ballWidth+10);
        rect.top = 0;}

        rect.right = rect.left + ballWidth;
        rect.bottom = rect.top + ballHeight;
                }

}
