/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package schedule;

/**
 *
 * @author chris
 */
public class Orgnization {

    private String name;
    private int homeGames;
    private int awayGames;
    private int type;
    //0 full stack
    //1 missing 1 team;
    //2 missing 2 teams;
    public Orgnization(String name, int type){
        homeGames=0;
        awayGames=0;
        this.type = type;
        this.name = name;
    }
    /**
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * @param name the name to set
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * @return the homeGames
     */
    public int getHomeGames() {
        return homeGames;
    }

    /**
     * @param homeGames the homeGames to set
     */
    public void setHomeGames(int homeGames) {
        this.homeGames = homeGames;
    }

    /**
     * @return the awayGames
     */
    public int getAwayGames() {
        return awayGames;
    }

    /**
     * @param awayGames the awayGames to set
     */
    public void setAwayGames(int awayGames) {
        this.awayGames = awayGames;
    }

    /**
     * @return the type
     */
    public int getType() {
        return type;
    }

    /**
     * @param type the type to set
     */
    public void setType(int type) {
        this.type = type;
    }

}
