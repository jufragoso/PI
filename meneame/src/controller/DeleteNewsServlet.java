package controller;

import java.io.IOException;
import java.sql.Connection;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dao.CommentDAO;
import dao.JDBCCommentDAOImpl;
import dao.JDBCNewsDAOImpl;
import dao.NewsDAO;
import model.Comment;

/**
 * Servlet implementation class DeleteNewsServlet
 */
@WebServlet("/login/DeleteNewsServlet")
public class DeleteNewsServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public DeleteNewsServlet() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		Connection conn = (Connection) getServletContext().getAttribute("dbConn");
		NewsDAO newsdao = new JDBCNewsDAOImpl();
		CommentDAO commentdao = new JDBCCommentDAOImpl();
		newsdao.setConnection(conn);
		commentdao.setConnection(conn);

		String id = request.getParameter("id");
		
		String error_message = "Error al borrar noticia";

		if (id != null) {
			long oid = Long.parseLong(id);
			newsdao.delete(oid);
			List<Comment> listComment = commentdao.getAllByNews(oid);
			for(int i=0; i< listComment.size(); i++){
				commentdao.delete(listComment.get(i).getId());
			}
			response.sendRedirect("UserNewsServlet");
		}else{
			request.setAttribute("error_message", error_message);
			request.getRequestDispatcher("/WEB-INF/ListNews.jsp").forward(request, response);
		}
		
		

	}

}
