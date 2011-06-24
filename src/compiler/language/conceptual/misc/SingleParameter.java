package compiler.language.conceptual.misc;

import compiler.language.conceptual.type.Type;

/*
 * Created on 22 Jun 2011
 */

/**
 * Represents a single parameter to a closure, method or constructor.
 * @author Anthony Bryant
 */
public class SingleParameter extends Parameter
{

  private boolean isFinal;
  private boolean isVolatile;

  private Type type;
  private String name;

  /**
   * Creates a new SingleParameter with the specified properties
   * @param isFinal - true if the parameter is final, false otherwise
   * @param isVolatile - true if the parameter is volatile, false otherwise
   * @param type - the type of the parameter
   * @param name - the name of the parameter
   */
  public SingleParameter(boolean isFinal, boolean isVolatile, Type type, String name)
  {
    this.isFinal = isFinal;
    this.isVolatile = isVolatile;
    this.type = type;
    this.name = name;
  }

  /**
   * @return the isFinal
   */
  public boolean isFinal()
  {
    return isFinal;
  }

  /**
   * @return the isVolatile
   */
  public boolean isVolatile()
  {
    return isVolatile;
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

}
