package compiler.language.conceptual.typeDefinition;

import compiler.language.conceptual.member.Constructor;
import compiler.language.conceptual.member.MemberVariable;
import compiler.language.conceptual.member.Method;
import compiler.language.conceptual.member.Property;
import compiler.language.conceptual.member.StaticInitializer;
import compiler.language.conceptual.member.VariableInitializers;
import compiler.language.conceptual.misc.AccessSpecifier;
import compiler.language.conceptual.misc.SinceSpecifier;
import compiler.language.conceptual.type.PointerType;
import compiler.language.conceptual.type.TypeArgument;

/*
 * Created on 16 Oct 2010
 */

/**
 * @author Anthony Bryant
 */
public class ConceptualClass
{

  private AccessSpecifier accessSpecifier;
  private boolean isAbstract;
  private boolean isFinal;
  private boolean isImmutable;
  private SinceSpecifier sinceSpecifier;
  private String name;

  // headers
  private TypeArgument[] typeArguments;
  private PointerType baseClass;
  private PointerType[] interfaces;

  // members
  private StaticInitializer staticInitializer;
  private VariableInitializers variableInitializers;
  private MemberVariable[] staticVariables;
  private MemberVariable[] variables;
  private Property[] properties;
  private Constructor[] constructors;
  private Method[] methods;

  private InnerClass[] innerClasses;
  private ConceptualInterface[] innerInterfaces;
  private ConceptualEnum[] innerEnums;

  /**
   * Creates a new Conceptual class with the specified properties.
   * The members of the new class and other data must be set later on.
   * @param accessSpecifier
   * @param isAbstract
   * @param isFinal
   * @param isImmutable
   * @param sinceSpecifier
   * @param name
   */
  public ConceptualClass(AccessSpecifier accessSpecifier, boolean isAbstract, boolean isFinal, boolean isImmutable,
                         SinceSpecifier sinceSpecifier, String name)
  {
    this.accessSpecifier = accessSpecifier;
    this.isAbstract = isAbstract;
    this.isFinal = isFinal;
    this.isImmutable = isImmutable;
    this.sinceSpecifier = sinceSpecifier;
    this.name = name;
  }

  /**
   * Sets the type arguments of this conceptual class
   * @param typeArguments - the new type arguments for this conceptual class
   */
  public void setTypeArguments(TypeArgument[] typeArguments)
  {
    this.typeArguments = typeArguments;
  }

  /**
   * Sets the members of this conceptual class
   * @param staticInitializer
   * @param variableInitializers
   * @param staticVariables
   * @param variables
   * @param properties
   * @param constructors
   * @param methods
   * @param innerClasses
   * @param innerInterfaces
   * @param innerEnums
   */
  public void setMembers(StaticInitializer staticInitializer, VariableInitializers variableInitializers,
                         MemberVariable[] staticVariables, MemberVariable[] variables,
                         Property[] properties, Constructor[] constructors, Method[] methods,
                         InnerClass[] innerClasses, ConceptualInterface[] innerInterfaces, ConceptualEnum[] innerEnums)
  {
    this.staticInitializer = staticInitializer;
    this.variableInitializers = variableInitializers;
    this.staticVariables = staticVariables;
    this.variables = variables;
    this.properties = properties;
    this.constructors = constructors;
    this.methods = methods;
    this.innerClasses = innerClasses;
    this.innerInterfaces = innerInterfaces;
    this.innerEnums = innerEnums;
  }

  /**
   * @return the accessSpecifier
   */
  public AccessSpecifier getAccessSpecifier()
  {
    return accessSpecifier;
  }
  /**
   * @return the isAbstract
   */
  public boolean isAbstract()
  {
    return isAbstract;
  }
  /**
   * @return the isFinal
   */
  public boolean isFinal()
  {
    return isFinal;
  }
  /**
   * @return the isImmutable
   */
  public boolean isImmutable()
  {
    return isImmutable;
  }
  /**
   * @return the sinceSpecifier
   */
  public SinceSpecifier getSinceSpecifier()
  {
    return sinceSpecifier;
  }
  /**
   * @return the name
   */
  public String getName()
  {
    return name;
  }
  /**
   * @return the typeArguments
   */
  public TypeArgument[] getTypeArguments()
  {
    return typeArguments;
  }
  /**
   * @return the baseClass
   */
  public PointerType getBaseClass()
  {
    return baseClass;
  }
  /**
   * @return the interfaces
   */
  public PointerType[] getInterfaces()
  {
    return interfaces;
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
