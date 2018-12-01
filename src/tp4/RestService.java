package tp4;


import com.mongodb.MongoClient;

import javax.ws.rs.ApplicationPath;
import javax.ws.rs.GET;
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
	
		
	// http://localhost:8080/TP4/resources/REST/hello?name=Vero
	@GET
	@Path("/hello")
	public Response getHello(@QueryParam("name") String name) {
		return Response.ok("Hello " + name).build();
	}
	
	@GET
	@Path("/article")
	@Produces(MediaType.APPLICATION_JSON)
	public Item getItem() {
		postToDB();
		return new Item("Banane", 4);
	}
	
	private void postToDB() {
		if(mongo == null ) {
			mongo = new MongoClient();
		}
		
		
		mongo.getDatabase("LOG8430").getCollection("receipes").insertOne(ItemHelper.toDBDocument(new Item("Banana", 2)));
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
