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
import compiler.language.parser.ParseType;
import compiler.parser.ParseException;
import compiler.parser.Production;
import compiler.parser.Rule;

/*
 * Created on 8 Aug 2010
 */

/**
 * @author Anthony Bryant
 */
public final class MultiplicativeExpressionRule extends Rule<ParseType>
{
  private static final long serialVersionUID = 1L;

  private static final Production<ParseType> START_PRODUCTION               = new Production<ParseType>(TUPLE_INDEX_EXPRESSION);
  private static final Production<ParseType> TIMES_PRODUCTION               = new Production<ParseType>(MULTIPLICATIVE_EXPRESSION, STAR,          TUPLE_INDEX_EXPRESSION);
  private static final Production<ParseType> TIMES_QNAME_PRODUCTION         = new Production<ParseType>(MULTIPLICATIVE_EXPRESSION, STAR,          QNAME_EXPRESSION);
  private static final Production<ParseType> QNAME_TIMES_PRODUCTION         = new Production<ParseType>(QNAME_EXPRESSION,          STAR,          TUPLE_INDEX_EXPRESSION);
  private static final Production<ParseType> QNAME_TIMES_QNAME_PRODUCTION   = new Production<ParseType>(QNAME_EXPRESSION,          STAR,          QNAME_EXPRESSION);
  private static final Production<ParseType> DIVIDE_PRODUCTION              = new Production<ParseType>(MULTIPLICATIVE_EXPRESSION, FORWARD_SLASH, TUPLE_INDEX_EXPRESSION);
  private static final Production<ParseType> DIVIDE_QNAME_PRODUCTION        = new Production<ParseType>(MULTIPLICATIVE_EXPRESSION, FORWARD_SLASH, QNAME_EXPRESSION);
  private static final Production<ParseType> QNAME_DIVIDE_PRODUCTION        = new Production<ParseType>(QNAME_EXPRESSION,          FORWARD_SLASH, TUPLE_INDEX_EXPRESSION);
  private static final Production<ParseType> QNAME_DIVIDE_QNAME_PRODUCTION  = new Production<ParseType>(QNAME_EXPRESSION,          FORWARD_SLASH, QNAME_EXPRESSION);
  private static final Production<ParseType> MODULUS_PRODUCTION             = new Production<ParseType>(MULTIPLICATIVE_EXPRESSION, PERCENT,       TUPLE_INDEX_EXPRESSION);
  private static final Production<ParseType> MODULUS_QNAME_PRODUCTION       = new Production<ParseType>(MULTIPLICATIVE_EXPRESSION, PERCENT,       QNAME_EXPRESSION);
  private static final Production<ParseType> QNAME_MODULUS_PRODUCTION       = new Production<ParseType>(QNAME_EXPRESSION,          PERCENT,       TUPLE_INDEX_EXPRESSION);
  private static final Production<ParseType> QNAME_MODULUS_QNAME_PRODUCTION = new Production<ParseType>(QNAME_EXPRESSION,          PERCENT,       QNAME_EXPRESSION);

  @SuppressWarnings("unchecked")
  public MultiplicativeExpressionRule()
  {
    super(MULTIPLICATIVE_EXPRESSION, START_PRODUCTION,
                                     TIMES_PRODUCTION,   TIMES_QNAME_PRODUCTION,   QNAME_TIMES_PRODUCTION,   QNAME_TIMES_QNAME_PRODUCTION,
                                     DIVIDE_PRODUCTION,  DIVIDE_QNAME_PRODUCTION,  QNAME_DIVIDE_PRODUCTION,  QNAME_DIVIDE_QNAME_PRODUCTION,
                                     MODULUS_PRODUCTION, MODULUS_QNAME_PRODUCTION, QNAME_MODULUS_PRODUCTION, QNAME_MODULUS_QNAME_PRODUCTION);
  }

  /**
   * {@inheritDoc}
   * @see compiler.parser.Rule#match(compiler.parser.Production, java.lang.Object[])
   */
  @Override
  public Object match(Production<ParseType> production, Object[] args) throws ParseException
  {
    if (START_PRODUCTION.equals(production))
    {
      // return the existing Expression
      return args[0];
    }

    MultiplicativeExpressionType type = null;
    if (TIMES_PRODUCTION.equals(production) || TIMES_QNAME_PRODUCTION.equals(production) || QNAME_TIMES_PRODUCTION.equals(production) || QNAME_TIMES_QNAME_PRODUCTION.equals(production))
    {
      type = MultiplicativeExpressionType.TIMES;
    }
    else if (DIVIDE_PRODUCTION.equals(production) || DIVIDE_QNAME_PRODUCTION.equals(production) || QNAME_DIVIDE_PRODUCTION.equals(production) || QNAME_DIVIDE_QNAME_PRODUCTION.equals(production))
    {
      type = MultiplicativeExpressionType.DIVIDE;
    }
    else if (MODULUS_PRODUCTION.equals(production) || MODULUS_QNAME_PRODUCTION.equals(production) || QNAME_MODULUS_PRODUCTION.equals(production) || QNAME_MODULUS_QNAME_PRODUCTION.equals(production))
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
