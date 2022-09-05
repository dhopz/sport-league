package league;

import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.IntStream;

public class App {
    private static final Random rand;
    static Logger logger = Logger.getLogger(App.class.getName());

    private static final Integer number = 6;

    private static final List<String> completedGames = new ArrayList<>();
    private static final List<int[]> games = new ArrayList<>();
    private static final List<int[]> gamePoints = new ArrayList<>();
    private static final List<int[]> resultsTable = new ArrayList<>();




    static {
        try {
            rand = SecureRandom.getInstanceStrong();
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }

    public static void main(String[] args){
        logger.log(Level.INFO,"Hello World {0} ",randomScore());

        gameResults();
        calcPoints();

        generateTable();
        for (int[] game:resultsTable){
            System.out.println(Arrays.toString(game));
        }


    }

    public static Integer randomScore() {
        return rand.nextInt(5);
    }

    public static int[] numberOfTeams(){
        return IntStream.rangeClosed(0, 5).toArray();
    }

    public static void createGames(int[] game){
        games.add(game);
    }

    public static void completeFixtures(String fixture) {
        completedGames.add(fixture);
    }

    public static boolean gameCompleted(String homeTeam, String awayTeam){
        for (String game: completedGames){
            if (Objects.equals(game, homeTeam + awayTeam)) {
                return true;
            } else if (Objects.equals(game, awayTeam + homeTeam)) return true;
            }
        return false;
    }

    public static void gameResults(){
        int[] teams = numberOfTeams();

        for (int homeTeam : teams) {
            for (int awayTeam : teams) {
                if (Objects.equals(homeTeam, awayTeam)) continue;
                if (!gameCompleted(String.valueOf(homeTeam), String.valueOf(awayTeam))) {
                    completeFixtures(String.valueOf(homeTeam) + String.valueOf(awayTeam));
                    int[] game = {homeTeam,awayTeam,randomScore(),randomScore()};
                    createGames(game);
                }
            }
        }
    }
    public static void calcPoints(){
        //[team,goalsfor,goalsagainst,goaldiff,points]
        for (int[] game:games){
            int homePoints;
            int awayPoints;

            if(game[2] > game[3]){
                homePoints = 3;
                awayPoints = 0;
            } else if(game[2] < game[3]){
                homePoints = 0;
                awayPoints = 3;
            } else {
                homePoints =1;
                awayPoints = 1;
            }
            gamePoints.add(new int[]{game[0],game[2],game[3],game[2]-game[3],homePoints});
            gamePoints.add(new int[]{game[1],game[3],game[2],game[3]-game[2],awayPoints});

        }

    }

    public static void generateTable(){
        int[] teams = numberOfTeams();
        for (int team:teams){
            int goalsFor= 0;
            int goalsAgainst= 0;
            int goalDifference= 0;
            int points= 0;


            for (int[] result: gamePoints){

                if(Objects.equals(result[0], team)){
                    points = points + result[4];
                    goalsFor= goalsFor + result[1];
                    goalsAgainst= goalsAgainst + result[2];
                    goalDifference= goalDifference + result[3];
                }
            }
            resultsTable.add(new int[] {team,goalsFor,goalsAgainst,goalDifference,points});
        }
    }

}
