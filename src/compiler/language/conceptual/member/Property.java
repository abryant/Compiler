package compiler.language.conceptual.member;

import compiler.language.conceptual.misc.AccessSpecifier;
import compiler.language.conceptual.misc.SinceSpecifier;
import compiler.language.conceptual.statement.Statement;
import compiler.language.conceptual.type.Type;

/*
 * Created on 21 Oct 2010
 */

/**
 * @author Anthony Bryant
 */
public class Property
{

  private boolean isSealed;
  private boolean isMutable;
  private boolean isFinal;
  private boolean isStatic;
  private boolean isSynchronized; // this applies to both the getter and the setter
  private boolean isTransient;
  private boolean isVolatile; // this applies to the backing-variable
  private SinceSpecifier sinceSpecifier;
  private Type type;
  private String name;
  private AccessSpecifier retrieveAccessSpecifier;
  private Statement[] retrieveStatements;
  private AccessSpecifier assignAccessSpecifier;
  private Statement[] assignStatements;

  /**
   * Creates a new Property with the specified properties.
   * The members of the new Property and other data must be set later on
   * @param isSealed
   * @param isMutable
   * @param isFinal
   * @param isStatic
   * @param isSynchronized
   * @param isTransient
   * @param isVolatile
   * @param sinceSpecifier
   * @param name
   * @param retrieveAccessSpecifier
   * @param assignAccessSpecifier
   */
  public Property(boolean isSealed, boolean isMutable, boolean isFinal, boolean isStatic, boolean isSynchronized,
                  boolean isTransient, boolean isVolatile, SinceSpecifier sinceSpecifier,
                  String name, AccessSpecifier retrieveAccessSpecifier, AccessSpecifier assignAccessSpecifier)
  {
    this.isSealed = isSealed;
    this.isMutable = isMutable;
    this.isFinal = isFinal;
    this.isStatic = isStatic;
    this.isSynchronized = isSynchronized;
    this.isTransient = isTransient;
    this.isVolatile = isVolatile;
    this.sinceSpecifier = sinceSpecifier;
    this.name = name;
    this.retrieveAccessSpecifier = retrieveAccessSpecifier;
    this.assignAccessSpecifier = assignAccessSpecifier;
  }

  /**
   * @return the isSealed
   */
  public boolean isSealed()
  {
    return isSealed;
  }
  /**
   * @return the isMutable
   */
  public boolean isMutable()
  {
    return isMutable;
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
   * @return the isTransient
   */
  public boolean isTransient()
  {
    return isTransient;
  }
  /**
   * @return the isVolatile
   */
  public boolean isVolatile()
  {
    return isVolatile;
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
  public Type getType()
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
   * @return the retrieveAccessSpecifier
   */
  public AccessSpecifier getRetrieveAccessSpecifier()
  {
    return retrieveAccessSpecifier;
  }
  /**
   * @return the retrieveStatements
   */
  public Statement[] getRetrieveStatements()
  {
    return retrieveStatements;
  }
  /**
   * @return the assignAccessSpecifier
   */
  public AccessSpecifier getAssignAccessSpecifier()
  {
    return assignAccessSpecifier;
  }
  /**
   * @return the assignStatements
   */
  public Statement[] getAssignStatements()
  {
    return assignStatements;
  }

}
