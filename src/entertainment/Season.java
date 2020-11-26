package entertainment;

import java.util.ArrayList;
import java.util.List;

/**
 * Information about a season of a tv show
 * <p>
 * DO NOT MODIFY
 */
public final class Season {
    /**
     * Number of current season
     */
    private final int currentSeason;
    /**
     * Duration in minutes of a season
     */
    private int duration;

    public List<String> getUsersRated() {
        return usersRated;
    }

    public void setUsersRated(final List<String> usersRated) {
        this.usersRated = usersRated;
    }

    /**
     * List of ratings for each season
     */
    private List<Double> ratings;
    /**
     * Lista userilor care au dat rating sezonului
     */
    private List<String> usersRated;

    public Season(final int currentSeason, final int duration) {
        this.currentSeason = currentSeason;
        this.duration = duration;
        this.ratings = new ArrayList<>();
        this.usersRated = new ArrayList<>();
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(final int duration) {
        this.duration = duration;
    }

    public List<Double> getRatings() {
        return ratings;
    }

    public void setRatings(final List<Double> ratings) {
        this.ratings = ratings;
    }
/**
 * calculeaza ratingul unui serial
 */
    public double rating() {
       double sum  = 0;
       double rating = 0;
       for (Double i : this.ratings) {
           sum += i;
       }
       if (this.ratings.size() != 0) {
           rating = sum / this.ratings.size();
       }
    return rating;
    }
    @Override
    public String toString() {
        return "Episode{"
                + "currentSeason="
                + currentSeason
                + ", duration="
                + duration
                + '}';
    }
}

