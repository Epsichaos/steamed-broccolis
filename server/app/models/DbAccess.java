package models;

import com.google.inject.Singleton;

import com.mongodb.DB;
import com.mongodb.MongoClient;
import com.mongodb.client.MongoDatabase;
import org.jongo.Jongo;

/**
 * Created by constant on 29/11/2016.
 */
@Singleton
public class DbAccess {

    private DB database;

    public DbAccess() {
        this.database = new MongoClient().getDB("todos-db");
    }

    public DB getDB() {
        return this.database;
    }

}
