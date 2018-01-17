package mongoweek2;

import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;

import com.mongodb.client.model.Filters;
import com.mongodb.client.model.Projections;
import org.bson.Document;
import org.bson.conversions.Bson;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import static com.mongodb.client.model.Filters.*;
import static com.mongodb.client.model.Projections.*;

public class MongodbDemo4 {

    public static void main(String[] args) {
        try {
            MongoClient mongoClient = new MongoClient();
            MongoDatabase mongoDatabase = mongoClient.getDatabase("course");
            MongoCollection<Document> collection = mongoDatabase.getCollection("findFilterTest");

            collection.drop();

            for (int i = 0; i < 10; i++) {
                collection.insertOne(new Document()
                        .append("x", new Random().nextInt(2))
                        .append("y", new Random().nextInt(100))
                );
            }

            //System.out.println(collection.find(new Document("x", 0).append("y", new Document("$gt", 10).append("$lt",20))));

            //Bson filter = new Document("x",0).append("y", new Document("$gt", 10).append("$lt", 20));
            //System.out.println(collection.find(Filters.and(Filters.eq("x", 0),Filters.gt("y", 10))));
            //Bson filter = Filters.eq("x", 0);
            Bson filter = and(eq("x",0), gt("y",10), lt("y", 60));

            //Bson projection = new Document("x", 0);
            Bson projection =  fields(include("x","y"), excludeId());


            List<Document> documentList = collection.find(filter).projection(projection).into(new ArrayList<Document>());

            for (Document document : documentList) {
                System.out.println(document.toJson());
            }



        }catch (Exception e){
            System.out.println(e);
        }
    }
}
