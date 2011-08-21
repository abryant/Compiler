package compiler.language.conceptual.member;

import compiler.language.conceptual.ConceptualException;
import compiler.language.conceptual.NameConflictException;
import compiler.language.conceptual.Resolvable;
import compiler.language.conceptual.ScopeType;
import compiler.language.conceptual.misc.AccessSpecifier;
import compiler.language.conceptual.misc.NativeSpecifier;
import compiler.language.conceptual.misc.ParameterList;
import compiler.language.conceptual.misc.SinceSpecifier;
import compiler.language.conceptual.statement.Statement;
import compiler.language.conceptual.type.PointerType;
import compiler.language.conceptual.type.Type;
import compiler.language.conceptual.type.TypeParameter;
import compiler.language.conceptual.typeDefinition.TypeDefinition;

/*
 * Created on 21 Oct 2010
 */

/**
 * @author Anthony Bryant
 */
public class Method extends Resolvable
{
  private TypeDefinition enclosingTypeDefinition;

  private AccessSpecifier accessSpecifier;
  private boolean isAbstract;
  private boolean isSealed;
  private boolean isStatic;
  private boolean isSynchronized;
  private boolean isImmutable;
  private SinceSpecifier sinceSpecifier;
  private NativeSpecifier nativeSpecifier;
  private TypeParameter[] typeParameters;
  private String name;
  private ParameterList parameters;
  private Type returnType;
  private PointerType[] thrownTypes;
  private Statement[] statements;

  /**
   * Creates a new Method with the specified parameters
   * @param enclosingTypeDefinition - the enclosing type definition
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
  public Method(TypeDefinition enclosingTypeDefinition, AccessSpecifier accessSpecifier, boolean isAbstract, boolean isSealed, boolean isStatic,
                boolean isSynchronized, boolean isImmutable, SinceSpecifier sinceSpecifier, NativeSpecifier nativeSpecifier, String name)
  {
    this.enclosingTypeDefinition = enclosingTypeDefinition;
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
   * Sets the header of this Method, which includes all information that is not populated by the constructor and is not part of the method body.
   * @param typeParameters - the type parameters
   * @param parameters - the parameters
   * @param returnType - the return type
   * @param thrownTypes - the thrown types
   */
  public void setHeader(TypeParameter[] typeParameters, ParameterList parameters, Type returnType, PointerType[] thrownTypes)
  {
    this.typeParameters = typeParameters;
    this.parameters = parameters;
    this.returnType = returnType;
    this.thrownTypes = thrownTypes;
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
   * @return the typeParameters
   */
  public TypeParameter[] getTypeParameters()
  {
    return typeParameters;
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
  public ParameterList getParameters()
  {
    return parameters;
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

  /**
   * {@inheritDoc}
   */
  @Override
  public ScopeType getType()
  {
    return ScopeType.METHOD;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public Resolvable resolve(String name) throws NameConflictException, ConceptualException
  {
    // TODO: implement
    return null;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public Resolvable getParent()
  {
    return enclosingTypeDefinition;
  }



}
