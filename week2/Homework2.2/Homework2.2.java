package mongoweek2;

import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Aggregates;
import com.mongodb.client.model.BsonField;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.Sorts;
import org.bson.Document;
import org.bson.conversions.Bson;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static com.mongodb.client.model.Aggregates.*;
import static com.mongodb.client.model.Filters.*;
import static com.mongodb.client.model.Sorts.*;

public class MongodbDemo1 {
    public static void main(String[] args) {
        MongoClient mongoClient = new MongoClient();
        MongoDatabase mongoDatabase = mongoClient.getDatabase("students");
        MongoCollection<Document> collection = mongoDatabase.getCollection("grades");
        System.out.println(collection.count());

        Bson filter = gte("score",65);
        Bson sort = ascending("score");

        List<Document> documentList = collection.find(filter)
                .sort(sort)
                .limit(1)
                .into(new ArrayList<Document>());

        for (Document document : documentList) {
            System.out.println(document.toJson());
        }

//        collection.grades.aggregate([{'$group':{'_id':'$student_id', 'average':{$avg:'$score'}}},
//        {'$sort':{'average':-1}}, {'$limit':1}])

    }
}
