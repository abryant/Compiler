package compiler.language.parser.rules.expression;

import static compiler.language.parser.ParseType.QNAME_EXPRESSION;
import static compiler.language.parser.ParseType.QNAME_OR_LESS_THAN_EXPRESSION;
import static compiler.language.parser.ParseType.RELATIONAL_EXPRESSION_LESS_THAN_QNAME;

import compiler.parser.ParseException;
import compiler.parser.Production;
import compiler.parser.Rule;

/*
 * Created on 19 Aug 2010
 */

/**
 * @author Anthony Bryant
 */
public class QNameOrLessThanExpressionRule extends Rule
{
  private static final long serialVersionUID = 1L;

  private static final Production QNAME_PRODUCTION = new Production(QNAME_EXPRESSION);
  private static final Production LESS_THAN_PRODUCTION = new Production(RELATIONAL_EXPRESSION_LESS_THAN_QNAME);

  public QNameOrLessThanExpressionRule()
  {
    super(QNAME_OR_LESS_THAN_EXPRESSION, QNAME_PRODUCTION, LESS_THAN_PRODUCTION);
  }

  /**
   * {@inheritDoc}
   * @see compiler.parser.Rule#match(compiler.parser.Production, java.lang.Object[])
   */
  @Override
  public Object match(Production production, Object[] args) throws ParseException
  {
    if (QNAME_PRODUCTION.equals(production) || LESS_THAN_PRODUCTION.equals(production))
    {
      // both of the productions result in an Expression, so just return it
      return args[0];
    }
    throw badTypeList();
  }

}
