package compiler.language.parser.rules.expression;

import compiler.language.ast.expression.FieldAccessExpression;
import compiler.language.ast.misc.QName;
import compiler.language.ast.misc.QNameElement;
import compiler.language.parser.ParseType;
import compiler.parser.ParseException;
import compiler.parser.Rule;

/*
 * Created on 17 Aug 2010
 */

/**
 * @author Anthony Bryant
 */
public class QNameExpressionRule extends Rule
{

  private static final Object[] QNAME_PRODUCTION = new Object[] {ParseType.QNAME};
  private static final Object[] NESTED_QNAME_PRODUCTION = new Object[] {ParseType.NESTED_QNAME_LIST};

  public QNameExpressionRule()
  {
    super(ParseType.QNAME_EXPRESSION, QNAME_PRODUCTION, NESTED_QNAME_PRODUCTION);
  }

  /**
   * {@inheritDoc}
   * @see compiler.parser.Rule#match(java.lang.Object[], java.lang.Object[])
   */
  @Override
  public Object match(Object[] types, Object[] args) throws ParseException
  {
    if (types == QNAME_PRODUCTION)
    {
      QName qname = (QName) args[0];
      return new FieldAccessExpression(qname, qname.getParseInfo());
    }
    if (types == NESTED_QNAME_PRODUCTION)
    {
      QNameElement element = (QNameElement) args[0];
      return element.toExpression();
    }
    throw badTypeList();
  }

}
