package compiler.language.parser.rules.expression;

import compiler.language.ast.ParseInfo;
import compiler.language.ast.expression.BitwiseAndExpression;
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
public class BitwiseAndExpressionRule extends Rule
{

  private static final Object[] START_PRODUCTION = new Object[] {ParseType.SHIFT_EXPRESSION};
  private static final Object[] CONTINUATION_PRODUCTION = new Object[] {ParseType.BITWISE_AND_EXPRESSION, ParseType.AMPERSAND, ParseType.SHIFT_EXPRESSION};

  public BitwiseAndExpressionRule()
  {
    super(ParseType.BITWISE_AND_EXPRESSION, START_PRODUCTION, CONTINUATION_PRODUCTION);
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
      if (args[0] instanceof BitwiseAndExpression)
      {
        // continue the current BitwiseAndExpression if we've already started one
        BitwiseAndExpression startExpression = (BitwiseAndExpression) args[0];
        return new BitwiseAndExpression(startExpression, secondExpression, ParseInfo.combine(startExpression.getParseInfo(), (ParseInfo) args[1], secondExpression.getParseInfo()));
      }
      Expression firstExpression = (Expression) args[0];
      return new BitwiseAndExpression(firstExpression, secondExpression, ParseInfo.combine(firstExpression.getParseInfo(), (ParseInfo) args[1], secondExpression.getParseInfo()));
    }
    throw badTypeList();
  }

}
