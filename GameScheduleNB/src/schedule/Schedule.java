/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package schedule;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Stack;

/**
 *
 * @author chris
 */
public class Schedule {

    public Schedule() {
        List<Orgnization> list = new ArrayList<>();
        list.add(new Orgnization("Rome", 0));
        list.add(new Orgnization("dodgeville", 1));
        list.add(new Orgnization("utica", 2));
        list.add(new Orgnization("camden", 1));
        list.add(new Orgnization("n utica", 1));
        list.add(new Orgnization("norwich", 0));
        list.add(new Orgnization("new hartford", 0));
        list.add(new Orgnization("cazenovia", 0));
        list.add(new Orgnization("illon", 3));
        list.add(new Orgnization("waterloo", 2));
        list.add(new Orgnization("waterville", 1));
        list.add(new Orgnization("squque", 0));
        list.add(new Orgnization("whitestown", 1));
        list.add(new Orgnization("northern community", 0));
        list.add(new Orgnization("squque", 1));
        System.out.println("total teams " + list.size());
        System.out.println("WEEK 1");
        List<String> playedAlready = new LinkedList<>();
        fullStackGames(list, 0, playedAlready);
        fullStackGames(list, 1, playedAlready);
        fullStackGames(list, 2, playedAlready);
        System.out.println("WEEK 2");
        fullStackGames(list, 0, playedAlready);
        fullStackGames(list, 1, playedAlready);
        fullStackGames(list, 2, playedAlready);
        System.out.println("WEEK 3");
        fullStackGames(list, 0, playedAlready);
        fullStackGames(list, 1, playedAlready);
        fullStackGames(list, 2, playedAlready);
//        System.out.println("WEEK 4");
//        fullStackGames(list, 0,playedAlready);
//        fullStackGames(list, 1,playedAlready);
//        fullStackGames(list, 2,playedAlready);

    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        new Schedule();
    }

    private void fullStackGames(List<Orgnization> list, int type, List<String> playedAlready) {
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
        if(teamA.getType()==1){
            count=3;
        }else if(teamA.getType()==2){
            count = 2;
        }
        for (int i = 1; i < count; i++) {
            String home = teamB.getName() + i;
            String away = teamA.getName() + i;
            System.out.println(away + " @ " + home);
        }
        System.out.println(teamA.getName() + " MM @ "+teamB.getName()+" MM");
        System.out.println(teamA.getName() + " C-Team @ "+teamB.getName()+" C-Team");
        System.out.println(teamA.getName() + " B-Team @ "+teamB.getName()+" B-Team");
        
    }

}
