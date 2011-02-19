package compiler.language.conceptual.topLevel;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import compiler.language.conceptual.NameConflictException;
import compiler.language.conceptual.QName;
import compiler.language.conceptual.ScopeType;
import compiler.language.conceptual.ScopedResult;
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

  private ConceptualPackage rootPackage;
  private ConceptualPackage enclosingPackage;

  private List<Import> imports;

  private Map<String, ConceptualClass> classes = new HashMap<String, ConceptualClass>();
  private Map<String, ConceptualInterface> interfaces = new HashMap<String, ConceptualInterface>();
  private Map<String, ConceptualEnum> enums = new HashMap<String, ConceptualEnum>();

  /**
   * Creates a new ConceptualFile with the specified enclosing package, imports and sets of classes, interfaces and enums
   * @param rootPackage - the root package
   * @param enclosingPackage - the package that this file is defined in
   * @param imports - the imports for this conceptual file
   */
  public ConceptualFile(ConceptualPackage rootPackage, ConceptualPackage enclosingPackage, List<Import> imports)
  {
    this.rootPackage = rootPackage;
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

  /**
   * Resolves the specified name in this file.
   * @param name - the name to resolve
   * @return the result of resolving the specified name, or null if nothing could be resolved
   * @throws NameConflictException - if a name conflict is detected while trying to resolve the name
   */
  private ScopedResult resolve(String name) throws NameConflictException
  {
    ConceptualClass conceptualClass = classes.get(name);
    if (conceptualClass != null)
    {
      return new ScopedResult(ScopeType.CLASS, conceptualClass);
    }
    ConceptualInterface conceptualInterface = interfaces.get(name);
    if (conceptualInterface != null)
    {
      return new ScopedResult(ScopeType.INTERFACE, conceptualInterface);
    }
    ConceptualEnum conceptualEnum = enums.get(name);
    if (conceptualEnum != null)
    {
      return new ScopedResult(ScopeType.ENUM, conceptualEnum);
    }

    // lookup the name in the imports
    for (Import imported : imports)
    {
      QName importedQName = imported.getImportedQName();
      if (imported.isAddChildren())
      {
        ScopedResult baseResult = rootPackage.resolve(importedQName, false);
        if (baseResult == null)
        {
          // TODO: handle import failure better
          throw new IllegalStateException("Import resolution failed for: " + importedQName);
        }
        ScopedResult wildcardResult = baseResult.resolve(new QName(name));
        if (wildcardResult != null)
        {
          return wildcardResult;
        }
      }
      else if (importedQName.getLastName().equals(name))
      {
        ScopedResult result = rootPackage.resolve(importedQName, false);
        if (result == null)
        {
          // TODO: handle import failure better
          throw new IllegalStateException("Import resolution failed for: " + importedQName);
        }
      }
    }

    return null;
  }

  /**
   * Resolves the specified QName from this file.
   * @param name - the QName to resolve
   * @param recurseUpwards - true to recurse back to the parent package if there are no results in this file, false to just return null in this scenario
   * @return the result of resolving the QName, as a ScopedResult, or null if the name could not be resolved
   * @throws NameConflictException - if a name conflict is detected while trying to resolve the name
   */
  public ScopedResult resolve(QName name, boolean recurseUpwards) throws NameConflictException
  {
    String first = name.getFirstName();
    ScopedResult result = resolve(first);
    if (result != null)
    {
      if (name.getLength() == 1)
      {
        return result;
      }
      return result.resolve(name.getTrailingNames());
    }
    if (recurseUpwards)
    {
      return enclosingPackage.resolve(name, true);
    }
    return null;
  }

}
