package it.polito.tdp.imdb.model;

import java.util.List;

public class DirectorPesoList {
	
	List <Director> bestPercorso; 

	double pesoTot;

	
	public DirectorPesoList(List<Director> bestPercorso, double pesoTot) {
		super();
		this.bestPercorso = bestPercorso;
		this.pesoTot = pesoTot;
	}

	

	public List<Director> getBestPercorso() {
		return bestPercorso;
	}


	public void setBestPercorso(List<Director> bestPercorso) {
		this.bestPercorso = bestPercorso;
	}


	public double getPesoTot() {
		return pesoTot;
	}


	public void setPesoTot(double pesoTot) {
		this.pesoTot = pesoTot;
	} 
	
	

}
