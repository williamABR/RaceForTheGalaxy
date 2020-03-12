package Localizador;

import javax.naming.NamingException;
import logica.FachadaLogicaBeanRemote;

public class Main {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		LocalizadorServicios serviceLocator = new LocalizadorServicios();
		FachadaLogicaBeanRemote fachadaLogica = null;
		try {
			fachadaLogica = serviceLocator.getRemoteFachadaLogica();
		} catch (NamingException e) {
		// TODO Auto-generated catch block
			e.printStackTrace();
		}
		 System.out.println(fachadaLogica.servicio01("Servicio01"));

	}

}
