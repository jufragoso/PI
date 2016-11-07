package resources;

import java.sql.Connection;
import java.util.Calendar;
import java.util.GregorianCalendar;
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

import dao.JDBCNewsDAOImpl;
import dao.NewsDAO;
import model.News;
import model.User;
import resources.exceptions.CustomBadRequestException;
import resources.exceptions.CustomNotFoundException;

@Path("/news")
public class NewsResource {

	@Context
	ServletContext sc;
	@Context
	UriInfo uriInfo;

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public List<News> getNewsJSON() {
		Connection conn = (Connection) sc.getAttribute("dbConn");
		NewsDAO newsdao = new JDBCNewsDAOImpl();
		newsdao.setConnection(conn);

		List<News> listNews = newsdao.getAll();

		return listNews;
	}

	@GET
	@Path("/populares/")
	@Produces(MediaType.APPLICATION_JSON)
	public List<News> getNewsPopularesJSON() {
		Connection conn = (Connection) sc.getAttribute("dbConn");
		NewsDAO newsdao = new JDBCNewsDAOImpl();
		newsdao.setConnection(conn);

		List<News> listNews = newsdao.getNewsMostPopular();

		return listNews;
	}

	@GET
	@Path("/visitadas/")
	@Produces(MediaType.APPLICATION_JSON)
	public List<News> getNewsVisitadasJSON() {
		Connection conn = (Connection) sc.getAttribute("dbConn");
		NewsDAO newsdao = new JDBCNewsDAOImpl();
		newsdao.setConnection(conn);

		List<News> listNews = newsdao.getNewsMostVisited();

		return listNews;
	}

	@GET
	@Path("/destacadas/")
	@Produces(MediaType.APPLICATION_JSON)
	public List<News> getNewsDestacadasJSON() {
		Connection conn = (Connection) sc.getAttribute("dbConn");
		NewsDAO newsdao = new JDBCNewsDAOImpl();
		newsdao.setConnection(conn);

		List<News> listNews = newsdao.getNewsMostCommented();

		return listNews;
	}

	@GET
	@Path("/byuser/{userid: [0-9]+}")
	@Produces(MediaType.APPLICATION_JSON)
	public List<News> getNewsByUserJSON(@PathParam("userid") long userid) {

		Connection conn = (Connection) sc.getAttribute("dbConn");
		NewsDAO newsdao = new JDBCNewsDAOImpl();
		newsdao.setConnection(conn);

		List<News> listNews = newsdao.getAllByOwner(userid);

		return listNews;

	}

	@GET
	@Path("/{category: [a-zA-Z]+}")
	@Produces(MediaType.APPLICATION_JSON)
	public List<News> getNewsByCategoryJSON(@PathParam("category") String category) {

		Connection conn = (Connection) sc.getAttribute("dbConn");
		NewsDAO newsdao = new JDBCNewsDAOImpl();
		newsdao.setConnection(conn);

		List<News> listNews = newsdao.getAllByCategory(category);

		return listNews;

	}

	@GET
	@Path("/{newsid: [0-9]+}")
	@Produces(MediaType.APPLICATION_JSON)
	public News getNewsJSON(@PathParam("newsid") long newsId,@Context HttpServletRequest request) {

		Connection conn = (Connection) sc.getAttribute("dbConn");
		NewsDAO newsdao = new JDBCNewsDAOImpl();
		newsdao.setConnection(conn);

		News news = newsdao.get(newsId);
		
		HttpSession session = request.getSession();
		User user = (User) session.getAttribute("user");
		
		
		if (news == null || news.getOwner()!=user.getId()) {
			throw new CustomNotFoundException("News (" + newsId + ") is not found");

		}

		return news;

	}
	
	@GET
	@Path("/detalle/{newsid: [0-9]+}")
	@Produces(MediaType.APPLICATION_JSON)
	public News getNewsDetalleJSON(@PathParam("newsid") long newsId) {

		Connection conn = (Connection) sc.getAttribute("dbConn");
		NewsDAO newsdao = new JDBCNewsDAOImpl();
		newsdao.setConnection(conn);

		News news = newsdao.get(newsId);
		
		if (news == null) {
			throw new CustomNotFoundException("News (" + newsId + ") is not found");

		}

		return news;

	}
	

	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	public Response post(News newNews, @Context HttpServletRequest request) throws Exception {

		Response response = null;

		Connection conn = (Connection) sc.getAttribute("dbConn");
		NewsDAO newsdao = new JDBCNewsDAOImpl();
		newsdao.setConnection(conn);
		
		Calendar calendar = new GregorianCalendar();
		
		HttpSession session = request.getSession();
		User user = (User) session.getAttribute("user");
		newNews.setOwner(user.getId());

		long id = newsdao.add(newNews);

		response = Response.created(uriInfo.getAbsolutePathBuilder().path(Long.toString(id)).build())
				.contentLocation(uriInfo.getAbsolutePathBuilder().path(Long.toString(id)).build()).build();
		return response;

	}

	@PUT
	@Path("/{newsid: [0-9]+}")
	@Consumes(MediaType.APPLICATION_JSON)
	public Response put(News newsUpdate, @PathParam("newsid") long newsid, @Context HttpServletRequest request) throws Exception {

		Response response = null;

		Connection conn = (Connection) sc.getAttribute("dbConn");
		NewsDAO newsdao = new JDBCNewsDAOImpl();
		newsdao.setConnection(conn);

		News news = newsdao.get(newsUpdate.getId());
		
		HttpSession session = request.getSession();
		User user = (User) session.getAttribute("user");

		if (news != null) {
			if (news.getId() != newsid && news.getOwner() != user.getId()) {
				throw new CustomBadRequestException("Error in id");
			} else {
				newsdao.save(newsUpdate);
			}
		} else {
			throw new CustomBadRequestException("Errors in parameters");
		}

		return response;

	}
	
	@PUT
	@Path("/likes/{newsid: [0-9]+}")
	@Consumes(MediaType.APPLICATION_JSON)
	public Response votarNoticia(News newsUpdate, @PathParam("newsid") long newsid) throws Exception {

		Response response = null;

		Connection conn = (Connection) sc.getAttribute("dbConn");
		NewsDAO newsdao = new JDBCNewsDAOImpl();
		newsdao.setConnection(conn);

		News news = newsdao.get(newsUpdate.getId());
		

		if (news != null) {
			if (news.getId() != newsid ) {
				throw new CustomBadRequestException("Error in id");
			} else {
				news.setLikes(news.getLikes()+1);
				newsdao.save(newsUpdate);
			}
		} else {
			throw new CustomBadRequestException("Errors in parameters");
		}

		return response;

	}

	@DELETE
	@Path("/{newsid: [0-9]+}")
	public Response deleteNews(@PathParam("newsid") long newsid) {

		Connection conn = (Connection) sc.getAttribute("dbConn");
		NewsDAO newsdao = new JDBCNewsDAOImpl();
		newsdao.setConnection(conn);

		newsdao.delete(newsid);

		return Response.noContent().build(); // 204 no content
	}

}
