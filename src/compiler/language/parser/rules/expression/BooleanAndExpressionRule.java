package compiler.language.parser.rules.expression;

import compiler.language.ast.expression.BooleanAndExpression;
import compiler.language.ast.expression.Expression;
import compiler.language.parser.ParseType;
import compiler.parser.Rule;

/*
 * Created on 4 Aug 2010
 */

/**
 * @author Anthony Bryant
 */
public class BooleanAndExpressionRule extends Rule
{

  private static final Object[] START_PRODUCTION = new Object[] {ParseType.EQUALITY_EXPRESSION};
  private static final Object[] CONTINUATION_PRODUCTION = new Object[] {ParseType.BOOLEAN_AND_EXPRESSION, ParseType.DOUBLE_AMPERSAND, ParseType.EQUALITY_EXPRESSION};

  public BooleanAndExpressionRule()
  {
    super(ParseType.BOOLEAN_AND_EXPRESSION, START_PRODUCTION, CONTINUATION_PRODUCTION);
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
      if (args[0] instanceof BooleanAndExpression)
      {
        // continue the current boolean and if we've already started one
        return new BooleanAndExpression((BooleanAndExpression) args[0], (Expression) args[2]);
      }
      return new BooleanAndExpression((Expression) args[0], (Expression) args[2]);
    }
    throw badTypeList();
  }

}
