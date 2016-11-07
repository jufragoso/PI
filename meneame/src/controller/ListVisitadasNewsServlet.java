package controller;

import java.io.IOException;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.LinkedList;
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
 * Servlet implementation class ListVisitadasNewsServlet
 */
@WebServlet("/ListVisitadasNewsServlet")
public class ListVisitadasNewsServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public ListVisitadasNewsServlet() {
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

		String categoria = "Más Visitadas";
		request.setAttribute("category", categoria);

		String logueado = "no_login";
		request.setAttribute("category", categoria);

		if (request.getSession().getAttribute("user") != null) {
			logueado = "login";
		}
		request.setAttribute("logueado", logueado);

		List<News> newsList = newsDAO.getNewsMostVisited();

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

		Map<News, User> visitNewsMap = new LinkedHashMap<News, User>();
		News news;
		if (newsList != null) {
			for (int i = 0; i < newsList.size(); i++) {
				if (newsList.size() > i) {
					news = newsList.get(i);
					visitNewsMap.put(news, userDAO.get(news.getOwner()));
				}
			}
		}

		request.setAttribute("newsMap", visitNewsMap);

		RequestDispatcher view = request.getRequestDispatcher("/WEB-INF/ListNews.jsp");
		view.forward(request, response);
	}

}
