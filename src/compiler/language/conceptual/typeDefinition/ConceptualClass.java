package compiler.language.conceptual.typeDefinition;

import compiler.language.conceptual.Resolvable;
import compiler.language.conceptual.member.Constructor;
import compiler.language.conceptual.member.MemberVariable;
import compiler.language.conceptual.member.Method;
import compiler.language.conceptual.member.Property;
import compiler.language.conceptual.member.StaticInitializer;
import compiler.language.conceptual.member.VariableInitializers;
import compiler.language.conceptual.misc.AccessSpecifier;
import compiler.language.conceptual.misc.SinceSpecifier;
import compiler.language.conceptual.type.ClassPointerType;
import compiler.language.conceptual.type.InterfacePointerType;
import compiler.language.conceptual.type.TypeParameter;

/*
 * Created on 16 Oct 2010
 */

/**
 * @author Anthony Bryant
 */
public abstract class ConceptualClass extends TypeDefinition
{

  private AccessSpecifier accessSpecifier;
  private boolean isAbstract;
  private boolean isSealed;
  private boolean isImmutable;
  private SinceSpecifier sinceSpecifier;
  private String name;

  // headers
  private TypeParameter[] typeParameters;
  private ClassPointerType baseClass;
  private InterfacePointerType[] interfaces;

  // members
  private StaticInitializer staticInitializer;
  private VariableInitializers variableInitializers;
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
   * @param isSealed
   * @param isImmutable
   * @param sinceSpecifier
   * @param name
   */
  public ConceptualClass(AccessSpecifier accessSpecifier, boolean isAbstract, boolean isSealed, boolean isImmutable,
                         SinceSpecifier sinceSpecifier, String name)
  {
    this.accessSpecifier = accessSpecifier;
    this.isAbstract = isAbstract;
    this.isSealed = isSealed;
    this.isImmutable = isImmutable;
    this.sinceSpecifier = sinceSpecifier;
    this.name = name;
  }

  /**
   * Sets the base class of this conceptual class
   * @param baseClass - the new base class of this conceptual class
   */
  public void setBaseClass(ClassPointerType baseClass)
  {
    this.baseClass = baseClass;
  }

  /**
   * Sets the interfaces of this conceptual class
   * @param interfaces - the implemented interfaces of this conceptual class
   */
  public void setInterfaces(InterfacePointerType[] interfaces)
  {
    this.interfaces = interfaces;
  }

  /**
   * Sets the type parameters of this conceptual class
   * @param typeParameters - the new type parameters for this conceptual class
   */
  public void setTypeParameters(TypeParameter[] typeParameters)
  {
    this.typeParameters = typeParameters;
  }

  /**
   * Sets the members of this conceptual class
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
                         MemberVariable[] variables, Property[] properties, Constructor[] constructors, Method[] methods,
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
   * @return the isSealed
   */
  public boolean isSealed()
  {
    return isSealed;
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
   * @return the typeParameters
   */
  public TypeParameter[] getTypeParameters()
  {
    return typeParameters;
  }
  /**
   * @return the baseClass
   */
  public ClassPointerType getBaseClass()
  {
    return baseClass;
  }
  /**
   * @return the interfaces
   */
  public InterfacePointerType[] getInterfaces()
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

  /**
   * {@inheritDoc}
   */
  @Override
  public final Resolvable resolve(String name)
  {
    for (TypeParameter typeParameter : typeParameters)
    {
      if (typeParameter.getName().equals(name))
      {
        return typeParameter;
      }
    }
    for (MemberVariable variable : variables)
    {
      if (variable.getName().equals(name))
      {
        return variable;
      }
    }
    for (Property property : properties)
    {
      if (property.getName().equals(name))
      {
        return property;
      }
    }
    // TODO: how will methods work here? there can be multiple methods with one name
    for (InnerClass innerClass : innerClasses)
    {
      if (innerClass.getName().equals(name))
      {
        return innerClass;
      }
    }
    for (ConceptualInterface innerInterface : innerInterfaces)
    {
      if (innerInterface.getName().equals(name))
      {
        return innerInterface;
      }
    }
    for (ConceptualEnum innerEnum : innerEnums)
    {
      if (innerEnum.getName().equals(name))
      {
        return innerEnum;
      }
    }
    if (baseClass != null)
    {
      Resolvable result = baseClass.getClassType().resolve(name);
      if (result != null)
      {
        return result;
      }
    }
    if (interfaces != null)
    {
      for (InterfacePointerType type : interfaces)
      {
        if (type == null)
        {
          // the type has not yet been populated, so go on to the next one
          continue;
        }
        Resolvable result = type.getInterfaceType().resolve(name);
        if (result != null)
        {
          return result;
        }
      }
    }
    return null;
  }

}
