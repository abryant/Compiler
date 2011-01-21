package compiler.language.conceptual;

import java.util.HashSet;
import java.util.Set;

import compiler.language.conceptual.typeDefinition.ConceptualClass;
import compiler.language.conceptual.typeDefinition.ConceptualEnum;
import compiler.language.conceptual.typeDefinition.ConceptualInterface;

/*
 * Created on 19 Jan 2011
 */

/**
 * @author Anthony Bryant
 */
public class ConceptualProgram
{
  private Set<ConceptualClass> classes = new HashSet<ConceptualClass>();
  private Set<ConceptualInterface> interfaces = new HashSet<ConceptualInterface>();
  private Set<ConceptualEnum> enums = new HashSet<ConceptualEnum>();

  private Scope scope;

  /**
   * Creates a new ConceptualProgram.
   * @param scope - the root scope
   */
  public ConceptualProgram(Scope scope)
  {
    this.scope = scope;
  }

  /**
   * Adds the specified class to this ConceptualProgram
   * @param newClass - the class to add
   */
  public void addClass(ConceptualClass newClass)
  {
    classes.add(newClass);
  }

  /**
   * Adds the specified interface to this ConceptualProgram
   * @param newInterface - the interface to add
   */
  public void addInterface(ConceptualInterface newInterface)
  {
    interfaces.add(newInterface);
  }

  /**
   * Adds the specified enum to this ConceptualProgram
   * @param newEnum - the enum to add
   */
  public void addEnum(ConceptualEnum newEnum)
  {
    enums.add(newEnum);
  }

  /**
   * @return the root scope for this program
   */
  public Scope getRootScope()
  {
    return scope;
  }

  /**
   * @return the classes
   */
  public Set<ConceptualClass> getClasses()
  {
    return classes;
  }

  /**
   * @return the interfaces
   */
  public Set<ConceptualInterface> getInterfaces()
  {
    return interfaces;
  }

  /**
   * @return the enums
   */
  public Set<ConceptualEnum> getEnums()
  {
    return enums;
  }

}
