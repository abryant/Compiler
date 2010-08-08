package compiler.language.parser.rules.type;

import static compiler.language.parser.ParseType.NORMAL_TYPE_PARAMETER;
import static compiler.language.parser.ParseType.TYPE;

import compiler.language.ast.type.NormalTypeParameter;
import compiler.language.ast.type.Type;
import compiler.parser.Rule;

/*
 * Created on 13 Jul 2010
 */

/**
 * @author Anthony Bryant
 */
public class NormalTypeParameterRule extends Rule
{

  private static final Object[] PRODUCTION = new Object[] {TYPE};

  public NormalTypeParameterRule()
  {
    super(NORMAL_TYPE_PARAMETER, PRODUCTION);
  }

  /**
   * @see compiler.parser.Rule#match(java.lang.Object[], java.lang.Object[])
   */
  @Override
  public Object match(Object[] types, Object[] args)
  {
    if (types == PRODUCTION)
    {
      Type type = (Type) args[0];
      return new NormalTypeParameter(type, type.getParseInfo());
    }
    throw badTypeList();
  }

}
