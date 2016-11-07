package controller;

import java.io.IOException;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

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
 * Servlet implementation class UserNewsServlet
 */
@WebServlet(urlPatterns= "/login/UserNewsServlet")
public class UserNewsServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static final Logger logger = Logger.getLogger(HttpServlet.class.getName());

       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public UserNewsServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		
		logger.info("Atendiendo GET");

		Connection conn = (Connection) getServletContext().getAttribute("dbConn");
		NewsDAO newsDAO = new JDBCNewsDAOImpl();
		newsDAO.setConnection(conn);
		UserDAO userDAO = new JDBCUserDAOImpl();
		userDAO.setConnection(conn);

		String categoria = "Mis Noticias";
		String logueado = "login";
		request.setAttribute("category", categoria);
		
		User user = (User) request.getSession().getAttribute("user");
		
		request.setAttribute("logueado", logueado);

		List<News> newsList = newsDAO.getAllByOwner(user.getId());

		List<News> newsDestacadas = new ArrayList<News>();
		List<News> newsVisitadas = new ArrayList<News>();
		List<News> newsPopulares = new ArrayList<News>();
	
		for(int i=0; i< 3; i++){
			if (newsDAO.getNewsMostCommented() != null && newsDAO.getNewsMostCommented().size() > i)
				newsDestacadas.add(newsDAO.getNewsMostCommented().get(i));
			if (newsDAO.getNewsMostVisited() != null && newsDAO.getNewsMostVisited().size() > i)
				newsVisitadas.add(newsDAO.getNewsMostVisited().get(i));
			if (newsDAO.getNewsMostPopular() != null && newsDAO.getNewsMostPopular().size() > i)
				newsPopulares.add(newsDAO.getNewsMostPopular().get(i));
		}
		
		request.setAttribute("destacadas", newsDestacadas);
		request.setAttribute("visitadas", newsVisitadas);
		request.setAttribute("populares", newsPopulares);

		Map<News, User> newsMap = new LinkedHashMap<News, User>();

		Iterator<News> it = newsList.iterator();

		while (it.hasNext()) {
			News news = (News) it.next();
			//User user = userDAO.get(news.getOwner());
			newsMap.put(news, user);

		}
		

		request.setAttribute("newsMap", newsMap);

		RequestDispatcher view = request.getRequestDispatcher("/WEB-INF/ListNews.jsp");
		view.forward(request, response);
	
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
