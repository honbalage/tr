package org.crf.tr.mongoDB;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;




import org.crf.tr.model.Project;
import org.crf.tr.model.Project.TestFramework;
import org.crf.tr.parser.Parser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.mongodb.BasicDBObjectBuilder;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.mongodb.MongoClient;
import com.mongodb.WriteResult;
import com.mongodb.client.FindIterable;
import com.mongodb.Block;
import com.mongodb.client.FindIterable;
import com.mongodb.client.model.Aggregates;
import com.mongodb.client.model.Aggregates.*;

import org.bson.Document;

import static com.mongodb.client.model.Filters.*;
import static com.mongodb.client.model.Sorts.ascending;
import static java.util.Arrays.asList;

public class MongoDBJDBC {
	static MongoClient mongo = null;
	static DB db = null;
	private static final Logger _log = LoggerFactory.getLogger( MongoDBJDBC.class );
	
	public static void startMongo(){

		
		
		db = connectToDB("localhost", 27017);
		
		db.getCollection("projects").drop();
//		DBCollection col = getCollection(db, "projects");


	}
	
	public static void finishMongo(){
		mongo.close();
	}
	
	public static DBObject createProjectDBObject(String name, String fw, String xml) {
        BasicDBObjectBuilder docBuilder = BasicDBObjectBuilder.start();
        
        //If we don’t provide id, MongoDB will create one for us
        docBuilder.append("name", name);
        docBuilder.append("framework", fw);
        docBuilder.append("coverage_data", xml);
        docBuilder.append("date", new SimpleDateFormat("dd-MM-yyyy HH:mm:ss").format(new Date()));
        
        WriteResult result = insertCollection(getCollection(db, "projects"), docBuilder.get());
//		System.out.println(result.getUpsertedId());
//		System.out.println(result.getN());
//		System.out.println(result.isUpdateOfExisting());
        return docBuilder.get();
    }

	public static String getProjectDBObject(String name, String date) {
		FindIterable<Document> iterable = db.getCollection("projects").find(
		        new Document("date", new Document("$lt", date)).append("name", name));
		String retString = null;
		iterable.forEach(new Block<Document>() {
		    @Override
		    public void apply(final Document document) {
		        retString = document.toString();
		    }
		});
		return retString;
	}
 
	
	public static DB connectToDB(String URL, int Port){
		DB db = null;
		try{
			
			mongo = new MongoClient( URL, Port );
			db = mongo.getDB("tr");
			
			_log.debug("Connected to mongoDB successfully");

		}catch(Exception e){
			_log.error( e.getClass().getName() + ": " + e.getMessage() );
		}
		return db;
	}
	
	
	public static DBCollection getCollection(DB db, String coll){
		DBCollection collection = null;
		try{
			
			 collection = db.getCollection(coll);
	         _log.debug("Collection " + coll + " selected successfully");
	         
		}catch(Exception e){
			_log.error( e.getClass().getName() + ": " + e.getMessage() );
		}
		return collection;
	}
	
	public static WriteResult insertCollection(DBCollection coll, DBObject obj){
		WriteResult wr = null;
		try{
			
			wr = coll.insert(obj);
			
		}catch(Exception e){
			_log.error( e.getClass().getName() + ": " + e.getMessage() );
		}
		return wr;
	}
	
	public void removeCollection(DBCollection coll, DBObject obj){
		try{
			
			coll.remove(obj);
			
		}catch(Exception e){
			_log.error( e.getClass().getName() + ": " + e.getMessage() );
		}
	}



}
