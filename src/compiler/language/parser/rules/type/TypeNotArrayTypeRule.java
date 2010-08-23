package compiler.language.parser.rules.type;

import static compiler.language.parser.ParseType.CLOSURE_TYPE;
import static compiler.language.parser.ParseType.POINTER_TYPE;
import static compiler.language.parser.ParseType.PRIMITIVE_TYPE;
import static compiler.language.parser.ParseType.TUPLE_TYPE;
import static compiler.language.parser.ParseType.TYPE_NOT_ARRAY_TYPE;
import static compiler.language.parser.ParseType.VOID_TYPE;

import compiler.parser.ParseException;
import compiler.parser.Rule;

/*
 * Created on 12 Aug 2010
 */

/**
 * @author Anthony Bryant
 */
public class TypeNotArrayTypeRule extends Rule
{

  private static final Object[] CLOSURE_TYPE_PRODUCTION = new Object[] {CLOSURE_TYPE};
  private static final Object[] POINTER_TYPE_PRODUCTION = new Object[] {POINTER_TYPE};
  private static final Object[] PRIMITIVE_TYPE_PRODUCTION = new Object[] {PRIMITIVE_TYPE};
  private static final Object[] TUPLE_TYPE_PRODUCTION = new Object[] {TUPLE_TYPE};
  private static final Object[] VOID_TYPE_PRODUCTION = new Object[] {VOID_TYPE};

  public TypeNotArrayTypeRule()
  {
    super(TYPE_NOT_ARRAY_TYPE, CLOSURE_TYPE_PRODUCTION, POINTER_TYPE_PRODUCTION, PRIMITIVE_TYPE_PRODUCTION, TUPLE_TYPE_PRODUCTION, VOID_TYPE_PRODUCTION);
  }

  /**
   * {@inheritDoc}
   * @see compiler.parser.Rule#match(java.lang.Object[], java.lang.Object[])
   */
  @Override
  public Object match(Object[] types, Object[] args) throws ParseException
  {
    if (types == CLOSURE_TYPE_PRODUCTION || types == POINTER_TYPE_PRODUCTION || types == PRIMITIVE_TYPE_PRODUCTION ||
        types == TUPLE_TYPE_PRODUCTION   || types == VOID_TYPE_PRODUCTION)
    {
      // All types are actually subclasses of Type, so just return the argument
      return args[0];
    }
    throw badTypeList();
  }

}
