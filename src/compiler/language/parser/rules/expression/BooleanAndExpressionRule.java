package compiler.language.parser.rules.expression;

import static compiler.language.parser.ParseType.BOOLEAN_AND_EXPRESSION;
import static compiler.language.parser.ParseType.DOUBLE_AMPERSAND;
import static compiler.language.parser.ParseType.EQUALITY_EXPRESSION;
import static compiler.language.parser.ParseType.QNAME_OR_LESS_THAN_EXPRESSION;

import compiler.language.ast.ParseInfo;
import compiler.language.ast.expression.BooleanAndExpression;
import compiler.language.ast.expression.Expression;
import compiler.language.parser.ParseType;
import compiler.parser.ParseException;
import compiler.parser.Production;
import compiler.parser.Rule;

/*
 * Created on 4 Aug 2010
 */

/**
 * @author Anthony Bryant
 */
public final class BooleanAndExpressionRule extends Rule<ParseType>
{
  private static final long serialVersionUID = 1L;

  private static final Production<ParseType> START_PRODUCTION           = new Production<ParseType>(EQUALITY_EXPRESSION);
  private static final Production<ParseType> AND_PRODUCTION             = new Production<ParseType>(BOOLEAN_AND_EXPRESSION,        DOUBLE_AMPERSAND, EQUALITY_EXPRESSION);
  private static final Production<ParseType> AND_QNAME_PRODUCTION       = new Production<ParseType>(BOOLEAN_AND_EXPRESSION,        DOUBLE_AMPERSAND, QNAME_OR_LESS_THAN_EXPRESSION);
  private static final Production<ParseType> QNAME_AND_PRODUCTION       = new Production<ParseType>(QNAME_OR_LESS_THAN_EXPRESSION, DOUBLE_AMPERSAND, EQUALITY_EXPRESSION);
  private static final Production<ParseType> QNAME_AND_QNAME_PRODUCTION = new Production<ParseType>(QNAME_OR_LESS_THAN_EXPRESSION, DOUBLE_AMPERSAND, QNAME_OR_LESS_THAN_EXPRESSION);

  @SuppressWarnings("unchecked")
  public BooleanAndExpressionRule()
  {
    super(BOOLEAN_AND_EXPRESSION, START_PRODUCTION, AND_PRODUCTION, AND_QNAME_PRODUCTION, QNAME_AND_PRODUCTION, QNAME_AND_QNAME_PRODUCTION);
  }

  /**
   * {@inheritDoc}
   * @see compiler.parser.Rule#match(compiler.parser.Production, java.lang.Object[])
   */
  @Override
  public Object match(Production<ParseType> production, Object[] args) throws ParseException
  {
    if (START_PRODUCTION.equals(production))
    {
      // return the existing Expression
      return args[0];
    }
    if (AND_PRODUCTION.equals(production) || AND_QNAME_PRODUCTION.equals(production) || QNAME_AND_PRODUCTION.equals(production) || QNAME_AND_QNAME_PRODUCTION.equals(production))
    {
      Expression secondExpression = (Expression) args[2];
      if (args[0] instanceof BooleanAndExpression)
      {
        // continue the current BooleanAndExpression if we've already started one
        BooleanAndExpression startExpression = (BooleanAndExpression) args[0];
        return new BooleanAndExpression(startExpression, secondExpression, ParseInfo.combine(startExpression.getParseInfo(), (ParseInfo) args[1], secondExpression.getParseInfo()));
      }
      Expression firstExpression = (Expression) args[0];
      return new BooleanAndExpression(firstExpression, secondExpression, ParseInfo.combine(firstExpression.getParseInfo(), (ParseInfo) args[1], secondExpression.getParseInfo()));
    }
    throw badTypeList();
  }

}
