package compiler.language.parser.rules.type;

import static compiler.language.parser.ParseType.CLOSURE_TYPE;
import static compiler.language.parser.ParseType.PRIMITIVE_TYPE;
import static compiler.language.parser.ParseType.VOID_TYPE;

import compiler.language.parser.ParseType;
import compiler.parser.ParseException;
import compiler.parser.Production;
import compiler.parser.Rule;

/*
 * Created on 17 Aug 2010
 */

/**
 * @author Anthony Bryant
 */
public class TypeNotQNameListRule extends Rule
{
  private static final long serialVersionUID = 1L;

  private static final Production ARRAY_TYPE_PRODUCTION = new Production(ParseType.ARRAY_TYPE);
  private static final Production CLOSURE_TYPE_PRODUCTION = new Production(CLOSURE_TYPE);
  private static final Production POINTER_TYPE_PRODUCTION = new Production(ParseType.POINTER_TYPE_NOT_QNAME);
  private static final Production PRIMITIVE_TYPE_PRODUCTION = new Production(PRIMITIVE_TYPE);
  private static final Production TUPLE_TYPE_PRODUCTION = new Production(ParseType.TUPLE_TYPE_NOT_QNAME_LIST);
  private static final Production VOID_TYPE_PRODUCTION = new Production(VOID_TYPE);

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
   * @see compiler.parser.Rule#match(compiler.parser.Production, java.lang.Object[])
   */
  @Override
  public Object match(Production production, Object[] args) throws ParseException
  {
    if (ARRAY_TYPE_PRODUCTION.equals(production)     || CLOSURE_TYPE_PRODUCTION.equals(production) || POINTER_TYPE_PRODUCTION.equals(production) ||
        PRIMITIVE_TYPE_PRODUCTION.equals(production) || TUPLE_TYPE_PRODUCTION.equals(production)   || VOID_TYPE_PRODUCTION.equals(production))
    {
      // return the existing Type
      return args[0];
    }
    throw badTypeList();
  }

}
