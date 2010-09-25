package compiler.language.parser.rules.statement;

import static compiler.language.parser.ParseType.ASSIGNEE_LIST;
import static compiler.language.parser.ParseType.ASSIGNMENT;
import static compiler.language.parser.ParseType.ASSIGNMENT_OPERATOR;
import static compiler.language.parser.ParseType.EXPRESSION;

import compiler.language.ast.ParseContainer;
import compiler.language.ast.ParseInfo;
import compiler.language.ast.ParseList;
import compiler.language.ast.expression.ExpressionAST;
import compiler.language.ast.misc.AssigneeAST;
import compiler.language.ast.misc.AssignmentOperatorAST;
import compiler.language.ast.statement.AssignmentStatementAST;
import compiler.language.parser.ParseType;
import compiler.parser.ParseException;
import compiler.parser.Production;
import compiler.parser.Rule;

/*
 * Created on 23 Aug 2010
 */

/**
 * @author Anthony Bryant
 */
public final class AssignmentRule extends Rule<ParseType>
{
  private static final long serialVersionUID = 1L;

  private static final Production<ParseType> PRODUCTION = new Production<ParseType>(ASSIGNEE_LIST, ASSIGNMENT_OPERATOR, EXPRESSION);

  @SuppressWarnings("unchecked")
  public AssignmentRule()
  {
    super(ASSIGNMENT, PRODUCTION);
  }

  /**
   * {@inheritDoc}
   * @see compiler.parser.Rule#match(compiler.parser.Production, java.lang.Object[])
   */
  @Override
  public Object match(Production<ParseType> production, Object[] args) throws ParseException
  {
    if (PRODUCTION.equals(production))
    {
      @SuppressWarnings("unchecked")
      ParseList<AssigneeAST> assignees = (ParseList<AssigneeAST>) args[0];
      @SuppressWarnings("unchecked")
      ParseContainer<AssignmentOperatorAST> operator = (ParseContainer<AssignmentOperatorAST>) args[1];
      ExpressionAST expression = (ExpressionAST) args[2];
      return new AssignmentStatementAST(assignees.toArray(new AssigneeAST[0]), operator.getItem(), expression,
                                     ParseInfo.combine(assignees.getParseInfo(), operator.getParseInfo(), expression.getParseInfo()));
    }
    throw badTypeList();
  }

}
