package compiler.language.ast;

/*
 * Created on 10 Jul 2010
 */

/**
 * @author Anthony Bryant
 */
public class ArgumentList extends Argument
{

  private Argument[] arguments;

  /**
   * Creates a new ArgumentList with the specified arguments.
   * @param arguments - the arguments to store
   */
  public ArgumentList(Argument[] arguments)
  {
    this.arguments = arguments;
  }

  /**
   * @return the arguments
   */
  public Argument[] getArguments()
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
