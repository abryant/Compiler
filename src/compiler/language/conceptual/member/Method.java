package compiler.language.conceptual.member;

import compiler.language.ast.member.MethodAST;
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
  private boolean isFinal;
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
   * @param isFinal
   * @param isStatic
   * @param isSynchronized
   * @param isImmutable
   * @param sinceSpecifier
   * @param nativeSpecifier
   * @param name
   */
  public Method(AccessSpecifier accessSpecifier, boolean isAbstract, boolean isFinal, boolean isStatic, boolean isSynchronized,
                boolean isImmutable, SinceSpecifier sinceSpecifier, NativeSpecifier nativeSpecifier, String name)
  {
    this.accessSpecifier = accessSpecifier;
    this.isAbstract = isAbstract;
    this.isFinal = isFinal;
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
  public static Method fromAST(MethodAST methodAST)
  {
    AccessSpecifier accessSpecifier = AccessSpecifier.fromAST(methodAST.getAccessSpecifier());
    // TODO: finish
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
