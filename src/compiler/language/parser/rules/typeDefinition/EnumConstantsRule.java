package compiler.language.parser.rules.typeDefinition;

import static compiler.language.parser.ParseType.COMMA;
import static compiler.language.parser.ParseType.ENUM_CONSTANTS;
import static compiler.language.parser.ParseType.ENUM_CONSTANT_LIST;

import compiler.language.ast.ParseInfo;
import compiler.language.ast.ParseList;
import compiler.language.ast.typeDefinition.EnumConstant;
import compiler.language.parser.ParseType;
import compiler.parser.ParseException;
import compiler.parser.Production;
import compiler.parser.Rule;

/*
 * Created on 31 Jul 2010
 */

/**
 * @author Anthony Bryant
 */
public final class EnumConstantsRule extends Rule<ParseType>
{
  private static final long serialVersionUID = 1L;

  private static final Production<ParseType> EMPTY_PRODUCTION = new Production<ParseType>();
  private static final Production<ParseType> PRODUCTION = new Production<ParseType>(ENUM_CONSTANT_LIST);
  private static final Production<ParseType> TRAILING_COMMA_PRODUCTION = new Production<ParseType>(ENUM_CONSTANT_LIST, COMMA);

  @SuppressWarnings("unchecked")
  public EnumConstantsRule()
  {
    super(ENUM_CONSTANTS, EMPTY_PRODUCTION, PRODUCTION, TRAILING_COMMA_PRODUCTION);
  }

  /**
   * @see compiler.parser.Rule#match(compiler.parser.Production, java.lang.Object[])
   */
  @Override
  public Object match(Production<ParseType> production, Object[] args) throws ParseException
  {
    if (EMPTY_PRODUCTION.equals(production))
    {
      return new ParseList<EnumConstant>(null);
    }
    if (PRODUCTION.equals(production))
    {
      // the first argument contains an already-build list, so return it
      return args[0];
    }
    if (TRAILING_COMMA_PRODUCTION.equals(production))
    {
      @SuppressWarnings("unchecked")
      ParseList<EnumConstant> list = (ParseList<EnumConstant>) args[0];
      list.setParseInfo(ParseInfo.combine(list.getParseInfo(), (ParseInfo) args[2]));
      return list;
    }
    throw badTypeList();
  }

}
