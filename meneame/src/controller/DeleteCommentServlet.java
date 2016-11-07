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

/**
 * Servlet implementation class DeleteCommentServlet
 */
@WebServlet("/login/DeleteCommentServlet")
public class DeleteCommentServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public DeleteCommentServlet() {
        super();
        // TODO Auto-generated constructor stub
    }


	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		Connection conn = (Connection) getServletContext().getAttribute("dbConn");
		CommentDAO commentdao = new JDBCCommentDAOImpl();
		commentdao.setConnection(conn);
		
		String id = request.getParameter("idComment");

		long oid = Long.parseLong(id);
		request.setAttribute("idComment", oid);
		
		long idNoticia = commentdao.get(oid).getNews();

//		if (id != null) {
//			oid = Long.parseLong(id);
//			request.setAttribute("idComment", oid);
//			
//		}
		
		commentdao.delete(oid);
		response.sendRedirect("../CommentNewsServlet?idNoticia="+idNoticia);
	}

}
