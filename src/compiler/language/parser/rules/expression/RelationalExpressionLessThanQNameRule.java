package compiler.language.parser.rules.expression;

import static compiler.language.parser.ParseType.LANGLE;
import static compiler.language.parser.ParseType.NESTED_QNAME_LIST;
import static compiler.language.parser.ParseType.QNAME;
import static compiler.language.parser.ParseType.RELATIONAL_EXPRESSION_LESS_THAN_QNAME;

import compiler.language.ast.ParseInfo;
import compiler.language.ast.expression.Expression;
import compiler.language.ast.expression.FieldAccessExpression;
import compiler.language.ast.expression.RelationalExpression;
import compiler.language.ast.expression.RelationalExpressionType;
import compiler.language.ast.misc.QName;
import compiler.language.ast.misc.QNameElement;
import compiler.parser.ParseException;
import compiler.parser.Rule;

/*
 * Created on 19 Aug 2010
 */

/**
 * @author Anthony Bryant
 */
public class RelationalExpressionLessThanQNameRule extends Rule
{

  private static final Object[] QNAME_LESS_THAN_QNAME_PRODUCTION               = new Object[] {QNAME,             LANGLE, QNAME};
  private static final Object[] QNAME_LESS_THAN_NESTED_QNAME_PRODUCTION        = new Object[] {QNAME,             LANGLE, NESTED_QNAME_LIST};
  private static final Object[] NESTED_QNAME_LESS_THAN_QNAME_PRODUCTION        = new Object[] {NESTED_QNAME_LIST, LANGLE, QNAME};
  private static final Object[] NESTED_QNAME_LESS_THAN_NESTED_QNAME_PRODUCTION = new Object[] {NESTED_QNAME_LIST, LANGLE, NESTED_QNAME_LIST};

  public RelationalExpressionLessThanQNameRule()
  {
    super(RELATIONAL_EXPRESSION_LESS_THAN_QNAME, QNAME_LESS_THAN_QNAME_PRODUCTION,
                                                 QNAME_LESS_THAN_NESTED_QNAME_PRODUCTION,
                                                 NESTED_QNAME_LESS_THAN_QNAME_PRODUCTION,
                                                 NESTED_QNAME_LESS_THAN_NESTED_QNAME_PRODUCTION);
  }

  /**
   * {@inheritDoc}
   * @see compiler.parser.Rule#match(java.lang.Object[], java.lang.Object[])
   */
  @Override
  public Object match(Object[] types, Object[] args) throws ParseException
  {
    if (types == QNAME_LESS_THAN_QNAME_PRODUCTION)
    {
      QName firstQName = (QName) args[0];
      QName secondQName = (QName) args[2];
      Expression firstExpression = new FieldAccessExpression(firstQName, firstQName.getParseInfo());
      Expression secondExpression = new FieldAccessExpression(secondQName, secondQName.getParseInfo());
      return new RelationalExpression(firstExpression, RelationalExpressionType.LESS_THAN, secondExpression,
                                      ParseInfo.combine(firstExpression.getParseInfo(), (ParseInfo) args[1], secondExpression.getParseInfo()));
    }
    if (types == QNAME_LESS_THAN_NESTED_QNAME_PRODUCTION)
    {
      QName qname = (QName) args[0];
      QNameElement element = (QNameElement) args[2];
      Expression firstExpression = new FieldAccessExpression(qname, qname.getParseInfo());
      Expression secondExpression = element.toExpression();
      return new RelationalExpression(firstExpression, RelationalExpressionType.LESS_THAN, secondExpression,
                                      ParseInfo.combine(firstExpression.getParseInfo(), (ParseInfo) args[1], secondExpression.getParseInfo()));
    }
    if (types == NESTED_QNAME_LESS_THAN_QNAME_PRODUCTION)
    {
      QNameElement element = (QNameElement) args[0];
      QName qname = (QName) args[2];
      Expression firstExpression = element.toExpression();
      Expression secondExpression = new FieldAccessExpression(qname, qname.getParseInfo());
      return new RelationalExpression(firstExpression, RelationalExpressionType.LESS_THAN, secondExpression,
                                      ParseInfo.combine(firstExpression.getParseInfo(), (ParseInfo) args[1], secondExpression.getParseInfo()));
    }
    if (types == NESTED_QNAME_LESS_THAN_NESTED_QNAME_PRODUCTION)
    {
      QNameElement firstElement = (QNameElement) args[0];
      QNameElement secondElement = (QNameElement) args[2];
      Expression firstExpression = firstElement.toExpression();
      Expression secondExpression = secondElement.toExpression();
      return new RelationalExpression(firstExpression, RelationalExpressionType.LESS_THAN, secondExpression,
                                      ParseInfo.combine(firstExpression.getParseInfo(), (ParseInfo) args[1], secondExpression.getParseInfo()));
    }
    throw badTypeList();
  }

}
