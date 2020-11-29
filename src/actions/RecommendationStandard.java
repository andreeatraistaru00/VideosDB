package actions;


import fileio.Input;
import fileio.Writer;
import fileio.ActionInputData;
import fileio.MovieInputData;
import fileio.UserInputData;
import fileio.SerialInputData;
import org.json.simple.JSONArray;

import java.util.List;

public class RecommendationStandard extends ActionsRead {
    public RecommendationStandard(final Input input, final Writer fileWriter,
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

        // se cauta user-ul specificat in actiune
        for (UserInputData usernameInput : userInput) {
            if (usernameInput.getUsername().equals(user)) {
                // se ia pe rand fiecare film din input si se returneaza
                // primul film care nu se afla in istoricul user-ului
                for (MovieInputData movie : getInput().getMovies()) {
                    if (!usernameInput.getHistory().containsKey(movie.getTitle())) {
                        output.append("StandardRecommendation result: ");
                        output.append(movie.getTitle());
                        return output;
                    }
                }
                // daca toate filmele sunt vazute se cauta in lista de seriale
                for (SerialInputData serial : getInput().getSerials()) {
                    if (!usernameInput.getHistory().containsKey(serial.getTitle())) {
                        output.append("StandardRecommendation result: ");
                        output.append(serial.getTitle());
                        return output;
                    }
                }
            }
        }
        output.append("StandardRecommendation cannot be applied!");
        return output;
    }
}
