package it.polito.tdp.borders.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.jgrapht.Graph;
import org.jgrapht.Graphs;
import org.jgrapht.alg.connectivity.ConnectivityInspector;
import org.jgrapht.event.ConnectedComponentTraversalEvent;
import org.jgrapht.event.EdgeTraversalEvent;
import org.jgrapht.event.TraversalListener;
import org.jgrapht.event.VertexTraversalEvent;
import org.jgrapht.graph.DefaultEdge;
import org.jgrapht.graph.SimpleGraph;
import org.jgrapht.traverse.BreadthFirstIterator;

import it.polito.tdp.borders.db.BordersDAO;

public class Model {
	private BordersDAO dao ;
	private Graph<Country,DefaultEdge> grafo;
	private Map<Integer,Country> countrys;
	private Set<Border> borders;
	private Map<Country,Country> predecessore;
	private List<Country> parziale;
//	private List<Country> soluzioneMigliore;
	
	

	public Model() {
		
		countrys = new HashMap<Integer,Country>();
		dao = new BordersDAO();
		borders = new HashSet<Border>();
	}
	
	
	
	public void creaGrafo() {
		grafo = new SimpleGraph<>(DefaultEdge.class);
		
	}
	
	
	public void caricaGrafo(int anno){
		dao.loadAllCountry(countrys,anno);
		Graphs.addAllVertices(grafo,countrys.values());
		dao.loadAllBorder(borders,countrys,anno);
		for(Border b:borders) {
			DefaultEdge e = this.grafo.getEdge(b.getC1(),b.getC2());
			if(e==null) {
				grafo.addEdge(b.getC1(),b.getC2());
			}
		}
		
	}
	
	
	public String getNVertici() {
		// TODO Auto-generated method stub
		return ""+grafo.vertexSet().size();
	}
	public String getNArchi() {
		// TODO Auto-generated method stub
		return ""+grafo.edgeSet().size();
	}
	
	
	public String getVertici() {
		StringBuilder s = new StringBuilder();
		for(Country c :grafo.vertexSet()) {
			s.append(c.getNome()+" "+ grafo.degreeOf(c)+" \n");
		}
		return s.toString();
	}
	public String getComponentiConnesse() {
		String result;
		
		ConnectivityInspector<Country,DefaultEdge> c= new ConnectivityInspector<>(this.grafo);
		
		List s = c.connectedSets();
		
		result="il numero di parti connesse :"+s.size()+"\n";
		
		return result;
	}



	public Set<Country> getVertexSet() {
		// TODO Auto-generated method stub
		return this.grafo.vertexSet();
	}



	public String cercaPercorsoBFI(Country partenza) {
		BreadthFirstIterator<Country,DefaultEdge> bfv = new BreadthFirstIterator<>(this.grafo,partenza);
		this.predecessore = new HashMap<>();
		this.predecessore.put(partenza,null);
		
		bfv.addTraversalListener(new TraversalListener<Country,DefaultEdge>(){

			@Override
			public void connectedComponentFinished(ConnectedComponentTraversalEvent e) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void connectedComponentStarted(ConnectedComponentTraversalEvent e) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void edgeTraversed(EdgeTraversalEvent<DefaultEdge> e) {
				DefaultEdge edge = e.getEdge();
				Country c1 = grafo.getEdgeSource(edge);
				Country c2 = grafo.getEdgeTarget(edge);
				
				if(!predecessore.containsKey(c1)&&predecessore.containsKey(c2)) {
					// Ho scoperto il passaggio da C1 ------> C2
					predecessore.put(c1,c2);
				}
				if(!predecessore.containsKey(c2)&&predecessore.containsKey(c1)) {
					//Ho scoperto il passaggio da C2 a --------> C1
					predecessore.put(c2,c1);
				}
				
			}

			@Override
			public void vertexTraversed(VertexTraversalEvent<Country> e) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void vertexFinished(VertexTraversalEvent<Country> e) {
				// TODO Auto-generated method stub
				
			}
			
		});
		
		List<Country> cc = new ArrayList<>();
		while(bfv.hasNext()) {
			Country c = bfv.next();
			cc.add(c);
			
		}
		
		String result ="";
		if(cc.isEmpty()) {
			
			return result+" Lista vuota , Errore";
		}
		 result= "Numero di Stati Raggiungibili: "+ cc.size()+"\n";
		for(Country n:cc) {
			result = result +n.toString();
		}
		
		
		
		
		return result;
	}



	public String cercaPercorsoricorsivo(Country c) {
		this.parziale=new ArrayList<>();
//	this.soluzioneMigliore = new ArrayList<>();
		parziale.add(c);
		cerca(parziale);
		
		StringBuilder result = new StringBuilder("Numero di Stati Raggiungibili: "+ parziale.size()+"\n");
		for(Country c1:parziale) {
			result.append(c1);
		}
		
		
		return result.toString();
	}



	private void cerca(List<Country> parziale) {
		for(Country c:Graphs.neighborListOf(this.grafo,parziale.get(parziale.size()-1))) {
			if(!parziale.contains(c)) {
				parziale.add(c);
				cerca(parziale);
			}
		}
	}



	public String cercaPercorsoIt(Country c) {
		List<Country> visitati =new ArrayList<>();
		List<Country> daVisitare = new ArrayList<>();
		Set<DefaultEdge> archi ;
		
		daVisitare.add(c);
		
		int cont =0;
		
		while(cont<daVisitare.size()) {
			Country c1= daVisitare.get(cont);
			
			archi = this.grafo.edgesOf(c1);
			for(DefaultEdge e :archi) {
				
				if((!visitati.contains(this.grafo.getEdgeTarget(e))||!daVisitare.contains(this.grafo.getEdgeTarget(e)))){
					daVisitare.add(this.grafo.getEdgeTarget(e));
				}
				if((!visitati.contains(this.grafo.getEdgeSource(e))||!daVisitare.contains(this.grafo.getEdgeSource(e)))) {
					daVisitare.add(this.grafo.getEdgeSource(e));
					
				}
				
				
			}	
			if(!visitati.contains(c1)) 
				visitati.add(c1);
				
			cont++;
			
		}
		StringBuilder result = new StringBuilder("Numero di Stati Raggiungibili: "+ visitati.size()+"\n");
		for(Country c2:visitati) {
			result.append(c2);
		}
		
		
		return result.toString();
		
		
		
	}

	
	

}
