package tp4;

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

		
	// http://localhost:8080/TP4/resources/REST/hello?name=Vero
	@GET
	@Path("/hello")
	public Response getHello(@QueryParam("name") String name) {
		return Response.ok("Hello " + name).build();
	}
	
	@GET
	@Path("/article")
	@Produces(MediaType.APPLICATION_JSON)
	public Article getArticle() {
		return new Article("Banane", 4);
	}
	
	
}
