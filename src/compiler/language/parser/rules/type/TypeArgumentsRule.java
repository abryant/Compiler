package compiler.language.parser.rules.type;

import static compiler.language.parser.ParseType.LANGLE;
import static compiler.language.parser.ParseType.RANGLE;
import static compiler.language.parser.ParseType.TYPE_ARGUMENTS;
import static compiler.language.parser.ParseType.TYPE_ARGUMENT_LIST;

import compiler.parser.Rule;

/*
 * Created on 3 Jul 2010
 */

/**
 * @author Anthony Bryant
 */
public class TypeArgumentsRule extends Rule
{

  private static final Object[] PRODUCTION = new Object[] {LANGLE, TYPE_ARGUMENT_LIST, RANGLE};

  public TypeArgumentsRule()
  {
    super(TYPE_ARGUMENTS, PRODUCTION);
  }

  /**
   * @see compiler.parser.Rule#match(java.lang.Object[], java.lang.Object[])
   */
  @Override
  public Object match(Object[] types, Object[] args)
  {
    if (types == PRODUCTION)
    {
      // just return the result of the TYPE_ARGUMENT_LIST argument, as it has already built the list we need here
      return args[1];
    }
    throw badTypeList();
  }

}
