package compiler.language.parser.rules.type;

import static compiler.language.parser.ParseType.NESTED_QNAME_LIST;
import static compiler.language.parser.ParseType.TUPLE_TYPE;
import static compiler.language.parser.ParseType.TUPLE_TYPE_NOT_QNAME_LIST;

import compiler.language.ast.misc.QNameElement;
import compiler.parser.ParseException;
import compiler.parser.Rule;

/*
 * Created on 13 Jul 2010
 */

/**
 * @author Anthony Bryant
 */
public class TupleTypeRule extends Rule
{

  private static final Object[] PRODUCTION = new Object[] {TUPLE_TYPE_NOT_QNAME_LIST};
  private static final Object[] QNAME_LIST_PRODUCTION = new Object[] {NESTED_QNAME_LIST};

  public TupleTypeRule()
  {
    super(TUPLE_TYPE, PRODUCTION, QNAME_LIST_PRODUCTION);
  }

  /**
   * @see compiler.parser.Rule#match(java.lang.Object[], java.lang.Object[])
   */
  @Override
  public Object match(Object[] types, Object[] args) throws ParseException
  {
    if (types == PRODUCTION)
    {
      // return the existing TupleType
      return args[0];
    }
    if (types == QNAME_LIST_PRODUCTION)
    {
      QNameElement element = (QNameElement) args[0];
      // since this QNameElement is from the NESTED_QNAME_LIST rule, it must be
      // a compound element which will return a TupleType
      return element.toType();
    }
    throw badTypeList();
  }

}
