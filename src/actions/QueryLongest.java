package actions;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.TreeMap;
import org.json.simple.JSONArray;

import fileio.ActionInputData;
import fileio.Input;
import fileio.Writer;
import fileio.MovieInputData;
import fileio.SerialInputData;

public class QueryLongest extends ActionsRead {
    public QueryLongest(final Input input, final Writer fileWriter,
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

    // am creat un map cu titlul fiecarui video si durata sa
        // se filtreaza dupa an si gen daca sunt date ca filtre
            if (getAction().getObjectType().equals("movies")) {
                for (MovieInputData movie : getInput().getMovies()) {
                    if (yearFilter == null || yearFilter.equals(String.valueOf(movie.getYear()))) {
                        if (genreFilter == null || movie.getGenres().contains(genreFilter)) {
                            map.put(movie.getTitle(), movie.getDuration());
                        }
                    }
                }
            } else {
                for (SerialInputData serial : getInput().getSerials()) {
                    if (yearFilter == null || yearFilter.equals(String.valueOf(serial.getYear()))) {
                        if (genreFilter == null || serial.getGenres().contains(genreFilter)) {
                            map.put(serial.getTitle(), serial.durata());
                        }
                    }
                }
            }

        // se sorteaza map-ul

        TreeMap<String, Integer> tree = new TreeMap<String, Integer>();
        List<String> result = new ArrayList<String>();
        tree = sortbykey(map);
        result = result(tree);
        output = queryVideos(result);
        return output;
    }

}
