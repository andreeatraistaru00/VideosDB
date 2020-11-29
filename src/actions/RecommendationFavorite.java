package actions;
import fileio.ActionInputData;
import fileio.Input;
import fileio.Writer;
import fileio.UserInputData;
import fileio.MovieInputData;
import fileio.SerialInputData;
import org.json.simple.JSONArray;

import java.util.List;

public class RecommendationFavorite extends ActionsRead {
    public RecommendationFavorite(final Input input, final Writer fileWriter,
                                  final JSONArray jsonArray, final ActionInputData action) {
        super(input, fileWriter, jsonArray, action);
    }
    /**
     */
    public StringBuilder result() {
        StringBuilder output = new StringBuilder();
        String user = getAction().getUsername();
        List<UserInputData> userInput = getInput().getUsers();

        // am parcurs lista de useri din input si am incrementat
        // pentru fiecare video campul care retine de cate ori apare in lista
        // de favorite a unui user
        for (UserInputData userInputData : userInput) {
            // se parcurge lista de favorite a user-ului caruia i se face
            // recomandarea
            if (!userInputData.getUsername().equals(user)) {
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
        }
        for (UserInputData usernameInput : userInput) {
                if (usernameInput.getUsername().equals(user)) {
                    // recomandarea se aplica doar daca user-ul are
                    // abonament premium
                    if (usernameInput.getSubscriptionType().equals("PREMIUM")) {
                    int maxFav = 0;
                    String title = null;
                    // se parcurg video-urile si se cauta cel mai des intalnit
                        // in lista de favorite a userilor
                    for (MovieInputData movie : getInput().getMovies()) {
                        if (!usernameInput.getHistory().containsKey(movie.getTitle())) {
                            if (movie.getFavorite() > maxFav) {
                                maxFav = movie.getFavorite();
                                title = movie.getTitle();
                            }
                        }
                    }
                        for (SerialInputData serial : getInput().getSerials()) {
                            if (!usernameInput.getHistory().containsKey(serial.getTitle())) {
                                if (serial.getFavorite() > maxFav) {
                                    maxFav = serial.getFavorite();
                                    title = serial.getTitle();
                                }
                            }
                        }
                    if (maxFav == 0) {
                        output.append("FavoriteRecommendation cannot be applied!");
                        return  output;
                    }
                    output.append("FavoriteRecommendation result: ");
                    output.append(title);
                    return output;
                } else {
                        output.append("FavoriteRecommendation cannot be applied!");
                    }
            }
        }
        output.append("FavoriteRecommendation cannot be applied!");
        return output;
    }
}
