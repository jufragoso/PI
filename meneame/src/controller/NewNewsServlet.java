package controller;

import java.io.IOException;
import java.sql.Connection;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dao.JDBCNewsDAOImpl;
import dao.NewsDAO;
import model.News;
import model.User;

/**
 * Servlet implementation class NewNewsServlet
 */
@WebServlet("/login/NewNewsServlet")
public class NewNewsServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public NewNewsServlet() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		request.getRequestDispatcher("/WEB-INF/NewNews.jsp").forward(request, response);

	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		request.setCharacterEncoding("UTF-8");

		Connection conn = (Connection) getServletContext().getAttribute("dbConn");
		NewsDAO newsdao = new JDBCNewsDAOImpl();
		newsdao.setConnection(conn);
		
		User user = (User) request.getSession().getAttribute("user");
		
		News news = new News();
		
		long owner = user.getId();
		
		System.out.println("ID usuario -->" + owner);
		
		String titulo = request.getParameter("titulo");
		String url = request.getParameter("url");
		String texto = request.getParameter("texto");
		String imagen = request.getParameter("imagen");
		String categoria = request.getParameter("categoria");
		
		news.setOwner(owner);
		news.setTitle(titulo);
		news.setUrl(url);
		news.setText(texto);
		news.setImage(imagen);
		news.setCategory(categoria);
		
		
		newsdao.add(news);
		
		response.sendRedirect("UserNewsServlet");
		
		
	}

}
