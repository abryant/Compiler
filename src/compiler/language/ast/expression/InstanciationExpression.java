package compiler.language.ast.expression;

import compiler.language.ast.ParseInfo;
import compiler.language.ast.member.Member;
import compiler.language.ast.misc.Parameter;
import compiler.language.ast.type.PointerType;

/*
 * Created on 3 Aug 2010
 */

/**
 * @author Anthony Bryant
 */
public class InstanciationExpression extends StatementExpression
{

  private PointerType type;
  private Parameter[] parameters;
  private Member[] members;

  /**
   * Creates a new InstanciationExpression with the specified type, parameters and overridden members
   * @param type - the type of the class to create
   * @param parameters - the parameters for the constructor call
   * @param members - the list of members for the class, or null if no class body was specified
   * @param parseInfo - the parsing information
   */
  public InstanciationExpression(PointerType type, Parameter[] parameters, Member[] members, ParseInfo parseInfo)
  {
    super(parseInfo);
    this.type = type;
    this.parameters = parameters;
    this.members = members;
  }

  /**
   * @return the type
   */
  public PointerType getType()
  {
    return type;
  }

  /**
   * @return the parameters
   */
  public Parameter[] getParameters()
  {
    return parameters;
  }

  /**
   * @return the members
   */
  public Member[] getMembers()
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
