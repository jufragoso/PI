package controller;

import java.io.IOException;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dao.JDBCUserDAOImpl;
import dao.UserDAO;
import model.User;

/**
 * Servlet implementation class NewUserServlet
 */
@WebServlet("/NewUserServlet")
public class NewUserServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public NewUserServlet() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		request.getRequestDispatcher("/WEB-INF/NewUser.jsp").forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		request.setCharacterEncoding("UTF-8");

		
		Connection conn = (Connection) getServletContext().getAttribute("dbConn");

		UserDAO userdao = new JDBCUserDAOImpl();
		userdao.setConnection(conn);
		String name = request.getParameter("name");
		String password = request.getParameter("password");
		String v_password = request.getParameter("v_password");
		String email = request.getParameter("email");

		User user = new User();
		user.setName(name);
		user.setPassword(password);
		user.setEmail(email);

		List<String> error_message = new ArrayList<String>();

		if ((user.validate(error_message)) && (userdao.get(name) == null) && (password.equals(v_password))) {

			userdao.add(user);
			response.sendRedirect("ListNewsServlet");

		} else {

			if (!password.equals(v_password)) {
				error_message.add("Password no coincide");
			} else if (userdao.get(name)!=null){
				error_message.add("El usuario ya existe");
			}
			request.setAttribute("error_message", error_message);
			request.getRequestDispatcher("/WEB-INF/NewUser.jsp").forward(request, response);

		}

	}

}
