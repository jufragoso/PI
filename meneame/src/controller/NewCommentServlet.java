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
import dao.JDBCNewsDAOImpl;
import dao.JDBCUserDAOImpl;
import dao.NewsDAO;
import dao.UserDAO;
import model.Comment;
import model.User;

/**
 * Servlet implementation class NewCommentServlet
 */
@WebServlet("/login/NewCommentServlet")
public class NewCommentServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public NewCommentServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	
		String id  = request.getParameter("idNoticia");
		long oid = Long.parseLong(id);
		
		request.setAttribute("idNoticia", oid);
		
		request.getRequestDispatcher("/WEB-INF/NewComment.jsp").forward(request, response);
		
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		request.setCharacterEncoding("UTF-8");
		
		Connection conn = (Connection) getServletContext().getAttribute("dbConn");
		NewsDAO newsdao = new JDBCNewsDAOImpl();
		CommentDAO commentdao = new JDBCCommentDAOImpl();
		UserDAO userdao = new JDBCUserDAOImpl();
		newsdao.setConnection(conn);
		commentdao.setConnection(conn);
		userdao.setConnection(conn);
		
		Comment comment = new Comment();
		
		
		String id = request.getParameter("idNoticia");
		
		long oid  = Long.parseLong(id);
		
		
		User user = (User)request.getSession().getAttribute("user");
		
		comment.setOwner(user.getId());
		
		comment.setNews(oid);
		
		comment.setText(request.getParameter("texto"));

		commentdao.add(comment);
		
		response.sendRedirect("../CommentNewsServlet?idNoticia="+oid);
		
	}

}
