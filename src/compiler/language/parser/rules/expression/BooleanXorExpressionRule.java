package compiler.language.parser.rules.expression;

import compiler.language.ast.ParseInfo;
import compiler.language.ast.expression.BooleanXorExpression;
import compiler.language.ast.expression.Expression;
import compiler.language.parser.ParseType;
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

  private static final Object[] START_PRODUCTION = new Object[] {ParseType.BOOLEAN_AND_EXPRESSION};
  private static final Object[] CONTINUATION_PRODUCTION = new Object[] {ParseType.BOOLEAN_XOR_EXPRESSION, ParseType.DOUBLE_CARET, ParseType.BOOLEAN_AND_EXPRESSION};

  public BooleanXorExpressionRule()
  {
    super(ParseType.BOOLEAN_XOR_EXPRESSION, START_PRODUCTION, CONTINUATION_PRODUCTION);
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
    if (types == CONTINUATION_PRODUCTION)
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
