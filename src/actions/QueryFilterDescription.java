package actions;

import fileio.ActionInputData;
import fileio.ActorInputData;
import fileio.Input;
import fileio.Writer;
import org.json.simple.JSONArray;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


public class QueryFilterDescription extends ActionsRead {

    public QueryFilterDescription(final Input input, final Writer fileWriter,
                                  final JSONArray jsonArray, final ActionInputData action) {
        super(input, fileWriter, jsonArray, action);
    }
    /**
     */
    public StringBuilder result() {
        StringBuilder output = new StringBuilder();
        List<String> words = getAction().getFilters().get(2);
        List<String> actors = new ArrayList<String>();

        // se creaza o lista cu toti actorii care contin in descriere
        // cuvintele din filtru

        for (ActorInputData actor : getInput().getActors()) {
            if (actor.containsWords(actor.getCareerDescription(), words)) {
                actors.add(actor.getName());
            }
        }

        if (actors.size() == 0) {
            output.append("Query result: []");
            return output;
        }

        // se sorteaza lista crescator sau descrescator

        Collections.sort(actors);
        if (getAction().getSortType().equals("desc")) {
            Collections.reverse(actors);
        }
        output.append("Query result: ");
        output.append(actors);
        return output;
    }
}
