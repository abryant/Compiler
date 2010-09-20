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
public final class QNameExpressionRule extends Rule<ParseType>
{
  private static final long serialVersionUID = 1L;

  private static final Production<ParseType> QNAME_PRODUCTION = new Production<ParseType>(ParseType.QNAME);
  private static final Production<ParseType> NESTED_QNAME_PRODUCTION = new Production<ParseType>(ParseType.NESTED_QNAME_LIST);

  @SuppressWarnings("unchecked")
  public QNameExpressionRule()
  {
    super(ParseType.QNAME_EXPRESSION, QNAME_PRODUCTION, NESTED_QNAME_PRODUCTION);
  }

  /**
   * {@inheritDoc}
   * @see compiler.parser.Rule#match(compiler.parser.Production, java.lang.Object[])
   */
  @Override
  public Object match(Production<ParseType> production, Object[] args) throws ParseException
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
