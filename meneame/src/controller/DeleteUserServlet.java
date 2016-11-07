package controller;

import java.io.IOException;
import java.sql.Connection;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

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
 * Servlet implementation class DeleteUserServlet
 */
@WebServlet("/login/DeleteUserServlet")
public class DeleteUserServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public DeleteUserServlet() {
		super();
		// TODO Auto-generated constructor stub
	}

//	/**
//	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
//	 *      response)
//	 */
//	protected void doGet(HttpServletRequest request, HttpServletResponse response)
//			throws ServletException, IOException {
//		Connection conn = (Connection) getServletContext().getAttribute("dbConn");
//		UserDAO userdao = new JDBCUserDAOImpl();
//		NewsDAO newsdao = new JDBCNewsDAOImpl();
//		CommentDAO commentdao = new JDBCCommentDAOImpl();
//		userdao.setConnection(conn);
//		newsdao.setConnection(conn);
//		commentdao.setConnection(conn);
//
//		String password = request.getParameter("password_borrar");
//
//		User user = (User) request.getSession().getAttribute("user");
//		String message = "Password incorrecta";
//		List<News> listNews = newsdao.getAllByOwner(user.getId());
//		List<Comment> listComment = commentdao.getAllByOwner(user.getId());
//		
//		if (user.getPassword().equals(password)) {
//
//			for (int i = 0; i < listNews.size(); i++) {
//				newsdao.delete(listNews.get(i).getId());
//				
//			}
//			for(int i = 0; i< listComment.size(); i++){
//				commentdao.delete(listComment.get(i).getId());
//			}
//
//			userdao.delete(user.getId());
//			HttpSession session = request.getSession(false);
//			if (session != null)
//				session.invalidate();
//			response.sendRedirect("../ListNewsServlet");
//		} else {
//			request.setAttribute("message", message);
//			request.getRequestDispatcher("/WEB-INF/EditUser.jsp").forward(request, response);
//		}
//	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		Connection conn = (Connection) getServletContext().getAttribute("dbConn");
		UserDAO userdao = new JDBCUserDAOImpl();
		NewsDAO newsdao = new JDBCNewsDAOImpl();
		CommentDAO commentdao = new JDBCCommentDAOImpl();
		userdao.setConnection(conn);
		newsdao.setConnection(conn);
		commentdao.setConnection(conn);

		String password = request.getParameter("password_borrar");

		User user = (User) request.getSession().getAttribute("user");
		String message = "Password incorrecta";
		List<News> listNews = newsdao.getAllByOwner(user.getId());
		List<Comment> listComment = commentdao.getAllByOwner(user.getId());
		
		if (user.getPassword().equals(password)) {

			for (int i = 0; i < listNews.size(); i++) {
				newsdao.delete(listNews.get(i).getId());
				
			}
			for(int i = 0; i< listComment.size(); i++){
				commentdao.delete(listComment.get(i).getId());
			}

			userdao.delete(user.getId());
			HttpSession session = request.getSession(false);
			if (session != null)
				session.invalidate();
			response.sendRedirect("../ListNewsServlet");
		} else {
			request.setAttribute("message", message);
			request.getRequestDispatcher("/WEB-INF/EditUser.jsp").forward(request, response);
		}
	}

}
