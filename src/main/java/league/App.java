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


    static {
        try {
            rand = SecureRandom.getInstanceStrong();
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }

    public static void main(String[] args){
        logger.log(Level.INFO,"Hello World {0} ",randomScore());

        System.out.println("Hello world " + randomScore() + " " + number);
        System.out.println(createGames());
        gameResults();

        System.out.println(completedGames);

    }

    public static Integer randomScore() {
        return rand.nextInt(5);
    }

    public static int[] numberOfTeams(){
        return IntStream.rangeClosed(0, 5).toArray();
    }

    public static List<ArrayList<Integer>> createGames(){
        ArrayList<Integer> results = new ArrayList<>();
        ArrayList<ArrayList<Integer>> games = new ArrayList<>();

        results.add(1);
        results.add(2);
        results.add(3);
        results.add(4);

        games.add(results);

        return games;
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
        System.out.println(Arrays.toString(teams));

        int n = 5;
        int [][] a = new int[n][];


        for (int homeTeam : teams) {
            for (int awayTeam : teams) {
                if (Objects.equals(homeTeam, awayTeam)) continue;
                if (!gameCompleted(String.valueOf(homeTeam), String.valueOf(awayTeam))) {
                    completeFixtures(String.valueOf(homeTeam) + String.valueOf(awayTeam));
                    System.out.println(homeTeam + " " + awayTeam + " " + randomScore() + " " + randomScore());
                }
            }
        }



//        int n = 5;
//        int [][] a = new int[n][];

//        for (int i=0; i<n; i++) {
//            a[i] = new int[]{i,i+1,randomScore(),randomScore()};
//        }

        System.out.println(Arrays.deepToString(a));
    }

}
