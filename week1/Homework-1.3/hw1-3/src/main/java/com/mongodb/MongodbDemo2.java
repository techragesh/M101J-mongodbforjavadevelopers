package com.mongodb;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.MongoIterable;
import org.bson.Document;

import javax.print.Doc;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ArrayBlockingQueue;

public class MongodbDemo2 {
    public static void main(String[] args) {
        MongoClient mongoClient = new MongoClient();
        MongoDatabase mongoDatabase = mongoClient.getDatabase("course");
        MongoCollection<Document> courseCol =  mongoDatabase.getCollection("findCourse");

        courseCol.drop();

        for (int i = 0; i < 5; i++) {
            courseCol.insertOne(new Document("x", i));
        }

        //Find One
        System.out.println("=====Find One======");
        Document doc1 = courseCol.find().first();
        System.out.println(doc1.toJson());

        //Find All with into
        System.out.println("=====Find All with Into======");
        List<Document> cursor = courseCol.find().into(new ArrayList<Document>());
        for (Document document : cursor) System.out.println(document.toJson());

        //Find all with Iterator
        System.out.println("=====Find all with iterator");
        MongoCursor<Document> iterator = courseCol.find().iterator();
        while (iterator.hasNext()){
            Document document = iterator.next();
            System.out.println(document.toJson());
        }
        System.out.println(courseCol.count());
    }
}
