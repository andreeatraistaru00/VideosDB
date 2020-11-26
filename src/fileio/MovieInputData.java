package fileio;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Information about a movie, retrieved from parsing the input test files
 * <p>
 * DO NOT MODIFY
 */
public final class MovieInputData extends ShowInput {
    /**
     * Duration in minutes of a season
     */
    private final int duration;
    private List<Double> ratings;

    public List<Double> getRatings() {
        return ratings;
    }
    private int favorite = 0;

    public void setRatings(final List<Double> ratings) {
        this.ratings = ratings;
    }

    public int getFavorite() {
        return favorite;
    }

    public void setFavorite(final int favorite) {
        this.favorite = favorite;
    }

    public MovieInputData(final String title, final ArrayList<String> cast,
                          final ArrayList<String> genres, final int year,
                          final int duration) {
        super(title, year, cast, genres);
        this.duration = duration;
        this.ratings = new ArrayList<>();

    }
    /**
     * calculeaza ratingul unui film
    */
    public Double rating() {

        Iterator<Double> it = this.ratings.iterator();
        double res = 0;
        while (it.hasNext()) {
            double num = it.next();
            res += num;
        }
        if (ratings.size() != 0) {
            res = res / this.ratings.size();
        }
        return res;
    }
    public int getDuration() {
        return duration;
    }

    @Override
    public String toString() {
        return "MovieInputData{" + "title= "
                + super.getTitle() + "year= "
                + super.getYear() + "duration= "
                + duration + "cast {"
                + super.getCast() + " }\n"
                + "genres {" + super.getGenres() + " }\n ";
    }
}
