package compiler.language.parser.rules.expression;

import static compiler.language.parser.ParseType.EXPRESSION_NO_TUPLE;
import static compiler.language.parser.ParseType.INLINE_IF_EXPRESSION;
import static compiler.language.parser.ParseType.QNAME_OR_LESS_THAN_EXPRESSION;

import compiler.parser.ParseException;
import compiler.parser.Production;
import compiler.parser.Rule;

/*
 * Created on 4 Aug 2010
 */

/**
 * @author Anthony Bryant
 */
public class ExpressionNoTupleRule extends Rule
{
  private static final long serialVersionUID = 1L;

  private static final Production PRODUCTION = new Production(INLINE_IF_EXPRESSION);
  private static final Production QNAME_PRODUCTION = new Production(QNAME_OR_LESS_THAN_EXPRESSION);

  public ExpressionNoTupleRule()
  {
    super(EXPRESSION_NO_TUPLE, PRODUCTION, QNAME_PRODUCTION);
  }

  /**
   * {@inheritDoc}
   * @see compiler.parser.Rule#match(compiler.parser.Production, java.lang.Object[])
   */
  @Override
  public Object match(Production production, Object[] args) throws ParseException
  {
    if (PRODUCTION.equals(production) || QNAME_PRODUCTION.equals(production))
    {
      // return the already created Expression
      return args[0];
    }
    throw badTypeList();
  }

}
