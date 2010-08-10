package compiler.language.parser.rules.type;

import static compiler.language.parser.ParseType.LANGLE;
import static compiler.language.parser.ParseType.TYPE_ARGUMENTS;
import static compiler.language.parser.ParseType.TYPE_ARGUMENT_LIST_RANGLE;

import compiler.language.ast.ParseContainer;
import compiler.language.ast.ParseInfo;
import compiler.language.ast.ParseList;
import compiler.language.ast.type.TypeArgument;
import compiler.parser.ParseException;
import compiler.parser.Rule;

/*
 * Created on 3 Jul 2010
 */

/**
 * @author Anthony Bryant
 */
public class TypeArgumentsRule extends Rule
{

  private static final Object[] PRODUCTION = new Object[] {LANGLE, TYPE_ARGUMENT_LIST_RANGLE};

  public TypeArgumentsRule()
  {
    super(TYPE_ARGUMENTS, PRODUCTION);
  }

  /**
   * @see compiler.parser.Rule#match(java.lang.Object[], java.lang.Object[])
   */
  @Override
  public Object match(Object[] types, Object[] args) throws ParseException
  {
    if (types == PRODUCTION)
    {
      @SuppressWarnings("unchecked")
      ParseContainer<ParseList<TypeArgument>> container = (ParseContainer<ParseList<TypeArgument>>) args[1];
      ParseList<TypeArgument> list = container.getItem();
      list.setParseInfo(ParseInfo.combine((ParseInfo) args[0], container.getParseInfo()));
      return list;
    }
    throw badTypeList();
  }

}
