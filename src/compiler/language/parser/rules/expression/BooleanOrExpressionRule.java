package compiler.language.parser.rules.expression;

import compiler.language.ast.expression.BooleanOrExpression;
import compiler.language.ast.expression.Expression;
import compiler.language.parser.ParseType;
import compiler.parser.Rule;

/*
 * Created on 4 Aug 2010
 */

/**
 * @author Anthony Bryant
 */
public class BooleanOrExpressionRule extends Rule
{

  private static final Object[] START_PRODUCTION = new Object[] {ParseType.BOOLEAN_XOR_EXPRESSION};
  private static final Object[] CONTINUATION_PRODUCTION = new Object[] {ParseType.BOOLEAN_OR_EXPRESSION, ParseType.DOUBLE_PIPE, ParseType.BOOLEAN_XOR_EXPRESSION};

  public BooleanOrExpressionRule()
  {
    super(ParseType.BOOLEAN_OR_EXPRESSION, START_PRODUCTION, CONTINUATION_PRODUCTION);
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
      if (args[0] instanceof BooleanOrExpression)
      {
        // continue the existing boolean or if we've already started one
        return new BooleanOrExpression((BooleanOrExpression) args[0], (Expression) args[2]);
      }
      return new BooleanOrExpression((Expression) args[0], (Expression) args[2]);
    }
    throw badTypeList();
  }

}
