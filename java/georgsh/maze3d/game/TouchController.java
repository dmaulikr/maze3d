package georgsh.maze3d.game;

import android.opengl.GLSurfaceView;
import android.view.MotionEvent;

/**
 * Created by yura on 22.08.16.
 */
public class TouchController
    {
    float previousX = 0;
    float previousY = 0;
    float controllerX = 0;
    float controllerY = 0;
    float rotatorX = 0;
    float rotatorY = 0;

    int controllerid = -1;
    int rotatorid = -1;

    MyGlSurface game;
    TouchController(MyGlSurface game)
        {
        this.game = game;
        }


    void ControllerMove(MotionEvent event, int pointerindex, int ptrid)
        {
        //Log.d("maze3d", "ACTION_MOVE, ptr_index = "+pointerindex+", ptr_id = "+ptrid);
        float x = event.getX(pointerindex);
        float y = event.getY(pointerindex);
        //Log.d("maze3d", "controller move");
        float dx = x - controllerX;
        float dy = y - controllerY;
        float actual_rotz = (game.rotz - 90f) / 180f * (float) Math.PI;
        float movx = -dx * (float) Math.sin(actual_rotz) +
                dy * (float) Math.sin(actual_rotz + Math.PI / 2);
        float movy = -dx * (float) Math.cos(actual_rotz) +
                dy * (float) Math.cos(actual_rotz + Math.PI / 2);
        float ds = (float)Math.sqrt(movx*movx+movy*movy);
        if(ds > 20)
            {
            game.xsp = 0.04f * movx / ds;
            game.ysp = 0.04f * movy / ds;
            //Log.d("maze3d", "speed: (" + xsp + ", " + ysp + ")");
            }
        }

    void RotatorMove(MotionEvent event, int pointerindex, int ptrid)
        {
        //Log.d("maze3d", "ACTION_MOVE, ptr_index = "+pointerindex+", ptr_id = "+ptrid);
        float x = event.getX(pointerindex);
        float y = event.getY(pointerindex);;
        //Log.d("maze3d", "rotator move");
        //float dx = x - rotatorX;
        //float dy = y - rotatorY;
        float dx = x - previousX;
        float dy = y - previousY;
        float ds = (float) Math.sqrt(dx * dx + dy * dy);
        //dx = dx / scrh;
        //dy = dy / scrh;
        //if (ds > 20)
        //    {
        game.rotMov(dx * 0.3f, dy * 0.3f);
        //rotxsp = dx/ds * 3.0f;
        //rotysp = dy/ds * 3.0f;
        //    }
        previousX = x;
        previousY = y;
        }

    public boolean onTouchEvent(MotionEvent event)
        {
        float x = event.getX();
        float y = event.getY();
        int pointerindex = (event.getAction() & MotionEvent.ACTION_POINTER_INDEX_MASK)
                >> MotionEvent.ACTION_POINTER_INDEX_SHIFT;
        int ptrid = event.getPointerId(pointerindex);
        switch (event.getAction() & MotionEvent.ACTION_MASK)
            {
            case MotionEvent.ACTION_MOVE:
            {
            int pointerCount = event.getPointerCount();
            for (int i = 0; i < pointerCount; ++i)
                {
                pointerindex = i;
                ptrid = event.getPointerId(pointerindex);
                // Log.i("maze3d", "ptrid = " + ptrid+"; rot:"+rotatorid+", cont:"+controllerid);
                if (ptrid == rotatorid)
                    {
                    RotatorMove(event, pointerindex, ptrid);
                    }
                else if (ptrid == controllerid)
                    {
                    ControllerMove(event, pointerindex, ptrid);
                    }
                }

            break;
            }
            case MotionEvent.ACTION_DOWN:
            case MotionEvent.ACTION_POINTER_DOWN:
            {
                /*
                for(LeverLogic lever: levers)
                    {

                    lever.changeState();
                    }
                */
            //Log.d("maze3d", "ACTION_DOWN, ptr_index = "+pointerindex+", ptr_id = "+ptrid+", x:"+x);
            x = event.getX(pointerindex);
            y = event.getY(pointerindex);


            if (x < game.scrw / 2)
                {
                controllerX = x;
                controllerY = y;
                controllerid = ptrid;

                }
            else
                {
                rotatorX = x;
                rotatorY = y;
                rotatorid = ptrid;
                previousX = x;
                previousY = y;
                }
            break;
            }
            case MotionEvent.ACTION_UP:
            case MotionEvent.ACTION_POINTER_UP:
            {
            game.AddTouchEvent(x,y);
            }
            case MotionEvent.ACTION_CANCEL:
            case MotionEvent.ACTION_OUTSIDE:
            {
            //Log.d("maze3d", "ACTION_UP, ptr_index = "+pointerindex+", ptr_id = "+ptrid);
            if (ptrid == controllerid)
                {
                controllerid = -1;
                game.xsp = 0;
                game.ysp = 0;
                }
            if (ptrid == rotatorid)
                {
                rotatorid = -1;
                game.rotxsp = 0;
                game.rotysp = 0;
                }


            //Log.d("maze3d", "ActionUp");
            break;
            }
            }
        return true;
        }
    }
