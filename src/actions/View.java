package actions;


import fileio.ActionInputData;
import fileio.Input;
import fileio.UserInputData;
import fileio.Writer;
import org.json.simple.JSONArray;

import java.util.List;

public class View extends ActionsRead {
    public View(final Input input, final Writer fileWriter,
                final JSONArray jsonArray, final ActionInputData action) {
        super(input, fileWriter, jsonArray, action);
    }
    /**
     * functia construieste mesajul pentru comanda view
     */
    public StringBuilder result() {
        StringBuilder output = new StringBuilder();
        String user = getAction().getUsername();
        List<UserInputData> userInput = getInput().getUsers();

        // se cauta in input user-ul specificat in actiune

        for (UserInputData usernameInput : userInput) {

            if (user.compareTo(usernameInput.getUsername()) == 0) {
                // daca video-ul nu a fost vazut se adauga in istoric
                if (usernameInput.getHistory().get(getAction().getTitle()) == null) {
                    usernameInput.getHistory().put(getAction().getTitle(), 1);
                    output.append("success -> ");
                    output.append(getAction().getTitle());
                    output.append(" was viewed with total views of 1");

                    return output;
                    // daca a fost vazut se incrementeaza nr de vizualizari
                } else {
                    int aux = usernameInput.getHistory().get(getAction().getTitle()) + 1;
                    usernameInput.getHistory().replace(getAction().getTitle(), aux);

                    output.append("success -> ");
                    output.append(getAction().getTitle());
                    output.append(" was viewed with total views of ");
                    output.append(aux);
                    return output;
                }
            }
        }
        return null;
    }

}
