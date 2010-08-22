package compiler.language.ast.statement;

import compiler.language.ast.ParseInfo;
import compiler.language.ast.expression.Expression;
import compiler.language.ast.member.Modifier;
import compiler.language.ast.misc.DeclarationAssignee;
import compiler.language.ast.type.Type;

/*
 * Created on 13 Aug 2010
 */

/**
 * @author Anthony Bryant
 */
public class LocalDeclarationStatement extends Statement
{

  private Modifier[] modifiers;
  private Type type;
  private DeclarationAssignee[] assignees;
  private Expression expression;

  /**
   * Creates a new LocalDeclarationStatement.
   * @param modifiers - the modifiers for the local variable
   * @param type - the type of the local variables
   * @param assignees - the assignees which are being declared (or defined to the expression)
   * @param expression - the expression to assign to the assignees, or null if this is just a declaration and not also a definition
   * @param parseInfo - the parsing information
   */
  public LocalDeclarationStatement(Modifier[] modifiers, Type type, DeclarationAssignee[] assignees, Expression expression, ParseInfo parseInfo)
  {
    super(parseInfo);
    this.modifiers = modifiers;
    this.type = type;
    this.assignees = assignees;
    this.expression = expression;
  }

  /**
   * @return the modifiers
   */
  public Modifier[] getModifiers()
  {
    return modifiers;
  }

  /**
   * @return the type
   */
  public Type getType()
  {
    return type;
  }

  /**
   * @return the assignees
   */
  public DeclarationAssignee[] getAssignees()
  {
    return assignees;
  }

  /**
   * @return the expression, or null if no expression is specified
   */
  public Expression getExpression()
  {
    return expression;
  }

  /**
   * {@inheritDoc}
   * @see java.lang.Object#toString()
   */
  @Override
  public String toString()
  {
    StringBuffer buffer = new StringBuffer();
    for (Modifier modifier : modifiers)
    {
      buffer.append(modifier);
      buffer.append(" ");
    }
    buffer.append(type);
    buffer.append(" ");
    for (int i = 0; i < assignees.length; i++)
    {
      buffer.append(assignees[i]);
      if (i != assignees.length - 1)
      {
        buffer.append(", ");
      }
    }
    if (expression != null)
    {
      buffer.append(" = ");
      buffer.append(expression);
    }
    buffer.append(";");
    return buffer.toString();
  }
}
