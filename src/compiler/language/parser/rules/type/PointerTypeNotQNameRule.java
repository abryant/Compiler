package compiler.language.parser.rules.type;

import static compiler.language.parser.ParseType.POINTER_TYPE_NOT_QNAME;
import static compiler.language.parser.ParseType.POINTER_TYPE_NO_TRAILING_PARAMS_NOT_QNAME;
import static compiler.language.parser.ParseType.POINTER_TYPE_TRAILING_PARAMS;

import compiler.parser.ParseException;
import compiler.parser.Rule;

/*
 * Created on 8 Aug 2010
 */

/**
 * @author Anthony Bryant
 */
public class PointerTypeNotQNameRule extends Rule
{

  private static final Object[] NO_TRAILING_PARAMS_PRODUCTION = new Object[] {POINTER_TYPE_NO_TRAILING_PARAMS_NOT_QNAME};
  private static final Object[] TRAILING_PARAMS_PRODUCTION = new Object[] {POINTER_TYPE_TRAILING_PARAMS};

  public PointerTypeNotQNameRule()
  {
    super(POINTER_TYPE_NOT_QNAME, NO_TRAILING_PARAMS_PRODUCTION, TRAILING_PARAMS_PRODUCTION);
  }

  /**
   * {@inheritDoc}
   * @see compiler.parser.Rule#match(java.lang.Object[], java.lang.Object[])
   */
  @Override
  public Object match(Object[] types, Object[] args) throws ParseException
  {
    if (types == NO_TRAILING_PARAMS_PRODUCTION || types == TRAILING_PARAMS_PRODUCTION)
    {
      // A PointerType has already been built by one of the rules, so return it
      return args[0];
    }
    throw badTypeList();
  }

}
