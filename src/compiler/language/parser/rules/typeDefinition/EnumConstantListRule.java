package compiler.language.parser.rules.typeDefinition;

import static compiler.language.parser.ParseType.COMMA;
import static compiler.language.parser.ParseType.ENUM_CONSTANT;
import static compiler.language.parser.ParseType.ENUM_CONSTANT_LIST;

import compiler.language.ast.ParseInfo;
import compiler.language.ast.ParseList;
import compiler.language.ast.typeDefinition.EnumConstantAST;
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
public final class EnumConstantListRule extends Rule<ParseType>
{
  private static final long serialVersionUID = 1L;

  private static final Production<ParseType> START_PRODUCTION = new Production<ParseType>(ENUM_CONSTANT);
  private static final Production<ParseType> CONTINUATION_PRODUCTION = new Production<ParseType>(ENUM_CONSTANT_LIST, COMMA, ENUM_CONSTANT);

  @SuppressWarnings("unchecked")
  public EnumConstantListRule()
  {
    super(ENUM_CONSTANT_LIST, START_PRODUCTION, CONTINUATION_PRODUCTION);
  }

  /**
   * @see compiler.parser.Rule#match(compiler.parser.Production, java.lang.Object[])
   */
  @Override
  public Object match(Production<ParseType> production, Object[] args) throws ParseException
  {
    if (START_PRODUCTION.equals(production))
    {
      EnumConstantAST enumConstant = (EnumConstantAST) args[0];
      return new ParseList<EnumConstantAST>(enumConstant, enumConstant.getParseInfo());
    }
    if (CONTINUATION_PRODUCTION.equals(production))
    {
      @SuppressWarnings("unchecked")
      ParseList<EnumConstantAST> list = (ParseList<EnumConstantAST>) args[0];
      EnumConstantAST newEnumConstant = (EnumConstantAST) args[2];
      list.addLast(newEnumConstant, ParseInfo.combine(list.getParseInfo(), (ParseInfo) args[1], newEnumConstant.getParseInfo()));
      return list;
    }
    throw badTypeList();
  }

}
