package compiler.language.parser.rules.statement;

import static compiler.language.parser.ParseType.ASSIGNMENT;
import static compiler.language.parser.ParseType.DECREMENT;
import static compiler.language.parser.ParseType.FOR_UPDATE;
import static compiler.language.parser.ParseType.INCREMENT;
import static compiler.language.parser.ParseType.STATEMENT_EXPRESSION;
import parser.ParseException;
import parser.Production;
import parser.Rule;

import compiler.language.ast.expression.StatementExpressionAST;
import compiler.language.ast.statement.ExpressionStatementAST;
import compiler.language.parser.ParseType;

/*
 * Created on 28 Aug 2010
 */

/**
 * @author Anthony Bryant
 */
public final class ForUpdateRule extends Rule<ParseType>
{
  private static final long serialVersionUID = 1L;

  private static final Production<ParseType> EMPTY_PRODUCTION      = new Production<ParseType>();
  private static final Production<ParseType> EXPRESSION_PRODUCTION = new Production<ParseType>(STATEMENT_EXPRESSION);
  private static final Production<ParseType> ASSIGNMENT_PRODUCTION = new Production<ParseType>(ASSIGNMENT);
  private static final Production<ParseType> INCREMENT_PRODUCTION  = new Production<ParseType>(INCREMENT);
  private static final Production<ParseType> DECREMENT_PRODUCTION  = new Production<ParseType>(DECREMENT);

  @SuppressWarnings("unchecked")
  public ForUpdateRule()
  {
    super(FOR_UPDATE, EMPTY_PRODUCTION, EXPRESSION_PRODUCTION, ASSIGNMENT_PRODUCTION, INCREMENT_PRODUCTION, DECREMENT_PRODUCTION);
  }

  /**
   * {@inheritDoc}
   * @see parser.Rule#match(parser.Production, java.lang.Object[])
   */
  @Override
  public Object match(Production<ParseType> production, Object[] args) throws ParseException
  {
    if (EMPTY_PRODUCTION.equals(production))
    {
      return null;
    }
    if (EXPRESSION_PRODUCTION.equals(production))
    {
      StatementExpressionAST expression = (StatementExpressionAST) args[0];
      return new ExpressionStatementAST(expression, expression.getParseInfo());
    }
    if (ASSIGNMENT_PRODUCTION.equals(production) || INCREMENT_PRODUCTION.equals(production) || DECREMENT_PRODUCTION.equals(production))
    {
      // a StatementAST has already been created, so return it
      return args[0];
    }
    throw badTypeList();
  }

}
