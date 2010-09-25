package compiler.language.ast.misc;

import compiler.language.ast.ParseInfo;


/*
 * Created on 10 Jul 2010
 */

/**
 * @author Anthony Bryant
 */
public class ArgumentListAST extends ArgumentAST
{

  private ArgumentAST[] arguments;

  /**
   * Creates a new ArgumentListAST with the specified arguments.
   * @param arguments - the arguments to store
   * @param parseInfo - the parsing information
   */
  public ArgumentListAST(ArgumentAST[] arguments, ParseInfo parseInfo)
  {
    super(parseInfo);
    this.arguments = arguments;
  }

  /**
   * @return the arguments
   */
  public ArgumentAST[] getArguments()
  {
    return arguments;
  }

  /**
   * @see java.lang.Object#toString()
   */
  @Override
  public String toString()
  {
    StringBuffer buffer = new StringBuffer();
    buffer.append("(");
    for (int i = 0; i < arguments.length; i++)
    {
      buffer.append(arguments[i]);
      if (i != arguments.length - 1)
      {
        buffer.append(", ");
      }
    }
    buffer.append(")");
    return buffer.toString();
  }
}
