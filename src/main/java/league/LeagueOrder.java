package league;

import java.util.*;

public class LeagueOrder {

    public static int[] computeRanks(int number, int[][] games) {
        ArrayList<Team> league = new ArrayList<>();

        for (int i = 0; i < number; i++) {
            Team team = new Team();
            team.name = "Team " + i;
            team.number = i;
            league.add(team);
        }
        //assigning points, goals and differences
        for (int[] game : games) {
            //let's record points
            if (game[2] == game[3]) {
                league.get(game[0]).points += 1;
                league.get(game[1]).points += 1;
                //since a draw - nothing in goals dif is changed
            } else if (game[2] > game[3]) {
                league.get(game[0]).points += 2;
            } else {
                league.get(game[1]).points += 2;
            }

            //let's record goals difference
            league.get(game[0]).goalsDifference += game[2] - game[3];
            league.get(game[1]).goalsDifference += game[3] - game[2];

            //let's record goals scored
            league.get(game[0]).goalsScored += game[2];
            league.get(game[1]).goalsScored += game[3];
        }

        league.sort(new SortByGoalsScored());
        league.sort(new SortByGoalsDifference());
        league.sort(new SortByPoints());


        for (int i = 0; i < league.size(); i++) {
            league.get(i).place = i;
        }

        Map<Integer, Integer> map = new HashMap<>();
        //check if places are equal, assigning same rank to teams with equal performance params
        for (int i = 0; i < league.size() - 1; i++) {
            if (league.get(i).goalsScored == league.get(i+1).goalsScored && league.get(i).points == league.get(i + 1).points && league.get(i).goalsDifference == league.get(i+1).goalsDifference){
                league.get(i + 1).place = league.get(i).place;
            }
            map.put(league.get(i).number, league.get(i).place);
            if (i == league.size() - 2) map.put(league.get(i + 1).number, league.get(i + 1).place);
        }

        int[] result = new int[number];
        for (int i = 0; i < number; i++) {
            result[i] = map.get(i) + 1;
        }
        return result;
    }

    static class Team {
        String name;
        int number;
        int place;
        int points;
        int goalsScored;
        int goalsDifference;

        public Team () {
            this.place = 0;
            this.points = 0;
            this.goalsScored = 0;
            this.goalsDifference = 0;
        }
    }

    static class SortByPoints implements Comparator<Team> {
        @Override
        public int compare(Team a, Team b) {
            return b.points - a.points;
        }
    }

    static class SortByGoalsDifference implements Comparator<Team> {
        @Override
        public int compare(Team a, Team b) {
            return b.goalsDifference - a.goalsDifference;
        }
    }

    static class SortByGoalsScored implements Comparator<Team> {
        @Override
        public int compare(Team a, Team b) {
            return b.goalsScored - a.goalsScored;
        }

    }
}

