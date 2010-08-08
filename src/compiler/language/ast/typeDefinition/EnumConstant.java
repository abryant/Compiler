package compiler.language.ast.typeDefinition;

import compiler.language.ast.ParseInfo;
import compiler.language.ast.misc.Parameter;
import compiler.language.ast.terminal.Name;

/*
 * Created on 16 Jul 2010
 */

/**
 * @author Anthony Bryant
 */
public class EnumConstant
{

  private ParseInfo parseInfo;

  private Name name;
  private Parameter[] parameters;

  /**
   * Creates a new Enum Constant with the specified name and parameters
   * @param name - the name of the constant
   * @param parameters - the parameters to be passed into the enum's constructor
   * @param parseInfo - the parsing information
   */
  public EnumConstant(Name name, Parameter[] parameters, ParseInfo parseInfo)
  {
    this.parseInfo = parseInfo;
    this.name = name;
    this.parameters = parameters;
  }

  /**
   * @return the name
   */
  public Name getName()
  {
    return name;
  }

  /**
   * @return the parameters
   */
  public Parameter[] getParameters()
  {
    return parameters;
  }

  /**
   * @return the parseInfo
   */
  public ParseInfo getParseInfo()
  {
    return parseInfo;
  }

  /**
   * @see java.lang.Object#toString()
   */
  @Override
  public String toString()
  {
    StringBuffer buffer = new StringBuffer();
    buffer.append(name);
    if (parameters != null)
    {
      buffer.append("(");
      for (int i = 0; i < parameters.length; i++)
      {
        buffer.append(parameters[i]);
        if (i != parameters.length - 1)
        {
          buffer.append(", ");
        }
      }
      buffer.append(")");
    }
    return buffer.toString();
  }
}