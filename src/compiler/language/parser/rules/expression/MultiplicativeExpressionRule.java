package compiler.language.parser.rules.expression;

import static compiler.language.parser.ParseType.FORWARD_SLASH;
import static compiler.language.parser.ParseType.MULTIPLICATIVE_EXPRESSION;
import static compiler.language.parser.ParseType.PERCENT;
import static compiler.language.parser.ParseType.STAR;
import static compiler.language.parser.ParseType.TUPLE_INDEX_EXPRESSION;

import compiler.language.ast.ParseInfo;
import compiler.language.ast.expression.Expression;
import compiler.language.ast.expression.MultiplicativeExpression;
import compiler.language.ast.expression.MultiplicativeExpressionType;
import compiler.parser.Rule;

/*
 * Created on 8 Aug 2010
 */

/**
 * @author Anthony Bryant
 */
public class MultiplicativeExpressionRule extends Rule
{

  private static final Object[] START_PRODUCTION = new Object[] {TUPLE_INDEX_EXPRESSION};
  private static final Object[] TIMES_PRODUCTION = new Object[] {MULTIPLICATIVE_EXPRESSION, STAR, TUPLE_INDEX_EXPRESSION};
  private static final Object[] DIVIDE_PRODUCTION = new Object[] {MULTIPLICATIVE_EXPRESSION, FORWARD_SLASH, TUPLE_INDEX_EXPRESSION};
  private static final Object[] MODULUS_PRODUCTION = new Object[] {MULTIPLICATIVE_EXPRESSION, PERCENT, TUPLE_INDEX_EXPRESSION};

  public MultiplicativeExpressionRule()
  {
    super(MULTIPLICATIVE_EXPRESSION, START_PRODUCTION, TIMES_PRODUCTION, DIVIDE_PRODUCTION, MODULUS_PRODUCTION);
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
    if (types == TIMES_PRODUCTION)
    {
      Expression secondExpression = (Expression) args[2];
      if (args[0] instanceof MultiplicativeExpression)
      {
        // continue the existing MultiplicativeExpression if we've already started one
        MultiplicativeExpression startExpression = (MultiplicativeExpression) args[0];
        return new MultiplicativeExpression(startExpression, MultiplicativeExpressionType.TIMES, secondExpression,
                                            ParseInfo.combine(startExpression.getParseInfo(), (ParseInfo) args[1], secondExpression.getParseInfo()));
      }
      Expression firstExpression = (Expression) args[0];
      return new MultiplicativeExpression(firstExpression, MultiplicativeExpressionType.TIMES, secondExpression,
                                          ParseInfo.combine(firstExpression.getParseInfo(), (ParseInfo) args[1], secondExpression.getParseInfo()));
    }
    if (types == DIVIDE_PRODUCTION)
    {
      Expression secondExpression = (Expression) args[2];
      if (args[0] instanceof MultiplicativeExpression)
      {
        // continue the existing MultiplicativeExpression if we've already started one
        MultiplicativeExpression startExpression = (MultiplicativeExpression) args[0];
        return new MultiplicativeExpression(startExpression, MultiplicativeExpressionType.DIVIDE, secondExpression,
                                            ParseInfo.combine(startExpression.getParseInfo(), (ParseInfo) args[1], secondExpression.getParseInfo()));
      }
      Expression firstExpression = (Expression) args[0];
      return new MultiplicativeExpression(firstExpression, MultiplicativeExpressionType.DIVIDE, secondExpression,
                                          ParseInfo.combine(firstExpression.getParseInfo(), (ParseInfo) args[1], secondExpression.getParseInfo()));
    }
    if (types == MODULUS_PRODUCTION)
    {
      Expression secondExpression = (Expression) args[2];
      if (args[0] instanceof MultiplicativeExpression)
      {
        // continue the existing MultiplicativeExpression if we've already started one
        MultiplicativeExpression startExpression = (MultiplicativeExpression) args[0];
        return new MultiplicativeExpression(startExpression, MultiplicativeExpressionType.MODULUS, secondExpression,
                                            ParseInfo.combine(startExpression.getParseInfo(), (ParseInfo) args[1], secondExpression.getParseInfo()));
      }
      Expression firstExpression = (Expression) args[0];
      return new MultiplicativeExpression(firstExpression, MultiplicativeExpressionType.MODULUS, secondExpression,
                                          ParseInfo.combine(firstExpression.getParseInfo(), (ParseInfo) args[1], secondExpression.getParseInfo()));
    }
    throw badTypeList();
  }

}
