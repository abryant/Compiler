package compiler.language.parser.rules.expression;

import static compiler.language.parser.ParseType.ARRAY_ACCESS_EXPRESSION;
import static compiler.language.parser.ParseType.DIMENSION_EXPRESSION;
import static compiler.language.parser.ParseType.NESTED_QNAME_LIST;
import static compiler.language.parser.ParseType.PRIMARY_NO_TRAILING_DIMENSIONS_NOT_QNAME;
import static compiler.language.parser.ParseType.QNAME;

import compiler.language.ast.ParseContainer;
import compiler.language.ast.ParseInfo;
import compiler.language.ast.expression.ArrayAccessExpression;
import compiler.language.ast.expression.Expression;
import compiler.language.ast.expression.FieldAccessExpression;
import compiler.language.ast.misc.QName;
import compiler.language.ast.misc.QNameElement;
import compiler.parser.ParseException;
import compiler.parser.Production;
import compiler.parser.Rule;

/*
 * Created on 11 Aug 2010
 */

/**
 * @author Anthony Bryant
 */
public class ArrayAccessExpressionRule extends Rule
{
  private static final long serialVersionUID = 1L;

  private static final Production PRODUCTION                   = new Production(PRIMARY_NO_TRAILING_DIMENSIONS_NOT_QNAME, DIMENSION_EXPRESSION);
  private static final Production QNAME_PRODUCTION             = new Production(QNAME,                                    DIMENSION_EXPRESSION);
  private static final Production NESTED_QNAME_LIST_PRODUCTION = new Production(NESTED_QNAME_LIST,                        DIMENSION_EXPRESSION);

  public ArrayAccessExpressionRule()
  {
    super(ARRAY_ACCESS_EXPRESSION, PRODUCTION, QNAME_PRODUCTION, NESTED_QNAME_LIST_PRODUCTION);
  }

  /**
   * {@inheritDoc}
   * @see compiler.parser.Rule#match(compiler.parser.Production, java.lang.Object[])
   */
  @Override
  public Object match(Production production, Object[] args) throws ParseException
  {
    if (PRODUCTION.equals(production))
    {
      Expression expression = (Expression) args[0];
      @SuppressWarnings("unchecked")
      ParseContainer<Expression> dimensionExpression = (ParseContainer<Expression>) args[1];
      return new ArrayAccessExpression(expression, dimensionExpression.getItem(),
                                       ParseInfo.combine(expression.getParseInfo(), dimensionExpression.getParseInfo()));
    }
    if (QNAME_PRODUCTION.equals(production))
    {
      QName qname = (QName) args[0];
      FieldAccessExpression fieldAccessExpression = new FieldAccessExpression(qname, qname.getParseInfo());
      @SuppressWarnings("unchecked")
      ParseContainer<Expression> dimensionExpression = (ParseContainer<Expression>) args[1];
      return new ArrayAccessExpression(fieldAccessExpression, dimensionExpression.getItem(),
                                       ParseInfo.combine(qname.getParseInfo(), dimensionExpression.getParseInfo()));
    }
    if (NESTED_QNAME_LIST_PRODUCTION.equals(production))
    {
      QNameElement element = (QNameElement) args[0];
      Expression expression = element.toExpression();
      @SuppressWarnings("unchecked")
      ParseContainer<Expression> dimensionExpression = (ParseContainer<Expression>) args[1];
      return new ArrayAccessExpression(expression, dimensionExpression.getItem(),
                                       ParseInfo.combine(expression.getParseInfo(), dimensionExpression.getParseInfo()));
    }
    throw badTypeList();
  }

}
