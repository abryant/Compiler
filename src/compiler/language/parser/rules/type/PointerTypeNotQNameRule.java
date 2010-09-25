package compiler.language.parser.rules.type;

import static compiler.language.parser.ParseType.POINTER_TYPE_NOT_QNAME;
import static compiler.language.parser.ParseType.POINTER_TYPE_NO_TRAILING_PARAMS_NOT_QNAME;
import static compiler.language.parser.ParseType.POINTER_TYPE_TRAILING_PARAMS;

import compiler.language.parser.ParseType;
import compiler.parser.ParseException;
import compiler.parser.Production;
import compiler.parser.Rule;

/*
 * Created on 8 Aug 2010
 */

/**
 * @author Anthony Bryant
 */
public final class PointerTypeNotQNameRule extends Rule<ParseType>
{
  private static final long serialVersionUID = 1L;

  private static final Production<ParseType> NO_TRAILING_PARAMS_PRODUCTION = new Production<ParseType>(POINTER_TYPE_NO_TRAILING_PARAMS_NOT_QNAME);
  private static final Production<ParseType> TRAILING_PARAMS_PRODUCTION = new Production<ParseType>(POINTER_TYPE_TRAILING_PARAMS);

  @SuppressWarnings("unchecked")
  public PointerTypeNotQNameRule()
  {
    super(POINTER_TYPE_NOT_QNAME, NO_TRAILING_PARAMS_PRODUCTION, TRAILING_PARAMS_PRODUCTION);
  }

  /**
   * {@inheritDoc}
   * @see compiler.parser.Rule#match(compiler.parser.Production, java.lang.Object[])
   */
  @Override
  public Object match(Production<ParseType> production, Object[] args) throws ParseException
  {
    if (NO_TRAILING_PARAMS_PRODUCTION.equals(production) || TRAILING_PARAMS_PRODUCTION.equals(production))
    {
      // A PointerTypeAST has already been built by one of the rules, so return it
      return args[0];
    }
    throw badTypeList();
  }

}
