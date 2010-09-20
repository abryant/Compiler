package compiler.language.parser.rules.expression;

import static compiler.language.parser.ParseType.ADDITIVE_EXPRESSION;
import static compiler.language.parser.ParseType.MINUS;
import static compiler.language.parser.ParseType.MULTIPLICATIVE_EXPRESSION;
import static compiler.language.parser.ParseType.PLUS;
import static compiler.language.parser.ParseType.QNAME_EXPRESSION;

import compiler.language.ast.ParseInfo;
import compiler.language.ast.expression.AdditiveExpression;
import compiler.language.ast.expression.AdditiveExpressionType;
import compiler.language.ast.expression.Expression;
import compiler.parser.ParseException;
import compiler.parser.Production;
import compiler.parser.Rule;

/*
 * Created on 8 Aug 2010
 */

/**
 * @author Anthony Bryant
 */
public class AdditiveExpressionRule extends Rule
{
  private static final long serialVersionUID = 1L;

  private static final Production START_PRODUCTION             = new Production(MULTIPLICATIVE_EXPRESSION);
  private static final Production PLUS_PRODUCTION              = new Production(ADDITIVE_EXPRESSION, PLUS,  MULTIPLICATIVE_EXPRESSION);
  private static final Production PLUS_QNAME_PRODUCTION        = new Production(ADDITIVE_EXPRESSION, PLUS,  QNAME_EXPRESSION);
  private static final Production QNAME_PLUS_PRODUCTION        = new Production(QNAME_EXPRESSION,    PLUS,  MULTIPLICATIVE_EXPRESSION);
  private static final Production QNAME_PLUS_QNAME_PRODUCTION  = new Production(QNAME_EXPRESSION,    PLUS,  QNAME_EXPRESSION);
  private static final Production MINUS_PRODUCTION             = new Production(ADDITIVE_EXPRESSION, MINUS, MULTIPLICATIVE_EXPRESSION);
  private static final Production MINUS_QNAME_PRODUCTION       = new Production(ADDITIVE_EXPRESSION, MINUS, QNAME_EXPRESSION);
  private static final Production QNAME_MINUS_PRODUCTION       = new Production(QNAME_EXPRESSION,    MINUS, MULTIPLICATIVE_EXPRESSION);
  private static final Production QNAME_MINUS_QNAME_PRODUCTION = new Production(QNAME_EXPRESSION,    MINUS, QNAME_EXPRESSION);

  public AdditiveExpressionRule()
  {
    super(ADDITIVE_EXPRESSION, START_PRODUCTION,
                               PLUS_PRODUCTION,  PLUS_QNAME_PRODUCTION,  QNAME_PLUS_PRODUCTION,  QNAME_PLUS_QNAME_PRODUCTION,
                               MINUS_PRODUCTION, MINUS_QNAME_PRODUCTION, QNAME_MINUS_PRODUCTION, QNAME_MINUS_QNAME_PRODUCTION);
  }

  /**
   * {@inheritDoc}
   * @see compiler.parser.Rule#match(compiler.parser.Production, java.lang.Object[])
   */
  @Override
  public Object match(Production production, Object[] args) throws ParseException
  {
    if (START_PRODUCTION.equals(production))
    {
      // return the existing Expression
      return args[0];
    }

    AdditiveExpressionType type = null;
    if (PLUS_PRODUCTION.equals(production) || PLUS_QNAME_PRODUCTION.equals(production) || QNAME_PLUS_PRODUCTION.equals(production) || QNAME_PLUS_QNAME_PRODUCTION.equals(production))
    {
      type = AdditiveExpressionType.PLUS;
    }
    else if (MINUS_PRODUCTION.equals(production) || MINUS_QNAME_PRODUCTION.equals(production) || QNAME_MINUS_PRODUCTION.equals(production) || QNAME_MINUS_QNAME_PRODUCTION.equals(production))
    {
      type = AdditiveExpressionType.MINUS;
    }
    else
    {
      throw badTypeList();
    }
    Expression secondExpression = (Expression) args[2];

    if (args[0] instanceof AdditiveExpression)
    {
      // continue the existing AdditiveExpression if we've already started one
      AdditiveExpression startExpression = (AdditiveExpression) args[0];
      return new AdditiveExpression(startExpression, type, secondExpression,
                                    ParseInfo.combine(startExpression.getParseInfo(), (ParseInfo) args[1], secondExpression.getParseInfo()));
    }
    Expression firstExpression = (Expression) args[0];
    return new AdditiveExpression(firstExpression, type, secondExpression,
                                  ParseInfo.combine(firstExpression.getParseInfo(), (ParseInfo) args[1], secondExpression.getParseInfo()));
  }

}
