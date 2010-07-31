package compiler.language.parser.rules;

import static compiler.language.parser.ParseType.COMMA;
import static compiler.language.parser.ParseType.ENUM_CONSTANTS;
import static compiler.language.parser.ParseType.ENUM_CONSTANT_LIST;

import compiler.language.ast.EnumConstant;
import compiler.parser.Rule;

/*
 * Created on 31 Jul 2010
 */

/**
 * @author Anthony Bryant
 */
public class EnumConstantsRule extends Rule
{

  private static final Object[] EMPTY_PRODUCTION = new Object[] {};
  private static final Object[] PRODUCTION = new Object[] {ENUM_CONSTANT_LIST};
  private static final Object[] TRAILING_COMMA_PRODUCTION = new Object[] {ENUM_CONSTANT_LIST, COMMA};

  public EnumConstantsRule()
  {
    super(ENUM_CONSTANTS, EMPTY_PRODUCTION, PRODUCTION, TRAILING_COMMA_PRODUCTION);
  }

  /**
   * @see compiler.parser.Rule#match(java.lang.Object[], java.lang.Object[])
   */
  @Override
  public Object match(Object[] types, Object[] args)
  {
    if (types == EMPTY_PRODUCTION)
    {
      return new EnumConstant[0];
    }
    if (types == PRODUCTION || types == TRAILING_COMMA_PRODUCTION)
    {
      // in both of these cases the first argument contains an already-build list, so return it
      return args[0];
    }
    throw badTypeList();
  }

}
