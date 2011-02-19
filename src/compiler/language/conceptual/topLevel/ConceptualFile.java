package compiler.language.conceptual.topLevel;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import compiler.language.conceptual.typeDefinition.ConceptualClass;
import compiler.language.conceptual.typeDefinition.ConceptualEnum;
import compiler.language.conceptual.typeDefinition.ConceptualInterface;

/*
 * Created on 13 Feb 2011
 */

/**
 * @author Anthony Bryant
 */
public class ConceptualFile
{

  private ConceptualPackage enclosingPackage;

  private List<Import> imports;

  private Map<String, ConceptualClass> classes = new HashMap<String, ConceptualClass>();
  private Map<String, ConceptualInterface> interfaces = new HashMap<String, ConceptualInterface>();
  private Map<String, ConceptualEnum> enums = new HashMap<String, ConceptualEnum>();

  /**
   * Creates a new ConceptualFile with the specified enclosing package, imports and sets of classes, interfaces and enums
   * @param enclosingPackage - the package that this file is defined in
   * @param imports - the imports for this conceptual file
   */
  public ConceptualFile(ConceptualPackage enclosingPackage, List<Import> imports)
  {
    this.enclosingPackage = enclosingPackage;
    this.imports = imports;
  }

  /**
   * Sets all of the type definitions in this file
   * @param classes - the classes to store
   * @param interfaces - the interfaces to store
   * @param enums - the enums to store
   */
  public void setTypes(Set<ConceptualClass> classes, Set<ConceptualInterface> interfaces, Set<ConceptualEnum> enums)
  {
    for (ConceptualClass conceptualClass : classes)
    {
      this.classes.put(conceptualClass.getName(), conceptualClass);
    }
    for (ConceptualInterface conceptualInterface : interfaces)
    {
      this.interfaces.put(conceptualInterface.getName(), conceptualInterface);
    }
    for (ConceptualEnum conceptualEnum : enums)
    {
      this.enums.put(conceptualEnum.getName(), conceptualEnum);
    }
  }

  /**
   * @return the enclosingPackage
   */
  public ConceptualPackage getEnclosingPackage()
  {
    return enclosingPackage;
  }

  /**
   * @return the imports
   */
  public List<Import> getImports()
  {
    return imports;
  }

  /**
   * @return an array containing all of the type names in this conceptual file
   */
  public String[] getDeclaredTypeNames()
  {
    List<String> names = new LinkedList<String>();
    for (String className : classes.keySet())
    {
      names.add(className);
    }
    for (String interfaceName : interfaces.keySet())
    {
      names.add(interfaceName);
    }
    for (String enumName : enums.keySet())
    {
      names.add(enumName);
    }
    return names.toArray(new String[names.size()]);
  }

  /**
   * Finds the class with the specified name.
   * @param name - the name of the class to get
   * @return the class with the specified name, or null if none exists
   */
  public ConceptualClass getClass(String name)
  {
    return classes.get(name);
  }

  /**
   * Finds the interface with the specified name.
   * @param name - the name of the interface to get
   * @return the interface with the specified name, or null if none exists
   */
  public ConceptualInterface getInterface(String name)
  {
    return interfaces.get(name);
  }

  /**
   * Finds the enum with the specified name.
   * @param name - the name of the enum to get
   * @return the enum with the specified name, or null if none exists
   */
  public ConceptualEnum getEnum(String name)
  {
    return enums.get(name);
  }



}
