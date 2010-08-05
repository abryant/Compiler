package compiler.language.parser.rules.expression;

import compiler.language.ast.expression.BooleanXorExpression;
import compiler.language.ast.expression.Expression;
import compiler.language.parser.ParseType;
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
  public Object match(Object[] types, Object[] args)
  {
    if (types == START_PRODUCTION)
    {
      // return the existing Expression
      return args[0];
    }
    if (types == CONTINUATION_PRODUCTION)
    {
      if (args[0] instanceof BooleanXorExpression)
      {
        // continue the existing boolean xor if we've already started one
        return new BooleanXorExpression((BooleanXorExpression) args[0], (Expression) args[2]);
      }
      return new BooleanXorExpression((Expression) args[0], (Expression) args[2]);
    }
    throw badTypeList();
  }

}
