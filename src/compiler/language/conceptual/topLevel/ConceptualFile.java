package compiler.language.conceptual.topLevel;

import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import compiler.language.conceptual.NameConflictException;
import compiler.language.conceptual.QName;
import compiler.language.conceptual.Resolvable;
import compiler.language.conceptual.ScopeType;
import compiler.language.conceptual.typeDefinition.ConceptualClass;
import compiler.language.conceptual.typeDefinition.ConceptualEnum;
import compiler.language.conceptual.typeDefinition.ConceptualInterface;

/*
 * Created on 13 Feb 2011
 */

/**
 * @author Anthony Bryant
 */
public final class ConceptualFile extends Resolvable
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
   * @return the classes
   */
  public Collection<ConceptualClass> getClasses()
  {
    return classes.values();
  }

  /**
   * @return the interfaces
   */
  public Collection<ConceptualInterface> getInterfaces()
  {
    return interfaces.values();
  }

  /**
   * @return the enums
   */
  public Collection<ConceptualEnum> getEnums()
  {
    return enums.values();
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
   * {@inheritDoc}
   */
  @Override
  public Resolvable resolve(String name) throws NameConflictException
  {
    ConceptualClass conceptualClass = classes.get(name);
    if (conceptualClass != null)
    {
      return conceptualClass;
    }
    ConceptualInterface conceptualInterface = interfaces.get(name);
    if (conceptualInterface != null)
    {
      return conceptualInterface;
    }
    ConceptualEnum conceptualEnum = enums.get(name);
    if (conceptualEnum != null)
    {
      return conceptualEnum;
    }

    // lookup the name in the imports
    for (Import imported : imports)
    {
      QName importedQName = imported.getImportedQName();
      if (imported.isAddChildren())
      {
        Resolvable baseResult = rootPackage.resolve(importedQName, false);
        if (baseResult == null)
        {
          // TODO: handle import failure better
          throw new IllegalStateException("Import resolution failed for: " + importedQName);
        }
        Resolvable wildcardResult = baseResult.resolve(new QName(name), false);
        if (wildcardResult != null)
        {
          return wildcardResult;
        }
      }
      else if (importedQName.getLastName().equals(name))
      {
        Resolvable result = rootPackage.resolve(importedQName, false);
        if (result == null)
        {
          // TODO: handle import failure better
          throw new IllegalStateException("Import resolution failed for: " + importedQName);
        }
        return result;
      }
    }

    return null;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public ScopeType getType()
  {
    return ScopeType.FILE;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  protected Resolvable getParent()
  {
    return enclosingPackage;
  }

}
