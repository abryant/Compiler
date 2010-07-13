package compiler.language.parser.rules;

import static compiler.language.parser.ParseType.LANGLE;
import static compiler.language.parser.ParseType.RANGLE;
import static compiler.language.parser.ParseType.TYPE_PARAMETERS;
import static compiler.language.parser.ParseType.TYPE_PARAMETER_LIST;

import compiler.parser.Rule;

/*
 * Created on 13 Jul 2010
 */

/**
 * @author Anthony Bryant
 */
public class TypeParametersRule extends Rule
{

  private static final Object[] PRODUCTION = new Object[] {LANGLE, TYPE_PARAMETER_LIST, RANGLE};

  public TypeParametersRule()
  {
    super(TYPE_PARAMETERS, PRODUCTION);
  }

  /**
   * @see compiler.parser.Rule#match(java.lang.Object[], java.lang.Object[])
   */
  @Override
  public Object match(Object[] types, Object[] args)
  {
    if (types == PRODUCTION)
    {
      // the TypeParameter[] result has already been built by the rule for TYPE_PARAMETER_LIST, so just return it
      return args[1];
    }
    throw badTypeList();
  }

}
