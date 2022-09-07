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
    private static final List<String> completedGames = new ArrayList<>();
    private static final List<int[]> games = new ArrayList<>();
    private static final List<int[]> gamePoints = new ArrayList<>();
    private static final List<int[]> resultsTable = new ArrayList<>();
//    private static final HashMap<String,Integer> createGameResults = new HashMap<>();

    static {
        try {
            rand = SecureRandom.getInstanceStrong();
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }

    public static void main(String[] args){
        int number = 6;

//        int[][] games = {
//                new int[]{1, 2, 3, 2},
//                new int[]{1, 3, 2, 0},
//                new int[]{1, 4, 0, 4},
//                new int[]{1, 5, 2, 0},
//                new int[]{2, 3, 0, 0},
//                new int[]{2, 4, 3, 4},
//                new int[]{2, 5, 1, 1},
//                new int[]{3, 4, 0, 0},
//                new int[]{3, 5, 3, 3},
//                new int[]{4, 5, 2, 0},
//        };

//        int[][] games ={
//                {0, 5, 2, 2},
//                {1, 4, 0, 2},
//                {2, 3, 1, 2},
//                {1, 5, 2, 2},
//                {2, 0, 1, 1},
//                {3, 4, 1, 1},
//                {2, 5, 0, 2},
//                {3, 1, 1, 1},
//                {4, 0, 2, 0}
//        };

        int[][] games = {
                {0, 5, 2, 2},   // Team 0 - Team 5 => 2:2
        {1, 4, 0, 2},   // Team 1 - Team 4 => 0:2
            {2, 3, 1, 2},   // Team 2 - Team 3 => 1:2
                {1, 5, 2, 2},   // Team 1 - Team 5 => 2:2
                    {2, 0, 1, 1},   // Team 2 - Team 0 => 1:1
                        {3, 4, 1, 1},   // Team 3 - Team 4 => 1:1
                            {2, 5, 0, 2},   // Team 2 - Team 5 => 0:2
                                {3, 1, 1, 1},   // Team 3 - Team 1 => 1:1
                                    {4, 0, 2, 0}
        };   // Team 4 - Team 0 => 2:0

        System.out.println(Arrays.toString(leagueTable(number, games)));

//        generateGameResults(6);

    }
    public static void sortTable(List<int[]> table){
        table.sort(Comparator.comparing((int[] a) -> a[a.length - 1]).thenComparing((int[] a) -> a[a.length - 2]));
    }

    public static int[] leagueTable(int number, int[][] games){
        calcPoints(games);
        generateTable(number);

        for (int[] game:resultsTable){
            System.out.println(Arrays.toString(game));
        }
        sortTable(resultsTable);

        changeArr(teamRank());

        return teamRank();
    }

    public static int[] teamRank(){
        ArrayList<Integer> rank = new ArrayList<>();
        for (int[] game:resultsTable){
//            logger.log(Level.INFO," Result {0} ",Arrays.toString(game));
            rank.add(game[5]);
        }
        Collections.reverse(rank);

//        System.out.println(rank);

        return rank.stream().mapToInt(i -> i).toArray();
    }

    static void changeArr(int[] input){
        // Copy input array into newArray
        int[] newArray = Arrays.copyOfRange(input,0,input.length);
        Arrays.sort(newArray);
        Map<Integer, Integer> ranks  = new HashMap<>();
        int rank = 1;
        for (int element : newArray) {
            // Update rank of element
            if (ranks.get(element) == null) {
                ranks.put(element, rank);
                rank++;
            }
        }
        // Assign ranks to elements
        for (int index = 0;index < input.length;index++) {
            input[index] = ranks.get(input[index]);
        }

        Map<Integer, Integer> myNewHashMap = new HashMap<>();
        for(Map.Entry<Integer, Integer> entry : ranks.entrySet()){
            myNewHashMap.put(entry.getValue(), entry.getKey());
        }
        System.out.println("this is new hashmap " + myNewHashMap);

        Object[] bbb = myNewHashMap.keySet().toArray();
        List<Integer> nnn = new ArrayList<>();
        for (Object x:bbb){
            int i = (int)x;
            nnn.add(i);
        }
        System.out.println(nnn + " this is nnn \n");


        Object[] aaa = myNewHashMap.values().toArray();
        List<Integer> lll = new ArrayList<>();
        for (Object x:aaa){
            int i = (int)x;
            lll.add(i);
        }
        Collections.reverse(lll);
        System.out.println(lll + " this is lll\n");

        Map<Integer, Integer> map = new HashMap<>();
        for (int i = 0; i < lll.size(); i++) {
            map.put(nnn.get(i),lll.get(i));
            // or map.put(foods.get(i), value[i]) if foods is a List
        }
        System.out.println(map + " this is map \n");


    }

    public static Integer randomScore() {
        return rand.nextInt(5);
    }

    public static int[] numberOfTeams(Integer num){
        return IntStream.rangeClosed(0, num-1).toArray();
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
        int[] teams = numberOfTeams(5);

        for (int homeTeam : teams) {
            for (int awayTeam : teams) {
                if (Objects.equals(homeTeam, awayTeam)) continue;
                if (!gameCompleted(String.valueOf(homeTeam), String.valueOf(awayTeam))) {
                    completeFixtures(String.valueOf(homeTeam) + String.valueOf(awayTeam));
                    int[] game = {homeTeam,awayTeam,randomScore(),randomScore()};
                    createGames(game);
                    System.out.println(Arrays.toString(game));
                }
            }
        }
    }
    public static void calcPoints(int[][] games){
        //[team,goalsfor,goalsagainst,goaldiff,points]
        for (int[] game:games){
            int homePoints;
            int awayPoints;

            if(game[2] > game[3]){
                homePoints = 2;
                awayPoints = 0;
            } else if(game[2] < game[3]){
                homePoints = 0;
                awayPoints = 2;
            } else {
                homePoints =1;
                awayPoints = 1;
            }
            gamePoints.add(new int[]{game[0],game[2],game[3],game[2]-game[3],homePoints});
            gamePoints.add(new int[]{game[1],game[3],game[2],game[3]-game[2],awayPoints});

        }

    }

    public static void generateTable(int number){
        int[] teams = numberOfTeams(number);
        for (int team:teams){
            int goalsFor= 0;
            int goalsAgainst= 0;
            int goalDifference= 0;
            int points= 0;
            int ratio = 0;


            for (int[] result: gamePoints){

                if(Objects.equals(result[0], team)){
                    points = points + result[4];
                    goalsFor= goalsFor + result[1];
                    goalsAgainst= goalsAgainst + result[2];
                    goalDifference= goalDifference + result[3];
                    ratio = points+goalDifference;
                }
            }
            resultsTable.add(new int[] {team,goalsFor,goalsAgainst,goalDifference,points,ratio});
        }
    }
    public static void generateGameResults(int number){
        List<HashMap<String,Integer>> allTheStuff = new ArrayList<>();
        HashMap<String,Integer> createGameResults = new HashMap<>();

        int[] teams = numberOfTeams(number);
        for (int team:teams){

            int goalsFor= 0;
            int goalsAgainst= 0;
            int goalDifference= 0;
            int points= 0;


            for (int[] result: gamePoints){

                if(Objects.equals(result[0], team)) {
                    createGameResults.put("team", result[0]);
                    createGameResults.put("points", points + result[4]);
                    createGameResults.put("goalsFor", goalsFor + result[1]);
                    createGameResults.put("goalsAgainst", goalsAgainst + result[2]);
                    createGameResults.put("goalDifference", goalDifference + result[3]);
                }
                allTheStuff.add(createGameResults);
            }
        }
        System.out.println(allTheStuff);

    }

}
