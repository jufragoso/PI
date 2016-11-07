package controller;

import java.io.IOException;
import java.sql.Connection;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dao.CommentDAO;
import dao.JDBCCommentDAOImpl;
import model.Comment;

/**
 * Servlet implementation class EditCommentServlet
 */
@WebServlet("/login/EditCommentServlet")
public class EditCommentServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public EditCommentServlet() {
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
		CommentDAO commentdao = new JDBCCommentDAOImpl();
		commentdao.setConnection(conn);

		String id = request.getParameter("idComment");

		long oid = Long.parseLong(id);
		request.setAttribute("idComment", oid);
//		if (id != null) {
//			oid = Long.parseLong(id);
//			request.setAttribute("idComment", oid);
//			
//		}
		Comment comment = commentdao.get(oid);

		request.setAttribute("commentTexto", comment.getText());

		request.getRequestDispatcher("/WEB-INF/EditComment.jsp").forward(request, response);

	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		request.setCharacterEncoding("UTF-8");

		Connection conn = (Connection) getServletContext().getAttribute("dbConn");
		CommentDAO commentdao = new JDBCCommentDAOImpl();
		commentdao.setConnection(conn);

		String id = request.getParameter("idComment");

		long oid = Long.parseLong(id);

		Comment comment = commentdao.get(oid);

		if (comment != null) {
			String texto = request.getParameter("texto");
			comment.setText(texto);

			commentdao.save(comment);
			response.sendRedirect("../CommentNewsServlet?idNoticia="+comment.getNews());

		} else {
			String message = "Error al actualizar noticia";
			request.setAttribute("message", message);

			request.getRequestDispatcher("/WEB-INF/EditComment.jsp").forward(request, response);
		}

	}

}
