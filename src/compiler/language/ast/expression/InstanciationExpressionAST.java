package compiler.language.ast.expression;

import compiler.language.ast.ParseInfo;
import compiler.language.ast.member.MemberAST;
import compiler.language.ast.misc.ParameterAST;
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
  private ParameterAST[] parameters;
  private MemberAST[] members;

  /**
   * Creates a new InstanciationExpressionAST with the specified type, parameters and overridden members
   * @param type - the type of the class to create
   * @param parameters - the parameters for the constructor call
   * @param members - the list of members for the class, or null if no class body was specified
   * @param parseInfo - the parsing information
   */
  public InstanciationExpressionAST(PointerTypeAST type, ParameterAST[] parameters, MemberAST[] members, ParseInfo parseInfo)
  {
    super(parseInfo);
    this.type = type;
    this.parameters = parameters;
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
   * @return the parameters
   */
  public ParameterAST[] getParameters()
  {
    return parameters;
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
    for (int i = 0; i < parameters.length; i++)
    {
      buffer.append(parameters[i]);
      if (i != parameters.length - 1)
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
