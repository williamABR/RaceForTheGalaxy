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
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.swing.JOptionPane;

import Beans.cartas;
import Beans.jugador;

/**
 * Servlet implementation class adminController
 */
@WebServlet("/adminController")
public class adminController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private Connection conexion;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public adminController() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see Servlet#init(ServletConfig)
	 */
	public void init(ServletConfig config) throws ServletException {
		// TODO Auto-generated method stub
		try {
			conexion = DriverManager.getConnection("jdbc:mysql://localhost:3306/Carrito", "root", "William98");
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
		HttpSession session = request.getSession();
		String loggedInA = (String) session.getAttribute("loggedInA"); 
		if (loggedInA.equals("true")) {
			response.sendRedirect("./admin.html");
		}else{
			response.sendRedirect("./");
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		//doGet(request, response);
		response.sendRedirect("./admin.html");
		/*String sqlQuery="INSERT INTO products (idproducts, name, units) VALUES ("+id+",'"+name+"',"+unit+")";
		Statement con;
		try {
			con = conexion.createStatement();
			con.execute(sqlQuery);
			response.getWriter().println("<script type=\"text/javascript\">");
			response.getWriter().println("alert('Se creo con exito el producto');");
			response.getWriter().println("</script>");
			response.sendRedirect("./admin.html");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			
			response.getWriter().println("alert('No se pudo crear el producto');");
			response.sendRedirect("./admin.html");
			e.printStackTrace();
		}*/
	}

}
