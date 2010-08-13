package compiler.language.parser.rules.expression;

import static compiler.language.parser.ParseType.ARRAY_ACCESS_EXPRESSION;
import static compiler.language.parser.ParseType.DIMENSION_EXPRESSION;
import static compiler.language.parser.ParseType.PRIMARY_NO_TRAILING_DIMENSIONS_NOT_QNAME;
import static compiler.language.parser.ParseType.QNAME;

import compiler.language.ast.ParseInfo;
import compiler.language.ast.expression.ArrayAccessExpression;
import compiler.language.ast.expression.Expression;
import compiler.language.ast.expression.FieldAccessExpression;
import compiler.language.ast.misc.QName;
import compiler.parser.ParseException;
import compiler.parser.Rule;

/*
 * Created on 11 Aug 2010
 */

/**
 * @author Anthony Bryant
 */
public class ArrayAccessExpressionRule extends Rule
{

  private static final Object[] PRODUCTION = new Object[] {PRIMARY_NO_TRAILING_DIMENSIONS_NOT_QNAME, DIMENSION_EXPRESSION};
  private static final Object[] QNAME_PRODUCTION = new Object[] {QNAME, DIMENSION_EXPRESSION};

  public ArrayAccessExpressionRule()
  {
    super(ARRAY_ACCESS_EXPRESSION, PRODUCTION, QNAME_PRODUCTION);
  }

  /**
   * {@inheritDoc}
   * @see compiler.parser.Rule#match(java.lang.Object[], java.lang.Object[])
   */
  @Override
  public Object match(Object[] types, Object[] args) throws ParseException
  {
    if (types == PRODUCTION)
    {
      Expression expression = (Expression) args[0];
      Expression dimensionExpression = (Expression) args[1];
      return new ArrayAccessExpression(expression, dimensionExpression,
                                       ParseInfo.combine(expression.getParseInfo(), dimensionExpression.getParseInfo()));
    }
    if (types == QNAME_PRODUCTION)
    {
      QName qname = (QName) args[0];
      FieldAccessExpression fieldAccessExpression = new FieldAccessExpression(qname, qname.getParseInfo());
      Expression dimensionExpression = (Expression) args[1];
      return new ArrayAccessExpression(fieldAccessExpression, dimensionExpression,
                                       ParseInfo.combine(qname.getParseInfo(), dimensionExpression.getParseInfo()));
    }
    throw badTypeList();
  }

}
