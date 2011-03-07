package compiler.language.conceptual.member;

import compiler.language.conceptual.NameConflictException;
import compiler.language.conceptual.Resolvable;
import compiler.language.conceptual.ScopeType;
import compiler.language.conceptual.misc.AccessSpecifier;
import compiler.language.conceptual.misc.SinceSpecifier;
import compiler.language.conceptual.type.Type;
import compiler.language.conceptual.typeDefinition.TypeDefinition;

/*
 * Created on 21 Oct 2010
 */

/**
 * @author Anthony Bryant
 */
public class MemberVariable extends Resolvable
{

  private TypeDefinition enclosingTypeDefinition;

  private AccessSpecifier accessSpecifier;
  private boolean isFinal;
  private boolean isMutable;
  private boolean isStatic;
  private boolean isVolatile;
  private boolean isTransient;
  private SinceSpecifier sinceSpecifier;

  private Type type;

  private String name;

  /**
   * Creates a new MemberVariable with the specified parameters.
   * @param enclosingTypeDefinition - the enclosing type definition
   * @param accessSpecifier
   * @param isFinal
   * @param isMutable
   * @param isStatic
   * @param isVolatile
   * @param isTransient
   * @param sinceSpecifier
   * @param name
   */
  public MemberVariable(TypeDefinition enclosingTypeDefinition, AccessSpecifier accessSpecifier, boolean isFinal, boolean isMutable, boolean isStatic, boolean isVolatile, boolean isTransient, SinceSpecifier sinceSpecifier, String name)
  {
    this.enclosingTypeDefinition = enclosingTypeDefinition;
    this.accessSpecifier = accessSpecifier;
    this.isFinal = isFinal;
    this.isMutable = isMutable;
    this.isStatic = isStatic;
    this.isVolatile = isVolatile;
    this.isTransient = isTransient;
    this.sinceSpecifier = sinceSpecifier;
    this.name = name;
  }

  /**
   * Sets the type of this member variable.
   * @param type
   */
  public void setType(Type type)
  {
    this.type = type;
  }

  /**
   * @return the accessSpecifier
   */
  public AccessSpecifier getAccessSpecifier()
  {
    return accessSpecifier;
  }

  /**
   * @return the isFinal
   */
  public boolean isFinal()
  {
    return isFinal;
  }

  /**
   * @return the isMutable
   */
  public boolean isMutable()
  {
    return isMutable;
  }

  /**
   * @return the isStatic
   */
  public boolean isStatic()
  {
    return isStatic;
  }

  /**
   * @return the isVolatile
   */
  public boolean isVolatile()
  {
    return isVolatile;
  }

  /**
   * @return the isTransient
   */
  public boolean isTransient()
  {
    return isTransient;
  }

  /**
   * @return the sinceSpecifier
   */
  public SinceSpecifier getSinceSpecifier()
  {
    return sinceSpecifier;
  }

  /**
   * @return the type
   */
  public Type getVariableType()
  {
    return type;
  }

  /**
   * @return the name
   */
  public String getName()
  {
    return name;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public ScopeType getType()
  {
    return ScopeType.MEMBER_VARIABLE;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public Resolvable resolve(String name) throws NameConflictException
  {
    // TODO: implement
    return null;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  protected Resolvable getParent()
  {
    return enclosingTypeDefinition;
  }

}
