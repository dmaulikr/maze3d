package georgsh.maze3d.game;

import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;
import java.util.Vector;

/**
 * Created by yura on 13.08.16.
 */
public class MapLoader
    {
    private Scanner fileread;
    private String header;
    MyGlSurface game;
    String getHeader() { return header; }

    MapLoader(MyGlSurface game, InputStream file)
        {
        this.game = game;
        fileread = new Scanner(new InputStreamReader(file));
        }
    void readHeader()
        {
        header = "";
        while(true)
            {
            String newline = fileread.nextLine();
            if (newline.equals("*")) { break; }

            header += newline + "\n";
            }
        Log.d("maze3d", "header = "+header);
        fileread.nextLine(); // empty line
        }
    char[][] LoadWalls()
        {
        readHeader();
        int width = fileread.nextInt();
        int height = fileread.nextInt();
        fileread.nextLine(); // end on line
        Log.d("maze3d", "Read map "+width+"x"+height);
        char[][] maze2d = new char[height][];
        for(int y = 0; y < height; y++)
            {
            String newline = fileread.nextLine();
            Log.d("maze3d", "read line "+newline);
            maze2d[y] = newline.toCharArray();
            }
        if(fileread.hasNext())
            fileread.nextLine(); // empty line
        return maze2d;
        }
    LeverWallsSystem LoadOneLever()
        {
        Log.d("maze3d", "Load One Lever");
        LeverWallsSystem new_switcher = new LeverWallsSystem(game);
        int levers_quantity = fileread.nextInt();
        int walls_quantity = fileread.nextInt();
        for(int i = 0; i < levers_quantity; i++)
            {
            int x = fileread.nextInt();
            int y = fileread.nextInt();
            int z_direction = fileread.nextInt();
            Log.d("maze3d", "lever: "+x+", "+y+", "+z_direction);
            new_switcher.AddLever(x,y,z_direction);
            }
        for(int i = 0; i < walls_quantity; i++)
            {
            int x = fileread.nextInt();
            int y = fileread.nextInt();
            Log.d("maze3d", "wall: "+x+", "+y);
            new_switcher.AddWall(x,y);
            }
        return new_switcher;
        }
    Vector<LeverWallsSystem> LoadLevers()
        {
        Log.d("maze3d", "Load Levers");
        Vector<LeverWallsSystem> levers = new Vector<LeverWallsSystem>();
        while (fileread.hasNext())
            {
            if(!fileread.next().equals("LEVER")) { break; }
            levers.add(LoadOneLever());
            }
        return levers;
        }
    }
