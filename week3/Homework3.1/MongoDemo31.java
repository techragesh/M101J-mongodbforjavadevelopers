package mongoweek3;

import com.mongodb.MongoClient;
import com.mongodb.client.AggregateIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Accumulators;
import com.mongodb.client.model.Aggregates;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.Sorts;
import org.bson.Document;

import java.util.Arrays;
import java.util.List;

import static com.mongodb.client.model.Sorts.*;

public class MongoDemo31 {
    public static void main(String[] args) {
        MongoClient mongoClient = new MongoClient();
        MongoDatabase mongoDatabase = mongoClient.getDatabase("school");
        MongoCollection<Document> mongoCollection = mongoDatabase.getCollection("students");

        MongoCursor<Document> mongoCursor = mongoCollection.find().iterator();

        //Iterate all Student Collections
        while (mongoCursor.hasNext()){
            Document stud = mongoCursor.next();
            //Get the scores collection from the students
            List<Document> scoresList = (List<Document>) stud.get("scores");
            //System.out.println(scoresList);

            Document minHomeWorkScoreObj = null;
            Double minHwScore = Double.MAX_VALUE;

            //Iterate the scores and get the score and type value
            for(Document scoreDoc: scoresList){

                double score = scoreDoc.getDouble("score");
                String type = scoreDoc.getString("type");

                //Find the homework type and minscore
                if(type.equals("homework") && score < minHwScore){
                    minHwScore = score;
                    minHomeWorkScoreObj = scoreDoc;
                }
            }

            //Delete the lowest homework score from the student collection
            if(minHomeWorkScoreObj != null){
                scoresList.remove(minHomeWorkScoreObj);
            }

            //Update the record
            mongoCollection.updateOne(Filters.eq("_id", stud.get("id"))
            , new Document("$set", new Document("scores", scoresList)));

        }

        //

        //Find highest average class student

        //Gets the student with the highest average in the class.

        //

        AggregateIterable<Document> output;
        output = mongoCollection.aggregate(
                Arrays.asList(
                        Aggregates.unwind("$scores"),
                        Aggregates.group("$_id", Accumulators.avg("average", "$scores.score")),
                        Aggregates.sort(descending("average")), Aggregates.limit(1)
                )
        );

        System.out.println("Solution : " + output.iterator().next().toJson());

    }
}
