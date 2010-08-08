package compiler.language.parser.rules.type;

import static compiler.language.parser.ParseType.LANGLE;
import static compiler.language.parser.ParseType.RANGLE;
import static compiler.language.parser.ParseType.TYPE_ARGUMENTS;
import static compiler.language.parser.ParseType.TYPE_ARGUMENT_LIST;

import compiler.language.ast.ParseInfo;
import compiler.language.ast.ParseList;
import compiler.language.ast.type.TypeArgument;
import compiler.parser.Rule;

/*
 * Created on 3 Jul 2010
 */

/**
 * @author Anthony Bryant
 */
public class TypeArgumentsRule extends Rule
{

  private static final Object[] PRODUCTION = new Object[] {LANGLE, TYPE_ARGUMENT_LIST, RANGLE};

  public TypeArgumentsRule()
  {
    super(TYPE_ARGUMENTS, PRODUCTION);
  }

  /**
   * @see compiler.parser.Rule#match(java.lang.Object[], java.lang.Object[])
   */
  @Override
  public Object match(Object[] types, Object[] args)
  {
    if (types == PRODUCTION)
    {
      @SuppressWarnings("unchecked")
      ParseList<TypeArgument> list = (ParseList<TypeArgument>) args[1];
      list.setParseInfo(ParseInfo.combine((ParseInfo) args[0], list.getParseInfo(), (ParseInfo) args[2]));
      return list;
    }
    throw badTypeList();
  }

}
