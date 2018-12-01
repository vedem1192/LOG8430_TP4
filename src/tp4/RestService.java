package tp4;


import com.google.gson.Gson;
import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.UUID;

import javax.ws.rs.ApplicationPath;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Application;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.bson.Document;

@Path("/REST")
@ApplicationPath("/resources")
public class RestService extends Application {
	
	MongoClient mongo = null;
	
	// http://localhost:8080/TP4/resources/REST/receipe
	
	@POST
	@Path("/receipt")
	@Consumes(MediaType.APPLICATION_JSON)
	public Response addReceipt(InputStream data) {
		StringBuilder sb = new StringBuilder();
		try {
			BufferedReader in = new BufferedReader(new InputStreamReader(data));
			String line = null;
			while ((line = in.readLine()) != null) {
				sb.append(line);
			}
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		
		postToDB(new Gson().fromJson(sb.toString(), Item[].class));
		return Response.ok().build();
	}
	
	private void postToDB(Item[] receipt) {
		if(mongo == null ) {
			mongo = new MongoClient();
		}
		
		String id = UUID.randomUUID().toString();		
		mongo.getDatabase("LOG8430").getCollection("receipts").insertMany(ItemHelper.toDBDocument(id,receipt));
	}
	
	@GET
	@Path("/logout")
	public void logout() {
		if(mongo != null) {
			mongo.close();
		}
	}
	
}
