package compiler.language.ast.expression;

import compiler.language.ast.ParseInfo;
import compiler.language.ast.member.MemberAST;
import compiler.language.ast.misc.ArgumentAST;
import compiler.language.ast.type.PointerTypeAST;

/*
 * Created on 3 Aug 2010
 */

/**
 * @author Anthony Bryant
 */
public class InstanciationExpressionAST extends StatementExpressionAST
{

  private PointerTypeAST type;
  private ArgumentAST[] arguments;
  private MemberAST[] members;

  /**
   * Creates a new InstanciationExpressionAST with the specified type, arguments and overridden members
   * @param type - the type of the class to create
   * @param arguments - the arguments for the constructor call
   * @param members - the list of members for the class, or null if no class body was specified
   * @param parseInfo - the parsing information
   */
  public InstanciationExpressionAST(PointerTypeAST type, ArgumentAST[] arguments, MemberAST[] members, ParseInfo parseInfo)
  {
    super(parseInfo);
    this.type = type;
    this.arguments = arguments;
    this.members = members;
  }

  /**
   * @return the type
   */
  public PointerTypeAST getType()
  {
    return type;
  }

  /**
   * @return the arguments
   */
  public ArgumentAST[] getArguments()
  {
    return arguments;
  }

  /**
   * @return the members
   */
  public MemberAST[] getMembers()
  {
    return members;
  }

  /**
   * {@inheritDoc}
   * @see java.lang.Object#toString()
   */
  @Override
  public String toString()
  {
    StringBuffer buffer = new StringBuffer();
    buffer.append("new ");
    buffer.append(type);
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
    if (members != null)
    {
      buffer.append("\n{\n");
      for (int i = 0; i < members.length; i++)
      {
        String memberStr = members[i].toString();
        buffer.append(memberStr.replaceAll("(?m)^", "   "));
        buffer.append("\n   ");
      }
      buffer.append("\n}");
    }
    return buffer.toString();
  }

}
