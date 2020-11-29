package actions;

import fileio.ActionInputData;
import fileio.Input;
import fileio.Writer;
import fileio.UserInputData;
import fileio.MovieInputData;
import fileio.SerialInputData;
import org.json.simple.JSONArray;

import java.util.List;

public class RecommendationBestUnseen extends ActionsRead {
    public RecommendationBestUnseen(final Input input, final Writer fileWriter,
                                    final JSONArray jsonArray, final ActionInputData action) {
        super(input, fileWriter, jsonArray, action);
    }
/**
 * functia construieste mesajul pentru fisierul de output
*/
    public StringBuilder result() {
        StringBuilder output = new StringBuilder();
        String user = getAction().getUsername();
        List<UserInputData> userInput = getInput().getUsers();
        // se cauta user-ul
        for (UserInputData usernameInput : userInput) {
            if (usernameInput.getUsername().equals(user)) {
                double maxRating = 0;
                String title = null;
                // se parcurg listele de filme si de seriale si se cauta video-ul cu
                // rating-ul maxim nu se afla in istoricul user-ului si se retine titlul acestuia
                for (MovieInputData movie : getInput().getMovies()) {
                    if (!usernameInput.getHistory().containsKey(movie.getTitle())) {
                        if (movie.rating() > maxRating) {
                            maxRating = movie.rating();
                            title = movie.getTitle();
                        }

                    }
                }
                for (SerialInputData serial : getInput().getSerials()) {
                    if (!usernameInput.getHistory().containsKey(serial.getTitle())) {
                        if (serial.rating() > maxRating) {
                            maxRating = serial.rating();
                            title = serial.getTitle();
                        }

                    }
                }
                // daca niciun video nu a primit rating se returneaza primul video nevazut
                if (maxRating == 0) {
                    for (MovieInputData movie : getInput().getMovies()) {
                        if (!usernameInput.getHistory().containsKey(movie.getTitle())) {
                            title = movie.getTitle();
                            output.append("BestRatedUnseenRecommendation result: ");
                            output.append(title);
                            return output;
                        }
                    }
                    for (SerialInputData movie : getInput().getSerials()) {
                        if (!usernameInput.getHistory().containsKey(movie.getTitle())) {
                            title = movie.getTitle();
                            output.append("BestRatedUnseenRecommendation result: ");
                            output.append(title);
                            return output;
                        }
                    }
                    // daca toate video-urile sunt vazute recomandarea nu poate fi aplicata
                    if (title == null) {
                        output.append("BestRatedUnseenRecommendation cannot be applied!");
                        return output;
                    }
                    output.append("BestRatedUnseenRecommendation result:");
                    output.append(title);

                } else {
                    output.append("BestRatedUnseenRecommendation result: ");
                    output.append(title);
                    return output;
                }
            }
        }
        return output;
    }
}
