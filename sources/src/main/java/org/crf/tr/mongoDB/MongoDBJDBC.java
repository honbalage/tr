package org.crf.tr.mongoDB;

import java.io.InputStream;
import java.net.UnknownHostException;
import java.util.List;

import org.crf.tr.commands.Executor;
import org.crf.tr.model.Project.TestFramework;
import org.crf.tr.mongoDB.model.ProjectModel;
import org.crf.tr.ui.factories.builders.ShellBuilder;

import com.mongodb.BasicDBObject;
import com.mongodb.BasicDBObjectBuilder;
import com.mongodb.CommandResult;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBObject;
import com.mongodb.Mongo;
import com.mongodb.MongoClient;
import com.mongodb.WriteResult;

public class MongoDBJDBC {
	static MongoClient mongo = null;
	
	public static void startMongo(){

		
		
		DB db = connectToDB("localhost", 27017);
		
		
		DBCollection col = getCollection(db, "projects");
		
        //create project example
//		ProjectModel project = createProject(123, "json clover", TestFramework.Boost);
//		DBObject doc = createDBObject(project);
//		WriteResult result = insertCollection(col, doc);
//		System.out.println(result.getUpsertedId());
//		System.out.println(result.getN());
//		System.out.println(result.isUpdateOfExisting());

	}
	
	public static void finishMongo(){
		mongo.close();
	}
	
	private static DBObject createDBObject(ProjectModel project) {
        BasicDBObjectBuilder docBuilder = BasicDBObjectBuilder.start();
        
        //If we don’t provide id, MongoDB will create one for us
        //docBuilder.append("_id", project.getId());
        docBuilder.append("name", project.getName());
        docBuilder.append("role", project.getFrameWork().toString());
        return docBuilder.get();
    }
 
    private static ProjectModel createProject(int id, String name, TestFramework tf) {
    	ProjectModel p = new ProjectModel();
        p.setId(id);
        p.setName(name);
        p.setFrameWork(tf);
        return p;
    }
	
	public static DB connectToDB(String URL, int Port){
		DB db = null;
		try{
			
			mongo = new MongoClient( URL, Port );
			db = mongo.getDB("tr");
			
			System.out.println("Connected successfully");
			
		}catch(Exception e){
			System.err.println( e.getClass().getName() + ": " + e.getMessage() );
		}
		return db;
	}
	
	
	public static DBCollection getCollection(DB db, String coll){
		DBCollection collection = null;
		try{
			
			 collection = db.getCollection(coll);
	         System.out.println("Collection " + coll + " selected successfully");
	         
		}catch(Exception e){
			System.err.println( e.getClass().getName() + ": " + e.getMessage() );
		}
		return collection;
	}
	
	public static WriteResult insertCollection(DBCollection coll, DBObject obj){
		WriteResult wr = null;
		try{
			
			wr = coll.insert(obj);
			
		}catch(Exception e){
			System.err.println( e.getClass().getName() + ": " + e.getMessage() );
		}
		return wr;
	}
	
	public void removeCollection(DBCollection coll, DBObject obj){
		try{
			
			coll.remove(obj);
			
		}catch(Exception e){
			System.err.println( e.getClass().getName() + ": " + e.getMessage() );
		}
	}

}
