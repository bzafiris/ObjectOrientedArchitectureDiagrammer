package model.graph;

import org.javatuples.Pair;
import org.javatuples.Triplet;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ClassifierVertex {

	private final List<Arc<ClassifierVertex>> 		    arcs;
	private final List<Method> 							methods;
	private final List<Field> 							fields;
	private final VertexType 							vertexType;
	private final Path 									path;
	private final String 								name;
	private 	  List<Triplet<String, String, String>> deserializedArcs;
	private 	  double 								x;
	private 	  double 								y;

	public ClassifierVertex(Path 	   path,
							String 	   name,
							VertexType vertexType) {
		this.vertexType = vertexType;
		this.path 		= path;
		this.name	    = name;
		this.arcs 		= new ArrayList<>();
		this.methods 	= new ArrayList<>();
		this.fields 	= new ArrayList<>();
	}

	public void addArc(ClassifierVertex sourceVertex, ClassifierVertex targetVertex, ArcType arcType) {
		arcs.add(new Arc<>(sourceVertex, targetVertex, arcType));
	}

	public void addMethod(String name, String returnType, ModifierType modifier, Map<String, String> parameters) {
		methods.add(new Method(name, returnType, modifier, parameters));
	}

	public void addField(String name, String type, ModifierType modifier) {
		fields.add(new Field(name, type, modifier));
	}

	public void setDeserializedArcs(List<Triplet<String, String, String>> deserializedArcs) {
		this.deserializedArcs = deserializedArcs;
	}

	public List<Triplet<String, String, String>> getDeserializedArcs() {
		return deserializedArcs;
	}

	public VertexType getVertexType() {
		return vertexType;
	}

	public List<Arc<ClassifierVertex>> getArcs() {
		return arcs;
	}

	public Path getPath() {
		return path;
	}

	public String getName() {
		return name;
	}

	public List<Method> getMethods() {
		return methods;
	}

	public List<Field> getFields() {
		return fields;
	}

	public void setCoordinates(double x, double y) {
		this.x = x;
		this.y = y;
	}

	public Pair<Double, Double> getCoordinates() {
        return new Pair<>(x, y);
	}

	public record Method (String 			  name,
						  String 			  returnType,
						  ModifierType 		  modifier,
						  Map<String, String> parameters) {}

	public record Field(String 		 name,
						String 		 type,
						ModifierType modifier) {}
}
