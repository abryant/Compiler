package compiler.language.ast.typeDefinition;

import compiler.language.ast.ParseInfo;
import compiler.language.ast.member.MemberAST;
import compiler.language.ast.misc.ArgumentAST;
import compiler.language.ast.terminal.NameAST;

/*
 * Created on 16 Jul 2010
 */

/**
 * @author Anthony Bryant
 */
public class EnumConstantAST
{

  private ParseInfo parseInfo;

  private NameAST name;
  private ArgumentAST[] arguments;
  private MemberAST[] members;

  /**
   * Creates a new Enum Constant with the specified name and arguments
   * @param name - the name of the constant
   * @param arguments - the arguments to be passed into the enum's constructor
   * @param members - the list of members of this enum constant, or null if there is no body for it
   * @param parseInfo - the parsing information
   */
  public EnumConstantAST(NameAST name, ArgumentAST[] arguments, MemberAST[] members, ParseInfo parseInfo)
  {
    this.parseInfo = parseInfo;
    this.name = name;
    this.arguments = arguments;
    this.members = members;
  }

  /**
   * @return the name
   */
  public NameAST getName()
  {
    return name;
  }

  /**
   * @return the arguments
   */
  public ArgumentAST[] getArguments()
  {
    return arguments;
  }

  /**
   * @return the members of this enum constant, or null if no body was specified
   */
  public MemberAST[] getMembers()
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
    if (arguments != null)
    {
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
