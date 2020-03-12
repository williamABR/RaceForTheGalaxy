package Servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Random;
import java.util.Vector;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import Beans.Juegos;
import Beans.cartas;

/**
 * Servlet implementation class game1
 */
@WebServlet("/game1")
public class game1 extends HttpServlet {
	private static final long serialVersionUID = 1L;
		private Juegos partida;
		private Connection conexion;
		private Vector<cartas> mundos;
       	private Vector<String> fases;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public game1() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see Servlet#init(ServletConfig)
	 */
	public void init(ServletConfig config) throws ServletException {
		// TODO Auto-generated method stub
		this.partida = new Juegos(); 
		this.mundos = new Vector<cartas>();
		this.fases = new Vector<String>();
		try {
			conexion = DriverManager.getConnection("jdbc:mysql://localhost:3306/Juego", "root", "william98");
			String sqlQuery="SELECT * FROM cartas";
			Statement con;
			con = conexion.createStatement();
			ResultSet query = con.executeQuery(sqlQuery);
			int mun = 4;
			while(query.next()) {
				cartas cartaAuxiliar =  new cartas(query.getInt(1),query.getString(2),query.getString(3),
												query.getInt(4),query.getInt(5),query.getInt(6),
												query.getInt(7),query.getInt(8),query.getInt(9),
												query.getBoolean(10),query.getInt(11),query.getString(12),query.getInt(13));
				
				if(cartaAuxiliar.getTipo().compareTo("Productor")==0||cartaAuxiliar.getTipo().compareTo("Mixta")==0||cartaAuxiliar.getTipo().compareTo("Militar")==0&&mun>0) {
					mundos.addElement(cartaAuxiliar);
					mun--;
				}else {
					partida.setMazo(cartaAuxiliar);
				}
			}
			for(int i=0 ; i<6 ;i++){
				int q = partida.getSizeMazo();
				//System.out.println("MAZO: "+q);
				int index = (int) (Math.random()*q);
				//System.out.println("IND: "+index);
				partida.setCartas(partida.getMazo(index));
				System.out.println("mazo ant: "+partida.getSizeMazo());
				partida.borrarMazo(partida.getMazo(index));
				System.out.println("mazo des: "+partida.getSizeMazo());
			}
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		int rand = (int)Math.random()*4;
		System.out.println(rand);
		cartas primerMundo = mundos.get(rand);
		System.out.println(primerMundo.getPoderMilitar()+"--"+primerMundo.getPuntosIntangibles());
		partida.setImperio(primerMundo);
		System.out.println(partida.getSizeImperio());
		
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		/*if(request.getParameter("opcion").startsWith("d")) {
			destroy(request, response);
		}*/
		if(request.getParameter("opcion").startsWith("o")){
			int id = Integer.parseInt(request.getParameter("opcion").substring(1));
			this.descartar(id,request, response);
			Cookie[] cookies = request.getCookies();
			int numeroDescartes = 0;
			for (int j=0; j<cookies.length; j++) {
			    Cookie cookie = cookies[j];
			    if(cookie.getName().compareTo("numDescarte")==0){
			    	numeroDescartes=Integer.parseInt(cookie.getValue())-1;
			    	cookie.setValue(String.valueOf(numeroDescartes));
			    	response.addCookie(cookie);
			    	if(numeroDescartes==0) {
			    		this.fases.remove(fases.get(0));
			    		bonus();
			    	}
			    }
			}
			//doPost(request, response);
		}
		
		if(request.getParameter("opcion").startsWith("u")){
			Cookie[] cookies = request.getCookies();
			Cookie cookie = cookies[1];
			int numeroDescartes=0;
			for (int j=0; j<cookies.length; j++) {
			     cookie = cookies[j];
			    if(cookie.getName().compareTo("numDescarte")==0){
			    	numeroDescartes=Integer.parseInt(cookie.getValue());
			    	break;
			    }
			}
			int id = Integer.parseInt(request.getParameter("opcion").substring(1));
			cartas carta = partida.buscarCarta(id);
			if(carta.getTipo().compareTo("Investigacion")==0&&carta.getSacrificio()<partida.getSizeCartas()){
				System.out.println(carta.getSacrificio()+"+"+partida.getCostoInvestigar()+"="+(carta.getSacrificio()+partida.getCostoInvestigar()));
				int costos=carta.getSacrificio()+partida.getCostoInvestigar();
				cookie.setValue(String.valueOf(numeroDescartes+costos));
		    	response.addCookie(cookie);
		    	if(costos==0) {
		    		fases.remove(0);
		    		bonus();
		    	}
		    	partida.setImperio(carta);
				partida.borrarCarta(carta);
			}
			if(carta.getTipo().compareTo("Productor")==0||carta.getTipo().compareTo("Planeta")==0&&carta.getSacrificio()<partida.getSizeCartas()){
				int costos=carta.getSacrificio()+partida.getCostoPlanetas();
				cookie.setValue(String.valueOf(numeroDescartes+costos));
		    	response.addCookie(cookie);
		    	if(costos==0) {
		    		fases.remove(0);
		    		bonus();
		    	}
		    	partida.setImperio(carta);
				partida.borrarCarta(carta);
			}
			if(carta.getTipo().compareTo("Militar")==0||carta.getTipo().compareTo("Mixta")==0&&partida.getPoderMilitar()>=carta.getSacrificio()){
				System.out.println("entro-->"+carta.getId());
				partida.setImperio(carta);
				partida.borrarCarta(carta);
				bonus();
				this.fases.remove(0);
			}
			//doPost(request, response);
		}
		
		if(request.getParameter("opcion").startsWith("f")){
			String fase = request.getParameter("opcion").substring(1);
			Cookie[] cookies = request.getCookies();
			if(fase.startsWith("1")) {
				for(int i=0;i<2;i++){
					partida.setCartas(partida.getMazo(i));
					partida.borrarMazo(partida.getMazo(i));
				}
				for (int j=0; j<cookies.length; j++) {
				    Cookie cookie = cookies[j];
				    if(cookie.getName().compareTo("numDescarte")==0){
				    	int numeroDescartes=Integer.parseInt(cookie.getValue())+1;
				    	cookie.setValue(String.valueOf(numeroDescartes));
				    	response.addCookie(cookie);
				    	break;
				    }
				}
			}
			if(fase.startsWith("2")) {
				for(int i=0;i<5;i++){
					System.out.println(partida.getSizeMazo());
					partida.setCartas(partida.getMazo(4-i));
					partida.borrarMazo(partida.getMazo(4-i));
				}
				for (int j=0; j<cookies.length; j++) {
				    Cookie cookie = cookies[j];
				    if(cookie.getName().compareTo("numDescarte")==0){
				    	int numeroDescartes=Integer.parseInt(cookie.getValue())+4;
				    	cookie.setValue(String.valueOf(numeroDescartes));
				    	response.addCookie(cookie);
				    	break;
				    }
				}
			}
			this.fases.addElement(fase);
			//doPost(request, response);
		}
		doPost(request, response);
	}
	public void descartar(int id, HttpServletRequest request, HttpServletResponse response){
		Cookie[] cookies = request.getCookies();
		cartas descarte = partida.buscarCarta(id);
		partida.setMazo(descarte);
		System.out.println("antes: "+partida.getSizeCartas());
		partida.borrarCarta(descarte);
		System.out.println("despues: "+partida.getSizeCartas());
	}
	
	public void bonus(){
		for(int i=0; i<partida.getBonusExploracion();i++){
			partida.setCartas(partida.getMazo(i));
			partida.borrarMazo(partida.getMazo(i));
		}
	}
	
	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		HttpSession session = request.getSession();
		try{
			PrintWriter out = response.getWriter();
		    out.println("<HTML>");
		    out.println("<HEAD>");
		    out.println("<TITLE>Shop</TITLE>");
		    out.println("<link rel='stylesheet' href='https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css' integrity='sha384-ggOyR0iXCbMQv3Xipma34MD+dH/1fQ784/j6cY/iJTQUOhcWr7x9JvoRxT2MZw1T' crossorigin='anonymous'>");
		    out.println("<style>"+
		    			"div.jugadores{background-color:LightGray;height: 100px;}"+
		    			".myButton {\n" + 
		    			"	-moz-box-shadow: -3px -2px 0px 0px #8a2a21;\n" + 
		    			"	-webkit-box-shadow: -3px -2px 0px 0px #8a2a21;\n" + 
		    			"	box-shadow: -3px -2px 0px 0px #8a2a21;\n" + 
		    			"	background:-webkit-gradient(linear, left top, left bottom, color-stop(0.05, #ff1500), color-stop(1, #f24437));\n" + 
		    			"	background:-moz-linear-gradient(top, #ff1500 5%, #f24437 100%);\n" + 
		    			"	background:-webkit-linear-gradient(top, #ff1500 5%, #f24437 100%);\n" + 
		    			"	background:-o-linear-gradient(top, #ff1500 5%, #f24437 100%);\n" + 
		    			"	background:-ms-linear-gradient(top, #ff1500 5%, #f24437 100%);\n" + 
		    			"	background:linear-gradient(to bottom, #ff1500 5%, #f24437 100%);\n" + 
		    			"	filter:progid:DXImageTransform.Microsoft.gradient(startColorstr='#ff1500', endColorstr='#f24437',GradientType=0);\n" + 
		    			"	background-color:#ff1500;\n" + 
		    			"	display:inline-block;\n" + 
		    			"	cursor:pointer;\n" + 
		    			"	color:#ffffff;\n" + 
		    			"	font-family:Arial;\n" + 
		    			"	font-size:16px;\n" + 
		    			"	font-weight:bold;\n" + 
		    			"	padding:9px 9px;\n" + 
		    			"	text-decoration:none;\n" + 
		    			"	text-shadow:0px 0px 0px #810e05;\n" + 
		    			"}\n" + 
		    			".myButton:hover {\n" + 
		    			"	background:-webkit-gradient(linear, left top, left bottom, color-stop(0.05, #f24437), color-stop(1, #ff1500));\n" + 
		    			"	background:-moz-linear-gradient(top, #f24437 5%, #ff1500 100%);\n" + 
		    			"	background:-webkit-linear-gradient(top, #f24437 5%, #ff1500 100%);\n" + 
		    			"	background:-o-linear-gradient(top, #f24437 5%, #ff1500 100%);\n" + 
		    			"	background:-ms-linear-gradient(top, #f24437 5%, #ff1500 100%);\n" + 
		    			"	background:linear-gradient(to bottom, #f24437 5%, #ff1500 100%);\n" + 
		    			"	filter:progid:DXImageTransform.Microsoft.gradient(startColorstr='#f24437', endColorstr='#ff1500',GradientType=0);\n" + 
		    			"	background-color:#f24437;\n" + 
		    			"}\n" + 
		    			".myButton:active {\n" + 
		    			"	position:relative;\n" + 
		    			"	top:1px;\n" + 
		    			"}\n" +  
		    			".intangible {\n" + 
		    			"	-moz-box-shadow:inset 0px 0px 50px -19px #23395e;\n" + 
		    			"	-webkit-box-shadow:inset 0px 0px 50px -19px #23395e;\n" + 
		    			"	box-shadow:inset 0px 0px 50px -19px #23395e;\n" + 
		    			"	background-color:transparent;\n" + 
		    			"	-moz-border-radius:5px;\n" + 
		    			"	-webkit-border-radius:5px;\n" + 
		    			"	border-radius:5px;\n" + 
		    			"	border:4px solid #1f2f47;\n" + 
		    			"	display:inline-block;\n" + 
		    			"	cursor:pointer;\n" + 
		    			"	color:#ffffff;\n" + 
		    			"	font-family:Arial;\n" + 
		    			"	font-size:15px;\n" + 
		    			"	padding:10px 13px;\n" + 
		    			"	text-decoration:none;\n" + 
		    			"	text-shadow:0px 0px 0px #263666;\n" + 
		    			"}\n" + 
		    			".intangible:hover {\n" + 
		    			"	background-color:transparent;\n" + 
		    			"}\n" + 
		    			".intangible:active {\n" + 
		    			"	position:relative;\n" + 
		    			"	top:1px;\n" + 
		    			"}"+
		    			".button1 {\n" + 
		    			"    display: inline-block;\n" + 
		    			"    text-align: center;\n" + 
		    			"    vertical-align: middle;\n" + 
		    			"    padding: 6px 10px;\n" + 
		    			"    border: 1px solid #31669e;\n" + 
		    			"    border-radius: 8px;\n" + 
		    			"    background: #4fa6ff;\n" + 
		    			"    background: -webkit-gradient(linear, left top, left bottom, from(#4fa6ff), to(#31669e));\n" + 
		    			"    background: -moz-linear-gradient(top, #4fa6ff, #31669e);\n" + 
		    			"    background: linear-gradient(to bottom, #4fa6ff, #31669e);\n" + 
		    			"    -webkit-box-shadow: #499aec -1px 0px 0px 0px;\n" + 
		    			"    -moz-box-shadow: #499aec -1px 0px 0px 0px;\n" + 
		    			"    box-shadow: #499aec -1px 0px 0px 0px;\n" + 
		    			"    text-shadow: #1f4063 1px 1px 1px;\n" + 
		    			"    font: normal normal bold 29px arial;\n" + 
		    			"    color: #ffffff;\n" + 
		    			"    text-decoration: none;\n" + 
		    			"}\n" + 
		    			".button1:hover,\n" + 
		    			".button1:focus {\n" + 
		    			"    border: 1px solid ##3d80c5;\n" + 
		    			"    background: #5fc7ff;\n" + 
		    			"    background: -webkit-gradient(linear, left top, left bottom, from(#5fc7ff), to(#3b7abe));\n" + 
		    			"    background: -moz-linear-gradient(top, #5fc7ff, #3b7abe);\n" + 
		    			"    background: linear-gradient(to bottom, #5fc7ff, #3b7abe);\n" + 
		    			"    color: #ffffff;\n" + 
		    			"    text-decoration: none;\n" + 
		    			"}\n" + 
		    			".button1:active {\n" + 
		    			"    background: #31669e;\n" + 
		    			"    background: -webkit-gradient(linear, left top, left bottom, from(#31669e), to(#31669e));\n" + 
		    			"    background: -moz-linear-gradient(top, #31669e, #31669e);\n" + 
		    			"    background: linear-gradient(to bottom, #31669e, #31669e);\n" + 
		    			"}"+
		    			"</style>");
		    out.println("</HEAD>");
		    out.println("<BODY style='background-color:#363663;'>");
		    out.println("<div class='row'>\n" + 
			    		"	<div class='col-sm' style='background-color:LightGray;height: 70px;'> Jugador Local "+
			    		"<div><a class='myButton'>"+partida.getPoderMilitar()+"</a>"+ 
			    		"<a class='intangible'>"+partida.getPuntosIntengibles()+"</a><a class='button1'>"+partida.getPuntoTangibles()+"</a></div>"+
			    		"</div>\n" + 
			    		"	<div class='col-sm' style='background-color:LightGray;height: 70px;'> Segundo Jugador" + 
			    		"		<div><a class='myButton'>"+partida.getPoderMilitar()+"</a>" + 
			    		"			<a class='intangible'>"+partida.getPuntosIntengibles()+"</a><a class='button1'>"+partida.getPuntoTangibles()+"</a></div>\n" + 
			    		"			</div>\n" +  
			    		"	<div class='col-sm' style='background-color:LightGray;height: 70px;'> Tercer Jugador </div>\n" + 
			    		"	<div class='col-sm' style='background-color:LightGray;height: 70px;'> Cuatro Jugador </div>\n" + 
			    		"	<div class='col-sm'>\n" + 
			    		"<form  method='GET'>"+
			    		"		<button type='submit' name='opcion' value='d'>Abandonar Partida</button>\n" + 
			    		"</form>"+
			    		"	</div>\n" + 
			    		"</div>\n"+
			    		"<br>");
		    out.println("<div>"+
		    			"		<form method='GET'>"+
		    			"		<h3>Fases de Juego (solo se puede escoger una fase por turno)</h3>"+
		    			"			<div>"+ 
						"			<input type='checkbox' name='opcion' value='f1'>Mostrar 2 cartas y seleccionar 1\n" + 
						"  			<input type='checkbox' name='opcion' value='f2'>Mostrar 5 cartas y seleccionar 1\n" + 
						"  			<input type='checkbox' name='opcion' value='f3'>Investigacion\n" +
						"  			<input type='checkbox' name='opcion' value='f4'>Colocar Planeta\n");
						if(fases.isEmpty()) {
							out.println("  	<input type='submit' value='Submit' >\n");
						}else {
							out.println("  	<input type='submit' value='Submit' disabled>\n");
						}
			out.println("			</div>"+
						"		</form>" +
						"</div>"+
		    			"<br>");
		    int mundo = (int) (Math.random()*partida.getSizeImperio());
		    out.println("<div class='container' style='height:280px;background-color:#364563;'>"+
		    			"<h3>Imperio</h3>"+
		    			"<div class='row'>");
		    for(int i=0; i<partida.getSizeImperio();i++) {
		    out.println("	<div class='col-md-1 bg-gradient-info'>"+
		    			"		<img src='"+partida.getImperio(i).getUrl()+"' class='card-img-left' style='width:100%; height:200px;'>"+
		    			"	</div>");
		    }
		    out.println("</div>"+
		    			"</div>"+
		    			"<br>"+
		    			"<div style='background-color:#364563;'>"+
		    			"<h3>Cartas</h3>"+
		    			"<div class='row'>");
		    for(int i=0; i<partida.getSizeCartas();i++) {
		    	out.println("	<div class='col-md-1 bg-gradient-info'>"+
		    				"		<img src='"+partida.getCartas(i).getUrl()+"' class='card-img-left' style='width:100%; height:200px;'>"+
		    				"		<form  method='GET''>");
		    Cookie[] cookies = request.getCookies();
		    int numeroDescartes = 0;
		    for (int j=0; j<cookies.length; j++) {
		    	Cookie cookie = cookies[j];
		    	if(cookie.getName().compareTo("numDescarte")==0)numeroDescartes=Integer.parseInt(cookie.getValue());
		    }
		    	if(fases.isEmpty()) {
		    		out.println("  		<button type='submit' name='opcion' value='o"+partida.getCartas(i).getId()+"' disabled>Descartar</button>"+
		    					"  		<button type='submit' name='opcion' value='u"+partida.getCartas(i).getId()+"' disabled>Agregrar</button>");
		    	}else {
			    	if(numeroDescartes>0) {
			    	out.println("  		<button type='submit' name='opcion' value='o"+partida.getCartas(i).getId()+"'>Descartar</button>"+
			    				"  		<button type='submit' name='opcion' value='u"+partida.getCartas(i).getId()+"' disabled>Agregrar</button>");
			    	}
			    	if(numeroDescartes==0) {
			    		if(this.fases.get(0).compareTo("3")==0) {
			    			if(partida.getCartas(i).getTipo().compareTo("Investigacion")==0){
			    				out.println("  		<button type='submit' name='opcion' value='o"+partida.getCartas(i).getId()+"' disabled>Descartar</button>"+
			    							"  		<button type='submit' name='opcion' value='u"+partida.getCartas(i).getId()+"'>Agregrar</button>");
			    			}else{
			    				out.println("  		<button type='submit' name='opcion' value='o"+partida.getCartas(i).getId()+"' disabled>Descartar</button>"+
				    						"  		<button type='submit' name='opcion' value='u"+partida.getCartas(i).getId()+"' disabled>Agregrar</button>");
			    			}
					   	}
			    		if(this.fases.get(0).compareTo("4")==0) {
			    			if(partida.getCartas(i).getTipo().compareTo("Investigacion")!=0){
			    				out.println("  		<button type='submit' name='opcion' value='o"+partida.getCartas(i).getId()+"' disabled>Descartar</button>"+
			    							"  		<button type='submit' name='opcion' value='u"+partida.getCartas(i).getId()+"'>Agregrar</button>");
			    			}else{
			    				out.println("  		<button type='submit' name='opcion' value='o"+partida.getCartas(i).getId()+"' disabled>Descartar</button>"+
				    						"  		<button type='submit' name='opcion' value='u"+partida.getCartas(i).getId()+"' disabled>Agregrar</button>");
			    			}
					   	}
			    	}
		    	}
		    	out.println("		</form>"+
		    				"	</div>");
		    }
		    	out.println("</div>"+
		    				"</div>");
		    	out.println("</BODY>");
		    	out.println("</HTML>");
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	}

}
