package Beans;

import java.io.Serializable;

public class cartas implements Serializable {
	
	private int id;
	private String tipo;
	private int poderMilitar;
	private int costoMilitar;
	private int costoInvestigacion;
	private int bonusExploracion;
	private int sacrificio;
	private int puntosIntangibles;
	private boolean pago;
	private String color;
	private int pBonusCarta;
	private String url;
	private int costoPlanetas;
	
	public cartas(int id, String tipo, String color, int poderMilitar, int costoMilitar, int costoInvestigacion, int bonusExploracion,
			int sacrificio, int puntosIntangibles, boolean pago, int pBonusCarta, String url,int costoPlanetas) {
		super();
		this.id = id;
		this.tipo = tipo;
		this.poderMilitar = poderMilitar;
		this.costoMilitar = costoMilitar;
		this.costoInvestigacion = costoInvestigacion;
		this.bonusExploracion = bonusExploracion;
		this.sacrificio = sacrificio;
		this.puntosIntangibles = puntosIntangibles;
		this.pago = pago;
		this.color = color;
		this.pBonusCarta = pBonusCarta;
		this.url = url;
		this.costoPlanetas = costoPlanetas;
	}
	
	public int getCostoPlanetas() {
		return costoPlanetas;
	}
	public void setCostoPlanetas(int costoPlanetas) {
		this.costoPlanetas = costoPlanetas;
	}
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getTipo() {
		return tipo;
	}
	public void setTipo(String tipo) {
		this.tipo = tipo;
	}
	public int getPoderMilitar() {
		return poderMilitar;
	}
	public void setPoderMilitar(int poderMilitar) {
		this.poderMilitar = poderMilitar;
	}
	public int getCostoMilitar() {
		return costoMilitar;
	}
	public void setCostoMilitar(int costoMilitar) {
		this.costoMilitar = costoMilitar;
	}
	public int getCostoInvestigacion() {
		return costoInvestigacion;
	}
	public void setCostoInvestigacion(int costoInvestigacion) {
		this.costoInvestigacion = costoInvestigacion;
	}
	public int getBonusExploracion() {
		return bonusExploracion;
	}
	public void setBonusExploracion(int bonusExploracion) {
		this.bonusExploracion = bonusExploracion;
	}
	public int getSacrificio() {
		return sacrificio;
	}
	public void setSacrificio(int sacrificio) {
		this.sacrificio = sacrificio;
	}
	public int getPuntosIntangibles() {
		return puntosIntangibles;
	}
	public void setPuntosIntangibles(int puntosIntangibles) {
		this.puntosIntangibles = puntosIntangibles;
	}
	public boolean isPago() {
		return pago;
	}
	public void setPago(boolean pago) {
		this.pago = pago;
	}
	public String getColor() {
		return color;
	}
	public void setColor(String color) {
		this.color = color;
	}
	public int getpBonusCarta() {
		return pBonusCarta;
	}
	public void setpBonusCarta(int pBonusCarta) {
		this.pBonusCarta = pBonusCarta;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	
	
	
}
