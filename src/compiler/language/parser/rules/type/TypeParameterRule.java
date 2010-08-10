package compiler.language.parser.rules.type;

import static compiler.language.parser.ParseType.TYPE;
import static compiler.language.parser.ParseType.TYPE_PARAMETER;
import static compiler.language.parser.ParseType.WILDCARD_TYPE_PARAMETER;

import compiler.language.ast.type.NormalTypeParameter;
import compiler.language.ast.type.Type;
import compiler.parser.Rule;

/*
 * Created on 13 Jul 2010
 */

/**
 * @author Anthony Bryant
 */
public class TypeParameterRule extends Rule
{

  private static final Object[] NORMAL_PRODUCTION = new Object[] {TYPE};
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
    if (types == NORMAL_PRODUCTION)
    {
      Type type = (Type) args[0];
      return new NormalTypeParameter(type, type.getParseInfo());
    }
    if (types == WILDCARD_PRODUCTION)
    {
      // a wildcard type parameter is actually a subclass of TypeParameter, so just return the argument
      return args[0];
    }
    throw badTypeList();
  }

}
