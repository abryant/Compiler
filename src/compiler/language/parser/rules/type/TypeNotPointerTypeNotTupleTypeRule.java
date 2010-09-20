package compiler.language.parser.rules.type;

import static compiler.language.parser.ParseType.ARRAY_TYPE;
import static compiler.language.parser.ParseType.CLOSURE_TYPE;
import static compiler.language.parser.ParseType.PRIMITIVE_TYPE;
import static compiler.language.parser.ParseType.TYPE_NOT_POINTER_TYPE_NOT_TUPLE_TYPE;
import static compiler.language.parser.ParseType.VOID_TYPE;

import compiler.parser.ParseException;
import compiler.parser.Production;
import compiler.parser.Rule;

/*
 * Created on 9 Aug 2010
 */

/**
 * @author Anthony Bryant
 */
public class TypeNotPointerTypeNotTupleTypeRule extends Rule
{
  private static final long serialVersionUID = 1L;

  private static final Production ARRAY_TYPE_PRODUCTION = new Production(ARRAY_TYPE);
  private static final Production CLOSURE_TYPE_PRODUCTION = new Production(CLOSURE_TYPE);
  private static final Production PRIMITIVE_TYPE_PRODUCTION = new Production(PRIMITIVE_TYPE);
  private static final Production VOID_TYPE_PRODUCTION = new Production(VOID_TYPE);

  public TypeNotPointerTypeNotTupleTypeRule()
  {
    super(TYPE_NOT_POINTER_TYPE_NOT_TUPLE_TYPE, ARRAY_TYPE_PRODUCTION, CLOSURE_TYPE_PRODUCTION, PRIMITIVE_TYPE_PRODUCTION, VOID_TYPE_PRODUCTION);
  }

  /**
   * {@inheritDoc}
   * @see compiler.parser.Rule#match(compiler.parser.Production, java.lang.Object[])
   */
  @Override
  public Object match(Production production, Object[] args) throws ParseException
  {
    if (ARRAY_TYPE_PRODUCTION.equals(production) || CLOSURE_TYPE_PRODUCTION.equals(production) ||
        PRIMITIVE_TYPE_PRODUCTION.equals(production) || VOID_TYPE_PRODUCTION.equals(production))
    {
      // All types are actually subclasses of Type, so just return the argument
      return args[0];
    }
    throw badTypeList();
  }

}
