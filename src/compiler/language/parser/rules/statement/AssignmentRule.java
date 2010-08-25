package compiler.language.parser.rules.statement;

import static compiler.language.parser.ParseType.ASSIGNEE_LIST;
import static compiler.language.parser.ParseType.ASSIGNMENT;
import static compiler.language.parser.ParseType.ASSIGNMENT_OPERATOR;
import static compiler.language.parser.ParseType.EXPRESSION;

import compiler.language.ast.ParseContainer;
import compiler.language.ast.ParseInfo;
import compiler.language.ast.ParseList;
import compiler.language.ast.expression.Expression;
import compiler.language.ast.misc.Assignee;
import compiler.language.ast.misc.AssignmentOperator;
import compiler.language.ast.statement.AssignmentStatement;
import compiler.parser.ParseException;
import compiler.parser.Rule;

/*
 * Created on 23 Aug 2010
 */

/**
 * @author Anthony Bryant
 */
public class AssignmentRule extends Rule
{

  private static final Object[] PRODUCTION = new Object[] {ASSIGNEE_LIST, ASSIGNMENT_OPERATOR, EXPRESSION};

  public AssignmentRule()
  {
    super(ASSIGNMENT, PRODUCTION);
  }

  /**
   * {@inheritDoc}
   * @see compiler.parser.Rule#match(java.lang.Object[], java.lang.Object[])
   */
  @Override
  public Object match(Object[] types, Object[] args) throws ParseException
  {
    if (types == PRODUCTION)
    {
      @SuppressWarnings("unchecked")
      ParseList<Assignee> assignees = (ParseList<Assignee>) args[0];
      @SuppressWarnings("unchecked")
      ParseContainer<AssignmentOperator> operator = (ParseContainer<AssignmentOperator>) args[1];
      Expression expression = (Expression) args[2];
      return new AssignmentStatement(assignees.toArray(new Assignee[0]), operator.getItem(), expression,
                                     ParseInfo.combine(assignees.getParseInfo(), operator.getParseInfo(), expression.getParseInfo()));
    }
    throw badTypeList();
  }

}
