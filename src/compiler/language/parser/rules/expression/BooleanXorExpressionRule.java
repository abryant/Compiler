package compiler.language.parser.rules.expression;

import static compiler.language.parser.ParseType.BOOLEAN_AND_EXPRESSION;
import static compiler.language.parser.ParseType.BOOLEAN_XOR_EXPRESSION;
import static compiler.language.parser.ParseType.DOUBLE_CARET;
import static compiler.language.parser.ParseType.QNAME_OR_LESS_THAN_EXPRESSION;

import compiler.language.ast.ParseInfo;
import compiler.language.ast.expression.BooleanXorExpression;
import compiler.language.ast.expression.Expression;
import compiler.parser.ParseException;
import compiler.parser.Rule;

/*
 * Created on 4 Aug 2010
 */

/**
 * @author Anthony Bryant
 */
public class BooleanXorExpressionRule extends Rule
{

  private static final Object[] START_PRODUCTION           = new Object[] {BOOLEAN_AND_EXPRESSION};
  private static final Object[] XOR_PRODUCTION             = new Object[] {BOOLEAN_XOR_EXPRESSION,        DOUBLE_CARET, BOOLEAN_AND_EXPRESSION};
  private static final Object[] XOR_QNAME_PRODUCTION       = new Object[] {BOOLEAN_XOR_EXPRESSION,        DOUBLE_CARET, QNAME_OR_LESS_THAN_EXPRESSION};
  private static final Object[] QNAME_XOR_PRODUCTION       = new Object[] {QNAME_OR_LESS_THAN_EXPRESSION, DOUBLE_CARET, BOOLEAN_AND_EXPRESSION};
  private static final Object[] QNAME_XOR_QNAME_PRODUCTION = new Object[] {QNAME_OR_LESS_THAN_EXPRESSION, DOUBLE_CARET, QNAME_OR_LESS_THAN_EXPRESSION};

  public BooleanXorExpressionRule()
  {
    super(BOOLEAN_XOR_EXPRESSION, START_PRODUCTION, XOR_PRODUCTION, XOR_QNAME_PRODUCTION, QNAME_XOR_PRODUCTION, QNAME_XOR_QNAME_PRODUCTION);
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
    if (types == XOR_PRODUCTION || types == XOR_QNAME_PRODUCTION || types == QNAME_XOR_PRODUCTION || types == QNAME_XOR_QNAME_PRODUCTION)
    {
      Expression secondExpression = (Expression) args[2];
      if (args[0] instanceof BooleanXorExpression)
      {
        // continue the existing BooleanXorExpression if we've already started one
        BooleanXorExpression startExpression = (BooleanXorExpression) args[0];
        return new BooleanXorExpression(startExpression, secondExpression, ParseInfo.combine(startExpression.getParseInfo(), (ParseInfo) args[1], secondExpression.getParseInfo()));
      }
      Expression firstExpression = (Expression) args[0];
      return new BooleanXorExpression(firstExpression, secondExpression, ParseInfo.combine(firstExpression.getParseInfo(), (ParseInfo) args[1], secondExpression.getParseInfo()));
    }
    throw badTypeList();
  }

}
