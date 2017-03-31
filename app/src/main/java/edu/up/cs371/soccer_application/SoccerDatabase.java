package edu.up.cs371.soccer_application;

import android.util.Log;

import edu.up.cs371.soccer_application.soccerPlayer.SoccerPlayer;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.*;

/**
 * Soccer player database -- presently, all dummied up
 * 
 * @author *** put your name here ***
 * @version *** put date of completion here ***
 *
 */
public class SoccerDatabase implements SoccerDB {

    private String playerFirstName;
    private String playerLastName;
    private int playerNumber;
    private String team;

    Hashtable<String, SoccerPlayer> soccerPlayerHashtable = new Hashtable<String, SoccerPlayer>();

    /**
     * add a player
     *
     * @see SoccerDB#addPlayer(String, String, int, String)
     */
    @Override
	public boolean addPlayer(String firstName, String lastName,
			int uniformNumber, String teamName) {
        String key = firstName + " ## " + lastName;
        SoccerPlayer soccerPlayer = new SoccerPlayer(firstName, lastName, uniformNumber, teamName);

        if (soccerPlayerHashtable.containsKey(key))
        {
            return false;
        }
        else
        {
            soccerPlayerHashtable.put(key, soccerPlayer);
            return true;
        }
	}

    /**
     * remove a player
     *
     * @see SoccerDB#removePlayer(String, String)
     */
    @Override
    public boolean removePlayer(String firstName, String lastName) {
        String key = firstName + " ## " + lastName;
        if(soccerPlayerHashtable.containsKey(key))
        {
            soccerPlayerHashtable.remove(key);
            return true;
        }
        else
        {
            return false;
        }
    }

    /**
     * look up a player
     *
     * @see SoccerDB#getPlayer(String, String)
     */
    @Override
	public SoccerPlayer getPlayer(String firstName, String lastName) {

        String key = firstName + " ## " + lastName;
        if (soccerPlayerHashtable.containsKey(key))
        {
            return soccerPlayerHashtable.get(key);
        }
        else
        {
            return null;
        }
    }

    /**
     * increment a player's goals
     *
     * @see SoccerDB#bumpGoals(String, String)
     */
    @Override
    public boolean bumpGoals(String firstName, String lastName) {

        String key = firstName + " ## " + lastName;
        if(soccerPlayerHashtable.containsKey(key))
        {
            SoccerPlayer temp = soccerPlayerHashtable.get(key);
            temp.bumpGoals();
            soccerPlayerHashtable.put(key, temp);
            return true;
        }
        else
        {
            return false;
        }
    }

    /**
     * increment a player's assists
     *
     * @see SoccerDB#bumpAssists(String, String)
     */
    @Override
    public boolean bumpAssists(String firstName, String lastName) {
        return false;
    }

    /**
     * increment a player's shots
     *
     * @see SoccerDB#bumpShots(String, String)
     */
    @Override
    public boolean bumpShots(String firstName, String lastName) {
        String key = firstName + " ## " + lastName;
        if(soccerPlayerHashtable.containsKey(key))
        {
            SoccerPlayer temp = soccerPlayerHashtable.get(key);
            temp.bumpShots();
            soccerPlayerHashtable.put(key, temp);
            return true;
        }
        else
        {
            return false;
        }
    }

    /**
     * increment a player's saves
     *
     * @see SoccerDB#bumpSaves(String, String)
     */
    @Override
    public boolean bumpSaves(String firstName, String lastName) {
        return false;
    }

    /**
     * increment a player's fouls
     *
     * @see SoccerDB#bumpFouls(String, String)
     */
    @Override
    public boolean bumpFouls(String firstName, String lastName) {
        String key = firstName + " ## " + lastName;
        if(soccerPlayerHashtable.containsKey(key))
        {
            SoccerPlayer temp = soccerPlayerHashtable.get(key);
            temp.bumpFouls();
            soccerPlayerHashtable.put(key, temp);
            return true;
        }
        else
        {
            return false;
        }
    }

    /**
     * increment a player's yellow cards
     *
     * @see SoccerDB#bumpYellowCards(String, String)
     */
    @Override
    public boolean bumpYellowCards(String firstName, String lastName) {
        return false;
    }

    /**
     * increment a player's red cards
     *
     * @see SoccerDB#bumpRedCards(String, String)
     */
    @Override
    public boolean bumpRedCards(String firstName, String lastName) {
        return false;
    }

    /**
     * tells the number of players on a given team
     *
     * @see SoccerDB#numPlayers(String)
     */
    @Override
    // report number of players on a given team (or all players, if null)
	public int numPlayers(String teamName) {

        Set<String> keys = soccerPlayerHashtable.keySet();
        int playerCount = 0;

        if(teamName == null)
        {
            return soccerPlayerHashtable.size();
        }
        else
        {
            for (String key: keys) {
                if(soccerPlayerHashtable.get(key).getTeamName().equals(teamName))
                {
                    playerCount++;
                }
            }
            return playerCount;
        }
	}

    /**
     * gives the nth player on a the given team
     *
     * @see SoccerDB#playerNum(int, String)
     */
	// get the nTH player
	@Override
    public SoccerPlayer playerNum(int idx, String teamName) {

        Set<String> keys = soccerPlayerHashtable.keySet();
        SoccerPlayer[] soccerPlayerArray = new SoccerPlayer[soccerPlayerHashtable.size()];
        int i=0;
        for (String key: keys) {
            if(teamName == null)
            {
                soccerPlayerArray[i] = soccerPlayerHashtable.get(key);
                i++;
            }
            else if(soccerPlayerHashtable.get(key).getTeamName().equals(teamName))
            {
                soccerPlayerArray[i] = soccerPlayerHashtable.get(key);
                i++;
            }

        }
        if (idx >= soccerPlayerArray.length)
        {
            return null;
        }
        return soccerPlayerArray[idx];
    }


    /**
     * reads database data from a file
     *
     * @see SoccerDB#readData(java.io.File)
     */
	// read data from file
    @Override
	public boolean readData(File file) {
        return file.exists();
	}

    /**
     * write database data to a file
     *
     * @see SoccerDB#writeData(java.io.File)
     */
	// write data to file
    @Override
	public boolean writeData(File file) {
        FileOutputStream fos = null;
        PrintWriter pr = null;

        Set<String> keys = soccerPlayerHashtable.keySet();
        try
        {
            pr = new PrintWriter(file);
            for (String key: keys)
            {
                pr.println(logString(soccerPlayerHashtable.get(key).getFirstName()));
                pr.println(logString(soccerPlayerHashtable.get(key).getLastName()));
                pr.println(logString(""+soccerPlayerHashtable.get(key).getUniform()));
                pr.println(logString(""+soccerPlayerHashtable.get(key).getGoals()));
                pr.println(logString(""+soccerPlayerHashtable.get(key).getAssists()));
                pr.println(logString(""+soccerPlayerHashtable.get(key).getShots()));
                pr.println(logString(""+soccerPlayerHashtable.get(key).getFouls()));
                pr.println(logString(""+soccerPlayerHashtable.get(key).getSaves()));
                pr.println(logString(""+soccerPlayerHashtable.get(key).getYellowCards()));
                pr.println(logString(""+soccerPlayerHashtable.get(key).getRedCards()));
                pr.println(logString(soccerPlayerHashtable.get(key).getTeamName()));
            }
            pr.close();
            return true;
        }
        catch (FileNotFoundException fne)
        {
            return false;
        }
	}

    /**
     * helper method that logcat-logs a string, and then returns the string.
     * @param s the string to log
     * @return the string s, unchanged
     */
    private String logString(String s) {
        Log.i("write string", s);
        return s;
    }

    /**
     * returns the list of team names in the database
     *
     * @see edu.up.cs371.soccer_application.SoccerDB#getTeams()
     */
	// return list of teams
    @Override
	public HashSet<String> getTeams() {
        return new HashSet<String>();
	}

}
