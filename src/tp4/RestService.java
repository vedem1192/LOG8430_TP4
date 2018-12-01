package tp4;


import com.mongodb.MongoClient;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

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
		
		System.out.println("Data received : " + sb.toString());
		
		return Response.ok().build();
	}
	
	private void postToDB() {
		if(mongo == null ) {
			mongo = new MongoClient();
		}
		
		mongo.getDatabase("LOG8430").getCollection("receipes").insertOne(ItemHelper.toDBDocument(new Item("Banana", 2, 1)));
		String connectPoint = mongo.getConnectPoint();
		System.out.println(connectPoint);
	}
	
	@GET
	@Path("/logout")
	public void logout() {
		if(mongo != null) {
			mongo.close();
		}
	}
	
}
