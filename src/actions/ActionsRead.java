package actions;

import fileio.ActionInputData;
import fileio.Input;
import fileio.Writer;
import org.json.simple.JSONArray;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Collections;
import java.util.TreeMap;
import java.io.IOException;

public abstract class ActionsRead {

    /**
     */
    private final Input input;
    /**
     */
    private final Writer fileWriter;
    /**
     */
    private final JSONArray jsonArray;
    /**
     */
    private final ActionInputData action;

    public ActionsRead(final Input input, final Writer fileWriter,
                       final JSONArray jsonArray, final ActionInputData action) {
        this.input = input;
        this.fileWriter = fileWriter;
        this.jsonArray = jsonArray;
        this.action = action;
    }
    /**
     */
    public Input getInput() {
        return input;
    }
    /**
     */
    public Writer getFileWriter() {
        return fileWriter;
    }
    /**
     */
    public JSONArray getJsonArray() {
        return jsonArray;
    }
    /**
     */
    public ActionInputData getAction() {
        return action;
    }

    /**
     *
     */
    public abstract StringBuilder result();
    /**
     * scrie mesajul de output in fisier
     */
    public void execute(final StringBuilder message) throws IOException {
        getJsonArray().add(getFileWriter().writeFile(Integer.toString(getAction().getActionId()),
                "message", message.toString()));
    }
    /**
     * sorteaza crescator un hashmap cu valori de tip double dupa cheie
     * cu ajutorul unui treemap
     */
    public static TreeMap<String, Double>  sortbykeyDouble(final HashMap<String, Double> map) {
        ArrayList<String> sortedKeys = new ArrayList<String>(map.keySet());
        TreeMap<String, Double> sorted = new TreeMap<String, Double>();
        Collections.sort(sortedKeys);

        for (String x : sortedKeys) {
            sorted.put(x, map.get(x));
        }
        return sorted;
    }
    /**
     * sorteaza crescator un hashmap cu valori de tip intreg dupa cheie
     * cu ajutorul unui treemap
     */
    public static TreeMap<String, Integer>  sortbykey(final HashMap<String, Integer> map) {
        ArrayList<String> sortedKeys = new ArrayList<String>(map.keySet());
        TreeMap<String, Integer> sorted = new TreeMap<String, Integer>();
        Collections.sort(sortedKeys);

        for (String x : sortedKeys) {
            sorted.put(x, map.get(x));
        }
        return sorted;
    }
    /**
     * sorteaza un treemap dupa valoare (de tip double)
     */
    public List<String> resultDouble(final TreeMap<String, Double> tree) {
        List<Double> values = new ArrayList<Double>();
        List<String> result = new ArrayList<String>();

        for (Map.Entry<String, Double> entry : tree.entrySet()) {
            values.add(entry.getValue());
        }
        Collections.sort(values);
        for (Double v : values) {
            for (Map.Entry<String, Double> entry : tree.entrySet()) {
                if (v == entry.getValue()) {
                    result.add(entry.getKey());
                }
            }
        }
        return result;
    }
/**
 * sorteaza un treemap dupa valoare (de tip intreg)
 */
    public List<String> result(final TreeMap<String, Integer> tree) {
        List<Integer> values = new ArrayList<Integer>();
        List<String> result = new ArrayList<String>();

        for (Map.Entry<String, Integer> entry : tree.entrySet()) {
            values.add(entry.getValue());
        }
        Collections.sort(values);
        for (Integer v : values) {
            for (Map.Entry<String, Integer> entry : tree.entrySet()) {
                if (v == entry.getValue()) {
                    result.add(entry.getKey());
                    tree.replace(entry.getKey(), -1);
                }
            }
        }
        return result;
    }
    /**
     * metoda este folosita de query-uri;
     * ea returneaza primele N elemente ale listei ordonate crescator sau descrescator
     */
    public StringBuilder queryVideos(final List<String> result) {
        StringBuilder output = new StringBuilder();
        if (result.size() < getAction().getNumber()) {
            output.append("Query result: ");
            if (getAction().getSortType().equals("asc")) {
                output.append(result);
                return output;
            } else {
                Collections.reverse(result);
                output.append(result);
                return output;
            }
        } else {
            output.append("Query result: ");

            if (getAction().getSortType().equals("asc")) {
                for (int i = result.size() - 1; i >= getAction().getNumber(); i--) {
                    result.remove(i);
                }
                output.append(result);
                return output;
            } else {
                Collections.reverse(result);
                for (int i = result.size() - 1; i >= getAction().getNumber(); i--) {
                    result.remove(i);
                }
                output.append(result);
                return output;
            }
        }
    }

}
