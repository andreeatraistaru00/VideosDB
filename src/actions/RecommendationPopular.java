package actions;


import fileio.ActionInputData;
import fileio.Input;
import fileio.Writer;
import fileio.UserInputData;
import fileio.MovieInputData;
import fileio.SerialInputData;
import org.json.simple.JSONArray;
import java.util.Map.Entry;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

public class RecommendationPopular extends ActionsRead {
    public RecommendationPopular(final Input input, final Writer fileWriter,
                                 final JSONArray jsonArray, final ActionInputData action) {
        super(input, fileWriter, jsonArray, action);
    }
    /**
     * functia construieste mesajul pentru fisierul de output
     */
    public StringBuilder result() {
        StringBuilder output = new StringBuilder();
        String username = getAction().getUsername();
        List<UserInputData> userInput = getInput().getUsers();

        for (UserInputData user: userInput) {
            // se cauta video-urile vazute de useri si se adauga
            // vizualizarile la fiecare film si serial

                for (MovieInputData movie : getInput().getMovies()) {
                    if (user.getHistory().containsKey(movie.getTitle())) {
                        movie.setViews(movie.getViews() + user.getHistory().get(movie.getTitle()));
                    }

                }
                for (SerialInputData film : getInput().getSerials()) {
                    if (user.getHistory().containsKey(film.getTitle())) {
                        film.setViews(film.getViews() + user.getHistory().get(film.getTitle()));
                    }

                }

        }

        //se calculeaza cel mai popular gen cu un HashMap
        HashMap<String, Integer> viewsGenres = new HashMap<String, Integer>();

        //se parcurge pentru fiecare video lista de genuri din care acesta face parte
        // si se adauga in hashmap la fiecare gen vizualizarile video-ului
        for (MovieInputData movie : getInput().getMovies()) {
            for (String gen : movie.getGenres()) {
                if (viewsGenres.containsKey(gen)) {
                    viewsGenres.replace(gen, movie.getViews() + viewsGenres.get(gen));
                } else {
                    viewsGenres.put(gen, movie.getViews());
                }
            }
        }
        for (SerialInputData serial : getInput().getSerials()) {
            for (String gen : serial.getGenres()) {
                if (viewsGenres.containsKey(gen)) {
                    viewsGenres.replace(gen, serial.getViews() + viewsGenres.get(gen));
                } else {
                    viewsGenres.put(gen, serial.getViews());

                }
            }
        }
        // se creaza o lista (listGen) in care se afla genurile
        // sortate descrescator dupa vizualizari
        ArrayList<Integer> list = new ArrayList<Integer>();
        viewsGenres.forEach((k, v) -> list.add(v));
        Collections.sort(list, Collections.reverseOrder());
        ArrayList<String> listGen = new ArrayList<String>();

        for (int i = 0; i  < list.size(); i++) {
            for (Entry<String, Integer> entry : viewsGenres.entrySet()) {
                if (entry.getValue() == list.get(i)) {
                    listGen.add(entry.getKey());
                    viewsGenres.replace(entry.getKey(), 0);
                    break;
                }
            }
        }

        for (UserInputData userInputData : userInput) {
            if (userInputData.getUsername().equals(username)) {
                // verificam daca user-ul e premium
                if (userInputData.getSubscriptionType().equals("PREMIUM")) {
                    int aux = 0;
                    // aux este un interator prin lista de genuri
                    // daca toate video-urile din cele mai popular gen sunt vizionate
                    // se trece la urmatorul
                    while (aux <= listGen.size()) {
                        // se cauta videoul care nu se afla in istoricul user-ului
                        // si care face parte din genul cel mai popular
                        for (MovieInputData movie : getInput().getMovies()) {
                            if (!userInputData.getHistory().containsKey(movie.getTitle())
                                    && movie.getGenres().contains(listGen.get(aux))) {
                                output.append("PopularRecommendation result: ");
                                output.append(movie.getTitle());
                                return output;
                            }
                        }
                        for (SerialInputData serial : getInput().getSerials()) {
                            if (!userInputData.getHistory().containsKey(serial.getTitle())
                                    && serial.getGenres().contains(listGen.get(aux))) {
                                output.append("PopularRecommendation result: ");
                                output.append(serial.getTitle());
                                return output;
                            }
                        }
                        aux++;
                    }
                }
            }
        }
           output.append("PopularRecommendation cannot be applied!");

        return output;
    }
}
