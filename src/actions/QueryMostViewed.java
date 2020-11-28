package actions;

import fileio.ActionInputData;
import fileio.Input;
import fileio.Writer;
import fileio.MovieInputData;
import fileio.SerialInputData;
import fileio.UserInputData;
import org.json.simple.JSONArray;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.TreeMap;


public class QueryMostViewed extends ActionsRead {
    public QueryMostViewed(final Input input, final Writer fileWriter,
                           final JSONArray jsonArray, final ActionInputData action) {
        super(input, fileWriter, jsonArray, action);
    }
/**
*/
    public StringBuilder result() {
        StringBuilder output = new StringBuilder();
        HashMap<String, Integer> map = new HashMap<>();
        String yearFilter = getAction().getFilters().get(0).get(0);
        String genreFilter = getAction().getFilters().get(1).get(0);

        for (UserInputData user : getInput().getUsers()) {
            // adaugam vizualizarile pentru fiecare video prin parcurgerea
            // istoricului userilor
            for (MovieInputData movie : getInput().getMovies()) {
                if (user.getHistory().containsKey(movie.getTitle())) {
                    movie.setViews(movie.getViews() + user.getHistory().get(movie.getTitle()));
                }
            }
            for (SerialInputData serial : getInput().getSerials()) {
                if (user.getHistory().containsKey(serial.getTitle())) {
                    serial.setViews(serial.getViews() + user.getHistory().get(serial.getTitle()));
                }

            }

        }
        // am creat un map cu titlul fiecarui video si nr de vizualizari
        // se filtreaza dupa an si gen daca sunt date ca filtre
        if (getAction().getObjectType().equals("movies")) {
            for (MovieInputData movie : getInput().getMovies()) {
                if (yearFilter == null || yearFilter.equals(String.valueOf(movie.getYear()))) {
                    if (genreFilter == null || movie.getGenres().contains(genreFilter)) {
                        if (movie.getViews() != 0) {
                            map.put(movie.getTitle(), movie.getViews());
                        }
                    }
                }
            }
        } else {
            for (SerialInputData serial : getInput().getSerials()) {
                if (yearFilter == null || yearFilter.equals(String.valueOf(serial.getYear()))) {
                    if (genreFilter == null || serial.getGenres().contains(genreFilter)) {
                        if (serial.getViews() != 0) {
                            map.put(serial.getTitle(), serial.getViews());
                        }
                    }
                }
            }
        }

        if (map.size() == 0) {
            output.append("Query result: []");
            return output;
        }
        // sortam map - ul
        TreeMap<String, Integer> tree = new TreeMap<String, Integer>();
        List<String> result = new ArrayList<String>();
        tree = sortbykey(map);
        result = result(tree);
        output = queryVideos(result);
        return output;

    }
}
