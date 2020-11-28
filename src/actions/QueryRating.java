package actions;

import fileio.ActionInputData;
import fileio.Input;
import fileio.Writer;
import fileio.MovieInputData;
import fileio.SerialInputData;
import org.json.simple.JSONArray;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.TreeMap;


public class QueryRating extends ActionsRead {
    public QueryRating(final Input input, final Writer fileWriter,
                       final JSONArray jsonArray, final ActionInputData action) {
        super(input, fileWriter, jsonArray, action);
    }
    /**
     */
    public StringBuilder result() {
        StringBuilder output = new StringBuilder();
        HashMap<String, Double> map = new HashMap<>();
        String yearFilter = getAction().getFilters().get(0).get(0);
        String genreFilter = getAction().getFilters().get(1).get(0);

        // se creaza un map cu video-uri si ratingul lor
        // se filtreaza dupa an si gen daca sunt specificate
        if (getAction().getObjectType().equals("movies")) {
            for (MovieInputData movie : getInput().getMovies()) {
                if (yearFilter == null || yearFilter.equals(String.valueOf(movie.getYear()))) {
                    if (genreFilter == null || movie.getGenres().contains(genreFilter)) {
                        if (movie.rating() != 0) {
                            map.put(movie.getTitle(), movie.rating());
                        }
                    }
                }
            }
        } else {
            for (SerialInputData serial : getInput().getSerials()) {
                if (yearFilter == null || yearFilter.equals(String.valueOf(serial.getYear()))) {
                    if (genreFilter == null || serial.getGenres().contains(genreFilter)) {
                        if (serial.rating() != 0) {
                            map.put(serial.getTitle(), serial.rating());
                        }
                    }
                }
            }
        }
        if (map.size() == 0) {
            output.append("Query result: []");
            return output;
        }
        // se sorteaza map-ul
        TreeMap<String, Double> tree = new TreeMap<String, Double>();
        List<String> result = new ArrayList<String>();
        tree = sortbykeyDouble(map);
        result = resultDouble(tree);
        output = queryVideos(result);
        return output;
    }
}
