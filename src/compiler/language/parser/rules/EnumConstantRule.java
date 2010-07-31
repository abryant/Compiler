package compiler.language.parser.rules;

import static compiler.language.parser.ParseType.ENUM_CONSTANT;
import static compiler.language.parser.ParseType.NAME;
import static compiler.language.parser.ParseType.PARAMETERS;

import compiler.language.ast.EnumConstant;
import compiler.language.ast.Name;
import compiler.language.ast.Parameter;
import compiler.parser.Rule;

/*
 * Created on 31 Jul 2010
 */

/**
 * @author Anthony Bryant
 */
public class EnumConstantRule extends Rule
{

  private static final Object[] PRODUCTION = new Object[] {NAME};
  private static final Object[] PARAMETERS_PRODUCTION = new Object[] {NAME, PARAMETERS};

  public EnumConstantRule()
  {
    super(ENUM_CONSTANT, PRODUCTION, PARAMETERS_PRODUCTION);
  }

  /**
   * @see compiler.parser.Rule#match(java.lang.Object[], java.lang.Object[])
   */
  @Override
  public Object match(Object[] types, Object[] args)
  {
    if (types == PRODUCTION)
    {
      return new EnumConstant((Name) args[0], new Parameter[0]);
    }
    if (types == PARAMETERS_PRODUCTION)
    {
      return new EnumConstant((Name) args[0], (Parameter[]) args[1]);
    }
    throw badTypeList();
  }

}
