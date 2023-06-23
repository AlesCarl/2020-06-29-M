package it.polito.tdp.imdb.model;

public class DirectorAttoriCondivisi implements Comparable<DirectorAttoriCondivisi>{
	
	Director d;
	Double peso;
	
	public DirectorAttoriCondivisi(Director d, Double peso)  {
		
		this.d = d;
		this.peso = peso;
	}

	
	@Override
	public String toString() {
		return d + " - #attori condivisi: " + peso+"\n" ;
	}


	public Director getD() {
		return d;
	}

	public void setD(Director d) {
		this.d = d;
	}

	public Double getPeso() {
		return peso;
	}

	public void setPeso(Double peso) {
		this.peso = peso;
	}

	@Override
	public int compareTo(DirectorAttoriCondivisi o) {
		return -(this.peso.compareTo(o.peso));   //decrescente
	} 
	
	

}
