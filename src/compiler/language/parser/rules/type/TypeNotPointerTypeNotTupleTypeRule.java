package compiler.language.parser.rules.type;

import static compiler.language.parser.ParseType.ARRAY_TYPE;
import static compiler.language.parser.ParseType.CLOSURE_TYPE;
import static compiler.language.parser.ParseType.PRIMITIVE_TYPE;
import static compiler.language.parser.ParseType.TYPE_NOT_POINTER_TYPE_NOT_TUPLE_TYPE;
import static compiler.language.parser.ParseType.VOID_TYPE;

import compiler.parser.ParseException;
import compiler.parser.Rule;

/*
 * Created on 9 Aug 2010
 */

/**
 * @author Anthony Bryant
 */
public class TypeNotPointerTypeNotTupleTypeRule extends Rule
{

  private static final Object[] ARRAY_TYPE_PRODUCTION = new Object[] {ARRAY_TYPE};
  private static final Object[] CLOSURE_TYPE_PRODUCTION = new Object[] {CLOSURE_TYPE};
  private static final Object[] PRIMITIVE_TYPE_PRODUCTION = new Object[] {PRIMITIVE_TYPE};
  private static final Object[] VOID_TYPE_PRODUCTION = new Object[] {VOID_TYPE};

  public TypeNotPointerTypeNotTupleTypeRule()
  {
    super(TYPE_NOT_POINTER_TYPE_NOT_TUPLE_TYPE, ARRAY_TYPE_PRODUCTION, CLOSURE_TYPE_PRODUCTION, PRIMITIVE_TYPE_PRODUCTION, VOID_TYPE_PRODUCTION);
  }

  /**
   * {@inheritDoc}
   * @see compiler.parser.Rule#match(java.lang.Object[], java.lang.Object[])
   */
  @Override
  public Object match(Object[] types, Object[] args) throws ParseException
  {
    if (types == ARRAY_TYPE_PRODUCTION || types == CLOSURE_TYPE_PRODUCTION ||
        types == PRIMITIVE_TYPE_PRODUCTION || types == VOID_TYPE_PRODUCTION)
    {
      // All types are actually subclasses of Type, so just return the argument
      return args[0];
    }
    throw badTypeList();
  }

}
