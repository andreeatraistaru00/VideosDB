package actions;

import fileio.ActionInputData;
import fileio.Input;
import fileio.Writer;
import fileio.UserInputData;
import fileio.MovieInputData;
import fileio.SerialInputData;
import org.json.simple.JSONArray;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.TreeMap;

public class RecommendationSearch extends ActionsRead {
    public RecommendationSearch(final Input input, final Writer fileWriter,
                                final JSONArray jsonArray, final ActionInputData action) {
        super(input, fileWriter, jsonArray, action);
    }
    /**
     */
    public StringBuilder result() {
        StringBuilder output = new StringBuilder();
        String user = getAction().getUsername();
        List<UserInputData> userInput = getInput().getUsers();
        HashMap<String, Double> videos = new HashMap<>();

        for (UserInputData userInputData : userInput) {
            if (userInputData.getUsername().equals(user)) {
                // se verifica tipul de abonament al userului
                if (userInputData.getSubscriptionType().equals("PREMIUM")) {
                    // se creaza un map cu toate videourile nevazute de user
                    // si care contin genul specificat
                    for (MovieInputData movie : getInput().getMovies()) {
                        if (!userInputData.getHistory().containsKey(movie.getTitle())
                                && movie.getGenres().contains(getAction().getGenre())) {
                            videos.put(movie.getTitle(), movie.rating());
                        }
                    }
                    for (SerialInputData serial : getInput().getSerials()) {
                        if (!userInputData.getHistory().containsKey(serial.getTitle())
                                && serial.getGenres().contains(getAction().getGenre())) {
                            videos.put(serial.getTitle(), serial.rating());
                        }
                    }
                }
            }
        }

    // se sorteaza map-ul

        TreeMap<String, Double> tree = new TreeMap<String, Double>();
        List<String> result = new ArrayList<String>();
        tree = sortbykeyDouble(videos);
        result = resultDouble(tree);

        if (videos.size() != 0) {
            output.append("SearchRecommendation result: ");
            output.append(result);
            return output;
        }
        output.append("SearchRecommendation cannot be applied!");
        return output;
    }
}
