package compiler.language.parser.rules.type;

import static compiler.language.parser.ParseType.NESTED_QNAME_LIST;
import static compiler.language.parser.ParseType.TUPLE_TYPE;
import static compiler.language.parser.ParseType.TUPLE_TYPE_NOT_QNAME_LIST;

import compiler.language.ast.misc.QNameElement;
import compiler.parser.ParseException;
import compiler.parser.Production;
import compiler.parser.Rule;

/*
 * Created on 13 Jul 2010
 */

/**
 * @author Anthony Bryant
 */
public class TupleTypeRule extends Rule
{
  private static final long serialVersionUID = 1L;

  private static final Production PRODUCTION = new Production(TUPLE_TYPE_NOT_QNAME_LIST);
  private static final Production QNAME_LIST_PRODUCTION = new Production(NESTED_QNAME_LIST);

  public TupleTypeRule()
  {
    super(TUPLE_TYPE, PRODUCTION, QNAME_LIST_PRODUCTION);
  }

  /**
   * @see compiler.parser.Rule#match(compiler.parser.Production, java.lang.Object[])
   */
  @Override
  public Object match(Production production, Object[] args) throws ParseException
  {
    if (PRODUCTION.equals(production))
    {
      // return the existing TupleType
      return args[0];
    }
    if (QNAME_LIST_PRODUCTION.equals(production))
    {
      QNameElement element = (QNameElement) args[0];
      // since this QNameElement is from the NESTED_QNAME_LIST rule, it must be
      // a compound element which will return a TupleType
      return element.toType();
    }
    throw badTypeList();
  }

}
