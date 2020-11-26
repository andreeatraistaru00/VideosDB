package fileio;

import actor.ActorsAwards;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Information about an actor, retrieved from parsing the input test files
 * <p>
 * DO NOT MODIFY
 */
public final class ActorInputData {
    /**
     * actor name
     */
    private String name;
    /**
     * description of the actor's career
     */
    private String careerDescription;
    /**
     * videos starring actor
     */
    private ArrayList<String> filmography;
    /**
     * awards won by the actor
     */
    private Map<ActorsAwards, Integer> awards;

    public ActorInputData(final String name, final String careerDescription,
                          final ArrayList<String> filmography,
                          final Map<ActorsAwards, Integer> awards) {
        this.name = name;
        this.careerDescription = careerDescription;
        this.filmography = filmography;
        this.awards = awards;
    }

    public String getName() {
        return name;
    }

    public void setName(final String name) {
        this.name = name;
    }

    public ArrayList<String> getFilmography() {
        return filmography;
    }

    public void setFilmography(final ArrayList<String> filmography) {
        this.filmography = filmography;
    }

    public Map<ActorsAwards, Integer> getAwards() {
        return awards;
    }

    public String getCareerDescription() {
        return careerDescription;
    }

    public void setCareerDescription(final String careerDescription) {
        this.careerDescription = careerDescription;
    }
/**
  * returneaza media ratingurilor filmelor și a serialelor în care a jucat un actor
 */
    public double average(final List<MovieInputData> movieInputData,
                          final List<SerialInputData> serialInputData) {
        double media = 0;
        int nr = 0;
        for (String s : this.filmography) {
            for (MovieInputData movie : movieInputData) {
                if (movie.getTitle().equals(s)) {
                    if (movie.rating() != 0) {
                        media += movie.rating();
                        nr++;
                    }
                }
            }
            for (SerialInputData serial : serialInputData) {
                if (serial.getTitle().equals(s)) {
                    if (serial.rating() != 0) {
                        media += serial.rating();
                        nr++;
                    }
                }
            }
        }
        if (nr != 0) {
            media = media / nr;
        }
        return media;
    }

    /**
     * Numarul total de premii ale unui actor
      */
    public int totalAwards() {
        int premii = 0;
        for (Map.Entry mapElement : this.awards.entrySet()) {
            premii = premii + (int) mapElement.getValue();
        }
        return premii;
    }

    /**
     * Verifica daca un actor are toate premiile din filtru
     */
    public boolean checkAwards(final List<String> list) {
        boolean flag = true;
        List<String> actorAwards = new ArrayList<String>();

        for (Map.Entry mapElement : this.awards.entrySet()) {
            actorAwards.add(mapElement.getKey().toString());
        }

        for (String award : list) {
            if (!actorAwards.contains(award)) {
                flag = false;
                break;
            }
        }
        return flag;
    }
    /**
     * verifica daca in descriere se afla toate cuvintele din filtru
    */
    public boolean containsWords(final String inputString, final List<String> items) {
        boolean found = true;
        String[] parts = inputString.split(",|\\s|\\.|-");
        List<String> tokens = new ArrayList<String>();
        for (String part : parts) {
            tokens.add(part.toLowerCase());
        }
        for (String item : items) {
            if (!tokens.contains(item.toLowerCase())) {
                found = false;
                break;
            }
        }
        return found;
    }
    @Override
    public String toString() {
        return "ActorInputData{"
                + "name='" + name + '\''
                + ", careerDescription='"
                + careerDescription + '\''
                + ", filmography=" + filmography + '}';
    }
}
