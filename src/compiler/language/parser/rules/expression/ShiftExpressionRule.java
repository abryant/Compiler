package compiler.language.parser.rules.expression;

import static compiler.language.parser.ParseType.ADDITIVE_EXPRESSION;
import static compiler.language.parser.ParseType.DOUBLE_LANGLE;
import static compiler.language.parser.ParseType.DOUBLE_RANGLE;
import static compiler.language.parser.ParseType.RANGLE;
import static compiler.language.parser.ParseType.SHIFT_EXPRESSION;

import compiler.language.ast.ParseInfo;
import compiler.language.ast.expression.Expression;
import compiler.language.ast.expression.ShiftExpression;
import compiler.language.ast.expression.ShiftExpressionType;
import compiler.parser.Rule;

/*
 * Created on 8 Aug 2010
 */

/**
 * @author Anthony Bryant
 */
public class ShiftExpressionRule extends Rule
{

  private static final Object[] START_PRODUCTION = new Object[] {ADDITIVE_EXPRESSION};
  private static final Object[] LEFT_SHIFT_PRODUCTION = new Object[] {SHIFT_EXPRESSION, DOUBLE_LANGLE, ADDITIVE_EXPRESSION};
  private static final Object[] ARITHMETIC_RIGHT_SHIFT_PRODUCTION = new Object[] {SHIFT_EXPRESSION, DOUBLE_RANGLE, ADDITIVE_EXPRESSION};
  private static final Object[] LOGICAL_RIGHT_SHIFT_PRODUCTION = new Object[] {SHIFT_EXPRESSION, DOUBLE_RANGLE, RANGLE, ADDITIVE_EXPRESSION};

  public ShiftExpressionRule()
  {
    super(SHIFT_EXPRESSION, START_PRODUCTION, LEFT_SHIFT_PRODUCTION, ARITHMETIC_RIGHT_SHIFT_PRODUCTION, LOGICAL_RIGHT_SHIFT_PRODUCTION);
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
    if (types == LEFT_SHIFT_PRODUCTION)
    {
      Expression secondExpression = (Expression) args[2];
      if (args[0] instanceof ShiftExpression)
      {
        // continue the existing ShiftExpression if we've already started one
        ShiftExpression startExpression = (ShiftExpression) args[0];
        return new ShiftExpression(startExpression, ShiftExpressionType.LEFT_SHIFT, secondExpression,
                                   ParseInfo.combine(startExpression.getParseInfo(), (ParseInfo) args[1], secondExpression.getParseInfo()));
      }
      Expression firstExpression = (Expression) args[0];
      return new ShiftExpression(firstExpression, ShiftExpressionType.LEFT_SHIFT, secondExpression,
                                 ParseInfo.combine(firstExpression.getParseInfo(), (ParseInfo) args[1], secondExpression.getParseInfo()));
    }
    if (types == ARITHMETIC_RIGHT_SHIFT_PRODUCTION)
    {
      Expression secondExpression = (Expression) args[2];
      if (args[0] instanceof ShiftExpression)
      {
        // continue the existing ShiftExpression if we've already started one
        ShiftExpression startExpression = (ShiftExpression) args[0];
        return new ShiftExpression(startExpression, ShiftExpressionType.ARITHMETIC_RIGHT_SHIFT, secondExpression,
                                   ParseInfo.combine(startExpression.getParseInfo(), (ParseInfo) args[1], secondExpression.getParseInfo()));
      }
      Expression firstExpression = (Expression) args[0];
      return new ShiftExpression(firstExpression, ShiftExpressionType.ARITHMETIC_RIGHT_SHIFT, secondExpression,
                                 ParseInfo.combine(firstExpression.getParseInfo(), (ParseInfo) args[1], secondExpression.getParseInfo()));
    }
    if (types == LOGICAL_RIGHT_SHIFT_PRODUCTION)
    {
      ParseInfo firstTwoAngles = (ParseInfo) args[1];
      ParseInfo thirdAngle = (ParseInfo) args[2];
      if (!ParseInfo.areAdjacent(firstTwoAngles, thirdAngle))
      {
        throw new IllegalArgumentException("The angle brackets in a shift expression must be directly adjacent to each other");
      }
      Expression secondExpression = (Expression) args[3];
      if (args[0] instanceof ShiftExpression)
      {
        // continue the existing ShiftExpression if we've already started one
        ShiftExpression startExpression = (ShiftExpression) args[0];
        return new ShiftExpression(startExpression, ShiftExpressionType.LOGICAL_RIGHT_SHIFT, secondExpression,
                                   ParseInfo.combine(startExpression.getParseInfo(), firstTwoAngles, thirdAngle, secondExpression.getParseInfo()));
      }
      Expression firstExpression = (Expression) args[0];
      return new ShiftExpression(firstExpression, ShiftExpressionType.LOGICAL_RIGHT_SHIFT, secondExpression,
                                 ParseInfo.combine(firstExpression.getParseInfo(), firstTwoAngles, thirdAngle, secondExpression.getParseInfo()));
    }
    throw badTypeList();
  }

}
