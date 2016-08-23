package georgsh.maze3d.game;

/**
 * Created by yura on 29.06.16.
 */
public class Block
    {
    private LeverLogic leftlever = null;
    private LeverLogic rightlever = null;
    private LeverLogic frontlever = null;
    private LeverLogic backlever = null;
    private static final int RIGHT = 1;
    private static final int LEFT = 2;
    private static final int FRONT = 4;
    private static final int BACK = 8;
    private static final int TOP = 16;
    private int mask = 0;
    private int levermask = 0;
    boolean isfinish = false;
    public Block()
        {

        }
    void ResetFaces() { mask = 0; }
    void setFinish() { isfinish = true; }
    boolean isFinish() { return isfinish; }
    LeverLogic getLeverByShift(int x, int y)
        {
        if(x == -1 && y == 0) { return rightlever; }
        if(x == +1 && y == 0) { return leftlever; }
        if(x == 0 && y == -1) { return backlever; }
        if(x == 0 && y == +1) { return frontlever; }

        return null;
        }

    void setRightLever(LeverLogic lever) { levermask |= RIGHT; rightlever = lever; }
    void setLeftLever(LeverLogic lever)  { levermask |= LEFT;  leftlever = lever; }
    void setFrontLever(LeverLogic lever) { levermask |= FRONT; frontlever = lever; }
    void setBackLever(LeverLogic lever)  { levermask |= BACK;  backlever = lever; }

    void setRightFace() { mask |= RIGHT; }
    void setLeftFace()  { mask |= LEFT; }
    void setFrontFace() { mask |= FRONT; }
    void setBackFace()  { mask |= BACK; }
    void setTopFace()   { mask |= TOP; }

    boolean isEmpty() { return mask == 0; }

    boolean haveRight() { return (mask & RIGHT) != 0; }
    boolean haveLeft() { return (mask & LEFT) != 0; }
    boolean haveFront() { return (mask & FRONT) != 0; }
    boolean haveBack() { return (mask & BACK) != 0; }

    boolean haveRightLever() { return (levermask & RIGHT) != 0; }
    boolean haveLeftLever() { return (levermask & LEFT) != 0; }
    boolean haveFrontLever() { return (levermask & FRONT) != 0; }
    boolean haveBackLever() { return (levermask & BACK) != 0; }

    void setFaces(boolean right, boolean left, boolean front, boolean back)
        {
        if(right) { setRightFace(); }
        if(left) { setLeftFace(); }
        if(front) { setFrontFace(); }
        if(back) { setBackFace(); }
        }
    }
