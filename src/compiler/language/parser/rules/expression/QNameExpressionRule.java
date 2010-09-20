package compiler.language.parser.rules.expression;

import compiler.language.ast.expression.FieldAccessExpression;
import compiler.language.ast.misc.QName;
import compiler.language.ast.misc.QNameElement;
import compiler.language.parser.ParseType;
import compiler.parser.ParseException;
import compiler.parser.Production;
import compiler.parser.Rule;

/*
 * Created on 17 Aug 2010
 */

/**
 * @author Anthony Bryant
 */
public class QNameExpressionRule extends Rule
{
  private static final long serialVersionUID = 1L;

  private static final Production QNAME_PRODUCTION = new Production(ParseType.QNAME);
  private static final Production NESTED_QNAME_PRODUCTION = new Production(ParseType.NESTED_QNAME_LIST);

  public QNameExpressionRule()
  {
    super(ParseType.QNAME_EXPRESSION, QNAME_PRODUCTION, NESTED_QNAME_PRODUCTION);
  }

  /**
   * {@inheritDoc}
   * @see compiler.parser.Rule#match(compiler.parser.Production, java.lang.Object[])
   */
  @Override
  public Object match(Production production, Object[] args) throws ParseException
  {
    if (QNAME_PRODUCTION.equals(production))
    {
      QName qname = (QName) args[0];
      return new FieldAccessExpression(qname, qname.getParseInfo());
    }
    if (NESTED_QNAME_PRODUCTION.equals(production))
    {
      QNameElement element = (QNameElement) args[0];
      return element.toExpression();
    }
    throw badTypeList();
  }

}
