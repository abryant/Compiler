package compiler.language.parser.rules.typeDefinition;

import static compiler.language.parser.ParseType.COMMA;
import static compiler.language.parser.ParseType.ENUM_CONSTANTS;
import static compiler.language.parser.ParseType.ENUM_CONSTANT_LIST;

import compiler.language.ast.ParseInfo;
import compiler.language.ast.ParseList;
import compiler.language.ast.typeDefinition.EnumConstant;
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
      return new ParseList<EnumConstant>(null);
    }
    if (types == PRODUCTION)
    {
      // the first argument contains an already-build list, so return it
      return args[0];
    }
    if (types == TRAILING_COMMA_PRODUCTION)
    {
      @SuppressWarnings("unchecked")
      ParseList<EnumConstant> list = (ParseList<EnumConstant>) args[0];
      list.setParseInfo(ParseInfo.combine(list.getParseInfo(), (ParseInfo) args[2]));
      return list;
    }
    throw badTypeList();
  }

}
