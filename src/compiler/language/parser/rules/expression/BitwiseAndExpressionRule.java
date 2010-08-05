package compiler.language.parser.rules.expression;

import compiler.language.ast.expression.BitwiseAndExpression;
import compiler.language.ast.expression.Expression;
import compiler.language.parser.ParseType;
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
  public Object match(Object[] types, Object[] args)
  {
    if (types == START_PRODUCTION)
    {
      // return the existing Expression
      return args[0];
    }
    if (types == CONTINUATION_PRODUCTION)
    {
      if (args[0] instanceof BitwiseAndExpression)
      {
        // continue the current bitwise and if we've already started one
        return new BitwiseAndExpression((BitwiseAndExpression) args[0], (Expression) args[2]);
      }
      return new BitwiseAndExpression((Expression) args[0], (Expression) args[2]);
    }
    throw badTypeList();
  }

}
