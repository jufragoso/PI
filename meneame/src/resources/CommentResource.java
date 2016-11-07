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

import dao.CommentDAO;
import dao.JDBCCommentDAOImpl;
import dao.JDBCUserDAOImpl;
import dao.UserDAO;
import model.Comment;
import model.User;
import resources.exceptions.CustomBadRequestException;
import resources.exceptions.CustomNotFoundException;

@Path("/comments")
public class CommentResource {

	@Context
	ServletContext sc;
	@Context
	UriInfo uriInfo;
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public List<Comment> getCommentsJSON() {
		Connection conn = (Connection) sc.getAttribute("dbConn");
		CommentDAO commentdao = new JDBCCommentDAOImpl();
		commentdao.setConnection(conn);

		List<Comment> listComments = commentdao.getAll();

		return listComments;
	}
	
	@GET
	@Path("/owner/{ownerid: [0-9]+}")
	@Produces(MediaType.APPLICATION_JSON)
	public List<Comment> getAllCommentsByOwner(@PathParam("ownerid") long ownerid) {

		Connection conn = (Connection) sc.getAttribute("dbConn");
		CommentDAO commentdao = new JDBCCommentDAOImpl();
		commentdao.setConnection(conn);

		List<Comment> listComments = commentdao.getAllByOwner(ownerid);
		
		return listComments;

	}
	
	@GET
	@Path("/news/{newsid: [0-9]+}")
	@Produces(MediaType.APPLICATION_JSON)
	public List<Comment> getAllCommentsByNews(@PathParam("newsid") long newsid) {

		Connection conn = (Connection) sc.getAttribute("dbConn");
		CommentDAO commentdao = new JDBCCommentDAOImpl();
		commentdao.setConnection(conn);

		List<Comment> listComments = commentdao.getAllByNews(newsid);
		
		return listComments;

	}
	
	@GET
	@Path("{commentid: [0-9]+}")
	@Produces(MediaType.APPLICATION_JSON)
	public Comment getCommentsById(@PathParam("commentid") long commentid,@Context HttpServletRequest request) {

		Connection conn = (Connection) sc.getAttribute("dbConn");
		CommentDAO commentdao = new JDBCCommentDAOImpl();
		commentdao.setConnection(conn);
		
		HttpSession session = request.getSession();
		User user = (User) session.getAttribute("user");

		Comment comment = commentdao.get(commentid);
		
		if(comment.getOwner()!=user.getId()){
			throw new CustomNotFoundException("Comment (" + commentid + ") is not found");
		}
		
		return comment;

	}
	
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	public Response post(Comment newComment,  @Context HttpServletRequest request) throws Exception {

		Response response = null;

		Connection conn = (Connection) sc.getAttribute("dbConn");
		CommentDAO commentdao = new JDBCCommentDAOImpl();
		commentdao.setConnection(conn);
		
		HttpSession session = request.getSession();
		User user = (User) session.getAttribute("user");
		newComment.setOwner(user.getId());

		long id = commentdao.add(newComment);

		response = Response.created(uriInfo.getAbsolutePathBuilder().path(Long.toString(id)).build())
				.contentLocation(uriInfo.getAbsolutePathBuilder().path(Long.toString(id)).build()).build();
		return response;

	}
	
	@PUT
	@Path("/{commentid: [0-9]+}")
	@Consumes(MediaType.APPLICATION_JSON)
	public Response put(Comment commentUpdate, @PathParam("commentid") long commentid) throws Exception {

		Response response = null;

		Connection conn = (Connection) sc.getAttribute("dbConn");
		CommentDAO commentdao = new JDBCCommentDAOImpl();
		commentdao.setConnection(conn);

		Comment comment = commentdao.get(commentUpdate.getId());

		if (comment != null) {
			if (comment.getId() != commentid) {
				throw new CustomBadRequestException("Error in commentid");
			} else {
				commentdao.save(commentUpdate);
			}
		} else {
			throw new CustomBadRequestException("Errors in parameters");
		}

		return response;

	}
	
	@DELETE
	@Path("/{commentid: [0-9]+}")
	public Response deleteNews(@PathParam("commentid") long commentid) {

		Connection conn = (Connection) sc.getAttribute("dbConn");
		CommentDAO commentdao = new JDBCCommentDAOImpl();
		commentdao.setConnection(conn);

		commentdao.delete(commentid);

		return Response.noContent().build(); // 204 no content
	}
	
}
