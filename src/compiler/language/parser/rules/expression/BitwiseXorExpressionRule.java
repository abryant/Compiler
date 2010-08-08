package compiler.language.parser.rules.expression;

import compiler.language.ast.ParseInfo;
import compiler.language.ast.expression.BitwiseXorExpression;
import compiler.language.ast.expression.Expression;
import compiler.language.parser.ParseType;
import compiler.parser.Rule;

/*
 * Created on 4 Aug 2010
 */

/**
 * @author Anthony Bryant
 */
public class BitwiseXorExpressionRule extends Rule
{

  private static final Object[] START_PRODUCTION = new Object[] {ParseType.BITWISE_AND_EXPRESSION};
  private static final Object[] CONTINUATION_PRODUCTION = new Object[] {ParseType.BITWISE_XOR_EXPRESSION, ParseType.CARET, ParseType.BITWISE_AND_EXPRESSION};

  public BitwiseXorExpressionRule()
  {
    super(ParseType.BITWISE_XOR_EXPRESSION, START_PRODUCTION, CONTINUATION_PRODUCTION);
  }

  /**
   * {@inheritDoc}
   * @see compiler.parser.Rule#match(java.lang.Object[], java.lang.Object[])
   */
  @Override
  public Object match(Object[] types, Object[] args)
  {
    if (types == START_PRODUCTION)
    {
      // return the existing Expression
      return args[0];
    }
    if (types == CONTINUATION_PRODUCTION)
    {
      Expression secondExpression = (Expression) args[2];
      if (args[0] instanceof BitwiseXorExpression)
      {
        // continue the current BitwiseXorExpression if we've already started one
        BitwiseXorExpression startExpression = (BitwiseXorExpression) args[0];
        return new BitwiseXorExpression(startExpression, secondExpression, ParseInfo.combine(startExpression.getParseInfo(), (ParseInfo) args[1], secondExpression.getParseInfo()));
      }
      Expression firstExpression = (Expression) args[0];
      return new BitwiseXorExpression(firstExpression, secondExpression, ParseInfo.combine(firstExpression.getParseInfo(), (ParseInfo) args[1], secondExpression.getParseInfo()));
    }
    throw badTypeList();
  }

}
