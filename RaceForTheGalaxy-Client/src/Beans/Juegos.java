package Beans;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Juegos implements Serializable {
	
	private List<cartas> imperio;
	private List<cartas> mazo;
	private List<cartas> cartas;
	private int costoInvestigar;
	private int costoPlanetas;
	private int bonusExploracion;
	private int costoMilitar;
	private int poderMilitar;
	private int puntoTangibles;
	private int puntosIntengibles;
	
	public Juegos() {
		
		super();
		this.imperio = new ArrayList<cartas>();
		this.mazo = new ArrayList<cartas>();;
		this.cartas = new ArrayList<cartas>();;
		this.costoInvestigar = 0;
		this.costoPlanetas = 0;
		this.bonusExploracion=0;
		this.costoMilitar = 0;
		this.poderMilitar = 0;
		this.puntoTangibles = 0;
		this.puntosIntengibles = 0;
	}

	
	
	public cartas getImperio(int index) {
		return imperio.get(index);
	}

	public int getSizeImperio(){
		return imperio.size();
	}

	public void setImperio(cartas e) {
		this.imperio.add(e);
		this.bonusExploracion += e.getBonusExploracion();
		this.costoInvestigar -= e.getCostoInvestigacion();
		this.costoPlanetas -= e.getCostoPlanetas();
		this.costoMilitar -= e.getCostoMilitar();
		this.poderMilitar += e.getPoderMilitar();
		this.puntosIntengibles += e.getPuntosIntangibles();
	}
	
	public cartas getCartas(int index) {
		return cartas.get(index);
	}

	public int getSizeCartas(){
		return cartas.size();
	}

	public void setCartas(cartas e) {
		this.cartas.add(e);
	}
	

	public cartas getMazo(int index) {
		return mazo.get(index);
	}

	public int getSizeMazo(){
		return this.mazo.size();
	}

	public void setMazo(cartas e) {
		this.mazo.add(e);
	}


	public int getBonusExploracion() {
		return bonusExploracion;
	}



	public void setBonusExploracion(int bonusExploracion) {
		this.bonusExploracion = bonusExploracion;
	}



	public int getCostoInvestigar() {
		return costoInvestigar;
	}

	public void setCostoInvestigar(int valor) {
		this.costoInvestigar += valor;
	}

	public int getCostoPlanetas() {
		return costoPlanetas;
	}

	public void setCostoPlanetas(int costoPlanetas) {
		this.costoPlanetas += costoPlanetas;
	}

	public int getCostoMilitar() {
		return costoMilitar;
	}

	public void setCostoMilitar(int costoMilitar) {
		this.costoMilitar += costoMilitar;
	}

	public int getPoderMilitar() {
		return poderMilitar;
	}

	public void setPoderMilitar(int poderMilitar) {
		this.poderMilitar += poderMilitar;
	}

	public int getPuntoTangibles() {
		return puntoTangibles;
	}

	public void setPuntoTangibles(int puntoTangibles) {
		this.puntoTangibles += puntoTangibles;
	}

	public int getPuntosIntengibles() {
		return puntosIntengibles;
	}

	public void setPuntosIntengibles(int puntosIntengibles) {
		this.puntosIntengibles += puntosIntengibles;
	}
	public cartas buscarCarta(int e){
		for(cartas aux: this.cartas){
			if(aux.getId()==e) {return aux;}
				
		}
		return null;
	}
	public cartas buscarMazo(int m){
		for(cartas aux: this.mazo){
			if(aux.getId()==m) {return aux;}
				
		}
		return null;
	}
	public void borrarMazo(cartas a){
		for(int i=0; i<this.getSizeMazo(); i++){
			if(this.mazo.get(i).getId()==a.getId()){this.mazo.remove(i);}
		}
	}
	public void borrarCarta(cartas a){
		for(int i=0; i<this.getSizeCartas(); i++){
			if(this.cartas.get(i).getId()==a.getId()){
				this.cartas.remove(i);
			}
		}
	}

}
