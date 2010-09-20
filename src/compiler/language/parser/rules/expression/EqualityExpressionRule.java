package compiler.language.parser.rules.expression;

import static compiler.language.parser.ParseType.DOUBLE_EQUALS;
import static compiler.language.parser.ParseType.EQUALITY_EXPRESSION;
import static compiler.language.parser.ParseType.EXCLAIMATION_MARK_EQUALS;
import static compiler.language.parser.ParseType.QNAME_OR_LESS_THAN_EXPRESSION;
import static compiler.language.parser.ParseType.RELATIONAL_EXPRESSION_NOT_LESS_THAN_QNAME;

import compiler.language.ast.ParseInfo;
import compiler.language.ast.expression.EqualityExpression;
import compiler.language.ast.expression.EqualityExpressionType;
import compiler.language.ast.expression.Expression;
import compiler.parser.ParseException;
import compiler.parser.Production;
import compiler.parser.Rule;

/*
 * Created on 4 Aug 2010
 */

/**
 * @author Anthony Bryant
 */
public class EqualityExpressionRule extends Rule
{
  private static final long serialVersionUID = 1L;

  private static final Production START_PRODUCTION                  = new Production(RELATIONAL_EXPRESSION_NOT_LESS_THAN_QNAME);
  private static final Production EQUALS_PRODUCTION                 = new Production(EQUALITY_EXPRESSION,           DOUBLE_EQUALS,            RELATIONAL_EXPRESSION_NOT_LESS_THAN_QNAME);
  private static final Production EQUALS_QNAME_PRODUCTION           = new Production(EQUALITY_EXPRESSION,           DOUBLE_EQUALS,            QNAME_OR_LESS_THAN_EXPRESSION);
  private static final Production QNAME_EQUALS_PRODUCTION           = new Production(QNAME_OR_LESS_THAN_EXPRESSION, DOUBLE_EQUALS,            RELATIONAL_EXPRESSION_NOT_LESS_THAN_QNAME);
  private static final Production QNAME_EQUALS_QNAME_PRODUCTION     = new Production(QNAME_OR_LESS_THAN_EXPRESSION, DOUBLE_EQUALS,            QNAME_OR_LESS_THAN_EXPRESSION);
  private static final Production NOT_EQUALS_PRODUCTION             = new Production(EQUALITY_EXPRESSION,           EXCLAIMATION_MARK_EQUALS, RELATIONAL_EXPRESSION_NOT_LESS_THAN_QNAME);
  private static final Production NOT_EQUALS_QNAME_PRODUCTION       = new Production(EQUALITY_EXPRESSION,           EXCLAIMATION_MARK_EQUALS, QNAME_OR_LESS_THAN_EXPRESSION);
  private static final Production QNAME_NOT_EQUALS_PRODUCTION       = new Production(QNAME_OR_LESS_THAN_EXPRESSION, EXCLAIMATION_MARK_EQUALS, RELATIONAL_EXPRESSION_NOT_LESS_THAN_QNAME);
  private static final Production QNAME_NOT_EQUALS_QNAME_PRODUCTION = new Production(QNAME_OR_LESS_THAN_EXPRESSION, EXCLAIMATION_MARK_EQUALS, QNAME_OR_LESS_THAN_EXPRESSION);

  public EqualityExpressionRule()
  {
    super(EQUALITY_EXPRESSION, START_PRODUCTION,
                               EQUALS_PRODUCTION,     EQUALS_QNAME_PRODUCTION,     QNAME_EQUALS_PRODUCTION,     QNAME_EQUALS_QNAME_PRODUCTION,
                               NOT_EQUALS_PRODUCTION, NOT_EQUALS_QNAME_PRODUCTION, QNAME_NOT_EQUALS_PRODUCTION, QNAME_NOT_EQUALS_QNAME_PRODUCTION);
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
      // return the existing expression
      return args[0];
    }

    EqualityExpressionType separator = null;
    if (EQUALS_PRODUCTION.equals(production) || EQUALS_QNAME_PRODUCTION.equals(production) || QNAME_EQUALS_PRODUCTION.equals(production) || QNAME_EQUALS_QNAME_PRODUCTION.equals(production))
    {
      separator = EqualityExpressionType.EQUAL;
    }
    else if (NOT_EQUALS_PRODUCTION.equals(production) || NOT_EQUALS_QNAME_PRODUCTION.equals(production) || QNAME_NOT_EQUALS_PRODUCTION.equals(production) || QNAME_NOT_EQUALS_QNAME_PRODUCTION.equals(production))
    {
      separator = EqualityExpressionType.NOT_EQUAL;
    }
    else
    {
      throw badTypeList();
    }
    Expression newExpression = (Expression) args[2];

    if (args[0] instanceof EqualityExpression)
    {
      // continue the current EqualityExpression if we've started one
      EqualityExpression startExpression = (EqualityExpression) args[0];
      return new EqualityExpression(startExpression, separator, newExpression, ParseInfo.combine(startExpression.getParseInfo(), (ParseInfo) args[1], newExpression.getParseInfo()));
    }
    Expression firstExpression = (Expression) args[0];
    return new EqualityExpression(firstExpression, separator, newExpression, ParseInfo.combine(firstExpression.getParseInfo(), (ParseInfo) args[1], newExpression.getParseInfo()));
  }

}
