package compiler.language.parser.rules.expression;

import static compiler.language.parser.ParseType.QNAME_EXPRESSION;
import static compiler.language.parser.ParseType.QNAME_OR_LESS_THAN_EXPRESSION;
import static compiler.language.parser.ParseType.RELATIONAL_EXPRESSION_LESS_THAN_QNAME;

import compiler.parser.ParseException;
import compiler.parser.Rule;

/*
 * Created on 19 Aug 2010
 */

/**
 * @author Anthony Bryant
 */
public class QNameOrLessThanExpressionRule extends Rule
{

  private static final Object[] QNAME_PRODUCTION = new Object[] {QNAME_EXPRESSION};
  private static final Object[] LESS_THAN_PRODUCTION = new Object[] {RELATIONAL_EXPRESSION_LESS_THAN_QNAME};

  public QNameOrLessThanExpressionRule()
  {
    super(QNAME_OR_LESS_THAN_EXPRESSION, QNAME_PRODUCTION, LESS_THAN_PRODUCTION);
  }

  /**
   * {@inheritDoc}
   * @see compiler.parser.Rule#match(java.lang.Object[], java.lang.Object[])
   */
  @Override
  public Object match(Object[] types, Object[] args) throws ParseException
  {
    if (types == QNAME_PRODUCTION || types == LESS_THAN_PRODUCTION)
    {
      // both of the productions result in an Expression, so just return it
      return args[0];
    }
    throw badTypeList();
  }

}
