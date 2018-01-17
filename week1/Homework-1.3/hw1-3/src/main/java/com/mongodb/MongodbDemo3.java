package com.mongodb;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.BSON;
import org.bson.Document;
import org.bson.conversions.Bson;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class MongodbDemo3 {
    public static void main(String[] args) {
        MongoClient mongoClient = new MongoClient();
        MongoDatabase mongoDatabase = mongoClient.getDatabase("college");
        MongoCollection<Document> docAll = mongoDatabase.getCollection("findCollege");

        docAll.drop();

        for (int i = 0; i < 10; i++) {
            docAll.insertOne(new Document()
                    .append("x",new Random().nextInt(2))
                    .append("y", new Random().nextInt(100))
            );
        }

        Bson filter = new Document("x", 0).append("y", new Document("$gt", 10));

        List<Document> doclist = docAll.find(filter).into(new ArrayList<Document>());

        for (Document document : doclist) {
            System.out.println(document.toJson());
        }

        System.out.println(docAll.count(filter));
    }
}
