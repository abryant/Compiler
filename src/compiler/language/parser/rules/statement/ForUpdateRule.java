package compiler.language.parser.rules.statement;

import static compiler.language.parser.ParseType.ASSIGNMENT;
import static compiler.language.parser.ParseType.DECREMENT;
import static compiler.language.parser.ParseType.FOR_UPDATE;
import static compiler.language.parser.ParseType.INCREMENT;
import static compiler.language.parser.ParseType.STATEMENT_EXPRESSION;

import compiler.language.ast.expression.StatementExpression;
import compiler.language.ast.statement.ExpressionStatement;
import compiler.parser.ParseException;
import compiler.parser.Production;
import compiler.parser.Rule;

/*
 * Created on 28 Aug 2010
 */

/**
 * @author Anthony Bryant
 */
public class ForUpdateRule extends Rule
{
  private static final long serialVersionUID = 1L;

  private static final Production EMPTY_PRODUCTION      = new Production();
  private static final Production EXPRESSION_PRODUCTION = new Production(STATEMENT_EXPRESSION);
  private static final Production ASSIGNMENT_PRODUCTION = new Production(ASSIGNMENT);
  private static final Production INCREMENT_PRODUCTION  = new Production(INCREMENT);
  private static final Production DECREMENT_PRODUCTION  = new Production(DECREMENT);

  public ForUpdateRule()
  {
    super(FOR_UPDATE, EMPTY_PRODUCTION, EXPRESSION_PRODUCTION, ASSIGNMENT_PRODUCTION, INCREMENT_PRODUCTION, DECREMENT_PRODUCTION);
  }

  /**
   * {@inheritDoc}
   * @see compiler.parser.Rule#match(compiler.parser.Production, java.lang.Object[])
   */
  @Override
  public Object match(Production production, Object[] args) throws ParseException
  {
    if (EMPTY_PRODUCTION.equals(production))
    {
      return null;
    }
    if (EXPRESSION_PRODUCTION.equals(production))
    {
      StatementExpression expression = (StatementExpression) args[0];
      return new ExpressionStatement(expression, expression.getParseInfo());
    }
    if (ASSIGNMENT_PRODUCTION.equals(production) || INCREMENT_PRODUCTION.equals(production) || DECREMENT_PRODUCTION.equals(production))
    {
      // a Statement has already been created, so return it
      return args[0];
    }
    throw badTypeList();
  }

}
