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
 * Servlet implementation class EditUserServlet
 */
@WebServlet("/login/EditUserServlet")
public class EditUserServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public EditUserServlet() {
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
		UserDAO userdao = new JDBCUserDAOImpl();
		userdao.setConnection(conn);

		User user = (User) request.getSession().getAttribute("user");

		request.setAttribute("usuario", user);

		String name = user.getName();
		String email = user.getEmail();

		request.setAttribute("name", name);
		request.setAttribute("email", email);

		if (user != null) {
			request.setAttribute("editUser", user);
		}

		request.getRequestDispatcher("/WEB-INF/EditUser.jsp").forward(request, response);

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

		User user = (User) request.getSession().getAttribute("user");

		String name = request.getParameter("name");
		String email = request.getParameter("email");
		String password = request.getParameter("password");
		String v_password = request.getParameter("v_password");

		user.setName(name);
		user.setEmail(email);
		user.setPassword(password);

		List<String> error_message = new ArrayList<String>();

		if ((user.validate(error_message)) && (password.equals(v_password))) {
			userdao.save(user);
			response.sendRedirect("../ListNewsServlet");
		} else {
			if (!password.equals(v_password)) {
				error_message.add("Password no coincide");
			}
			request.setAttribute("error_message", error_message);
			request.getRequestDispatcher("WEB-INF/EditUser.jsp").forward(request, response);
		}
	}

}
