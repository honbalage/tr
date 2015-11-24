package org.crf.tr.mongoDB;

import org.crf.tr.model.Project;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.mongodb.BasicDBObjectBuilder;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBObject;
import com.mongodb.MongoClient;
import com.mongodb.WriteResult;

public class MongoDBJDBC {
	static MongoClient mongo = null;
	private static final Logger _log = LoggerFactory.getLogger( MongoDBJDBC.class );
	
	public static void startMongo(){

		
		
		DB db = connectToDB("localhost", 27017);
		
		
		DBCollection col = getCollection(db, "projects");
		
        //create project example
//		Project project = new Project(123, "json clover", TestFramework.Boost);
//		DBObject doc = createDBObject(project);
//		WriteResult result = insertCollection(col, doc);
//		System.out.println(result.getUpsertedId());
//		System.out.println(result.getN());
//		System.out.println(result.isUpdateOfExisting());

	}
	
	public static void finishMongo(){
		mongo.close();
	}
	
	private static DBObject createDBObject(Project project) {
        BasicDBObjectBuilder docBuilder = BasicDBObjectBuilder.start();
        
        //If we don’t provide id, MongoDB will create one for us
        //docBuilder.append("_id", project.getId());
        docBuilder.append("name", project.name());
        docBuilder.append("role", project.framework().toString());
        return docBuilder.get();
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
