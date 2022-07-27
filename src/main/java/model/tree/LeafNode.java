package model.tree;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/* This class is responsible for the implementation of a leaf node in the tree.
 * Each has node has a parent node(the parent package), the path of the source file,
 * the branches that start from that node and also the field/method/method parameter types */
public class LeafNode extends Node{
	private String inheritanceLine[];
	private final Map<String, String> fields;
	private final Map<String, String> methods;
	private final List<String> methodsParametersTypes;

	/* This method is responsible for initializing the nodes structs */
	public LeafNode(String path) {
		super(path);
		methodsParametersTypes = new ArrayList<>();
		fields = new HashMap<>();
		methods = new HashMap<>();
	}
	
	/* This method is responsible for setting the nodes line that contains the declaration
	 *  of the source file */
	public void setInheritanceLine(String[] inheritanceLine) {
		this.inheritanceLine = inheritanceLine;
	}
	
	/* This method is responsible for adding to the nodes' method parameter types */
	public void addMethodParametersTypes(List<String> parameterTypes) {
		methodsParametersTypes.addAll(parameterTypes);
	}
	
	/* This method is responsible for adding the nodes' methods names and return types */
	public void addMethod(String methodName, String methodReturnType) {
		methods.put(methodName, methodReturnType);
	}

	/* This method is responsible for adding the nodes' fields names types */
	public void addField(String fieldName, String fieldType) {
		fields.put(fieldName, fieldType);
	}

	public PackageNode getParentNode() {
		return parentNode;
	}
	
	public String[] getInheritanceLine() {
		return inheritanceLine;
	}
	
	public String getName() {
		return path.substring(path.lastIndexOf("\\") + 1, path.lastIndexOf("."));
	}

	public Map<String, String> getMethods() {
		return methods;
	}
	
	public Map<String, String> getFields() {
		return fields;
	}
	
	public List<String> getMethodParameterTypes() {
		return methodsParametersTypes;
	}

	public List<String> getMethodsReturnTypes() { return new ArrayList<>(getMethods().values());}

	public List<String> getFieldsTypes(){ return new ArrayList<>(getFields().values());}

	public NodeType getType() {
		if (inheritanceLine[0].equals("enum")) {
			return NodeType.ENUM;
		}else if (inheritanceLine[0].equals("interface")) {
			return NodeType.INTERFACE;
		}else {
			return NodeType.CLASS;
		}
	}

}
