package compiler.language.conceptual.typeDefinition;

import java.util.HashMap;
import java.util.Map;

import com.sun.org.apache.bcel.internal.classfile.InnerClass;
import compiler.language.ast.member.FieldAST;
import compiler.language.ast.member.MemberAST;
import compiler.language.ast.member.MethodAST;
import compiler.language.ast.misc.ModifierAST;
import compiler.language.ast.terminal.SinceSpecifierAST;
import compiler.language.ast.typeDefinition.ClassDefinitionAST;
import compiler.language.conceptual.ConceptualException;
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
import compiler.language.translator.conceptual.Scope;
import compiler.language.translator.conceptual.ScopedMemberSet;

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
  private MemberVariable[] staticVariables;
  private Constructor[] constructors;
  private Property[] properties;
  private MemberVariable[] variables;
  private VariableInitializers variableInitializers;
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
   * Creates a new ConceptualClass from the specified ClassDefinitionAST.
   * @param classDefinition - the ClassDefinitionAST to base the new ConceptualClass on
   * @return the ConceptualClass created
   * @throws ConceptualException - if there is a problem converting the AST instance to a Conceptual instance
   */
  public static ConceptualClass fromAST(ClassDefinitionAST classDefinition) throws ConceptualException
  {
    AccessSpecifier access = AccessSpecifier.fromAST(classDefinition.getAccess());
    ModifierAST[] modifiers = classDefinition.getModifiers();
    boolean isAbstract = false;
    boolean isFinal = false;
    boolean isImmutable = false;
    SinceSpecifier sinceSpecifier = null;
    for (ModifierAST modifier : modifiers)
    {
      switch (modifier.getType())
      {
      case ABSTRACT:
        isAbstract = true;
        break;
      case FINAL:
        isFinal = true;
        break;
      case IMMUTABLE:
        isImmutable = true;
        break;
      case SINCE_SPECIFIER:
        sinceSpecifier = SinceSpecifier.fromAST((SinceSpecifierAST) modifier);
        break;
      default:
        throw new ConceptualException("Illegal Modifier for a Class Definition", modifier.getParseInfo());
      }
    }

    return new ConceptualClass(access, isAbstract, isFinal, isImmutable, sinceSpecifier, classDefinition.getName().getName());
  }

  /**
   *
   * @param classDefinition
   * @param classScope
   * @throws ConceptualException
   */
  public void buildScope(ClassDefinitionAST classDefinition, Scope classScope) throws ConceptualException
  {
    // TODO: type arguments
    MemberAST[] members = classDefinition.getMembers();
    Map<String, ScopedMemberSet> membersByName = new HashMap<String, ScopedMemberSet>();
    for (MemberAST member : members)
    {
      if (member instanceof FieldAST)
      {
        MemberVariable[] memberVariables = MemberVariable.fromAST((FieldAST) member);
        for (MemberVariable variable : memberVariables)
        {
          ScopedMemberSet scopedMemberSet = membersByName.get(variable.getName());
          if (scopedMemberSet == null)
          {
            scopedMemberSet = new ScopedMemberSet();
            membersByName.put(variable.getName(), scopedMemberSet);
          }
          if (variable.isStatic())
          {
            scopedMemberSet.addStaticVariable(variable);
          }
          else
          {
            scopedMemberSet.addVariable(variable);
          }
        }
      }
      else if (member instanceof MethodAST)
      {

      }
    }
  }

  /**
   * Sets the headers of this conceptual class, including type arguments, base class, and implemented interfaces
   * @param typeArguments
   * @param baseClass
   * @param interfaces
   */
  public void setHeaders(TypeArgument[] typeArguments, PointerType baseClass, PointerType[] interfaces)
  {
    this.typeArguments = typeArguments;
    this.baseClass = baseClass;
    this.interfaces = interfaces;
  }

  /**
   * Sets the members of this conceptual class
   * @param staticInitializer
   * @param staticVariables
   * @param constructors
   * @param properties
   * @param variables
   * @param variableInitializers
   * @param methods
   */
  public void setMembers(StaticInitializer staticInitializer, MemberVariable[] staticVariables, Constructor[] constructors,
                         Property[] properties, MemberVariable[] variables, VariableInitializers variableInitializers, Method[] methods)
  {
    this.staticInitializer = staticInitializer;
    this.staticVariables = staticVariables;
    this.constructors = constructors;
    this.properties = properties;
    this.variables = variables;
    this.variableInitializers = variableInitializers;
    this.methods = methods;
  }

  /**
   * Sets the inner type definitions for this conceptual class.
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
