package model.diagram;

import model.diagram.arrangement.geometry.DiagramGeometry;
import model.graph.Arc;
import model.graph.ClassifierVertex;
import org.javatuples.Pair;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

public class ClassDiagram {

	private final  Map<ClassifierVertex, Integer> 					 graphNodes;
	private static Map<ClassifierVertex, Set<Arc<ClassifierVertex>>> diagram;
	private static Map<Path, ClassifierVertex> 						 sinkVertices;
	private static Map<Integer, Pair<Double, Double>> 				 diagramGeometryGraphML;
	private static DiagramGeometry 									 diagramGeometry;

	public ClassDiagram() {
		this.graphNodes = new HashMap<>();
	}

	public void createNewDiagram(List<String> chosenFilesNames) {
		createGraphNodes(chosenFilesNames);
		createDiagram(this.graphNodes.keySet());
	}

	public void createDiagram(Set<ClassifierVertex> sinkVertices) {
		GraphClassDiagramConverter classDiagramConverter = new GraphClassDiagramConverter(sinkVertices);
		diagram = classDiagramConverter.convertGraphToClassDiagram();
	}

	private void createGraphNodes(List<String> chosenFileNames) {
		int nodeId = 0;
		for (ClassifierVertex classifierVertex : getChosenNodes(chosenFileNames)) {
			this.graphNodes.put(classifierVertex, nodeId);
			nodeId++;
		}
	}

	private List<ClassifierVertex> getChosenNodes(List<String> chosenClassesNames) {
		List<ClassifierVertex> chosenClasses = new ArrayList<>();
		for (String chosenClass: chosenClassesNames) {
			Optional<ClassifierVertex> optionalSinkVertex = sinkVertices.values()
				.stream()
				.filter(sinkVertex -> sinkVertex.getName().equals(chosenClass))
				.findFirst();
			if (optionalSinkVertex.isEmpty()) {
				continue;
			}
			chosenClasses.add(optionalSinkVertex.get());
		}
		return chosenClasses;
	}

	public void setSinkVertices(Map<Path, ClassifierVertex> sinkVertices) {
		ClassDiagram.sinkVertices = sinkVertices;
	}

	public void setDiagram(Map<ClassifierVertex, Set<Arc<ClassifierVertex>>> diagram) {
		ClassDiagram.diagram = diagram;
	}

	public void setGraphMLDiagramGeometry(Map<Integer, Pair<Double, Double>> diagramGeometryGraphML) {
		ClassDiagram.diagramGeometryGraphML = diagramGeometryGraphML;
	}

	public void setDiagramGeometry(DiagramGeometry diagramGeometry) {
		ClassDiagram.diagramGeometry = diagramGeometry;
	}

	public Map<ClassifierVertex, Set<Arc<ClassifierVertex>>> getDiagram() {
		return diagram;
	}

	public Map<ClassifierVertex, Integer> getGraphNodes() {
		return graphNodes;
	}

	public Map<Integer, Pair<Double, Double>> getGraphMLDiagramGeometry() {
		return diagramGeometryGraphML;
	}

	public DiagramGeometry getDiagramGeometry() {
		return diagramGeometry;
	}

}
