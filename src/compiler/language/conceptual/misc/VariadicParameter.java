package compiler.language.conceptual.misc;

import compiler.language.conceptual.type.Type;

/*
 * Created on 22 Jun 2011
 */

/**
 * Represents a single variadic parameter.
 * @author Anthony Bryant
 */
public class VariadicParameter extends SingleParameter
{

  /**
   * Creates a new VariadicParameter with the specified properties.
   * @param isFinal - true if the parameter is final, false otherwise
   * @param isVolatile - true if the parameter is volatile, false otherwise
   * @param type - the type of the parameter
   * @param name - the name of the parameter
   */
  public VariadicParameter(boolean isFinal, boolean isVolatile, Type type, String name)
  {
    super(isFinal, isVolatile, type, name);
  }

}
