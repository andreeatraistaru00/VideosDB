package actions;

import fileio.ActionInputData;
import fileio.Input;
import fileio.Writer;
import fileio.MovieInputData;
import fileio.SerialInputData;
import fileio.UserInputData;
import org.json.simple.JSONArray;

import java.util.List;


public class Favorite extends ActionsRead {

    public Favorite(final Input input, final Writer fileWriter,
                    final JSONArray jsonArray, final ActionInputData action) {
        super(input, fileWriter, jsonArray, action);
    }
    /**
     * metoda care returneaza mesajul care trebuie scris in fisierul
     * de output
     */
    public StringBuilder result() {
        StringBuilder output = new StringBuilder();
        String user = getAction().getUsername();
        List<UserInputData> userInput = getInput().getUsers();

    // Se cauta in input user-ul specificat in actiune
    // Dupa ce a fost gasit se verifica istoricul video-urilor

        for (UserInputData usernameInput : userInput) {
            if (user.compareTo(usernameInput.getUsername()) == 0) {
                //filmul nu a fost vazut
                if (usernameInput.getHistory().get(getAction().getTitle()) == null) {
                    output.append("error -> ");
                    output.append(getAction().getTitle());
                    output.append(" is not seen");
                    return output;

                } else {
                    // filmul este in lista de favorite
                        if (usernameInput.getFavoriteMovies().contains(getAction().getTitle())) {
                            output.append("error -> ");
                            output.append(getAction().getTitle());
                            output.append(" is already in favourite list");
                            return output;
                        } else {
                            // video-ul este adaugat in lista de favorite a user-ului

                            usernameInput.getFavoriteMovies().add(getAction().getTitle());

                            // filmului /serialului i se incrementeaza campul care
                            // tine cont de cate ori a fost adaugat la favorite

                            for (MovieInputData film : getInput().getMovies()) {
                                if (getAction().getTitle().equals(film.getTitle())) {
                                    film.setFavorite(film.getFavorite() + 1);
                                }
                            }
                            for (SerialInputData film : getInput().getSerials()) {
                                if (getAction().getTitle().equals(film.getTitle())) {
                                    film.setFavorite(film.getFavorite() + 1);
                                }
                            }
                            output.append("success -> ");
                            output.append(getAction().getTitle());
                            output.append(" was added as favourite");
                            return output;
                        }

                }
            }
        }
        return null;
    }


}
