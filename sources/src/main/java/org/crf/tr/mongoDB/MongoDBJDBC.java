package org.crf.tr.mongoDB;

import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBObject;
import com.mongodb.MongoClient;
import com.mongodb.WriteResult;

public class MongoDBJDBC {
	
	public DB connectToDB(String URL, int Port){
		DB db = null;
		try{
			
			MongoClient mongoClient = new MongoClient( URL, Port );
			
			db = mongoClient.getDB("test");
			System.out.println("Connected successfully");
			
		}catch(Exception e){
			System.err.println( e.getClass().getName() + ": " + e.getMessage() );
		}
		return db;
	}
	
	public boolean authenticateDB(DB db, String user, String pw){
		boolean auth = false;
		try{
			char[] password = pw.toCharArray();
			auth = db.authenticate(user, password);
	        System.out.println("Authentication: "+auth); 
			
		}catch(Exception e){
			System.err.println( e.getClass().getName() + ": " + e.getMessage() );
		}
		return auth;
	}
	
	public DBCollection getCollection(DB db, String coll){
		DBCollection collection = null;
		try{
			
			 collection = db.getCollection(coll);
	         System.out.println("Collection mycol selected successfully");
	         
		}catch(Exception e){
			System.err.println( e.getClass().getName() + ": " + e.getMessage() );
		}
		return collection;
	}
	
	public void insertCollection(DBCollection coll, DBObject obj){
		try{
			
			coll.insert(obj);
			
		}catch(Exception e){
			System.err.println( e.getClass().getName() + ": " + e.getMessage() );
		}
	}
	
	public void removeCollection(DBCollection coll, DBObject obj){
		try{
			
			coll.remove(obj);
			
		}catch(Exception e){
			System.err.println( e.getClass().getName() + ": " + e.getMessage() );
		}
	}

}
