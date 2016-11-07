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

/**
 * Servlet implementation class EditNewsServlet
 */
@WebServlet("/login/EditNewsServlet")
public class EditNewsServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public EditNewsServlet() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		request.setCharacterEncoding("UTF-8");

		
		Connection conn = (Connection) getServletContext().getAttribute("dbConn");
		NewsDAO newsdao = new JDBCNewsDAOImpl();
		newsdao.setConnection(conn);

		String id = request.getParameter("id");

		News news = newsdao.get(Long.parseLong(id));

		String url = news.getUrl();
		String titulo = news.getTitle();
		String texto = news.getText();
		String imagen = news.getImage();

		request.setAttribute("url", url);
		request.setAttribute("titulo", titulo);
		request.setAttribute("texto", texto);
		request.setAttribute("imagen", imagen);
		request.setAttribute("id", id);

		request.getRequestDispatcher("/WEB-INF/EditNews.jsp").forward(request, response);

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

		News news = newsdao.get(Long.parseLong(request.getParameter("id")));

		String titulo = request.getParameter("titulo");
		String texto = request.getParameter("texto");
		String url = request.getParameter("url");
		String imagen = request.getParameter("imagen");

		String error_message = "Error al actualizar la noticia";

		if (news != null) {
			news.setUrl(url);
			news.setText(texto);
			news.setTitle(titulo);
			news.setImage(imagen);
			newsdao.save(news);
			response.sendRedirect("UserNewsServlet");
		} else {
			request.setAttribute("error_message", error_message);
			request.getRequestDispatcher("/WEB-INF/EditNews.jps").forward(request, response);
		}

	}

}
