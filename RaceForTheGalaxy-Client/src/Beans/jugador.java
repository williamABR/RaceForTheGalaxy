package Beans;

import java.io.Serializable;

public class jugador implements Serializable {

	private String usuario;
	private String psw;
	public jugador(String usuario, String psw) {
		super();
		this.usuario = usuario;
		this.psw = psw;
	}
	public jugador() {
		super();
	}
	public String getUsuario() {
		return usuario;
	}
	public void setUsuario(String usuario) {
		this.usuario = usuario;
	}
	public String getPsw() {
		return psw;
	}
	public void setPsw(String psw) {
		this.psw = psw;
	}
	
	

}
