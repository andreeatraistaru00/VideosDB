package fileio;

import entertainment.Season;

import java.util.ArrayList;

/**
 * Information about a tv show, retrieved from parsing the input test files
 * <p>
 * DO NOT MODIFY
 */
public final class SerialInputData extends ShowInput {
    /**
     * Number of seasons
     */
    private final int numberOfSeasons;
    /**
     * Season list
     */
    private final ArrayList<Season> seasons;
    /**
     * numărul de apariții în listele de video-uri favorite ale utilizatorilor
     */
    private int favorite = 0;

    public int getFavorite() {
        return favorite;
    }

    public void setFavorite(final int favorite) {
        this.favorite = favorite;
    }

    public SerialInputData(final String title, final ArrayList<String> cast,
                           final ArrayList<String> genres,
                           final int numberOfSeasons, final ArrayList<Season> seasons,
                           final int year) {
        super(title, year, cast, genres);
        this.numberOfSeasons = numberOfSeasons;
        this.seasons = seasons;
    }
    /**
     * calculeaza ratingul unui seraial
    */
    public double rating() {
        double rating = 0;
        double sum = 0;
        int size = 0;
        for (Season season : this.seasons) {
            if (season.rating() != 0) {
                size++;
                sum += season.rating();
            }
        }
        if (this.numberOfSeasons != 0) {
            rating = sum / this.numberOfSeasons;
        }
        return rating;
    }
    public int getNumberSeason() {
        return numberOfSeasons;
    }

    public ArrayList<Season> getSeasons() {
        return seasons;
    }
    /**
     * calculeaza durata totala a unui serial
    */
    public int durata() {
        int durata = 0;
        for (Season s : this.seasons) {
            durata += s.getDuration();
        }
        return durata;
    }
    @Override
    public String toString() {
        return "SerialInputData{" + " title= "
                + super.getTitle() + " " + " year= "
                + super.getYear() + " cast {"
                + super.getCast() + " }\n" + " genres {"
                + super.getGenres() + " }\n "
                + " numberSeason= " + numberOfSeasons
                + ", seasons=" + seasons + "\n\n" + '}';
    }
}
