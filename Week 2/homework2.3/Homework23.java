package com.mongodb.m101j.crud;

import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;
import org.bson.conversions.Bson;

import java.util.ArrayList;
import java.util.List;

import static com.mongodb.client.model.Sorts.ascending;

/**
 * Created by anusa on 3/28/2016.
 */
public class Homework23 {

    public static void main(String[] args) {
        MongoClient client = new MongoClient();
        MongoDatabase database = client.getDatabase("students");
        MongoCollection<Document> collection = database.getCollection("grades");
        List<Document> allStudents = collection.find().into(new ArrayList<Document>());

        Bson sort = sort = ascending("student_id", "score");
        Bson filter = null;

        /* grades.json has 200 students from 0 to 199. Hence iterating for 200 times*/
        for(int j = 0; j < 200 ; j++) {
            filter = new Document("type", "homework").append("student_id", j);

            List<Document> hwScores = collection.find(filter)
                                                  .sort(sort)
                                                  .into(new ArrayList<Document>());

            /* For each student there are 4 rows for type - exam, quiz, homework, homework.
                As we have filtered only for homework type, only two rows will be there in the hwScores list
                Now we have sorted the result in ascending order for : "student_id", "score". Lowest hw score will always be at index 0.
             */

            collection.deleteOne(hwScores.get(0));
           
        }

    }

}
