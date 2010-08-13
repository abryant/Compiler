package compiler.language.ast.statement;

import compiler.language.ast.ParseInfo;
import compiler.language.ast.expression.Expression;
import compiler.language.ast.misc.Assignee;
import compiler.language.ast.misc.AssignmentOperator;

/*
 * Created on 13 Aug 2010
 */

/**
 * @author Anthony Bryant
 */
public class AssignmentStatement extends Statement
{

  private Assignee[] assignees;
  private AssignmentOperator operator;
  private Expression expression;

  /**
   * Creates a new AssignmentStatement with the specified assignees, operator, and expression.
   * @param assignees - the list of assignees
   * @param operator - the assignment operator
   * @param expression - the expression to assign to the assignees
   * @param parseInfo - the parsing information
   */
  public AssignmentStatement(Assignee[] assignees, AssignmentOperator operator, Expression expression, ParseInfo parseInfo)
  {
    super(parseInfo);
    this.assignees = assignees;
    this.operator = operator;
    this.expression = expression;
  }

  /**
   * @return the assignees
   */
  public Assignee[] getAssignees()
  {
    return assignees;
  }

  /**
   * @return the operator
   */
  public AssignmentOperator getOperator()
  {
    return operator;
  }

  /**
   * @return the expression
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
    for (int i = 0; i < assignees.length; i++)
    {
      buffer.append(assignees[i]);
      if (i != assignees.length - 1)
      {
        buffer.append(", ");
      }
    }
    buffer.append(" ");
    buffer.append(operator.getTokenString());
    buffer.append(" ");
    buffer.append(expression);
    buffer.append(";");
    return buffer.toString();
  }
}
