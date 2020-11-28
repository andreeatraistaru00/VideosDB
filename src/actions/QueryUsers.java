package actions;

import fileio.ActionInputData;
import fileio.Input;
import fileio.Writer;
import fileio.UserInputData;
import org.json.simple.JSONArray;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.TreeMap;

public class QueryUsers extends ActionsRead {
    /**
     */
    public QueryUsers(final Input input, final Writer fileWriter,
                      final JSONArray jsonArray, final ActionInputData action) {
        super(input, fileWriter, jsonArray, action);
    }
    /**
     */
    public StringBuilder result() {
        StringBuilder output = new StringBuilder();
        HashMap<String, Integer> map = new HashMap<>();

        // se creaza un map cu useri si nr de rating dat de fiecare
        for (UserInputData userInputData : getInput().getUsers()) {
            if (userInputData.getRatings() != 0) {
                map.put(userInputData.getUsername(), userInputData.getRatings());
            }
        }

        if (map.size() == 0) {
            output.append("Query result: []");
            return output;
        }
        // se sorteaza map-ul

        TreeMap<String, Integer> tree = new TreeMap<String, Integer>();
        List<String> result = new ArrayList<String>();
        tree = sortbykey(map);
        result = result(tree);
        output = queryVideos(result);
        return output;
    }
}
