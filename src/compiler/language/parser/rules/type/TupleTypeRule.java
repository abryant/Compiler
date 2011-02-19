package compiler.language.parser.rules.type;

import static compiler.language.parser.ParseType.NESTED_QNAME_LIST;
import static compiler.language.parser.ParseType.TUPLE_TYPE;
import static compiler.language.parser.ParseType.TUPLE_TYPE_NOT_QNAME_LIST;
import parser.ParseException;
import parser.Production;
import parser.Rule;

import compiler.language.ast.misc.QNameElementAST;
import compiler.language.parser.ParseType;

/*
 * Created on 13 Jul 2010
 */

/**
 * @author Anthony Bryant
 */
public final class TupleTypeRule extends Rule<ParseType>
{
  private static final long serialVersionUID = 1L;

  private static final Production<ParseType> PRODUCTION = new Production<ParseType>(TUPLE_TYPE_NOT_QNAME_LIST);
  private static final Production<ParseType> QNAME_LIST_PRODUCTION = new Production<ParseType>(NESTED_QNAME_LIST);

  @SuppressWarnings("unchecked")
  public TupleTypeRule()
  {
    super(TUPLE_TYPE, PRODUCTION, QNAME_LIST_PRODUCTION);
  }

  /**
   * @see parser.Rule#match(parser.Production, java.lang.Object[])
   */
  @Override
  public Object match(Production<ParseType> production, Object[] args) throws ParseException
  {
    if (PRODUCTION.equals(production))
    {
      // return the existing TupleTypeAST
      return args[0];
    }
    if (QNAME_LIST_PRODUCTION.equals(production))
    {
      QNameElementAST element = (QNameElementAST) args[0];
      // since this QNameElementAST is from the NESTED_QNAME_LIST rule, it must be
      // a compound element which will return a TupleTypeAST
      return element.toType();
    }
    throw badTypeList();
  }

}
