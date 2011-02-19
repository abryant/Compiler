package compiler.language.parser.rules.expression;

import static compiler.language.parser.ParseType.EXPRESSION_NO_TUPLE;
import static compiler.language.parser.ParseType.INLINE_IF_EXPRESSION;
import static compiler.language.parser.ParseType.QNAME_OR_LESS_THAN_EXPRESSION;
import parser.ParseException;
import parser.Production;
import parser.Rule;

import compiler.language.parser.ParseType;

/*
 * Created on 4 Aug 2010
 */

/**
 * @author Anthony Bryant
 */
public final class ExpressionNoTupleRule extends Rule<ParseType>
{
  private static final long serialVersionUID = 1L;

  private static final Production<ParseType> PRODUCTION = new Production<ParseType>(INLINE_IF_EXPRESSION);
  private static final Production<ParseType> QNAME_PRODUCTION = new Production<ParseType>(QNAME_OR_LESS_THAN_EXPRESSION);

  @SuppressWarnings("unchecked")
  public ExpressionNoTupleRule()
  {
    super(EXPRESSION_NO_TUPLE, PRODUCTION, QNAME_PRODUCTION);
  }

  /**
   * {@inheritDoc}
   * @see parser.Rule#match(parser.Production, java.lang.Object[])
   */
  @Override
  public Object match(Production<ParseType> production, Object[] args) throws ParseException
  {
    if (PRODUCTION.equals(production) || QNAME_PRODUCTION.equals(production))
    {
      // return the already created ExpressionAST
      return args[0];
    }
    throw badTypeList();
  }

}
