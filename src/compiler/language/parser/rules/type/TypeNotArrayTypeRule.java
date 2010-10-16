package compiler.language.parser.rules.type;

import static compiler.language.parser.ParseType.CLOSURE_TYPE;
import static compiler.language.parser.ParseType.POINTER_TYPE;
import static compiler.language.parser.ParseType.PRIMITIVE_TYPE;
import static compiler.language.parser.ParseType.TUPLE_TYPE;
import static compiler.language.parser.ParseType.TYPE_NOT_ARRAY_TYPE;
import static compiler.language.parser.ParseType.VOID_TYPE;
import parser.ParseException;
import parser.Production;
import parser.Rule;

import compiler.language.parser.ParseType;

/*
 * Created on 12 Aug 2010
 */

/**
 * @author Anthony Bryant
 */
public final class TypeNotArrayTypeRule extends Rule<ParseType>
{
  private static final long serialVersionUID = 1L;

  private static final Production<ParseType> CLOSURE_TYPE_PRODUCTION = new Production<ParseType>(CLOSURE_TYPE);
  private static final Production<ParseType> POINTER_TYPE_PRODUCTION = new Production<ParseType>(POINTER_TYPE);
  private static final Production<ParseType> PRIMITIVE_TYPE_PRODUCTION = new Production<ParseType>(PRIMITIVE_TYPE);
  private static final Production<ParseType> TUPLE_TYPE_PRODUCTION = new Production<ParseType>(TUPLE_TYPE);
  private static final Production<ParseType> VOID_TYPE_PRODUCTION = new Production<ParseType>(VOID_TYPE);

  @SuppressWarnings("unchecked")
  public TypeNotArrayTypeRule()
  {
    super(TYPE_NOT_ARRAY_TYPE, CLOSURE_TYPE_PRODUCTION, POINTER_TYPE_PRODUCTION, PRIMITIVE_TYPE_PRODUCTION, TUPLE_TYPE_PRODUCTION, VOID_TYPE_PRODUCTION);
  }

  /**
   * {@inheritDoc}
   * @see parser.Rule#match(parser.Production, java.lang.Object[])
   */
  @Override
  public Object match(Production<ParseType> production, Object[] args) throws ParseException
  {
    if (CLOSURE_TYPE_PRODUCTION.equals(production) || POINTER_TYPE_PRODUCTION.equals(production) || PRIMITIVE_TYPE_PRODUCTION.equals(production) ||
        TUPLE_TYPE_PRODUCTION.equals(production)   || VOID_TYPE_PRODUCTION.equals(production))
    {
      // All types are actually subclasses of TypeAST, so just return the argument
      return args[0];
    }
    throw badTypeList();
  }

}
