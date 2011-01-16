package compiler.language.conceptual.typeDefinition;

import compiler.language.ast.typeDefinition.EnumConstantAST;
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
  private MemberVariable[] staticVariables;
  private Constructor[] constructors;
  private Property[] properties;
  private MemberVariable[] memberVariables;
  private VariableInitializers variableInitializers;
  private Method[] methods;

  private InnerClass[] innerClasses;
  private ConceptualInterface[] innerInterfaces;
  private ConceptualEnum[] innerEnums;

  /**
   * Creates a new Enum Constant with the specified properties.
   * The parameters, members, and any other data must be set later on.
   * @param name
   */
  public EnumConstant(String name)
  {
    this.name = name;
  }

  /**
   * Creates a new EnumConstant from the specified EnumConstantAST.
   * @param enumConstantAST - the EnumConstantAST to base the new EnumConstant on
   * @return the EnumConstant created
   */
  public static EnumConstant fromAST(EnumConstantAST enumConstantAST)
  {
    return new EnumConstant(enumConstantAST.getName().getName());
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
   * @param staticVariables
   * @param constructors
   * @param properties
   * @param memberVariables
   * @param variableInitializers
   * @param methods
   */
  public void setMembers(StaticInitializer staticInitializer, MemberVariable[] staticVariables,
                         Constructor[] constructors, Property[] properties, MemberVariable[] memberVariables,
                         VariableInitializers variableInitializers, Method[] methods)
  {
    this.staticInitializer = staticInitializer;
    this.staticVariables = staticVariables;
    this.constructors = constructors;
    this.properties = properties;
    this.memberVariables = memberVariables;
    this.variableInitializers = variableInitializers;
    this.methods = methods;
  }

  /**
   * Sets the inner type definitions of this enum constant.
   * @param innerClasses
   * @param innerInterfaces
   * @param innerEnums
   */
  public void setInnerTypeDefinitions(InnerClass[] innerClasses, ConceptualInterface[] innerInterfaces, ConceptualEnum[] innerEnums)
  {
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
   * @return the staticVariables
   */
  public MemberVariable[] getStaticVariables()
  {
    return staticVariables;
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
   * @return the memberVariables
   */
  public MemberVariable[] getMemberVariables()
  {
    return memberVariables;
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
