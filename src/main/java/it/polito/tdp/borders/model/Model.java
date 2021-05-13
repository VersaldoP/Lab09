package it.polito.tdp.borders.model;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.jgrapht.Graph;
import org.jgrapht.Graphs;
import org.jgrapht.alg.connectivity.ConnectivityInspector;
import org.jgrapht.graph.DefaultEdge;
import org.jgrapht.graph.SimpleGraph;

import it.polito.tdp.borders.db.BordersDAO;

public class Model {
	private BordersDAO dao ;
	private Graph<Country,DefaultEdge> grafo;
	private Map<Integer,Country> countrys;
	private Set<Border> borders;
	
	

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
	
	

}
