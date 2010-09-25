package compiler.language.parser.rules.expression;

import static compiler.language.parser.ParseType.ADDITIVE_EXPRESSION;
import static compiler.language.parser.ParseType.MINUS;
import static compiler.language.parser.ParseType.MULTIPLICATIVE_EXPRESSION;
import static compiler.language.parser.ParseType.PLUS;
import static compiler.language.parser.ParseType.QNAME_EXPRESSION;

import compiler.language.ast.ParseInfo;
import compiler.language.ast.expression.AdditiveExpressionAST;
import compiler.language.ast.expression.AdditiveExpressionTypeAST;
import compiler.language.ast.expression.ExpressionAST;
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
public final class AdditiveExpressionRule extends Rule<ParseType>
{
  private static final long serialVersionUID = 1L;

  private static final Production<ParseType> START_PRODUCTION             = new Production<ParseType>(MULTIPLICATIVE_EXPRESSION);
  private static final Production<ParseType> PLUS_PRODUCTION              = new Production<ParseType>(ADDITIVE_EXPRESSION, PLUS,  MULTIPLICATIVE_EXPRESSION);
  private static final Production<ParseType> PLUS_QNAME_PRODUCTION        = new Production<ParseType>(ADDITIVE_EXPRESSION, PLUS,  QNAME_EXPRESSION);
  private static final Production<ParseType> QNAME_PLUS_PRODUCTION        = new Production<ParseType>(QNAME_EXPRESSION,    PLUS,  MULTIPLICATIVE_EXPRESSION);
  private static final Production<ParseType> QNAME_PLUS_QNAME_PRODUCTION  = new Production<ParseType>(QNAME_EXPRESSION,    PLUS,  QNAME_EXPRESSION);
  private static final Production<ParseType> MINUS_PRODUCTION             = new Production<ParseType>(ADDITIVE_EXPRESSION, MINUS, MULTIPLICATIVE_EXPRESSION);
  private static final Production<ParseType> MINUS_QNAME_PRODUCTION       = new Production<ParseType>(ADDITIVE_EXPRESSION, MINUS, QNAME_EXPRESSION);
  private static final Production<ParseType> QNAME_MINUS_PRODUCTION       = new Production<ParseType>(QNAME_EXPRESSION,    MINUS, MULTIPLICATIVE_EXPRESSION);
  private static final Production<ParseType> QNAME_MINUS_QNAME_PRODUCTION = new Production<ParseType>(QNAME_EXPRESSION,    MINUS, QNAME_EXPRESSION);

  @SuppressWarnings("unchecked")
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
  public Object match(Production<ParseType> production, Object[] args) throws ParseException
  {
    if (START_PRODUCTION.equals(production))
    {
      // return the existing ExpressionAST
      return args[0];
    }

    AdditiveExpressionTypeAST type = null;
    if (PLUS_PRODUCTION.equals(production) || PLUS_QNAME_PRODUCTION.equals(production) || QNAME_PLUS_PRODUCTION.equals(production) || QNAME_PLUS_QNAME_PRODUCTION.equals(production))
    {
      type = AdditiveExpressionTypeAST.PLUS;
    }
    else if (MINUS_PRODUCTION.equals(production) || MINUS_QNAME_PRODUCTION.equals(production) || QNAME_MINUS_PRODUCTION.equals(production) || QNAME_MINUS_QNAME_PRODUCTION.equals(production))
    {
      type = AdditiveExpressionTypeAST.MINUS;
    }
    else
    {
      throw badTypeList();
    }
    ExpressionAST secondExpression = (ExpressionAST) args[2];

    if (args[0] instanceof AdditiveExpressionAST)
    {
      // continue the existing AdditiveExpressionAST if we've already started one
      AdditiveExpressionAST startExpression = (AdditiveExpressionAST) args[0];
      return new AdditiveExpressionAST(startExpression, type, secondExpression,
                                    ParseInfo.combine(startExpression.getParseInfo(), (ParseInfo) args[1], secondExpression.getParseInfo()));
    }
    ExpressionAST firstExpression = (ExpressionAST) args[0];
    return new AdditiveExpressionAST(firstExpression, type, secondExpression,
                                  ParseInfo.combine(firstExpression.getParseInfo(), (ParseInfo) args[1], secondExpression.getParseInfo()));
  }

}
