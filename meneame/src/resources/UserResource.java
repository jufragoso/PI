package resources;

import java.sql.Connection;
import java.util.List;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

import dao.JDBCUserDAOImpl;
import dao.UserDAO;
import model.User;
import resources.exceptions.CustomBadRequestException;

@Path("/users")
public class UserResource {

	@Context
	ServletContext sc;
	@Context
	UriInfo uriInfo;
	
	@GET
	@Path("/getUser")
	@Produces(MediaType.APPLICATION_JSON)
	public User getUserSessionJSON(@Context HttpServletRequest request) {
		
		HttpSession session = request.getSession();
		User user = (User) session.getAttribute("user");

		return user;
	}

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public List<User> getUsersJSON() {
		Connection conn = (Connection) sc.getAttribute("dbConn");
		UserDAO userdao = new JDBCUserDAOImpl();
		userdao.setConnection(conn);

		List<User> listUsers = userdao.getAll();
		for(int i=0; i< listUsers.size();i++){
			listUsers.get(i).setPassword("");
		}

		return listUsers;
	}

	@GET
	@Path("/{userid: [0-9]+}")
	@Produces(MediaType.APPLICATION_JSON)
	public User getUserById(@PathParam("userid") long userid) {

		Connection conn = (Connection) sc.getAttribute("dbConn");
		UserDAO userdao = new JDBCUserDAOImpl();
		userdao.setConnection(conn);

		User user = userdao.get(userid);
		user.setPassword("");

		return user;

	}

	@GET
	@Path("/{name: [a-zA-Z]+}")
	@Produces(MediaType.APPLICATION_JSON)
	public User getUserByNameJSON(@PathParam("name") String name) {

		Connection conn = (Connection) sc.getAttribute("dbConn");
		UserDAO userdao = new JDBCUserDAOImpl();
		userdao.setConnection(conn);

		User user = userdao.get(name);
		user.setPassword("");

		return user;

	}

	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	public Response post(User newUser) throws Exception {

		Response response = null;

		Connection conn = (Connection) sc.getAttribute("dbConn");
		UserDAO userdao = new JDBCUserDAOImpl();
		userdao.setConnection(conn);

		long id = userdao.add(newUser);

		response = Response.created(uriInfo.getAbsolutePathBuilder().path(Long.toString(id)).build())
				.contentLocation(uriInfo.getAbsolutePathBuilder().path(Long.toString(id)).build()).build();
		return response;

	}

	@PUT
	@Path("/{userid: [0-9]+}")
	@Consumes(MediaType.APPLICATION_JSON)
	public Response put(User userUpdate, @PathParam("userid") long userid,@Context HttpServletRequest request) throws Exception {

		Response response = null;

		Connection conn = (Connection) sc.getAttribute("dbConn");
		UserDAO userdao = new JDBCUserDAOImpl();
		userdao.setConnection(conn);

		User user = userdao.get(userUpdate.getId());

		if (user != null) {
			if (user.getId() != userid) {
				throw new CustomBadRequestException("Error in userid");
			} else {
				userdao.save(userUpdate);
				HttpSession session = request.getSession();
				session.setAttribute("user",userUpdate);
				
			}
		} else {
			throw new CustomBadRequestException("Errors in parameters");
		}

		return response;

	}

	@DELETE
	@Path("/{userid: [0-9]+}")
	public Response deleteNews(@PathParam("userid") long userid) {

		Connection conn = (Connection) sc.getAttribute("dbConn");
		UserDAO userdao = new JDBCUserDAOImpl();
		userdao.setConnection(conn);

		userdao.delete(userid);

		return Response.noContent().build(); // 204 no content
	}

}
