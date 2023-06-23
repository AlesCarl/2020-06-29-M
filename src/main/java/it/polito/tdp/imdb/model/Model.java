package it.polito.tdp.imdb.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.jgrapht.Graphs;
import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.SimpleWeightedGraph;

import it.polito.tdp.imdb.db.ImdbDAO;


public class Model {
	
	ImdbDAO dao; 
    private SimpleWeightedGraph<Director, DefaultWeightedEdge> graph;  // SEMPLICE, PESATO, NON ORIENTATO
    private List<Director> allRegisti ; 
    
    
  public Model() {
    	
    	this.dao= new ImdbDAO();  
    	this.graph= new SimpleWeightedGraph<>(DefaultWeightedEdge.class);
    	this.allRegisti= new ArrayList<>();
    	
    }
  
  public void creaGrafo(int anno) {
	  
	  allRegisti= dao.listAllDirectorsYears(anno);
	  

		 /** VERTICI */
	    	Graphs.addAllVertices(this.graph, allRegisti);
	 		System.out.println("NUMERO vertici GRAFO: " +this.graph.vertexSet().size());
	 		
	 		
	 		/** ARCHI */
	 		
	 		/*
ARCO se hanno diretto almeno una volta lo stesso attore nell’anno 
in input.
 Il peso dell’arco è dato dal numero degli attori condivisi.
	 		*/
	 		
	 		
// trovo tutti gli attori diretti dal regista in questione nell'ANNO.
		 		
	 		 /** ARCHI **/ 
			for(Director a1: this.allRegisti) {
				for(Director a2 : this.allRegisti) {
					
					if(!a1.equals(a2)) {
					double peso= calcolaPeso(a1,a2,anno);
					
					if(peso > 0 ) {
						Graphs.addEdgeWithVertices(this.graph, a1, a2, peso);
						
					 }
					}
				}
			}
			
			System.out.println("\nVERTICI: "+this.graph.vertexSet().size());
			System.out.println("\nARCHI: "+this.graph.edgeSet().size());

	  
  }

  public List<DirectorAttoriCondivisi> getAdiacenti(Director dir){
	 
	  List<DirectorAttoriCondivisi> adiacenti= new ArrayList<>();  
	  
	  for( Director dd: Graphs.neighborListOf(this.graph, dir) ){
		  // itero sui nodi vicino a "dir"
			
		     DefaultWeightedEdge ee=  this.graph.getEdge(dir, dd); 
		  
			 double peso=  this.graph.getEdgeWeight(ee); 
			
	 /** E' INUTILE TROVARE il vertice opposto, E' QUELLO CHE STO ITERANDO**/ 
			 
			// Director d= this.graph.getEdgeTarget(ee);  MAI NELLA VITA
			// Director d= Graphs.getOppositeVertex(graph, ee, dir); 

			 DirectorAttoriCondivisi dac= new DirectorAttoriCondivisi(dd,peso); 
			 adiacenti.add(dac); 
		}
	  
	Collections.sort(adiacenti);
	return adiacenti;
	  
  }
private double calcolaPeso(Director a1, Director a2, int anno) {
	 
	  List <Integer> listActor1= dao.getAttoriRegista(a1,anno);  
	  List <Integer> listActor2= dao.getAttoriRegista(a2,anno);  
	  
	  List <Integer> temp= new ArrayList<>();
	  
	  for(Integer ii:listActor1 ) {
		  if(listActor2.contains(ii)) {
			  temp.add(ii);
		  }
	  }

	return temp.size();
}







/**SOLZ DA INTERNET **/ 
//
//List <Director> bestPercorso; 
//
//public DirectorPesoList getPercorso(double maxAttoriCondivisi, Director director) {
//	
//	
//	bestPercorso= new ArrayList<>(); 
//	List<Director> parziale = new ArrayList<>();
//	parziale.add(director);
//	double pesoPath = 0;
//	
//	this.ricorsione(parziale, maxAttoriCondivisi, pesoPath);
//	
//	this.bestPercorso= new ArrayList<>() ; 
//	
////	this.bestSize = 0; 
////	
////	
////	
////	parziale.add(dStart);
////	ricorsione(parziale, dStart, c); 
////	
////	
////   	double pesoTot= getPeso(bestPercorso); 
// 	  // double pesoTot= getPeso(bestPercorso); 
//
//    DirectorPesoList trt= new DirectorPesoList(bestPercorso, pesoPath); 
//	
//	return trt;
//}
//
//
//private void ricorsione(List<Director> parziale, double maxAttoriCondivisi, double pesoPath) {
//	
//	// condizione di terminazione
//	if(pesoPath >= maxAttoriCondivisi)
//		return;
//	
//	
//	// condizione di aggiornamento
//	if(parziale.size()>this.bestPercorso.size())
//		bestPercorso = new ArrayList<>(parziale);
//	
//	
//	
//	// prendo l'ultimo vertice inserito
//	Director ultimo = parziale.get(parziale.size()-1);
//	
//	//prendo i vertici adiacenti
//	List<Director> adiacenti = Graphs.neighborListOf(this.graph, ultimo);
//	Collections.sort(adiacenti); // ordino da quello con più attori in comune all'ultimo
//	
//	for(Director dir : adiacenti) {
//		if(!parziale.contains(dir)) {
//			
//			double peso = this.graph.getEdgeWeight(this.graph.getEdge(ultimo, dir));
//			if(peso+pesoPath <= maxAttoriCondivisi) {
//				
//				pesoPath+=peso;
//				parziale.add(dir);
//				
//				this.ricorsione(parziale, maxAttoriCondivisi, pesoPath);
//				
//				parziale.remove(dir);
//				pesoPath-= this.graph.getEdgeWeight(this.graph.getEdge(ultimo, dir));
//				
//			}
//		}
//	}
//}


List <Director> bestPercorso; 
double bestSize; 
//  double countDuratams; 

public DirectorPesoList getPercorso ( Double c, Director dStart) { // input in ms  ...
	
	List <Director> parziale = new ArrayList<>() ; 
	
	
	this.bestPercorso= new ArrayList<>() ; 
	this.bestSize = 0; 
	
	
	
	parziale.add(dStart);
	ricorsione(parziale, dStart, c); 
	
	
 	double pesoTot= getPeso(bestPercorso); 

    DirectorPesoList trt= new DirectorPesoList(bestPercorso, pesoTot); 
	
	return trt ;
	
}

/*
 cercare un gruppo contenente il massimo numero di registi concatenati a partire 
 da quello selezionato al punto 1.d per cui la somma degli
 attori condivisi sia minore o uguale a c.
 */





private void ricorsione(List<Director> parziale, Director dStart, Double c) {
		
	Director current = parziale.get(parziale.size()-1);
		
	/** condizione uscita **/ 
	
	if( getPeso(parziale) > c) { 
		System.out.println("\nPeso di uscita : "+ getPeso(parziale));
		return; 
	} 

	/** soluzione migliore **/ 
	
	if(parziale.size() > bestSize) {
		
		bestSize= parziale.size(); 
		bestPercorso= new ArrayList<>(parziale); 
	}

	/** continuo ad aggiungere elementi in parziale **/ 
    
    List<Director> successori= Graphs.successorListOf(graph, current);
	List<Director> newSuccessori= new ArrayList<>();  

	
	for(Director rr: successori) {
	    if(!parziale.contains(rr)) {
	    	newSuccessori.add(rr);  //QUI METTO SOLO I VERTICI CHE NON SONO GIA' STATI USATI
	    }
  }
	 
	/** condizione uscita 2 ( ? un po' inutile ... )   **/ 

	if(newSuccessori.size()==0) {
		if(parziale.size() > bestSize) {
			 
			bestSize= parziale.size(); 
			bestPercorso= new ArrayList<>(parziale); 
			
		}
		return; 
	}
	
    /** continuo ad aggiungere elementi in parziale **/ 
	
	for(Director tt : newSuccessori) {
		
			parziale.add(tt);
			ricorsione(parziale,dStart,c); 
			parziale.remove(tt);	
	}
}


/** SEMBRA FUNZIONARE **/ 

private double getPeso(List<Director> parzialle) { // peso: attori condivisi
	
	
	double peso=0; 
	Director current = parzialle.get(0);
	
	for(Director dd: parzialle) {
		
		   if(!current.equals(dd)){
			
		       DefaultWeightedEdge ee=  this.graph.getEdge(current, dd); 
		       double temp= this.graph.getEdgeWeight(ee); 
		       peso+=temp; 
		  	   System.out.println("\nPeso parziale : "+ temp);

		      current=dd; 
		}
	}
	
	System.out.println("\ndimensione di 'parzialle' : "+ parzialle.size());
	System.out.println("\nPeso finale : "+ peso);

	return peso;
}

public int getNumVertex() {
	return this.graph.vertexSet().size();
}
public int getNumEdges() {
	return this.graph.edgeSet().size();
}

public List<Director> getVertex() {
	return this.allRegisti;
}
	  
    

}
