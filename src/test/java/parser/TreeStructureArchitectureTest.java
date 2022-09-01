package parser;

import static org.junit.jupiter.api.Assertions.*;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import model.tree.NodeType;
import model.tree.RelationshipType;
import org.junit.jupiter.api.Test;
import model.tree.PackageNode;
import model.tree.Relationship;
import model.tree.LeafNode;

class TreeStructureArchitectureTest {

	Path currentDirectory = Path.of(".");

	@Test
	void getFieldAndMethodTypesTest() throws IOException {
		Parser parser = new ProjectParser();
		parser.parseSourcePackage(Paths.get(currentDirectory.toRealPath() + "\\src\\test\\resources\\LatexEditor\\src"));
		Map<Path, PackageNode> packages = parser.getPackageNodes();
		List<String> methodReturnTypes = new ArrayList<>(Arrays.asList("Constructor", "void"));
		List<String> fieldTypes = new ArrayList<>(List.of("VersionsManager"));
		List<String> methodParameterTypes = new ArrayList<>(List.of("VersionsManager"));

		PackageNode commandPackage = packages.get(Paths.get(currentDirectory.toRealPath().normalize().toString(), "\\src\\test\\resources\\LatexEditor\\src\\controller\\commands"));
		LeafNode addLatexCommand = commandPackage.getLeafNodes().get("AddLatexCommand");

		List<String> methodReturnTypesTest;
		List<String> fieldTypesTest;
		List<String> methodParameterTypesTest;
		methodParameterTypesTest = addLatexCommand.getMethodParameterTypes();
		fieldTypesTest = addLatexCommand.getFieldsTypes();
		methodReturnTypesTest = addLatexCommand.getMethodsReturnTypes();
		Collections.sort(methodReturnTypesTest);
		Collections.sort(methodReturnTypes);
		assertTrue(methodReturnTypesTest.size() == methodReturnTypes.size() 
				&& methodReturnTypes.containsAll(methodReturnTypesTest) 
				&& methodReturnTypesTest.containsAll(methodReturnTypes));
		Collections.sort(fieldTypesTest);
		Collections.sort(fieldTypes);
		assertTrue(fieldTypesTest.size() == fieldTypes.size() 
				&& fieldTypes.containsAll(fieldTypesTest) 
				&& fieldTypesTest.containsAll(fieldTypes));
		Collections.sort(methodParameterTypesTest);
		Collections.sort(methodParameterTypes);
		assertTrue(methodParameterTypesTest.size() == methodParameterTypes.size() 
				&& methodParameterTypes.containsAll(methodParameterTypesTest) 
				&& methodParameterTypesTest.containsAll(methodParameterTypes));
		
		//TODO: FIX LIKE THE leafNoreRelationshipsTypes!!!
		
		
		assertEquals("AddLatexCommand", addLatexCommand.getNodeRelationships().get(0).getStartingNode().getName());
		assertEquals("VersionsManager", addLatexCommand.getNodeRelationships().get(0).getEndingNode().getName());
		assertEquals(RelationshipType.DEPENDENCY, addLatexCommand.getNodeRelationships().get(0).getRelationshipType());
		assertEquals("AddLatexCommand", addLatexCommand.getNodeRelationships().get(1).getStartingNode().getName());
		assertEquals("Command", addLatexCommand.getNodeRelationships().get(1).getEndingNode().getName());
		assertEquals(RelationshipType.IMPLEMENTATION, addLatexCommand.getNodeRelationships().get(1).getRelationshipType());
		assertEquals(NodeType.CLASS, addLatexCommand.getType());

		LeafNode commandFactoryLeaf = commandPackage.getLeafNodes().get("CommandFactory");
		assertEquals("CommandFactory", commandFactoryLeaf.getNodeRelationships().get(0).getStartingNode().getName(), "message");
		assertEquals("VersionsManager", commandFactoryLeaf.getNodeRelationships().get(0).getEndingNode().getName(), "message");
		assertEquals(RelationshipType.DEPENDENCY, commandFactoryLeaf.getNodeRelationships().get(0).getRelationshipType(), "message");
		assertEquals("DocumentManager", commandFactoryLeaf.getNodeRelationships().get(1).getEndingNode().getName(), "message");
		assertEquals(RelationshipType.ASSOCIATION, commandFactoryLeaf.getNodeRelationships().get(1).getRelationshipType(), "message");
		assertEquals("Command", commandFactoryLeaf.getNodeRelationships().get(2).getEndingNode().getName(), "message");
		assertEquals(RelationshipType.DEPENDENCY, commandFactoryLeaf.getNodeRelationships().get(2).getRelationshipType(), "message");
	}

	@Test
	void leafNodeRelationshipsTest() throws IOException {
		Parser parser = new ProjectParser();
		parser.parseSourcePackage(Paths.get(currentDirectory.toRealPath() + "\\src\\test\\resources\\LatexEditor\\src"));
		Map<Path, PackageNode> packages = parser.getPackageNodes();
		PackageNode commandPackage = packages.get(Paths.get(currentDirectory.toRealPath().normalize().toString(), "\\src\\test\\resources\\LatexEditor\\src\\controller\\commands"));

		LeafNode addLatexCommand = commandPackage.getLeafNodes().get("AddLatexCommand");

		List<Relationship> pckgRels = addLatexCommand.getNodeRelationships();
		
		///TODO FIX all the get(0), get(1)
		
		boolean foundObligatoryRel = false;
		for(Relationship rel: pckgRels) {
			if((rel.getStartingNode().getName().equals("AddLatexCommand")) && (rel.getEndingNode().getName().equals("VersionsManager")) ) {
				foundObligatoryRel = true;
			}
		}
		assertTrue(foundObligatoryRel);
//		assertEquals("AddLatexCommand", addLatexCommand.getNodeRelationships().get(0).getStartingNode().getName());
//		assertEquals("VersionsManager", addLatexCommand.getNodeRelationships().get(0).getEndingNode().getName());
		assertEquals(RelationshipType.DEPENDENCY, addLatexCommand.getNodeRelationships().get(0).getRelationshipType());
		assertEquals("AddLatexCommand", addLatexCommand.getNodeRelationships().get(1).getStartingNode().getName());
		assertEquals("Command", addLatexCommand.getNodeRelationships().get(1).getEndingNode().getName());
		assertEquals(RelationshipType.IMPLEMENTATION, addLatexCommand.getNodeRelationships().get(1).getRelationshipType());
		assertEquals(NodeType.CLASS, addLatexCommand.getType());

		LeafNode commandFactory = commandPackage.getLeafNodes().get("CommandFactory");

		assertEquals("CommandFactory", commandFactory.getNodeRelationships().get(0).getStartingNode().getName(), "message");
		assertEquals("VersionsManager", commandFactory.getNodeRelationships().get(0).getEndingNode().getName(), "message");
		assertEquals(RelationshipType.DEPENDENCY, commandFactory.getNodeRelationships().get(0).getRelationshipType(), "message");
		assertEquals("DocumentManager", commandFactory.getNodeRelationships().get(1).getEndingNode().getName(), "message");
		assertEquals(RelationshipType.ASSOCIATION, commandFactory.getNodeRelationships().get(1).getRelationshipType(), "message");
		assertEquals("Command", commandFactory.getNodeRelationships().get(2).getEndingNode().getName(), "message");
		assertEquals(RelationshipType.DEPENDENCY, commandFactory.getNodeRelationships().get(2).getRelationshipType(), "message");
	}

	@Test
	void leadNodeInheritanceRelationshipTest() throws IOException {
		Parser parser = new ProjectParser();
		parser.parseSourcePackage(Paths.get(currentDirectory.toRealPath() + "\\src\\test\\resources\\InheritanceTesting\\src"));
		Map<Path, PackageNode> packages = parser.getPackageNodes();
		PackageNode sourcePackage = packages.get(Paths.get(currentDirectory.toRealPath().normalize().toString(), "\\src\\test\\resources\\InheritanceTesting\\src"));
		LeafNode implementingClassLeaf = sourcePackage.getLeafNodes().get("ImplementingClass");
		assertEquals("ImplementingClass", implementingClassLeaf.getNodeRelationships().get(0).getStartingNode().getName());
		assertEquals("TestingInterface2", implementingClassLeaf.getNodeRelationships().get(0).getEndingNode().getName());
		assertEquals(RelationshipType.IMPLEMENTATION, implementingClassLeaf.getNodeRelationships().get(0).getRelationshipType());

		assertEquals("ImplementingClass", implementingClassLeaf.getNodeRelationships().get(1).getStartingNode().getName());
		assertEquals("ExtensionClass", implementingClassLeaf.getNodeRelationships().get(1).getEndingNode().getName());
		assertEquals(RelationshipType.EXTENSION, implementingClassLeaf.getNodeRelationships().get(1).getRelationshipType());

		assertEquals("ImplementingClass", implementingClassLeaf.getNodeRelationships().get(2).getStartingNode().getName());
		assertEquals("TestingInterface", implementingClassLeaf.getNodeRelationships().get(2).getEndingNode().getName());
		assertEquals(RelationshipType.IMPLEMENTATION, implementingClassLeaf.getNodeRelationships().get(2).getRelationshipType());
	}

	@Test
	void packageNodeRelationshipsTest() throws IOException {
		Parser parser = new ProjectParser();
		parser.parseSourcePackage(Paths.get(currentDirectory.toRealPath() + "\\src\\test\\resources\\LatexEditor\\src"));
		Map<Path, PackageNode> packages = parser.getPackageNodes();
		PackageNode commands = packages.get(Paths.get(currentDirectory.toRealPath().normalize().toString(), "\\src\\test\\resources\\LatexEditor\\src\\controller\\commands"));

		assertEquals("src.controller.commands", commands.getNodeRelationships().get(0).getStartingNode().getName());
		assertEquals("src.model", commands.getNodeRelationships().get(0).getEndingNode().getName());
		assertEquals(RelationshipType.DEPENDENCY, commands.getNodeRelationships().get(0).getRelationshipType());

		PackageNode controller = packages.get(Paths.get(currentDirectory.toRealPath().normalize().toString(), "\\src\\test\\resources\\LatexEditor\\src\\controller"));

		assertEquals("src.controller", controller.getNodeRelationships().get(0).getStartingNode().getName());
		assertEquals("src.model", controller.getNodeRelationships().get(0).getEndingNode().getName());
		assertEquals(RelationshipType.DEPENDENCY, controller.getNodeRelationships().get(0).getRelationshipType());
	}

	@Test
	void leafNodeTypesTest() throws IOException {
		Parser parser = new ProjectParser();
		parser.parseSourcePackage(Paths.get(currentDirectory.toRealPath() + "\\src\\test\\resources\\InheritanceTesting\\src"));
		Map<Path, PackageNode> packages = parser.getPackageNodes();
		PackageNode sourcePackage = packages.get(Paths.get(currentDirectory.toRealPath().normalize().toString(), "\\src\\test\\resources\\InheritanceTesting\\src"));
		List<LeafNode> classLeafs = new ArrayList<>();
		List<LeafNode> interfaceLeafs = new ArrayList<>();
		classLeafs.add(sourcePackage.getLeafNodes().get("ImplementingClass"));
		classLeafs.add(sourcePackage.getLeafNodes().get("ExtensionClass"));
		interfaceLeafs.add(sourcePackage.getLeafNodes().get("TestingInterface"));
		interfaceLeafs.add(sourcePackage.getLeafNodes().get("TestingInterface2"));
		for (LeafNode l: classLeafs) {
			assertEquals(NodeType.CLASS, l.getType());
		}
		for (LeafNode l: interfaceLeafs) {
			assertEquals(NodeType.INTERFACE, l.getType());
		}
	}

}

