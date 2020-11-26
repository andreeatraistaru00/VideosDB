package actions;

import fileio.ActionInputData;
import fileio.Input;
import fileio.Writer;
import fileio.UserInputData;
import fileio.MovieInputData;
import fileio.SerialInputData;
import org.json.simple.JSONArray;

import java.util.List;


public class Rating extends ActionsRead {
    public Rating(final Input input, final Writer fileWriter,
                  final JSONArray jsonArray, final ActionInputData action) {
        super(input, fileWriter, jsonArray, action);
    }
    /**
     * functia construieste mesajul pentru comanda rating
     */
    public StringBuilder result() {
        StringBuilder output = new StringBuilder();
        String username = getAction().getUsername();
        List<UserInputData> userInput = getInput().getUsers();
        // se cauta user-ul din actiune
        for (UserInputData user : userInput) {
            if (username.compareTo(user.getUsername()) == 0) {
                // se verifica daca filmul a fost vazut
                if (user.getHistory().get(getAction().getTitle()) != null) {
                    // se cauta video-ul din actiune in lista de filme din input
                    for (MovieInputData movie : getInput().getMovies()) {
                        if (movie.getTitle().equals(getAction().getTitle())) {
                            //se verifica daca a mai primit rating
                            if (!user.getRatedMovies().contains(movie.getTitle())) {
                                //se adauga nota in lista de rating-uri a filmului
                                movie.getRatings().add(getAction().getGrade());
                                //incrementeaza nr de ratinguri date de user
                                user.setRatings(user.getRatings() + 1);
                                //adauga filmul in lista user-ului cu video-uri la care
                                // a dat nota
                                user.getRatedMovies().add(movie.getTitle());
                                output.append("success -> ");
                                output.append(getAction().getTitle());
                                output.append(" was rated with ");
                                output.append(getAction().getGrade());
                                output.append(" by ");
                                output.append(getAction().getUsername());
                            } else {
                                output.append("error -> ");
                                output.append(getAction().getTitle());
                                output.append(" has been already rated");
                            }
                            return output;
                        }
                    }
                    // se cauta video-ul in lista de seriale
                    for (SerialInputData serial : getInput().getSerials()) {
                        if (serial.getTitle().equals(getAction().getTitle())) {
                            int seasonNo = getAction().getSeasonNumber() - 1;
                            double grade = getAction().getGrade();
                            String name = user.getUsername();
                            // se verifica daca sezonul a mai primit rating
                            if (!serial.getSeasons().get(seasonNo).getUsersRated().contains(name)) {
                                user.setRatings(user.getRatings() + 1);
                                serial.getSeasons().get(seasonNo).getRatings().add(grade);
                                serial.getSeasons().get(seasonNo).getUsersRated().add(name);
                                output.append("success -> ");
                                output.append(getAction().getTitle());
                                output.append(" was rated with ");
                                output.append(getAction().getGrade());
                                output.append(" by ");
                                output.append(getAction().getUsername());
                            } else {
                                output.append("error -> ");
                                output.append(getAction().getTitle());
                                output.append(" has been already rated");
                            }
                            return output;
                        }
                    }
                    // daca video-ul nu a fost vazut nu i se da rating
                } else {
                    output.append("error -> ");
                    output.append(getAction().getTitle());
                    output.append(" is not seen");
                }
            }
        }
        return output;
    }
}
