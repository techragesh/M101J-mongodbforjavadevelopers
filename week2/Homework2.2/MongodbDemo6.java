package mongoweek2;

import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.Updates;
import org.bson.Document;
import org.bson.conversions.Bson;

import java.util.ArrayList;
import java.util.List;

import static com.mongodb.client.model.Filters.*;
import static com.mongodb.client.model.Projections.excludeId;
import static com.mongodb.client.model.Projections.fields;
import static com.mongodb.client.model.Projections.include;
import static com.mongodb.client.model.Sorts.ascending;
import static com.mongodb.client.model.Updates.*;

public class MongodbDemo6 {
    public static void main(String[] args) {
        try {
            MongoClient mongoClient = new MongoClient();
            MongoDatabase mongoDatabase = mongoClient.getDatabase("course");
            MongoCollection<Document> collection = mongoDatabase.getCollection("updateTest");

            collection.drop();

            for (int i = 0; i < 8; i++) {
                collection.insertOne(new Document()
                        .append("_id", i)
                        .append("x", i)
                        .append("y", true)
                );
            }

            //collection.updateOne(eq("x", 5), new Document("$set", new Document("x", 20)));
            //collection.replaceOne(eq("x",5),new Document("x", 20).append("updated", true));
            //collection.updateOne(eq("x",5), combine(set("x",5), set("updated", true)));
            //collection.updateMany(gte("x",5), inc("x", 1));

            List<Document> documentList = collection.find().into(new ArrayList<Document>());

            for (Document document : documentList) {
                System.out.println(document.toJson());
            }

            System.out.println(documentList.size());

            collection.deleteMany(gt("x", 5));

            List<Document> documentList1 = collection.find().into(new ArrayList<Document>());

            for (Document document : documentList1) {
                System.out.println(document.toJson());
            }

            System.out.println(documentList1.size());

        }catch (Exception e){
            System.out.println(e);
        }

    }
}
