package actions;

import fileio.ActionInputData;
import fileio.Input;
import fileio.Writer;
import fileio.ActorInputData;
import org.json.simple.JSONArray;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.TreeMap;

public class QueryAwards extends ActionsRead {

    public QueryAwards(final Input input, final Writer fileWriter,
                       final JSONArray jsonArray, final ActionInputData action) {
        super(input, fileWriter, jsonArray, action);
    }
    public static final int INDEX = 3;
    /**
     * functia returneaza mesajul de output
     */
    public StringBuilder result() {
        StringBuilder output = new StringBuilder();
        HashMap<String, Integer> map = new HashMap<String, Integer>();
        List<String> awards = getAction().getFilters().get(INDEX);

        // se creaza un map cu numele actorilor si numarul total de premii
        // obtinut de fiecare
        for (ActorInputData actor : getInput().getActors()) {
           if (actor.checkAwards(awards)) {
               map.put(actor.getName(), actor.totalAwards());
           }
        }
        if (map.size() == 0) {
            output.append("Query result: []");
            return output;
        }
        // se sorteaza map-ul si se afiseaza primii N actori

        TreeMap<String, Integer> tree = new TreeMap<String, Integer>();
        List<String> result = new ArrayList<String>();
        tree = sortbykey(map);
        result = result(tree);
        output = queryVideos(result);

        return output;
    }
}
