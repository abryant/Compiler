package compiler.language.parser.rules.expression;

import static compiler.language.parser.ParseType.AMPERSAND;
import static compiler.language.parser.ParseType.BITWISE_AND_EXPRESSION;
import static compiler.language.parser.ParseType.QNAME_EXPRESSION;
import static compiler.language.parser.ParseType.SHIFT_EXPRESSION;

import compiler.language.ast.ParseInfo;
import compiler.language.ast.expression.BitwiseAndExpression;
import compiler.language.ast.expression.Expression;
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

  private static final Object[] START_PRODUCTION           = new Object[] {SHIFT_EXPRESSION};
  private static final Object[] AND_PRODUCTION             = new Object[] {BITWISE_AND_EXPRESSION, AMPERSAND, SHIFT_EXPRESSION};
  private static final Object[] AND_QNAME_PRODUCTION       = new Object[] {BITWISE_AND_EXPRESSION, AMPERSAND, QNAME_EXPRESSION};
  private static final Object[] QNAME_AND_PRODUCTION       = new Object[] {QNAME_EXPRESSION,       AMPERSAND, SHIFT_EXPRESSION};
  private static final Object[] QNAME_AND_QNAME_PRODUCTION = new Object[] {QNAME_EXPRESSION,       AMPERSAND, QNAME_EXPRESSION};

  public BitwiseAndExpressionRule()
  {
    super(BITWISE_AND_EXPRESSION, START_PRODUCTION,
                                  AND_PRODUCTION, AND_QNAME_PRODUCTION, QNAME_AND_PRODUCTION, QNAME_AND_QNAME_PRODUCTION);
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
    if (types == AND_PRODUCTION || types == AND_QNAME_PRODUCTION || types == QNAME_AND_PRODUCTION || types == QNAME_AND_QNAME_PRODUCTION)
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
