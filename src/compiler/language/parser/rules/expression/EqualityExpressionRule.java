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
import compiler.parser.Rule;

/*
 * Created on 4 Aug 2010
 */

/**
 * @author Anthony Bryant
 */
public class EqualityExpressionRule extends Rule
{

  private static final Object[] START_PRODUCTION                  = new Object[] {RELATIONAL_EXPRESSION_NOT_LESS_THAN_QNAME};
  private static final Object[] EQUALS_PRODUCTION                 = new Object[] {EQUALITY_EXPRESSION,           DOUBLE_EQUALS,            RELATIONAL_EXPRESSION_NOT_LESS_THAN_QNAME};
  private static final Object[] EQUALS_QNAME_PRODUCTION           = new Object[] {EQUALITY_EXPRESSION,           DOUBLE_EQUALS,            QNAME_OR_LESS_THAN_EXPRESSION};
  private static final Object[] QNAME_EQUALS_PRODUCTION           = new Object[] {QNAME_OR_LESS_THAN_EXPRESSION, DOUBLE_EQUALS,            RELATIONAL_EXPRESSION_NOT_LESS_THAN_QNAME};
  private static final Object[] QNAME_EQUALS_QNAME_PRODUCTION     = new Object[] {QNAME_OR_LESS_THAN_EXPRESSION, DOUBLE_EQUALS,            QNAME_OR_LESS_THAN_EXPRESSION};
  private static final Object[] NOT_EQUALS_PRODUCTION             = new Object[] {EQUALITY_EXPRESSION,           EXCLAIMATION_MARK_EQUALS, RELATIONAL_EXPRESSION_NOT_LESS_THAN_QNAME};
  private static final Object[] NOT_EQUALS_QNAME_PRODUCTION       = new Object[] {EQUALITY_EXPRESSION,           EXCLAIMATION_MARK_EQUALS, QNAME_OR_LESS_THAN_EXPRESSION};
  private static final Object[] QNAME_NOT_EQUALS_PRODUCTION       = new Object[] {QNAME_OR_LESS_THAN_EXPRESSION, EXCLAIMATION_MARK_EQUALS, RELATIONAL_EXPRESSION_NOT_LESS_THAN_QNAME};
  private static final Object[] QNAME_NOT_EQUALS_QNAME_PRODUCTION = new Object[] {QNAME_OR_LESS_THAN_EXPRESSION, EXCLAIMATION_MARK_EQUALS, QNAME_OR_LESS_THAN_EXPRESSION};

  public EqualityExpressionRule()
  {
    super(EQUALITY_EXPRESSION, START_PRODUCTION,
                               EQUALS_PRODUCTION,     EQUALS_QNAME_PRODUCTION,     QNAME_EQUALS_PRODUCTION,     QNAME_EQUALS_QNAME_PRODUCTION,
                               NOT_EQUALS_PRODUCTION, NOT_EQUALS_QNAME_PRODUCTION, QNAME_NOT_EQUALS_PRODUCTION, QNAME_NOT_EQUALS_QNAME_PRODUCTION);
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
      // return the existing expression
      return args[0];
    }

    EqualityExpressionType separator = null;
    if (types == EQUALS_PRODUCTION || types == EQUALS_QNAME_PRODUCTION || types == QNAME_EQUALS_PRODUCTION || types == QNAME_EQUALS_QNAME_PRODUCTION)
    {
      separator = EqualityExpressionType.EQUAL;
    }
    else if (types == NOT_EQUALS_PRODUCTION || types == NOT_EQUALS_QNAME_PRODUCTION || types == QNAME_NOT_EQUALS_PRODUCTION || types == QNAME_NOT_EQUALS_QNAME_PRODUCTION)
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
