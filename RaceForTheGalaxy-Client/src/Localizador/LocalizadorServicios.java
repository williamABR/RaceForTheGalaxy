package Localizador;

import java.util.Properties;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import logica.FachadaLogicaBean;
import logica.FachadaLogicaBeanRemote;

public class LocalizadorServicios {
	public FachadaLogicaBeanRemote getRemoteFachadaLogica() throws NamingException {
		return this.lookupFachadaLogicaBean("ejb:");
	}
		
	private FachadaLogicaBeanRemote lookupFachadaLogicaBean(String namespace) throws NamingException {
		Context ctx = createInitialContext();
		String appName = "";
		String moduleName = "RaceForTheGalaxy-Servidor";
		String distinctName = "";
		String beanName = FachadaLogicaBean.class.getSimpleName();
		String viewClassName = FachadaLogicaBeanRemote.class.getName();
		return (FachadaLogicaBeanRemote) ctx.lookup(namespace + appName + "/" + moduleName + "/" + distinctName + "/" + beanName + "!" + viewClassName);
	}
		
	private static Context createInitialContext() throws NamingException {
		Properties jndiProps = new Properties();
	    jndiProps.put(Context.INITIAL_CONTEXT_FACTORY,"org.wildfly.naming.client.WildFlyInitialContextFactory");
	    jndiProps.put(Context.PROVIDER_URL,"http-remoting://localhost:8080");
	    jndiProps.put(Context.SECURITY_PRINCIPAL, "testuser");
	    jndiProps.put(Context.SECURITY_CREDENTIALS, "testpassword");
	    jndiProps.put("jboss.naming.client.ejb.context", true);
		return new InitialContext(jndiProps);
	}

}
