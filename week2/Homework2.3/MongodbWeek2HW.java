package mongoweek2;

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

public class MongodbWeek2HW {
    public static void main(String[] args) {
        MongoClient client = new MongoClient();
        MongoDatabase numbersDB = client.getDatabase("students");
        MongoCollection<Document> grades = numbersDB.getCollection("grades");

        // Gets all the grades.
        MongoCursor<Document> cursor = grades
                .find(Filters.eq("type", "homework"))
                .sort(Sorts.ascending("student_id", "score")).iterator();

        Object previousStudentId = null;
        try {
            // Finds the lowest homework score.
            while (cursor.hasNext()) {
                Document entry = cursor.next();

                // If the student_id is different from the previous one, this
                // means that this is the student's lowest graded homework
                // (they are sorted by score for each student).
                if (!entry.get("student_id").equals(previousStudentId)) {
                    Object id = entry.get("_id");
                    grades.deleteOne(Filters.eq("_id", id));

                }

                // The current document ID becomes the new previous one.
                previousStudentId = entry.get("student_id");
            }

            // Gets the student with the highest average in the class.
            AggregateIterable<Document> results = grades.aggregate(Arrays
                    .asList(Aggregates.group("$student_id",
                            Accumulators.avg("average", "$score")),
                            Aggregates.sort(Sorts.descending("average")),
                            Aggregates.limit(1)));

            // There should be only one result. Prints it.
            System.out.println("Solution : "
                    + results.iterator().next().toJson());

        } finally {
            cursor.close();
        }

        client.close();
    }
}
