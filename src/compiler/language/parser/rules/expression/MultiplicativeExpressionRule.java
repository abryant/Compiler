package compiler.language.parser.rules.expression;

import static compiler.language.parser.ParseType.FORWARD_SLASH;
import static compiler.language.parser.ParseType.MULTIPLICATIVE_EXPRESSION;
import static compiler.language.parser.ParseType.PERCENT;
import static compiler.language.parser.ParseType.QNAME_EXPRESSION;
import static compiler.language.parser.ParseType.STAR;
import static compiler.language.parser.ParseType.TUPLE_INDEX_EXPRESSION;

import compiler.language.ast.ParseInfo;
import compiler.language.ast.expression.Expression;
import compiler.language.ast.expression.MultiplicativeExpression;
import compiler.language.ast.expression.MultiplicativeExpressionType;
import compiler.parser.ParseException;
import compiler.parser.Rule;

/*
 * Created on 8 Aug 2010
 */

/**
 * @author Anthony Bryant
 */
public class MultiplicativeExpressionRule extends Rule
{

  private static final Object[] START_PRODUCTION               = new Object[] {TUPLE_INDEX_EXPRESSION};
  private static final Object[] TIMES_PRODUCTION               = new Object[] {MULTIPLICATIVE_EXPRESSION, STAR,          TUPLE_INDEX_EXPRESSION};
  private static final Object[] TIMES_QNAME_PRODUCTION         = new Object[] {MULTIPLICATIVE_EXPRESSION, STAR,          QNAME_EXPRESSION};
  private static final Object[] QNAME_TIMES_PRODUCTION         = new Object[] {QNAME_EXPRESSION,          STAR,          TUPLE_INDEX_EXPRESSION};
  private static final Object[] QNAME_TIMES_QNAME_PRODUCTION   = new Object[] {QNAME_EXPRESSION,          STAR,          QNAME_EXPRESSION};
  private static final Object[] DIVIDE_PRODUCTION              = new Object[] {MULTIPLICATIVE_EXPRESSION, FORWARD_SLASH, TUPLE_INDEX_EXPRESSION};
  private static final Object[] DIVIDE_QNAME_PRODUCTION        = new Object[] {MULTIPLICATIVE_EXPRESSION, FORWARD_SLASH, QNAME_EXPRESSION};
  private static final Object[] QNAME_DIVIDE_PRODUCTION        = new Object[] {QNAME_EXPRESSION,          FORWARD_SLASH, TUPLE_INDEX_EXPRESSION};
  private static final Object[] QNAME_DIVIDE_QNAME_PRODUCTION  = new Object[] {QNAME_EXPRESSION,          FORWARD_SLASH, QNAME_EXPRESSION};
  private static final Object[] MODULUS_PRODUCTION             = new Object[] {MULTIPLICATIVE_EXPRESSION, PERCENT,       TUPLE_INDEX_EXPRESSION};
  private static final Object[] MODULUS_QNAME_PRODUCTION       = new Object[] {MULTIPLICATIVE_EXPRESSION, PERCENT,       QNAME_EXPRESSION};
  private static final Object[] QNAME_MODULUS_PRODUCTION       = new Object[] {QNAME_EXPRESSION,          PERCENT,       TUPLE_INDEX_EXPRESSION};
  private static final Object[] QNAME_MODULUS_QNAME_PRODUCTION = new Object[] {QNAME_EXPRESSION,          PERCENT,       QNAME_EXPRESSION};

  public MultiplicativeExpressionRule()
  {
    super(MULTIPLICATIVE_EXPRESSION, START_PRODUCTION,
                                     TIMES_PRODUCTION,   TIMES_QNAME_PRODUCTION,   QNAME_TIMES_PRODUCTION,   QNAME_TIMES_QNAME_PRODUCTION,
                                     DIVIDE_PRODUCTION,  DIVIDE_QNAME_PRODUCTION,  QNAME_DIVIDE_PRODUCTION,  QNAME_DIVIDE_QNAME_PRODUCTION,
                                     MODULUS_PRODUCTION, MODULUS_QNAME_PRODUCTION, QNAME_MODULUS_PRODUCTION, QNAME_MODULUS_QNAME_PRODUCTION);
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

    MultiplicativeExpressionType type = null;
    if (types == TIMES_PRODUCTION || types == TIMES_QNAME_PRODUCTION || types == QNAME_TIMES_PRODUCTION || types == QNAME_TIMES_QNAME_PRODUCTION)
    {
      type = MultiplicativeExpressionType.TIMES;
    }
    else if (types == DIVIDE_PRODUCTION || types == DIVIDE_QNAME_PRODUCTION || types == QNAME_DIVIDE_PRODUCTION || types == QNAME_DIVIDE_QNAME_PRODUCTION)
    {
      type = MultiplicativeExpressionType.DIVIDE;
    }
    else if (types == MODULUS_PRODUCTION || types == MODULUS_QNAME_PRODUCTION || types == QNAME_MODULUS_PRODUCTION || types == QNAME_MODULUS_QNAME_PRODUCTION)
    {
      type = MultiplicativeExpressionType.MODULUS;
    }
    else
    {
      throw badTypeList();
    }
    Expression secondExpression = (Expression) args[2];

    if (args[0] instanceof MultiplicativeExpression)
    {
      // continue the existing MultiplicativeExpression if we've already started one
      MultiplicativeExpression startExpression = (MultiplicativeExpression) args[0];
      return new MultiplicativeExpression(startExpression, type, secondExpression,
                                          ParseInfo.combine(startExpression.getParseInfo(), (ParseInfo) args[1], secondExpression.getParseInfo()));
    }
    Expression firstExpression = (Expression) args[0];
    return new MultiplicativeExpression(firstExpression, type, secondExpression,
                                        ParseInfo.combine(firstExpression.getParseInfo(), (ParseInfo) args[1], secondExpression.getParseInfo()));
  }

}
