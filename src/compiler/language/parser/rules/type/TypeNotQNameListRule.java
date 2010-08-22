package compiler.language.parser.rules.type;

import static compiler.language.parser.ParseType.CLOSURE_TYPE;
import static compiler.language.parser.ParseType.PRIMITIVE_TYPE;
import static compiler.language.parser.ParseType.VOID_TYPE;

import compiler.language.parser.ParseType;
import compiler.parser.ParseException;
import compiler.parser.Rule;

/*
 * Created on 17 Aug 2010
 */

/**
 * @author Anthony Bryant
 */
public class TypeNotQNameListRule extends Rule
{

  private static final Object[] ARRAY_TYPE_PRODUCTION = new Object[] {ParseType.ARRAY_TYPE};
  private static final Object[] CLOSURE_TYPE_PRODUCTION = new Object[] {CLOSURE_TYPE};
  private static final Object[] POINTER_TYPE_PRODUCTION = new Object[] {ParseType.POINTER_TYPE_NOT_QNAME};
  private static final Object[] PRIMITIVE_TYPE_PRODUCTION = new Object[] {PRIMITIVE_TYPE};
  private static final Object[] TUPLE_TYPE_PRODUCTION = new Object[] {ParseType.TUPLE_TYPE_NOT_QNAME_LIST};
  private static final Object[] VOID_TYPE_PRODUCTION = new Object[] {VOID_TYPE};

  public TypeNotQNameListRule()
  {
    super(ParseType.TYPE_NOT_QNAME_LIST, ARRAY_TYPE_PRODUCTION,
                                         CLOSURE_TYPE_PRODUCTION,
                                         POINTER_TYPE_PRODUCTION,
                                         PRIMITIVE_TYPE_PRODUCTION,
                                         TUPLE_TYPE_PRODUCTION,
                                         VOID_TYPE_PRODUCTION);
  }

  /**
   * {@inheritDoc}
   * @see compiler.parser.Rule#match(java.lang.Object[], java.lang.Object[])
   */
  @Override
  public Object match(Object[] types, Object[] args) throws ParseException
  {
    if (types == ARRAY_TYPE_PRODUCTION     || types == CLOSURE_TYPE_PRODUCTION || types == POINTER_TYPE_PRODUCTION ||
        types == PRIMITIVE_TYPE_PRODUCTION || types == TUPLE_TYPE_PRODUCTION   || types == VOID_TYPE_PRODUCTION)
    {
      // return the existing Type
      return args[0];
    }
    throw badTypeList();
  }

}
