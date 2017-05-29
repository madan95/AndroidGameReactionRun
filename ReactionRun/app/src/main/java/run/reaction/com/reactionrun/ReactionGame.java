package run.reaction.com.reactionrun;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.RectF;
import android.os.Bundle;
import android.util.Log;
import android.view.Display;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;

import java.util.ArrayList;

public class ReactionGame extends Activity {
   public static final Integer PREFS_NAME = 0;
private static final String TAG = ReactionGame.class.getSimpleName();
    public GameView gameView;
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        gameView = new GameView(this);
        setContentView(R.layout.actvity_main_menu);
      //  Log.d(TAG, "ReactionGame onCreate");
            }

    public void onClickStartGame(View v){
        //Log.d(TAG, "onClickSTARTgAME");
        setContentView(gameView);
    }

    class GameView extends SurfaceView implements Runnable{
        float mX;
        float mY;
        Thread gameThread = null;
        SurfaceHolder ourHolder;
        volatile boolean playing = true;
        volatile boolean paused = true;
        volatile boolean isMoving = false;
        Canvas canvas;
        Paint paint;
        long fps;
        private long thisFrame;
        int screenX;
        int screenY;
        Person person;

        int count =0;
        int lives = 3;
        int score;
        long startTime;
        long timePassed;

        ArrayList<Balls> al;

        boolean crashed= false;

int highScore;
        public GameView(Context context){
            super(context);
           // Log.d(TAG, "GameView Consturctor");
            ourHolder = getHolder();
            paint = new Paint();
            Display display = getWindowManager().getDefaultDisplay();
            Point size = new Point();
            display.getSize(size);
            screenX = size.x;
            screenY = size.y;
            al = new ArrayList<>();
            //person = new Person(screenX, screenY);
            //ball = new Balls(screenX, screenY);
            newGame();

            }

        public void newGame(){
            SharedPreferences prefs = getSharedPreferences("highScore", Context.MODE_PRIVATE);
            highScore = prefs.getInt("score", 0); //0 is the default value
            //Log.d(TAG, "New Game");
            al.clear();
            person = new Person(screenX, screenY);
            person.reset(screenX, screenY);
            for(int i=0; i<al.size();i++){
                al.remove(i);
            }
            al.add(new Balls(screenX,screenY));
            //ball = new Balls(screenX, screenY);//SAME
            //al.add(ball);
            al.get(0).reset(screenX, screenY);
//            startTime = System.currentTimeMillis();
            lives = 3;
            score =0;

            count = 0;

            playing =true;
         //   paused = false;
                    }
        @Override
        public void run() {
          //  Log.d(TAG, "Inside Main Loop");
            while(playing){
/*
                if(c!=0) {
                counter.start();
                    c = 1;
                }
  */

                long startFrameTime = System.currentTimeMillis();
                if(!paused) {
                   timePassed = System.currentTimeMillis() - startTime;
                    update();
                }
                draw();
                thisFrame = System.currentTimeMillis()-startFrameTime;
                if(thisFrame >= 1){
                    fps = 1000/thisFrame;
                }
                      }
        }
/*
        Thread counter = new Thread() {
                public void run() {
                    do {
                        try {
                            while (paused){
                                wait();}
                            while (!paused){
               //                 score = (int)((System.currentTimeMillis()-startTime)/1000);
                            Thread.sleep(2000);
                            al.add(new Balls(screenX, screenY));

                            al.get(al.size() - 1).reset(screenX, screenY);
                            Log.d(TAG, "Size New Thread on Run");
                            };
                            // counter.join();
                         }catch (Exception e) {
                        }
                    } while (playing);

                }
            };
            */
public void ballCreater(){
           startTime = System.currentTimeMillis();
            al.add(new Balls(screenX, screenY));
        al.get(al.size() - 1).reset(screenX, screenY);
        //Log.d(TAG, "Size New Thread on Run");
}

        public void update(){
        if(timePassed >= 1000){
    ballCreater();
}

/*count = count +1;
            if(count==600){
                al.add(new Balls(screenX, screenY));;
                al.get(al.size()-1).reset(screenX, screenY);
                Log.d(TAG, "Size : " + al.size())
                count =0;
            }*/

score = al.size();
    for(int i=0; i<al.size(); i++){
    if (al.get(i).getRect().bottom > screenY) {
        al.get(i).setRandomXVelocity();
        al.get(i).reverseYVelocity();
        //Log.d(TAG, "TOP xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx");
    }
    if (al.get(i).getRect().top < 0) {
        al.get(i).setRandomXVelocity();
        al.get(i).reverseYVelocity();
    }
    if (al.get(i).getRect().left < 0) {
        al.get(i).setRandomYVelocity();
        al.get(i).reverseXVelocity();
    }
    if (al.get(i).getRect().right > screenX) {
        al.get(i).setRandomYVelocity();
        al.get(i).reverseXVelocity();

    }
    if (RectF.intersects(person.getRect(), al.get(i).getRect())) {

        lives = lives-1;
       // Log.d(TAG, "Number of Lives = " + lives);
        crashed = true;
      //  al.get(i).reverseYVelocity();
       // al.get(i).reverseXVelocity();
        al.get(i).reset(screenX,screenY);
    }
    for(int j=0; j<al.size(); j++){
        if(RectF.intersects(al.get(i).getRect(), al.get(j).getRect())) {
        al.get(i).reverseXVelocity();
           // Log.d(TAG, "2 box hit each other");
            al.get(i).reverseYVelocity();
            al.get(j).reverseYVelocity();
            al.get(j).reverseXVelocity();
        }
        }
    }
            if (lives <=0){
                //endTime = System.currentTimeMillis();
               // score = (int) ((endTime - startTime)/1000);
                paused = true;
                            }

            if(isMoving ){
              person.update(mX,mY,fps);
            }
                    }

        public void draw(){
            if(ourHolder.getSurface().isValid()){
                canvas = ourHolder.lockCanvas();
                canvas.drawColor(Color.argb(255, 0, 0, 0));
                paint.setColor(Color.argb(255, 5, 247, 247));
                canvas.drawRect(person.getRect(), paint);
                   for(int i =0; i<al.size(); i++){
                       paint.setColor(Color.argb(255,250,5,17));
                       canvas.drawRect(al.get(i).getRect(), paint);
                       al.get(i).update(fps);
                   }

               // ball.update(fps);
                //canvas.drawRect(ball.getRect(), paint);

                paint.setColor(Color.argb(255, 0, 255, 68));
                paint.setTextSize(50);
                canvas.drawText( " Score : " + score + "                 Lives Left = " + lives +"                             High Score :" + highScore, 10, 50, paint);
                if(crashed && lives <= 0){
                    paint.setTextSize(100);
                    canvas.drawText(">.< Score Is : " + score, 90, 600, paint);
                    canvas.drawText("Better Luck Next Time :^)", 90, 800, paint);
                    int temp;
                    if(score>highScore){
                        temp = score;
                    }else {
                        temp = highScore;
                    }
                    canvas.drawText("Try To Beat This Score : "+ temp, 90, 1000, paint);

                }

                ourHolder.unlockCanvasAndPost(canvas);
            }
        }
        public void pause(){
            //Log.d(TAG, " Before pause Game Thread State : " + gameThread.getState());
           // Log.d(TAG, " Before pause Game Thread Alive ? : " + gameThread.isAlive());
            playing = false;
            paused = true;
            try{
                gameThread.join();
         //       counter.join();

       //         Log.d(TAG, "Game and Counter " + gameThread.isAlive() + gameThread.getState() + counter.isAlive() + counter.getState());
            }catch(InterruptedException e){
                Log.e("Error:", "Thread Join");
        }
         //   Log.d(TAG, "After pause Game Thread State : " + gameThread.getState());
           // Log.d(TAG, "After Pause Game Thread Alive ? : " + gameThread.isAlive());
        }

    public void resume(){
            //Log.d(TAG, "Before start Game Thread State : " + gameThread.getState());
        //Log.d(TAG, "Before start Game Thread Alive ? : " + gameThread.isAlive());
   playing = true;
    gameThread = new Thread(this);
        if (score>0){
            paused = false;
        }
        //Log.d(TAG, "Before start Game Thread State : " + gameThread.getState());

        //Log.d(TAG, "Before start Game Thread Alive ? : " + gameThread.isAlive());
    gameThread.start();
        //Log.d(TAG, "After Resume Game Thread State : " + gameThread.getState());
       // Log.d(TAG, "After resume Game Thread Alive ? : " + gameThread.isAlive());

        }

        public boolean onTouchEvent(MotionEvent motionEvent){

            switch (motionEvent.getAction()){
                case MotionEvent.ACTION_DOWN:

      //              Log.d(TAG, "clicked down");
        //            Log.d(TAG, "Paused = " + paused + "Playing => " + playing + "Count ==" +count);
                //  Log.d(TAG, "Counter Trhead = "+ counter.isAlive() + "Main Loop = " + gameThread.isAlive() );
                /*    if(count ==0){
                        Log.d(TAG, "clicked down when count =0");
                        paused = false;
                        counter.start();
                        count =1;
                        Log.d(TAG, "After Counter is started " +  counter.isAlive());

                    }
                    */
                     if(count ==0){
                        ballCreater();
                         count =1;
                        }
paused = false;
  //                  Log.d(TAG, "Normal Clicked To get X AND Y");
                        isMoving = true;
                        mX = motionEvent.getX();
                        mY = motionEvent.getY();
    //                    Log.d(TAG, "Paused = " + paused + "Playing => " + playing + "Count ==" +count);
                    //    Log.d(TAG, "Counter Trhead = "+ counter.isAlive() + "Main Loop = " + gameThread.isAlive() );

                    if(lives <=0){
                    //    Log.d(TAG, "Counter after lives = 0 ?" +  counter.isAlive());
//                        Log.d(TAG, "Main Thread Before Pause " +  gameThread.isAlive());
                       /* counter.interrupt();
                       newGame();
                        Log.d(TAG, "OK MATE COUNTER TREHAERADJFL;ASJDFL;A : " + counter.isAlive());
                        count = 2;*/
                    /*pause();
                        pauseInner();
                        Log.d(TAG, "After Pause Counter is " +  counter.isAlive() + gameThread.getState());
                        Log.d(TAG, "After gameThread is interrupted" +  gameThread.isAlive() + gameThread.getState());
                         resume();
                        newGame();
                    */
                       // pause();
                        //resume();
                        if(highScore<score){

                        SharedPreferences prefs = getSharedPreferences( "highScore", Context.MODE_PRIVATE);
                        SharedPreferences.Editor editor = prefs.edit();
                        editor.putInt("score", score);
                        // Commit the edits!
                        editor.commit();
                        }


                        newGame();
                    //System.exit(0);
                    }

                    break;
                case MotionEvent.ACTION_MOVE:

                    if(mX != motionEvent.getX() || mY != motionEvent.getY()){
                        mX = motionEvent.getX();
                        mY = motionEvent.getY();
                    }
                    break;
                case MotionEvent.ACTION_UP:
                    isMoving = false;
                    break;
            }
            return true;
        }

    }
    protected void onResume(){
        super.onResume();
        //Log.d(TAG, "Outer onResume");
       gameView.resume();

    }

    protected void onPause(){
       // Log.d(TAG, "Outer onPause");
        super.onPause();

        gameView.pause();
    }
}
