package compiler.language.conceptual.typeDefinition;

import compiler.language.conceptual.member.Constructor;
import compiler.language.conceptual.member.MemberVariable;
import compiler.language.conceptual.member.Method;
import compiler.language.conceptual.member.Property;
import compiler.language.conceptual.member.StaticInitializer;
import compiler.language.conceptual.member.VariableInitializers;
import compiler.language.conceptual.misc.Parameter;

/*
 * Created on 31 Oct 2010
 */

/**
 * @author Anthony Bryant
 */
public class EnumConstant
{
  private String name;
  private Parameter[] parameters;

  // members
  private StaticInitializer staticInitializer;
  private Constructor[] constructors;
  private Property[] properties;
  private MemberVariable[] variables;
  private VariableInitializers variableInitializers;
  private Method[] methods;

  private InnerClass[] innerClasses;
  private ConceptualInterface[] innerInterfaces;
  private ConceptualEnum[] innerEnums;

  /**
   * Creates a new Enum Constant with the specified properties.
   * The parameters, members, and any other data must be set later on.
   * @param enclosingEnum - the enclosing enum
   * @param name
   */
  public EnumConstant(String name)
  {
    this.name = name;
  }

  /**
   * Sets the parameters of this enum constant.
   * @param parameters
   */
  public void setParameters(Parameter[] parameters)
  {
    this.parameters = parameters;
  }

  /**
   * Sets the members of this enum constant.
   * @param staticInitializer
   * @param variableInitializers
   * @param variables
   * @param properties
   * @param constructors
   * @param methods
   * @param innerClasses
   * @param innerInterfaces
   * @param innerEnums
   */
  public void setMembers(StaticInitializer staticInitializer, VariableInitializers variableInitializers,
                         MemberVariable[] variables, Property[] properties,
                         Constructor[] constructors, Method[] methods,
                         InnerClass[] innerClasses, ConceptualInterface[] innerInterfaces, ConceptualEnum[] innerEnums)
  {
    this.staticInitializer = staticInitializer;
    this.variableInitializers = variableInitializers;
    this.variables = variables;
    this.properties = properties;
    this.constructors = constructors;
    this.methods = methods;
    this.innerClasses = innerClasses;
    this.innerInterfaces = innerInterfaces;
    this.innerEnums = innerEnums;
  }

  /**
   * @return the name
   */
  public String getName()
  {
    return name;
  }
  /**
   * @return the parameters
   */
  public Parameter[] getParameters()
  {
    return parameters;
  }
  /**
   * @return the staticInitializer
   */
  public StaticInitializer getStaticInitializer()
  {
    return staticInitializer;
  }
  /**
   * @return the constructors
   */
  public Constructor[] getConstructors()
  {
    return constructors;
  }
  /**
   * @return the properties
   */
  public Property[] getProperties()
  {
    return properties;
  }
  /**
   * @return the variables
   */
  public MemberVariable[] getVariables()
  {
    return variables;
  }
  /**
   * @return the variableInitializers
   */
  public VariableInitializers getVariableInitializers()
  {
    return variableInitializers;
  }
  /**
   * @return the methods
   */
  public Method[] getMethods()
  {
    return methods;
  }
  /**
   * @return the innerClasses
   */
  public InnerClass[] getInnerClasses()
  {
    return innerClasses;
  }
  /**
   * @return the innerInterfaces
   */
  public ConceptualInterface[] getInnerInterfaces()
  {
    return innerInterfaces;
  }
  /**
   * @return the innerEnums
   */
  public ConceptualEnum[] getInnerEnums()
  {
    return innerEnums;
  }

}
