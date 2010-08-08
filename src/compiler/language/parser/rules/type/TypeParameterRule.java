package compiler.language.parser.rules.type;

import static compiler.language.parser.ParseType.NORMAL_TYPE_PARAMETER;
import static compiler.language.parser.ParseType.TYPE_PARAMETER;
import static compiler.language.parser.ParseType.WILDCARD_TYPE_PARAMETER;

import compiler.parser.Rule;

/*
 * Created on 13 Jul 2010
 */

/**
 * @author Anthony Bryant
 */
public class TypeParameterRule extends Rule
{

  private static final Object[] NORMAL_PRODUCTION = new Object[] {NORMAL_TYPE_PARAMETER};
  private static final Object[] WILDCARD_PRODUCTION = new Object[] {WILDCARD_TYPE_PARAMETER};

  public TypeParameterRule()
  {
    super(TYPE_PARAMETER, NORMAL_PRODUCTION, WILDCARD_PRODUCTION);
  }

  /**
   * @see compiler.parser.Rule#match(java.lang.Object[], java.lang.Object[])
   */
  @Override
  public Object match(Object[] types, Object[] args)
  {
    if (types == NORMAL_PRODUCTION || types == WILDCARD_PRODUCTION)
    {
      // both sorts of type parameter are actually subclasses of TypeParameter, so just return the arguments
      return args[0];
    }
    throw badTypeList();
  }

}
