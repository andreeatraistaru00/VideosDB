package actions;

import fileio.ActionInputData;
import fileio.Input;
import fileio.Writer;

import java.io.IOException;
import java.util.List;
import org.json.simple.JSONArray;

public class Commands {
    private Input input;
    private Writer fileWriter;
    private JSONArray jsonArray;

    public Commands(final Input input, final Writer fileWriter, final JSONArray jsonArray) {
        this.input = input;
        this.fileWriter = fileWriter;
        this.jsonArray = jsonArray;
    }
/**
*/
    public void getResult() throws IOException {
        List<ActionInputData> commandList = input.getCommands();

        for (ActionInputData command : commandList) {
            if (command.getActionType().equals("command")) {
                if (command.getType().equals("favorite")) {
                    Favorite favorite = new Favorite(input, fileWriter, jsonArray, command);
                    favorite.execute(favorite.result());
                } else if (command.getType().equals("view")) {
                    View view = new View(input, fileWriter, jsonArray, command);
                    view.execute(view.result());
                } else if (command.getType().equals("rating")) {
                    Rating rating = new Rating(input, fileWriter, jsonArray, command);
                   rating.execute(rating.result());
                }
            } else if (command.getActionType().equals("recommendation")) {
                if (command.getType().equals("standard")) {
                    RecommendationStandard standard;
                    standard = new RecommendationStandard(input, fileWriter, jsonArray, command);
                    standard.execute(standard.result());
                } else if (command.getType().equals("best_unseen")) {
                    RecommendationBestUnseen bestUnseen;
                    bestUnseen = new RecommendationBestUnseen(input, fileWriter,
                            jsonArray, command);
                    bestUnseen.execute(bestUnseen.result());
                } else if (command.getType().equals("favorite")) {
                    RecommendationFavorite recommandationFavorite;
                    recommandationFavorite = new RecommendationFavorite(input, fileWriter,
                            jsonArray, command);
                    recommandationFavorite.execute(recommandationFavorite.result());
                } else if (command.getType().equals("popular")) {
                    RecommendationPopular recommandationPopular;
                    recommandationPopular = new RecommendationPopular(input, fileWriter,
                            jsonArray, command);
                    recommandationPopular.execute(recommandationPopular.result());
                } else if (command.getType().equals("search")) {
                    RecommendationSearch recommendationSearch;
                    recommendationSearch = new RecommendationSearch(input, fileWriter,
                            jsonArray, command);
                    recommendationSearch.execute(recommendationSearch.result());
                }
            } else {
                if (command.getCriteria().equals("longest")) {
                    QueryLongest queryLongest;
                    queryLongest = new QueryLongest(input, fileWriter, jsonArray, command);
                    queryLongest.execute(queryLongest.result());
                } else if (command.getCriteria().equals("favorite")) {
                    QueryFavorite queryFavorite;
                    queryFavorite = new QueryFavorite(input, fileWriter, jsonArray, command);
                    queryFavorite.execute(queryFavorite.result());
                } else if (command.getCriteria().equals("ratings")) {
                    QueryRating queryRating;
                    queryRating = new QueryRating(input, fileWriter, jsonArray, command);
                    queryRating.execute(queryRating.result());
                } else if (command.getCriteria().equals("most_viewed")) {
                    QueryMostViewed queryMostViewed;
                    queryMostViewed = new QueryMostViewed(input, fileWriter, jsonArray, command);
                    queryMostViewed.execute(queryMostViewed.result());
                } else if (command.getObjectType().equals("users")) {
                    QueryUsers queryUsers;
                    queryUsers = new QueryUsers(input, fileWriter, jsonArray, command);
                    queryUsers.execute(queryUsers.result());
                } else if (command.getObjectType().equals("actors")
                        && command.getCriteria().equals("average")) {
                    QueryAverage queryAverage;
                    queryAverage = new QueryAverage(input, fileWriter, jsonArray, command);
                    queryAverage.execute(queryAverage.result());
                }   else if (command.getObjectType().equals("actors")) {
                        if (command.getCriteria().equals("awards")) {
                        QueryAwards queryAwards;
                        queryAwards = new QueryAwards(input, fileWriter, jsonArray, command);
                        queryAwards.execute(queryAwards.result());
                    } else if (command.getCriteria().equals("filter_description")) {
                        QueryFilterDescription queryFilterDescription;
                        queryFilterDescription = new QueryFilterDescription(input, fileWriter,
                                jsonArray, command);
                        queryFilterDescription.execute(queryFilterDescription.result());
                    }
                }
            }
        }
    }
}
