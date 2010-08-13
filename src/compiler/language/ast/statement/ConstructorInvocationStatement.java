package compiler.language.ast.statement;

import compiler.language.ast.ParseInfo;
import compiler.language.ast.misc.Parameter;

/*
 * Created on 13 Aug 2010
 */

/**
 * @author Anthony Bryant
 */
public class ConstructorInvocationStatement extends Statement
{

  private boolean superConstructor;
  private Parameter[] parameters;


  /**
   * @param superConstructor - true if this constructor invocation is for the superclass, false if it is for the current class
   * @param parameters - the list of parameters
   * @param parseInfo - the parsing information
   */
  public ConstructorInvocationStatement(boolean superConstructor, Parameter[] parameters, ParseInfo parseInfo)
  {
    super(parseInfo);
    this.superConstructor = superConstructor;
    this.parameters = parameters;
  }


  /**
   * @return the superConstructor
   */
  public boolean isSuperConstructor()
  {
    return superConstructor;
  }


  /**
   * @return the parameters
   */
  public Parameter[] getParameters()
  {
    return parameters;
  }

  /**
   * {@inheritDoc}
   * @see java.lang.Object#toString()
   */
  @Override
  public String toString()
  {
    StringBuffer buffer = new StringBuffer();
    if (superConstructor)
    {
      buffer.append("super(");
    }
    else
    {
      buffer.append("this(");
    }
    for (int i = 0; i < parameters.length; i++)
    {
      buffer.append(parameters[i]);
      if (i != parameters.length - 1)
      {
        buffer.append(", ");
      }
    }
    buffer.append(");");
    return buffer.toString();
  }
}
