package Servlet;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import Beans.cartas;

/**
 * Servlet implementation class indexController
 */
@WebServlet("/indexController")
public class indexController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private Connection conexion;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public indexController() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see Servlet#init(ServletConfig)
	 */
	public void init(ServletConfig config) throws ServletException {
		// TODO Auto-generated method stub
		try {
			conexion = DriverManager.getConnection("jdbc:mysql://localhost:3306/Juego", "root", "william98");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		if(request.getParameter("cerrar").compareTo("U")==0) {
			HttpSession session = request.getSession();
			session.setAttribute("loggedInU", new String("false"));
		}
		if(request.getParameter("cerrar").compareTo("A")==0) {
			HttpSession session = request.getSession();
			session.setAttribute("loggedInA", new String("false"));
		}
		response.sendRedirect("./index.html");
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		HttpSession session = request.getSession(true);
		Cookie c1 = new Cookie("numDescarte", "2"); 
		response.addCookie(c1);
		String password=request.getParameter("psw");
		String user=request.getParameter("uname");
		String sqlQuery="SELECT * FROM usuarios WHERE usuario = '"+user+"'";
		
		Statement con;
		try {
			con = conexion.createStatement();
			ResultSet query = con.executeQuery(sqlQuery);
			if (query.next()) {
				if(query.getString("password").compareTo(password) == 0) {
					session.setAttribute("user", new String(user));
					ArrayList<cartas> cartas = new ArrayList<cartas>();
					session.setAttribute("cartas", cartas);
					if(query.getString("type").compareTo("A") == 0) {
						session.setAttribute("loggedInA", new String("true"));
						session.setAttribute("loggedInU", new String("false"));
						response.sendRedirect("./admin.html");
					}else{
						 
						session.setAttribute("loggedInU", new String("true"));
						session.setAttribute("loggedInA", new String("false"));
						response.sendRedirect("./welcome.html");
					}
				}else {
					response.sendRedirect("./index.html");
				}
			}else {
				response.sendRedirect("./index.html");
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
