package compiler.language.parser.rules.type;

import static compiler.language.parser.ParseType.CLOSURE_TYPE;
import static compiler.language.parser.ParseType.PRIMITIVE_TYPE;
import static compiler.language.parser.ParseType.VOID_TYPE;
import parser.ParseException;
import parser.Production;
import parser.Rule;

import compiler.language.parser.ParseType;

/*
 * Created on 17 Aug 2010
 */

/**
 * @author Anthony Bryant
 */
public final class TypeNotQNameListRule extends Rule<ParseType>
{
  private static final long serialVersionUID = 1L;

  private static final Production<ParseType> ARRAY_TYPE_PRODUCTION = new Production<ParseType>(ParseType.ARRAY_TYPE);
  private static final Production<ParseType> CLOSURE_TYPE_PRODUCTION = new Production<ParseType>(CLOSURE_TYPE);
  private static final Production<ParseType> POINTER_TYPE_PRODUCTION = new Production<ParseType>(ParseType.POINTER_TYPE_NOT_QNAME);
  private static final Production<ParseType> PRIMITIVE_TYPE_PRODUCTION = new Production<ParseType>(PRIMITIVE_TYPE);
  private static final Production<ParseType> TUPLE_TYPE_PRODUCTION = new Production<ParseType>(ParseType.TUPLE_TYPE_NOT_QNAME_LIST);
  private static final Production<ParseType> VOID_TYPE_PRODUCTION = new Production<ParseType>(VOID_TYPE);

  @SuppressWarnings("unchecked")
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
   * @see parser.Rule#match(parser.Production, java.lang.Object[])
   */
  @Override
  public Object match(Production<ParseType> production, Object[] args) throws ParseException
  {
    if (ARRAY_TYPE_PRODUCTION.equals(production)     || CLOSURE_TYPE_PRODUCTION.equals(production) || POINTER_TYPE_PRODUCTION.equals(production) ||
        PRIMITIVE_TYPE_PRODUCTION.equals(production) || TUPLE_TYPE_PRODUCTION.equals(production)   || VOID_TYPE_PRODUCTION.equals(production))
    {
      // return the existing TypeAST
      return args[0];
    }
    throw badTypeList();
  }

}
