package compiler.language.parser.rules;

import static compiler.language.parser.ParseType.POINTER_TYPE;
import static compiler.language.parser.ParseType.PRIMITIVE_TYPE;
import static compiler.language.parser.ParseType.TUPLE_TYPE;
import static compiler.language.parser.ParseType.TYPE;
import static compiler.language.parser.ParseType.VOID_TYPE;

import compiler.parser.Rule;

/*
 * Created on 30 Jun 2010
 */

/**
 * @author Anthony Bryant
 */
public class TypeRule extends Rule
{

  private static final Object[] PRIMITIVE_TYPE_PRODUCTION = new Object[] {PRIMITIVE_TYPE};
  private static final Object[] POINTER_TYPE_PRODUCTION = new Object[] {POINTER_TYPE};
  private static final Object[] TUPLE_TYPE_PRODUCTION = new Object[] {TUPLE_TYPE};
  private static final Object[] VOID_TYPE_PRODUCTION = new Object[] {VOID_TYPE};

  public TypeRule()
  {
    super(TYPE, PRIMITIVE_TYPE_PRODUCTION, POINTER_TYPE_PRODUCTION, TUPLE_TYPE_PRODUCTION, VOID_TYPE_PRODUCTION);
  }

  /**
   * @see compiler.parser.Rule#match(java.lang.Object[], java.lang.Object[])
   */
  @Override
  public Object match(Object[] types, Object[] args)
  {
    if (types == PRIMITIVE_TYPE_PRODUCTION || types == POINTER_TYPE_PRODUCTION || types == TUPLE_TYPE_PRODUCTION || types == VOID_TYPE_PRODUCTION)
    {
      // All types are actually subclasses of Type, so just return the argument
      return args[0];
    }
    throw badTypeList();
  }

}
