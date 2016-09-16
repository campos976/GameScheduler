/*
 The MIT License (MIT)
 Copyright (c) <year> <copyright holders>

 Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated documentation files (the "Software"), to deal in the Software without restriction, including without limitation the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software, and to permit persons to whom the Software is furnished to do so, subject to the following conditions:

 The above copyright notice and this permission notice shall be included in all copies or substantial portions of the Software.

 THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */
package schedule;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Stack;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;
import org.apache.commons.io.FileUtils;

/**
 *
 * @author Coach Chris Campos
 */
public class Schedule {

    /**
     * Default team file name
     */
    public static final String TEAM_FILE_NAME = "teams.txt";
    /**
     * Default encoding of file
     */
    public static final String UTF_8 = "utf-8";

    /**
     * Program simply prints to screen. Will have to output to an excel format
     * too.
     *
     * @param fileName
     */
    public Schedule(String fileName) {
        System.out.println("Reading " + TEAM_FILE_NAME);

        List<Orgnization> list = fileToList(new File(fileName));

        System.out.println("total teams " + list.size());
        System.out.println("WEEK 1");
        List<String> playedAlready = new LinkedList<>();
        calculateWeeklyGame(list, 0, playedAlready);
        calculateWeeklyGame(list, 1, playedAlready);
        calculateWeeklyGame(list, 2, playedAlready);
        System.out.println("WEEK 2");
        calculateWeeklyGame(list, 0, playedAlready);
        calculateWeeklyGame(list, 1, playedAlready);
        calculateWeeklyGame(list, 2, playedAlready);
        System.out.println("WEEK 3");
        calculateWeeklyGame(list, 0, playedAlready);
        calculateWeeklyGame(list, 1, playedAlready);
        calculateWeeklyGame(list, 2, playedAlready);
        System.out.println("WEEK 4");
        calculateWeeklyGame(list, 0, playedAlready);
        calculateWeeklyGame(list, 1, playedAlready);
        calculateWeeklyGame(list, 2, playedAlready);
        System.out.println("WEEK 5");
        calculateWeeklyGame(list, 0, playedAlready);
        calculateWeeklyGame(list, 1, playedAlready);
        calculateWeeklyGame(list, 2, playedAlready);
        System.out.println("WEEK 6");
        calculateWeeklyGame(list, 0, playedAlready);
        calculateWeeklyGame(list, 1, playedAlready);
        calculateWeeklyGame(list, 2, playedAlready);

    }

    /**
     * Calculates games that should be played together based on team type and if
     * they have played each other already and also takes into account thow many
     * home games an orgnization has had.
     *
     * @param list
     * @param type
     * @param playedAlready
     */
    private void calculateWeeklyGame(List<Orgnization> list, int type, List<String> playedAlready) {
        Stack<Orgnization> fullStackTeams = new Stack<>();
        for (Orgnization fullStackTeam : list) {
            if (fullStackTeam.getType() == type) {
                fullStackTeams.add(fullStackTeam);
            }
        }
        Collections.shuffle(fullStackTeams);
        while (!fullStackTeams.empty()) {
            Orgnization[] twoTeams = new Orgnization[2];
            fillTwoTeamsWhoHaveNotPlayedBefore(fullStackTeams, playedAlready, twoTeams, 0);

            Orgnization teamA = twoTeams[0];
            Orgnization teamB = twoTeams[1];
            if (teamA == null || teamB == null) {
                System.out.println("Ran out of teams");
                for (Orgnization left : fullStackTeams) {
                    System.out.println("left over " + left.getName());
                }
                break;
            }
            String val = teamA.getName() + teamB.getName();
            playedAlready.add(val);
            if (teamA.getHomeGames() > teamB.getHomeGames()) {
                teamB.setHomeGames(teamB.getHomeGames() + 1);
                printeGame(teamA, teamB);
            } else if (teamA.getHomeGames() <= teamB.getHomeGames()) {
                teamA.setHomeGames(teamA.getHomeGames() + 1);
                printeGame(teamB, teamA);
            } else {
                System.out.println("huuuh?");
            }
        }

    }

    /**
     * Recursive method that finds out if two teams have already faced each
     * other either home @ away or away @ home.
     *
     * @param fullStackTeams
     * @param playedAlready
     * @param ray
     * @param reset
     */
    private void fillTwoTeamsWhoHaveNotPlayedBefore(Stack<Orgnization> fullStackTeams, List<String> playedAlready, Orgnization[] ray, int reset) {

        Collections.shuffle(fullStackTeams);
        Orgnization teamA = fullStackTeams.pop();
        Orgnization teamB = fullStackTeams.pop();
        String one = teamA.getName() + teamB.getName();
        String two = teamB.getName() + teamA.getName();
        if (reset > 200) {
            System.out.println("RESET--------------");
            return;
        }
        if (!playedAlready.contains(one) || !playedAlready.contains(two)) {
            ray[0] = teamA;
            ray[1] = teamB;
        } else {
            fullStackTeams.push(teamA);
            fullStackTeams.push(teamB);
            reset++;
            fillTwoTeamsWhoHaveNotPlayedBefore(fullStackTeams, playedAlready, ray, reset);
        }
    }

    private void printeGame(Orgnization teamA, Orgnization teamB) {
        int count = 4;
        if (teamA.getType() == 1) {
            count = 3;
        } else if (teamA.getType() == 2) {
            count = 2;
        }
        for (int i = 1; i < count; i++) {
            String home = teamB.getName() + i;
            String away = teamA.getName() + i;
            System.out.println(away + " @ " + home);
        }
        System.out.println(teamA.getName() + " MM @ " + teamB.getName() + " MM");
        System.out.println(teamA.getName() + " C-Team @ " + teamB.getName() + " C-Team");
        System.out.println(teamA.getName() + " B-Team @ " + teamB.getName() + " B-Team");

    }

    /**
     * Takes a file of teams and maps it to a list or organizations.
     *
     * @param file
     * @return
     */
    private List<Orgnization> fileToList(File file) {
        List<Orgnization> orgList = new ArrayList<>();
        List<String> readLines;
        String[] splitArray = null;
        try {
            readLines = FileUtils.readLines(file, UTF_8);
            for (String line : readLines) {
                if (!line.startsWith("#") && !line.isEmpty()) {
                    splitArray = line.split(",");
                    if (splitArray.length == 2) {
                        orgList.add(new Orgnization(splitArray[0], Integer.parseInt(splitArray[1].trim())));
                    } else {
                        System.out.println("Invalid Input");
                        System.exit(1);
                    }

                }
            }
        } catch (IOException ex) {
            Logger.getLogger(Schedule.class.getName()).log(Level.SEVERE, null, ex);
        }
        System.out.println("Total teams read in: " + orgList.size());
        return orgList;
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        System.out.println("Game Scheduling Command App");
        System.out.println("To run this program, pass in a file like this:");
        System.out.println("");
        System.out.println("java jar Schedule.jar -f MY_TEAM_FILE.txt");
        System.out.println("or simply run java -jar Schedule.jar");
        String fileName = TEAM_FILE_NAME;
        if (args.length == 0) {
            System.out.println("Using Default Settings and teams.txt");
        } else {
            Options options = new Options();
            options.addOption("f", true, "Team input File");
            CommandLineParser parser = new DefaultParser();
            try {
                CommandLine cmd = parser.parse(options, args);
                String optionValue = cmd.getOptionValue("f");
                if (optionValue == null) {
                    System.out.println("invalid params");
                } else {
                    fileName = optionValue;
                }

            } catch (ParseException ex) {
                Logger.getLogger(Schedule.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        System.out.println("");

        new Schedule(fileName);
    }

}
