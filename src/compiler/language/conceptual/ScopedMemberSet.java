package compiler.language.conceptual;

import java.util.HashSet;
import java.util.Set;

import compiler.language.conceptual.member.MemberVariable;
import compiler.language.conceptual.member.Method;
import compiler.language.conceptual.member.Property;
import compiler.language.conceptual.typeDefinition.ConceptualEnum;
import compiler.language.conceptual.typeDefinition.ConceptualInterface;
import compiler.language.conceptual.typeDefinition.InnerClass;

/*
 * Created on 16 Jan 2011
 */

/**
 * This class represents a set of members in a type definition which have the same name.
 * @author Anthony Bryant
 */
public class ScopedMemberSet
{

  private Set<MemberVariable> staticVariables;
  private Set<MemberVariable> variables;
  private Set<Property> properties;
  private Set<Method> methods;

  private Set<InnerClass> innerClasses;
  private Set<ConceptualInterface> innerInterfaces;
  private Set<ConceptualEnum> innerEnums;

  /**
   * Adds all of the members in the specified other set to this set
   * @param otherSet - the other ScopedMemberSet to get the members from
   */
  public void addAll(ScopedMemberSet otherSet)
  {
    if (otherSet.staticVariables != null)
    {
      for (MemberVariable staticVariable : otherSet.staticVariables)
      {
        addStaticVariable(staticVariable);
      }
    }
    if (otherSet.variables != null)
    {
      for (MemberVariable variable : otherSet.variables)
      {
        addVariable(variable);
      }
    }
    if (otherSet.properties != null)
    {
      for (Property property : otherSet.properties)
      {
        addProperty(property);
      }
    }
    if (otherSet.methods != null)
    {
      for (Method method : otherSet.methods)
      {
        addMethod(method);
      }
    }
    if (otherSet.innerClasses != null)
    {
      for (InnerClass innerClass : otherSet.innerClasses)
      {
        addInnerClass(innerClass);
      }
    }
    if (otherSet.innerInterfaces != null)
    {
      for (ConceptualInterface innerInterface : otherSet.innerInterfaces)
      {
        addInnerInterface(innerInterface);
      }
    }
    if (otherSet.innerEnums != null)
    {
      for (ConceptualEnum innerEnum : otherSet.innerEnums)
      {
        addInnerEnum(innerEnum);
      }
    }
  }

  /**
   * Adds the specified static variable to this ScopedMemberSet
   * @param staticVariable - the static variable to add
   */
  public void addStaticVariable(MemberVariable staticVariable)
  {
    if (staticVariables == null)
    {
      staticVariables = new HashSet<MemberVariable>();
    }
    staticVariables.add(staticVariable);
  }

  /**
   * Adds the specified non-static variable to this ScopedMemberSet
   * @param variable - the non-static variable to add
   */
  public void addVariable(MemberVariable variable)
  {
    if (variables == null)
    {
      variables = new HashSet<MemberVariable>();
    }
    variables.add(variable);
  }

  /**
   * Adds the specified property to this ScopedMemberSet
   * @param property - the property to add
   */
  public void addProperty(Property property)
  {
    if (properties == null)
    {
      properties = new HashSet<Property>();
    }
    properties.add(property);
  }

  /**
   * Adds the specified method to this ScopedMemberSet
   * @param method - the method to add
   */
  public void addMethod(Method method)
  {
    if (methods == null)
    {
      methods = new HashSet<Method>();
    }
    methods.add(method);
  }

  /**
   * Adds the specified inner class to this ScopedMemberSet
   * @param innerClass - the inner class to add
   */
  public void addInnerClass(InnerClass innerClass)
  {
    if (innerClasses == null)
    {
      innerClasses = new HashSet<InnerClass>();
    }
    innerClasses.add(innerClass);
  }

  /**
   * Adds the specified inner interface to this ScopedMemberSet
   * @param innerInterface - the inner interface to add
   */
  public void addInnerInterface(ConceptualInterface innerInterface)
  {
    if (innerInterfaces == null)
    {
      innerInterfaces = new HashSet<ConceptualInterface>();
    }
    innerInterfaces.add(innerInterface);
  }

  /**
   * Adds the specified inner enum to this ScopedMemberSet
   * @param innerEnum - the inner enum to add
   */
  public void addInnerEnum(ConceptualEnum innerEnum)
  {
    if (innerEnums == null)
    {
      innerEnums = new HashSet<ConceptualEnum>();
    }
    innerEnums.add(innerEnum);
  }

  /**
   * @return the staticVariables, or null if there are none
   */
  public Set<MemberVariable> getStaticVariables()
  {
    return staticVariables;
  }
  /**
   * @return the variables, or null if there are none
   */
  public Set<MemberVariable> getVariables()
  {
    return variables;
  }
  /**
   * @return the properties, or null if there are none
   */
  public Set<Property> getProperties()
  {
    return properties;
  }
  /**
   * @return the methods, or null if there are none
   */
  public Set<Method> getMethods()
  {
    return methods;
  }
  /**
   * @return the innerClasses, or null if there are none
   */
  public Set<InnerClass> getInnerClasses()
  {
    return innerClasses;
  }
  /**
   * @return the innerInterfaces, or null if there are none
   */
  public Set<ConceptualInterface> getInnerInterfaces()
  {
    return innerInterfaces;
  }
  /**
   * @return the innerEnums, or null if there are none
   */
  public Set<ConceptualEnum> getInnerEnums()
  {
    return innerEnums;
  }

}
