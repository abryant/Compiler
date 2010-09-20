package compiler.language.parser.rules.expression;

import static compiler.language.parser.ParseType.QNAME_EXPRESSION;
import static compiler.language.parser.ParseType.QNAME_OR_LESS_THAN_EXPRESSION;
import static compiler.language.parser.ParseType.RELATIONAL_EXPRESSION_LESS_THAN_QNAME;

import compiler.language.parser.ParseType;
import compiler.parser.ParseException;
import compiler.parser.Production;
import compiler.parser.Rule;

/*
 * Created on 19 Aug 2010
 */

/**
 * @author Anthony Bryant
 */
public final class QNameOrLessThanExpressionRule extends Rule<ParseType>
{
  private static final long serialVersionUID = 1L;

  private static final Production<ParseType> QNAME_PRODUCTION = new Production<ParseType>(QNAME_EXPRESSION);
  private static final Production<ParseType> LESS_THAN_PRODUCTION = new Production<ParseType>(RELATIONAL_EXPRESSION_LESS_THAN_QNAME);

  @SuppressWarnings("unchecked")
  public QNameOrLessThanExpressionRule()
  {
    super(QNAME_OR_LESS_THAN_EXPRESSION, QNAME_PRODUCTION, LESS_THAN_PRODUCTION);
  }

  /**
   * {@inheritDoc}
   * @see compiler.parser.Rule#match(compiler.parser.Production, java.lang.Object[])
   */
  @Override
  public Object match(Production<ParseType> production, Object[] args) throws ParseException
  {
    if (QNAME_PRODUCTION.equals(production) || LESS_THAN_PRODUCTION.equals(production))
    {
      // both of the productions result in an Expression, so just return it
      return args[0];
    }
    throw badTypeList();
  }

}
