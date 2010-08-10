package compiler.language.parser.rules.typeDefinition;

import static compiler.language.parser.ParseType.COMMA;
import static compiler.language.parser.ParseType.ENUM_CONSTANT;
import static compiler.language.parser.ParseType.ENUM_CONSTANT_LIST;

import compiler.language.ast.ParseInfo;
import compiler.language.ast.ParseList;
import compiler.language.ast.typeDefinition.EnumConstant;
import compiler.parser.ParseException;
import compiler.parser.Rule;

/*
 * Created on 31 Jul 2010
 */

/**
 * @author Anthony Bryant
 */
public class EnumConstantListRule extends Rule
{

  private static final Object[] START_PRODUCTION = new Object[] {ENUM_CONSTANT};
  private static final Object[] CONTINUATION_PRODUCTION = new Object[] {ENUM_CONSTANT_LIST, COMMA, ENUM_CONSTANT};

  public EnumConstantListRule()
  {
    super(ENUM_CONSTANT_LIST, START_PRODUCTION, CONTINUATION_PRODUCTION);
  }

  /**
   * @see compiler.parser.Rule#match(java.lang.Object[], java.lang.Object[])
   */
  @Override
  public Object match(Object[] types, Object[] args) throws ParseException
  {
    if (types == START_PRODUCTION)
    {
      EnumConstant enumConstant = (EnumConstant) args[0];
      return new ParseList<EnumConstant>(enumConstant, enumConstant.getParseInfo());
    }
    if (types == CONTINUATION_PRODUCTION)
    {
      @SuppressWarnings("unchecked")
      ParseList<EnumConstant> list = (ParseList<EnumConstant>) args[0];
      EnumConstant newEnumConstant = (EnumConstant) args[2];
      list.addLast(newEnumConstant, ParseInfo.combine(list.getParseInfo(), (ParseInfo) args[1], newEnumConstant.getParseInfo()));
      return list;
    }
    throw badTypeList();
  }

}
