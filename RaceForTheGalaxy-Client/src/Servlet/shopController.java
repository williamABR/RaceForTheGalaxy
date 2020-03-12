package Servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Enumeration;

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
 * Servlet implementation class shopController
 */
@WebServlet("/shopController")
public class shopController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private Connection conexion;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public shopController() {
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
		HttpSession session = request.getSession();
		String loggedInU = (String) session.getAttribute("loggedInU"); 
		if (loggedInU.equals("true")) {
			doPost(request,response);
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
		String sqlQuery="SELECT * FROM usuarios";
		Statement con;
		try {
			con = conexion.createStatement();
			ResultSet query = con.executeQuery(sqlQuery);
		
			PrintWriter out = response.getWriter();
		    out.println("<HTML>");
		    out.println("<HEAD>");
		    out.println("<TITLE>Shop</TITLE>");
		    out.println("<link rel=\"stylesheet\" href=\"https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css\" integrity=\"sha384-ggOyR0iXCbMQv3Xipma34MD+dH/1fQ784/j6cY/iJTQUOhcWr7x9JvoRxT2MZw1T\" crossorigin=\"anonymous\">");
		    out.println("</HEAD>");
		    out.println("<BODY>");
		    HttpSession session = request.getSession();
		    /*ArrayList<beanProducto> bean = (ArrayList<beanProducto>) session.getAttribute("cartas");
		    out.println("<B>Bean Name:</B> " + bean.size() + "<BR>");
		    for(beanProducto aux: bean) {
		    	out.println("<B>Bean Name:</B> " + aux.getName() + "<BR>");
		    	out.println("<B>Bean Name:</B> " + aux.getUnit() + "<BR>");
		    }
		    Cookie[] cookies = request.getCookies(); 
		    int length = cookies.length;
		    for (int i=0; i<length; i++) {
		    Cookie cookie = cookies[i];
		    out.println("<B>Cookie Name:</B> " + cookie.getName() + "<BR>"); 
		    out.println("<B>Cookie Value:</B> " + cookie.getValue() + "<BR>");
		    }*/
		    out.println("<B>Online Shop</B>");
		    out.println("<BR>");
		    out.println("<nav class='navbar navbar-light bg-light'>");
		      out.println("<form class='form-inline' action='carController' method='POST'>");
		      out.println("<button class='btn btn-outline-success' type='submit' name='carrito'>Carrito de Compras</button>");
		      out.println("</form>");
		      out.println("<form class='form-inline' action='indexController' method='GET'>");
		      out.println("<button class='btn btn-outline-success' type='submit' name='cerrar' value='U'>Cerrar Sesion</button>");
		      out.println("</form>");
		      out.println("<form class='form-inline' action='game1' method='POST'>");
		      out.println("<button class='btn btn-outline-success' type='submit' name='cerrar' value='U'>Nueva Partida</button>");
		      out.println("</form>");
		      out.println("</nav>");
		    /*while (query.next()) {
		    	beanProducto producto = new beanProducto();
		    	producto.setId(query.getString("idproducts"));
		      	producto.setName(query.getString("name"));
		      	producto.setUnit(Integer.parseInt(query.getString("price")));
		      	bean.add(producto);
		    }
		    for(beanProducto pro : bean) { 
		      out.println("<form action='carController' method='POST'>");
		      out.println("<div class='card' style='width: 18rem;'>");
		      out.println("<div class='card-body'>");
		      out.println("<label name='prod'>Producto: "+pro.getName()+"</label> <br>");
		      out.println("Ref: <input type=text name='id' value='"+pro.getId()+"' readonly>");
		      out.println("Precio: $<input type=text name='price' value='"+pro.getUnit()+"' readonly>");
		      out.println("<button type='submit' class='btn btn-primary'>Agregar</button>");
		 	  out.println("</div>");
		      out.println("</div>");
		      out.println("</form>");
		    }*/
		      out.println("</BODY>");
		      out.println("</HTML>");
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	}

}
