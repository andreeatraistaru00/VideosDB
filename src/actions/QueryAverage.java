package actions;

import fileio.ActionInputData;
import fileio.Input;
import fileio.Writer;
import fileio.MovieInputData;
import fileio.SerialInputData;
import fileio.ActorInputData;
import org.json.simple.JSONArray;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.TreeMap;


public class QueryAverage extends ActionsRead {
    /**
     */
    public QueryAverage(final Input input, final Writer fileWriter, final JSONArray jsonArray,
                        final ActionInputData action) {
        super(input, fileWriter, jsonArray, action);
    }
    /**
     * functia construieste mesajul pentru fisierul de output
     */
    public StringBuilder result() {
        StringBuilder output = new StringBuilder();
        HashMap<String, Double> map = new HashMap<String, Double>();
        List<MovieInputData> movies = getInput().getMovies();
        List<SerialInputData> series = getInput().getSerials();

        // se creaza un map cu fiecare actor si ratingul sau
        // nu se pun in map actorii cu rating nul
        for (ActorInputData actor : getInput().getActors()) {
            if (actor.average(movies, series) != 0) {
                map.put(actor.getName(), actor.average(movies, series));
            }
        }

        if (map.size() == 0) {
            output.append("Query result: []");
            return output;
        }

        // se sorteaza map-ul dupa rating si alfabetic
        // si se afiseaza primii N actori

        TreeMap<String, Double> tree = new TreeMap<String, Double>();
        List<String> result = new ArrayList<String>();

        tree = sortbykeyDouble(map);
        result = resultDouble(tree);
        output = queryVideos(result);

        return output;
    }
}
