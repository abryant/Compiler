package compiler.language.conceptual.member;

import compiler.language.ast.member.MethodAST;
import compiler.language.ast.misc.ModifierAST;
import compiler.language.ast.misc.NativeSpecifierAST;
import compiler.language.ast.terminal.SinceSpecifierAST;
import compiler.language.conceptual.ConceptualException;
import compiler.language.conceptual.misc.AccessSpecifier;
import compiler.language.conceptual.misc.Argument;
import compiler.language.conceptual.misc.NativeSpecifier;
import compiler.language.conceptual.misc.SinceSpecifier;
import compiler.language.conceptual.statement.Statement;
import compiler.language.conceptual.type.PointerType;
import compiler.language.conceptual.type.Type;
import compiler.language.conceptual.type.TypeArgument;

/*
 * Created on 21 Oct 2010
 */

/**
 * @author Anthony Bryant
 */
public class Method
{

  private AccessSpecifier accessSpecifier;
  private boolean isAbstract;
  private boolean isSealed;
  private boolean isStatic;
  private boolean isSynchronized;
  private boolean isImmutable;
  private SinceSpecifier sinceSpecifier;
  private NativeSpecifier nativeSpecifier;
  private TypeArgument[] typeArguments;
  private String name;
  private Argument[] arguments;
  private Type returnType;
  private PointerType[] thrownTypes;
  private Statement[] statements;

  /**
   * Creates a new Method with the specified parameters
   * @param accessSpecifier
   * @param isAbstract
   * @param isSealed
   * @param isStatic
   * @param isSynchronized
   * @param isImmutable
   * @param sinceSpecifier
   * @param nativeSpecifier
   * @param name
   */
  public Method(AccessSpecifier accessSpecifier, boolean isAbstract, boolean isSealed, boolean isStatic, boolean isSynchronized,
                boolean isImmutable, SinceSpecifier sinceSpecifier, NativeSpecifier nativeSpecifier, String name)
  {
    this.accessSpecifier = accessSpecifier;
    this.isAbstract = isAbstract;
    this.isSealed = isSealed;
    this.isStatic = isStatic;
    this.isSynchronized = isSynchronized;
    this.isImmutable = isImmutable;
    this.sinceSpecifier = sinceSpecifier;
    this.nativeSpecifier = nativeSpecifier;
    this.name = name;
  }

  /**
   * Creates a Method from the specified MethodAST.
   * @param methodAST - the MethodAST to base the new Method on
   * @return the Method created
   * @throws ConceptualException - if there is a problem converting the AST instance to a Conceptual instance
   */
  public static Method fromAST(MethodAST methodAST) throws ConceptualException
  {
    AccessSpecifier accessSpecifier = AccessSpecifier.fromAST(methodAST.getAccessSpecifier());
    boolean isAbstract = false;
    boolean isSealed = false;
    boolean isStatic = false;
    boolean isSynchronized = false;
    boolean isImmutable = false;
    SinceSpecifier sinceSpecifier = null;
    NativeSpecifier nativeSpecifier = null;
    ModifierAST[] modifiers = methodAST.getModifiers();
    for (ModifierAST modifier : modifiers)
    {
      switch (modifier.getType())
      {
      case ABSTRACT:
        isAbstract = true;
        break;
      case SEALED:
        isSealed = true;
        break;
      case IMMUTABLE:
        isImmutable = true;
        break;
      case NATIVE_SPECIFIER:
        nativeSpecifier = NativeSpecifier.fromAST((NativeSpecifierAST) modifier);
        break;
      case SINCE_SPECIFIER:
        sinceSpecifier = SinceSpecifier.fromAST((SinceSpecifierAST) modifier);
        break;
      case STATIC:
        isStatic = true;
        break;
      case SYNCHRONIZED:
        isSynchronized = true;
        break;
      default:
        throw new ConceptualException("Illegal Modifier for a Method", modifier.getParseInfo());
      }
    }
    return new Method(accessSpecifier, isAbstract, isSealed, isStatic, isSynchronized, isImmutable, sinceSpecifier, nativeSpecifier, methodAST.getName().getName());
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
   * @return the isStatic
   */
  public boolean isStatic()
  {
    return isStatic;
  }

  /**
   * @return the isSynchronized
   */
  public boolean isSynchronized()
  {
    return isSynchronized;
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
   * @return the nativeSpecifier
   */
  public NativeSpecifier getNativeSpecifier()
  {
    return nativeSpecifier;
  }

  /**
   * @return the typeArguments
   */
  public TypeArgument[] getTypeArguments()
  {
    return typeArguments;
  }

  /**
   * @return the name
   */
  public String getName()
  {
    return name;
  }

  /**
   * @return the arguments
   */
  public Argument[] getArguments()
  {
    return arguments;
  }

  /**
   * @return the returnType
   */
  public Type getReturnType()
  {
    return returnType;
  }

  /**
   * @return the thrownTypes
   */
  public PointerType[] getThrownTypes()
  {
    return thrownTypes;
  }

  /**
   * @return the statements
   */
  public Statement[] getStatements()
  {
    return statements;
  }



}
