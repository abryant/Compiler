package compiler.language.parser.rules.expression;

import static compiler.language.parser.ParseType.BITWISE_AND_EXPRESSION;
import static compiler.language.parser.ParseType.BITWISE_XOR_EXPRESSION;
import static compiler.language.parser.ParseType.CARET;
import static compiler.language.parser.ParseType.QNAME_EXPRESSION;

import compiler.language.ast.ParseInfo;
import compiler.language.ast.expression.BitwiseXorExpression;
import compiler.language.ast.expression.Expression;
import compiler.parser.ParseException;
import compiler.parser.Rule;

/*
 * Created on 4 Aug 2010
 */

/**
 * @author Anthony Bryant
 */
public class BitwiseXorExpressionRule extends Rule
{

  private static final Object[] START_PRODUCTION           = new Object[] {BITWISE_AND_EXPRESSION};
  private static final Object[] XOR_PRODUCTION             = new Object[] {BITWISE_XOR_EXPRESSION, CARET, BITWISE_AND_EXPRESSION};
  private static final Object[] XOR_QNAME_PRODUCTION       = new Object[] {BITWISE_XOR_EXPRESSION, CARET, QNAME_EXPRESSION};
  private static final Object[] QNAME_XOR_PRODUCTION       = new Object[] {QNAME_EXPRESSION,       CARET, BITWISE_AND_EXPRESSION};
  private static final Object[] QNAME_XOR_QNAME_PRODUCTION = new Object[] {QNAME_EXPRESSION,       CARET, QNAME_EXPRESSION};

  public BitwiseXorExpressionRule()
  {
    super(BITWISE_XOR_EXPRESSION, START_PRODUCTION, XOR_PRODUCTION, XOR_QNAME_PRODUCTION, QNAME_XOR_PRODUCTION, QNAME_XOR_QNAME_PRODUCTION);
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
    if (types == XOR_PRODUCTION || types == XOR_QNAME_PRODUCTION || types == QNAME_XOR_PRODUCTION || types == QNAME_XOR_QNAME_PRODUCTION)
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
