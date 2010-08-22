package compiler.language.parser.rules.expression;

import static compiler.language.parser.ParseType.EXPRESSION_NO_TUPLE;
import static compiler.language.parser.ParseType.INLINE_IF_EXPRESSION;
import static compiler.language.parser.ParseType.QNAME_OR_LESS_THAN_EXPRESSION;

import compiler.parser.ParseException;
import compiler.parser.Rule;

/*
 * Created on 4 Aug 2010
 */

/**
 * @author Anthony Bryant
 */
public class ExpressionNoTupleRule extends Rule
{

  private static final Object[] PRODUCTION = new Object[] {INLINE_IF_EXPRESSION};
  private static final Object[] QNAME_PRODUCTION = new Object[] {QNAME_OR_LESS_THAN_EXPRESSION};

  public ExpressionNoTupleRule()
  {
    super(EXPRESSION_NO_TUPLE, PRODUCTION, QNAME_PRODUCTION);
  }

  /**
   * {@inheritDoc}
   * @see compiler.parser.Rule#match(java.lang.Object[], java.lang.Object[])
   */
  @Override
  public Object match(Object[] types, Object[] args) throws ParseException
  {
    if (types == PRODUCTION || types == QNAME_PRODUCTION)
    {
      // return the already created Expression
      return args[0];
    }
    throw badTypeList();
  }

}
