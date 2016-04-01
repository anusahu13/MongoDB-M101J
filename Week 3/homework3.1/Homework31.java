package com.mongodb.m101j.crud;

import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;

import java.util.ArrayList;
import java.util.List;

import static com.mongodb.client.model.Filters.eq;

/**
 * Created by anusa on 3/31/2016.
 */
public class Homework31 {
    public static void main(String[] args) {
        MongoClient client = new MongoClient();
        MongoDatabase database = client.getDatabase("school");
        MongoCollection<Document> collection = database.getCollection("students");

        List<Document> allStudents = collection.find().into(new ArrayList<Document>());
        List<Document> scoresList;
        int studentId = 0;
        Double currentScore, nextScore;
        for (Document student : allStudents) {
            studentId = (Integer) student.get("_id");
            scoresList = (List<Document>) student.get("scores");
            for (int i = 0; i < 4; i++) {

                if (scoresList.get(i).get("type").equals("homework")) {

                    currentScore = (Double) scoresList.get(i).get("score");
                    nextScore = (Double) scoresList.get(i + 1).get("score");

                    System.out.println("studentId:" + studentId + " CS :" + currentScore + ": NS :" + nextScore);

                    if (currentScore.compareTo(nextScore) < 0 || currentScore.compareTo(nextScore) == 0) {
                        System.out.println("Delete currentScore :" + currentScore);
                        scoresList.remove(i);
                        break;
                    } else {
                        System.out.println("Delete nextScore :" + nextScore);
                        scoresList.remove(i + 1);
                        break;
                    }

                }

            }

            collection.updateOne(eq("_id", studentId), new Document("$set", new Document("scores", scoresList)));

            System.out.println("--------------------------------------");
        }
    }

}
