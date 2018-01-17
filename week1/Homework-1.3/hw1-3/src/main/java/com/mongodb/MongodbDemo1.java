package com.mongodb;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;

import java.util.Arrays;

public class MongodbDemo1 {

    public static void main(String[] args) {
        MongoClient mongoClient = new MongoClient();
        MongoDatabase mongoDatabase = mongoClient.getDatabase("school");
        MongoCollection<Document> coll = mongoDatabase.getCollection("schoolarea");

        Document sboa = new Document("name", "sboa")
                .append("place", "nagamalai");
        Document mnu = new Document("name", "mnu")
                .append("place", "nagamalai");

        coll.insertMany(Arrays.asList(sboa,mnu));
    }

}
