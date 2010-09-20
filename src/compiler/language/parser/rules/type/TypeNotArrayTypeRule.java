package compiler.language.parser.rules.type;

import static compiler.language.parser.ParseType.CLOSURE_TYPE;
import static compiler.language.parser.ParseType.POINTER_TYPE;
import static compiler.language.parser.ParseType.PRIMITIVE_TYPE;
import static compiler.language.parser.ParseType.TUPLE_TYPE;
import static compiler.language.parser.ParseType.TYPE_NOT_ARRAY_TYPE;
import static compiler.language.parser.ParseType.VOID_TYPE;

import compiler.parser.ParseException;
import compiler.parser.Production;
import compiler.parser.Rule;

/*
 * Created on 12 Aug 2010
 */

/**
 * @author Anthony Bryant
 */
public class TypeNotArrayTypeRule extends Rule
{
  private static final long serialVersionUID = 1L;

  private static final Production CLOSURE_TYPE_PRODUCTION = new Production(CLOSURE_TYPE);
  private static final Production POINTER_TYPE_PRODUCTION = new Production(POINTER_TYPE);
  private static final Production PRIMITIVE_TYPE_PRODUCTION = new Production(PRIMITIVE_TYPE);
  private static final Production TUPLE_TYPE_PRODUCTION = new Production(TUPLE_TYPE);
  private static final Production VOID_TYPE_PRODUCTION = new Production(VOID_TYPE);

  public TypeNotArrayTypeRule()
  {
    super(TYPE_NOT_ARRAY_TYPE, CLOSURE_TYPE_PRODUCTION, POINTER_TYPE_PRODUCTION, PRIMITIVE_TYPE_PRODUCTION, TUPLE_TYPE_PRODUCTION, VOID_TYPE_PRODUCTION);
  }

  /**
   * {@inheritDoc}
   * @see compiler.parser.Rule#match(compiler.parser.Production, java.lang.Object[])
   */
  @Override
  public Object match(Production production, Object[] args) throws ParseException
  {
    if (CLOSURE_TYPE_PRODUCTION.equals(production) || POINTER_TYPE_PRODUCTION.equals(production) || PRIMITIVE_TYPE_PRODUCTION.equals(production) ||
        TUPLE_TYPE_PRODUCTION.equals(production)   || VOID_TYPE_PRODUCTION.equals(production))
    {
      // All types are actually subclasses of Type, so just return the argument
      return args[0];
    }
    throw badTypeList();
  }

}
