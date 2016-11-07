package controller;

import java.io.IOException;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dao.JDBCNewsDAOImpl;
import dao.JDBCUserDAOImpl;
import dao.NewsDAO;
import dao.UserDAO;
import model.News;
import model.User;

/**
 * Servlet implementation class ListDeportesNewsServlet
 */
@WebServlet("/ListDeportesNewsServlet")
public class ListDeportesNewsServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public ListDeportesNewsServlet() {
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
		NewsDAO newsDAO = new JDBCNewsDAOImpl();
		newsDAO.setConnection(conn);
		UserDAO userDAO = new JDBCUserDAOImpl();
		userDAO.setConnection(conn);

		String categoria = "Deportes";
		request.setAttribute("category", categoria);

		String logueado = "no_login";
		request.setAttribute("category", categoria);

		if (request.getSession().getAttribute("user") != null) {
			logueado = "login";

		}
		request.setAttribute("logueado", logueado);

		List<News> newsList = newsDAO.getAllByCategory("Deportes");
		if (newsList != null) {
			Collections.reverse(newsList);
		}
		List<News> newsDestacadas = new ArrayList<News>();
		List<News> newsVisitadas = new ArrayList<News>();
		List<News> newsPopulares = new ArrayList<News>();

		for (int i = 0; i < 3; i++) {
			if (newsDAO.getNewsMostCommented()!=null && newsDAO.getNewsMostCommented().size() > i)
				newsDestacadas.add(newsDAO.getNewsMostCommented().get(i));
			if (newsDAO.getNewsMostVisited() != null && newsDAO.getNewsMostVisited().size() > i)
				newsVisitadas.add(newsDAO.getNewsMostVisited().get(i));
			if (newsDAO.getNewsMostPopular()!=null && newsDAO.getNewsMostPopular().size() > i)
				newsPopulares.add(newsDAO.getNewsMostPopular().get(i));
		}

		request.setAttribute("destacadas", newsDestacadas);
		request.setAttribute("visitadas", newsVisitadas);
		request.setAttribute("populares", newsPopulares);

		Map<News, User> depNewsMap = new LinkedHashMap<News, User>();
		if (newsList != null) {
			Iterator<News> it = newsList.iterator();

			while (it.hasNext()) {
				News news = (News) it.next();
				User user = userDAO.get(news.getOwner());
				depNewsMap.put(news, user);

			}
		}
		request.setAttribute("newsMap", depNewsMap);

		RequestDispatcher view = request.getRequestDispatcher("/WEB-INF/ListNews.jsp");
		view.forward(request, response);
	}

}
