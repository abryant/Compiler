package compiler.language.parser.rules.expression;

import static compiler.language.parser.ParseType.BOOLEAN_AND_EXPRESSION;
import static compiler.language.parser.ParseType.DOUBLE_AMPERSAND;
import static compiler.language.parser.ParseType.EQUALITY_EXPRESSION;
import static compiler.language.parser.ParseType.QNAME_OR_LESS_THAN_EXPRESSION;

import compiler.language.ast.ParseInfo;
import compiler.language.ast.expression.BooleanAndExpression;
import compiler.language.ast.expression.Expression;
import compiler.parser.ParseException;
import compiler.parser.Rule;

/*
 * Created on 4 Aug 2010
 */

/**
 * @author Anthony Bryant
 */
public class BooleanAndExpressionRule extends Rule
{

  private static final Object[] START_PRODUCTION           = new Object[] {EQUALITY_EXPRESSION};
  private static final Object[] AND_PRODUCTION             = new Object[] {BOOLEAN_AND_EXPRESSION,        DOUBLE_AMPERSAND, EQUALITY_EXPRESSION};
  private static final Object[] AND_QNAME_PRODUCTION       = new Object[] {BOOLEAN_AND_EXPRESSION,        DOUBLE_AMPERSAND, QNAME_OR_LESS_THAN_EXPRESSION};
  private static final Object[] QNAME_AND_PRODUCTION       = new Object[] {QNAME_OR_LESS_THAN_EXPRESSION, DOUBLE_AMPERSAND, EQUALITY_EXPRESSION};
  private static final Object[] QNAME_AND_QNAME_PRODUCTION = new Object[] {QNAME_OR_LESS_THAN_EXPRESSION, DOUBLE_AMPERSAND, QNAME_OR_LESS_THAN_EXPRESSION};

  public BooleanAndExpressionRule()
  {
    super(BOOLEAN_AND_EXPRESSION, START_PRODUCTION, AND_PRODUCTION, AND_QNAME_PRODUCTION, QNAME_AND_PRODUCTION, QNAME_AND_QNAME_PRODUCTION);
  }

  /**
   * {@inheritDoc}
   * @see compiler.parser.Rule#match(java.lang.Object[], java.lang.Object[])
   */
  @Override
  public Object match(Object[] types, Object[] args) throws ParseException
  {
    if (types == START_PRODUCTION)
    {
      // return the existing Expression
      return args[0];
    }
    if (types == AND_PRODUCTION || types == AND_QNAME_PRODUCTION || types == QNAME_AND_PRODUCTION || types == QNAME_AND_QNAME_PRODUCTION)
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
