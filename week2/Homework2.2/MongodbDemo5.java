package mongoweek2;

import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Sorts;
import org.bson.Document;
import org.bson.conversions.Bson;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static com.mongodb.client.model.Filters.*;
import static com.mongodb.client.model.Projections.excludeId;
import static com.mongodb.client.model.Projections.fields;
import static com.mongodb.client.model.Projections.include;
import static com.mongodb.client.model.Sorts.*;

public class MongodbDemo5 {
    public static void main(String[] args) {

        try {
            MongoClient mongoClient = new MongoClient();
            MongoDatabase mongoDatabase = mongoClient.getDatabase("course");
            MongoCollection<Document> collection = mongoDatabase.getCollection("findSortTest");

            collection.drop();

            for (int i = 0; i < 10; i++) {
                for (int j = 0; j < 10; j++) {
                    collection.insertOne(new Document().append("i", i).append("j", j));
                }
            }

            Bson projection = fields(include("i","j"),excludeId());
            //Bson sort = new Document("i", 1).append("j",-1);
            Bson sort = ascending("i", "j");

            List<Document> documentList = collection.find()
                    .projection(projection)
                    .sort(sort)
                    .skip(10)
                    .limit(20)
                    .into(new ArrayList<Document>());

            for (Document document : documentList) {
                System.out.println(document.toJson());
            }

            System.out.println(documentList.size());

        }catch (Exception e){
            System.out.println(e);
        }

    }
}
