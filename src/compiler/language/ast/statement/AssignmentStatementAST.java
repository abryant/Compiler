package compiler.language.ast.statement;

import compiler.language.LexicalPhrase;
import compiler.language.ast.expression.ExpressionAST;
import compiler.language.ast.misc.AssigneeAST;
import compiler.language.ast.misc.AssignmentOperatorAST;

/*
 * Created on 13 Aug 2010
 */

/**
 * @author Anthony Bryant
 */
public class AssignmentStatementAST extends StatementAST
{

  private AssigneeAST[] assignees;
  private AssignmentOperatorAST operator;
  private ExpressionAST expression;

  /**
   * Creates a new AssignmentStatementAST with the specified assignees, operator, and expression.
   * @param assignees - the list of assignees
   * @param operator - the assignment operator
   * @param expression - the expression to assign to the assignees
   * @param lexicalPhrase - the lexical phrase associated with this AST node
   */
  public AssignmentStatementAST(AssigneeAST[] assignees, AssignmentOperatorAST operator, ExpressionAST expression, LexicalPhrase lexicalPhrase)
  {
    super(lexicalPhrase);
    this.assignees = assignees;
    this.operator = operator;
    this.expression = expression;
  }

  /**
   * @return the assignees
   */
  public AssigneeAST[] getAssignees()
  {
    return assignees;
  }

  /**
   * @return the operator
   */
  public AssignmentOperatorAST getOperator()
  {
    return operator;
  }

  /**
   * @return the expression
   */
  public ExpressionAST getExpression()
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
