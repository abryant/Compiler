package compiler.language.parser.rules.type;

import static compiler.language.parser.ParseType.LANGLE;
import static compiler.language.parser.ParseType.TYPE_PARAMETERS;
import static compiler.language.parser.ParseType.TYPE_PARAMETER_LIST_RANGLE;

import compiler.language.ast.ParseContainer;
import compiler.language.ast.ParseInfo;
import compiler.language.ast.ParseList;
import compiler.language.ast.type.TypeParameter;
import compiler.parser.ParseException;
import compiler.parser.Rule;

/*
 * Created on 13 Jul 2010
 */

/**
 * @author Anthony Bryant
 */
public class TypeParametersRule extends Rule
{

  private static final Object[] PRODUCTION = new Object[] {LANGLE, TYPE_PARAMETER_LIST_RANGLE};

  public TypeParametersRule()
  {
    super(TYPE_PARAMETERS, PRODUCTION);
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
      ParseContainer<ParseList<TypeParameter>> container = (ParseContainer<ParseList<TypeParameter>>) args[1];
      ParseList<TypeParameter> list = container.getItem();
      list.setParseInfo(ParseInfo.combine((ParseInfo) args[0], container.getParseInfo()));
      return list;
    }
    throw badTypeList();
  }

}
