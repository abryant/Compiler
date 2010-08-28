package compiler.language.parser.rules.statement;

import static compiler.language.parser.ParseType.ASSIGNMENT;
import static compiler.language.parser.ParseType.DECREMENT;
import static compiler.language.parser.ParseType.FOR_UPDATE;
import static compiler.language.parser.ParseType.INCREMENT;
import static compiler.language.parser.ParseType.STATEMENT_EXPRESSION;

import compiler.language.ast.expression.StatementExpression;
import compiler.language.ast.statement.ExpressionStatement;
import compiler.parser.ParseException;
import compiler.parser.Rule;

/*
 * Created on 28 Aug 2010
 */

/**
 * @author Anthony Bryant
 */
public class ForUpdateRule extends Rule
{

  private static final Object[] EMPTY_PRODUCTION      = new Object[] {};
  private static final Object[] EXPRESSION_PRODUCTION = new Object[] {STATEMENT_EXPRESSION};
  private static final Object[] ASSIGNMENT_PRODUCTION = new Object[] {ASSIGNMENT};
  private static final Object[] INCREMENT_PRODUCTION  = new Object[] {INCREMENT};
  private static final Object[] DECREMENT_PRODUCTION  = new Object[] {DECREMENT};

  public ForUpdateRule()
  {
    super(FOR_UPDATE, EMPTY_PRODUCTION, EXPRESSION_PRODUCTION, ASSIGNMENT_PRODUCTION, INCREMENT_PRODUCTION, DECREMENT_PRODUCTION);
  }

  /**
   * {@inheritDoc}
   * @see compiler.parser.Rule#match(java.lang.Object[], java.lang.Object[])
   */
  @Override
  public Object match(Object[] types, Object[] args) throws ParseException
  {
    if (types == EMPTY_PRODUCTION)
    {
      return null;
    }
    if (types == EXPRESSION_PRODUCTION)
    {
      StatementExpression expression = (StatementExpression) args[0];
      return new ExpressionStatement(expression, expression.getParseInfo());
    }
    if (types == ASSIGNMENT_PRODUCTION || types == INCREMENT_PRODUCTION || types == DECREMENT_PRODUCTION)
    {
      // a Statement has already been created, so return it
      return args[0];
    }
    throw badTypeList();
  }

}
