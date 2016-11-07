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
 * Servlet implementation class LikesServlet
 */
@WebServlet("/LikesServlet")
public class LikesServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public LikesServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		Connection conn = (Connection) getServletContext().getAttribute("dbConn");
		NewsDAO newsdao = new JDBCNewsDAOImpl();
		newsdao.setConnection(conn);
		
		String id = request.getParameter("idNoticia");
		
		long oid = 0;
		
		if(id !=null){
			oid = Long.parseLong(id);
		}
		
		
		News news = newsdao.get(oid);
		
		news.setLikes(news.getLikes()+1);
		
		newsdao.save(news);
		
		response.sendRedirect(request.getHeader("referer"));
		
		
	}

}
