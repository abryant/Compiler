package compiler.language.parser.rules.type;

import static compiler.language.parser.ParseType.ARRAY_TYPE;
import static compiler.language.parser.ParseType.CLOSURE_TYPE;
import static compiler.language.parser.ParseType.PRIMITIVE_TYPE;
import static compiler.language.parser.ParseType.TUPLE_TYPE;
import static compiler.language.parser.ParseType.TYPE_NOT_POINTER_TYPE;
import static compiler.language.parser.ParseType.VOID_TYPE;

import compiler.parser.Rule;

/*
 * Created on 9 Aug 2010
 */

/**
 * @author Anthony Bryant
 */
public class TypeNotPointerTypeRule extends Rule
{

  private static final Object[] ARRAY_TYPE_PRODUCTION = new Object[] {ARRAY_TYPE};
  private static final Object[] CLOSURE_TYPE_PRODUCTION = new Object[] {CLOSURE_TYPE};
  private static final Object[] PRIMITIVE_TYPE_PRODUCTION = new Object[] {PRIMITIVE_TYPE};
  private static final Object[] TUPLE_TYPE_PRODUCTION = new Object[] {TUPLE_TYPE};
  private static final Object[] VOID_TYPE_PRODUCTION = new Object[] {VOID_TYPE};

  public TypeNotPointerTypeRule()
  {
    super(TYPE_NOT_POINTER_TYPE, ARRAY_TYPE_PRODUCTION, CLOSURE_TYPE_PRODUCTION, PRIMITIVE_TYPE_PRODUCTION, TUPLE_TYPE_PRODUCTION, VOID_TYPE_PRODUCTION);
  }

  /**
   * {@inheritDoc}
   * @see compiler.parser.Rule#match(java.lang.Object[], java.lang.Object[])
   */
  @Override
  public Object match(Object[] types, Object[] args)
  {
    if (types == ARRAY_TYPE_PRODUCTION || types == CLOSURE_TYPE_PRODUCTION || types == PRIMITIVE_TYPE_PRODUCTION ||
        types == TUPLE_TYPE_PRODUCTION || types == VOID_TYPE_PRODUCTION)
    {
      // All types are actually subclasses of Type, so just return the argument
      return args[0];
    }
    throw badTypeList();
  }

}
