package mongoweek2;

import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;
import org.bson.conversions.Bson;

import java.util.ArrayList;
import java.util.List;

import static com.mongodb.client.model.Filters.and;
import static com.mongodb.client.model.Filters.eq;

public class MongodbDemo8 {

    public static void main(String[] args) {
        try {
            MongoClient mongoClient = new MongoClient();
            MongoDatabase mongoDatabase = mongoClient.getDatabase("video");
            MongoCollection<Document> collection = mongoDatabase.getCollection("movieDetails");

            //collection.drop();

//            System.out.println(collection.find().first().toJson());



            //System.out.println(collection.find(new Document("x", 0).append("y", new Document("$gt", 10).append("$lt",20))));

            //Bson filter = new Document("x",0).append("y", new Document("$gt", 10).append("$lt", 20));
            //System.out.println(collection.find(Filters.and(Filters.eq("x", 0),Filters.gt("y", 10))));
            //Bson filter = Filters.eq("x", 0);
            Bson filter = and(eq("countries.1","Sweden"));

            //Bson projection = new Document("awards", 0);
            //Bson projection =  fields(include("year","rated","title","awards"), excludeId(), eq("awards", 0));


            List<Document> documentList = collection.find(filter).into(new ArrayList<Document>());

            for (Document document : documentList) {
                System.out.println(document.toJson());
            }

            System.out.println(documentList.size());



        }catch (Exception e){
            System.out.println(e);
        }
    }
}
