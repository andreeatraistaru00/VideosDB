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
import fileio.UserInputData;

public class QueryFavorite extends ActionsRead {
    /**
     */
    public QueryFavorite(final Input input, final Writer fileWriter,
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

        // am luat fiecare lista de favorite a fiecarui user
        //pentru fiecare video am incrementat campul care numara de cate ori
        // a fost adaugat intr-o lista de favorite
        for (UserInputData userInputData : getInput().getUsers()) {
                for (MovieInputData film : getInput().getMovies()) {
                    if (userInputData.getFavoriteMovies().contains(film.getTitle())) {
                        film.setFavorite(film.getFavorite() + 1);
                    }
                }
            for (SerialInputData film : getInput().getSerials()) {
                if (userInputData.getFavoriteMovies().contains(film.getTitle())) {
                    film.setFavorite(film.getFavorite() + 1);
                }
            }

        }
    // am creat un map cu titlul fiecarui video si nr de aparitii in listele de favorite
        // se filtreaza dupa an si gen daca sunt date ca filtre
        if (getAction().getObjectType().equals("movies")) {
            for (MovieInputData movie : getInput().getMovies()) {

                if (yearFilter == null || yearFilter.equals(String.valueOf(movie.getYear()))) {
                    if (genreFilter == null || movie.getGenres().contains(genreFilter)) {
                        if (movie.getFavorite() != 0) {
                            map.put(movie.getTitle(), movie.getFavorite());
                        }
                    }
                }
            }
        } else {
            for (SerialInputData serial : getInput().getSerials()) {
                if (yearFilter == null || yearFilter.equals(String.valueOf(serial.getYear()))) {
                    if (genreFilter == null || serial.getGenres().contains(genreFilter)) {
                        if (serial.getFavorite() != 0) {
                            map.put(serial.getTitle(), serial.getFavorite());
                        }
                    }
                }
            }
        }

        if (map.size() == 0) {
            output.append("Query result: []");
            return output;
        }
        // sortez map-ul

        TreeMap<String, Integer> tree = new TreeMap<String, Integer>();
        List<String> result = new ArrayList<String>();
        tree = sortbykey(map);
        result = result(tree);
        output = queryVideos(result);
        return output;

}

}
