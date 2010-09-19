package compiler.language.ast.typeDefinition;

import compiler.language.ast.ParseInfo;
import compiler.language.ast.member.Member;
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
  private Member[] members;

  /**
   * Creates a new Enum Constant with the specified name and parameters
   * @param name - the name of the constant
   * @param parameters - the parameters to be passed into the enum's constructor
   * @param members - the list of members of this enum constant, or null if there is no body for it
   * @param parseInfo - the parsing information
   */
  public EnumConstant(Name name, Parameter[] parameters, Member[] members, ParseInfo parseInfo)
  {
    this.parseInfo = parseInfo;
    this.name = name;
    this.parameters = parameters;
    this.members = members;
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
   * @return the members of this enum constant, or null if no body was specified
   */
  public Member[] getMembers()
  {
    return members;
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
    if (members != null)
    {
      buffer.append("\n{\n");
      for (int i = 0; i < members.length; i++)
      {
        String memberStr = members[i].toString();
        buffer.append(memberStr.replaceAll("(?m)^", "   "));
        buffer.append("\n   \n");
      }
      buffer.append("}");
    }
    return buffer.toString();
  }
}
