package controller;

import java.io.IOException;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dao.CommentDAO;
import dao.JDBCCommentDAOImpl;
import dao.JDBCNewsDAOImpl;
import dao.JDBCUserDAOImpl;
import dao.NewsDAO;
import dao.UserDAO;
import model.Comment;
import model.News;
import model.User;

/**
 * Servlet implementation class CommentNewsServlet
 */
@WebServlet("/CommentNewsServlet")
public class CommentNewsServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public CommentNewsServlet() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		Connection conn = (Connection) getServletContext().getAttribute("dbConn");
		NewsDAO newsdao = new JDBCNewsDAOImpl();
		CommentDAO commentdao = new JDBCCommentDAOImpl();
		UserDAO userdao = new JDBCUserDAOImpl();
		newsdao.setConnection(conn);
		commentdao.setConnection(conn);
		userdao.setConnection(conn);

		String id = request.getParameter("idNoticia");

		String logueado = "no_login";

		if (request.getSession().getAttribute("user") != null) {
			logueado = "login";

		}
		request.setAttribute("logueado", logueado);

		long oid = Long.parseLong(id);
//		if (id != null) {
//			oid = Long.parseLong(id);
//		}
		request.setAttribute("idNoticia", oid);

//		System.out.println("ID Noticia " + oid);

		// if (id != null) {
		// oid = Long.parseLong(id);
		// request.setAttribute("idNoticia", oid);
		// }

		News news = newsdao.get(oid);

//		news.setHits(news.getHits() + 1);
//
//		newsdao.save(news);

		List<News> newsDestacadas = new ArrayList<News>();
		List<News> newsVisitadas = new ArrayList<News>();
		List<News> newsPopulares = new ArrayList<News>();

		for (int i = 0; i < 3; i++) {
			if (newsdao.getNewsMostCommented().size() > i)
				newsDestacadas.add(newsdao.getNewsMostCommented().get(i));
			if (newsdao.getNewsMostVisited().size() > i)
				newsVisitadas.add(newsdao.getNewsMostVisited().get(i));
			if (newsdao.getNewsMostPopular().size() > i)
				newsPopulares.add(newsdao.getNewsMostPopular().get(i));
		}

		request.setAttribute("destacadas", newsDestacadas);
		request.setAttribute("visitadas", newsVisitadas);
		request.setAttribute("populares", newsPopulares);

		Map<News, User> newsmap = new LinkedHashMap<News, User>();

		newsmap.put(news, userdao.get(news.getOwner()));

		request.setAttribute("newsmap", newsmap);

		List<Comment> listcomentarios = commentdao.getAllByNews(news.getId());

		Collections.reverse(listcomentarios);

		Map<Comment, User> commentsmap = new LinkedHashMap<Comment, User>();

		Iterator<Comment> it = listcomentarios.iterator();

		while (it.hasNext()) {
			Comment comment = (Comment) it.next();
			User user = userdao.get(comment.getOwner());
			commentsmap.put(comment, user);

		}

		request.setAttribute("commentsmap", commentsmap);

		request.getRequestDispatcher("/WEB-INF/CommentsNews.jsp").forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
