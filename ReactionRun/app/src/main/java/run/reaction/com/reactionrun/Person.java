package run.reaction.com.reactionrun;

import android.graphics.RectF;

/**
 * Created by layfon on 16/07/2016.
 */
public class Person {
    private RectF rect;
    private float length;
    private float height;
    private float x;
    private float y;
    private float personSpeed;
    public Person(int screenX, int screenY){
        length = 40;
        height = 60;
        x = screenX/2;
        y = screenY/2;
        rect = new RectF(x,y,x+length,y+height);
        personSpeed=2000;
    }
    public RectF getRect(){return rect;}
    public void update(float mx, float my, float fps) {
      /* do {
            rect.left = rect.left + (personSpeed / fps);
            rect.right = rect.left+ length;
        rect.top = rect.top - (personSpeed/fps);
        rect.bottom = rect.top - height;
        }while(rect.left == mx && rect.top == my);     //rect.set(mx, my, mx+length, my  -height);

*/
        float xDistance = mx - rect.left;
        float yDistance = my - rect.top;
        float distance = (float) Math.sqrt(xDistance * xDistance + yDistance * yDistance);
        if(distance>1){
            rect.left += xDistance / (personSpeed/fps);
            rect.top += yDistance / (personSpeed/fps);
            rect.right = rect.left+length;
            rect.bottom = rect.top + height;
        }
    }
    public void reset(int x, int y){
        x = x/2;
        y = y/2;

        rect.left = x;
        rect.top = y;
        rect.right = rect.left + length;
        rect.bottom = rect.top + height;

    }
}
