package compiler.language.parser.rules.type;

import static compiler.language.parser.ParseType.COMMA;
import static compiler.language.parser.ParseType.TYPE_PARAMETER_LIST;
import static compiler.language.parser.ParseType.TYPE_PARAMETER_LIST_RANGLE;
import static compiler.language.parser.ParseType.TYPE_PARAMETER_RANGLE;

import compiler.language.ast.ParseContainer;
import compiler.language.ast.ParseInfo;
import compiler.language.ast.ParseList;
import compiler.language.ast.type.TypeParameter;
import compiler.parser.Rule;

/*
 * Created on 10 Aug 2010
 */

/**
 * @author Anthony Bryant
 */
public class TypeParameterListRAngleRule extends Rule
{

  private static final Object[] TYPE_PARAMETER_PRODUCTION = new Object[] {TYPE_PARAMETER_RANGLE};
  private static final Object[] LIST_END_PRODUCTION = new Object[] {TYPE_PARAMETER_LIST, COMMA, TYPE_PARAMETER_RANGLE};

  public TypeParameterListRAngleRule()
  {
    super(TYPE_PARAMETER_LIST_RANGLE, TYPE_PARAMETER_PRODUCTION, LIST_END_PRODUCTION);
  }

  /**
   * {@inheritDoc}
   * @see compiler.parser.Rule#match(java.lang.Object[], java.lang.Object[])
   */
  @Override
  public Object match(Object[] types, Object[] args)
  {
    if (types == TYPE_PARAMETER_PRODUCTION)
    {
      @SuppressWarnings("unchecked")
      ParseContainer<TypeParameter> parameter = (ParseContainer<TypeParameter>) args[0];
      ParseList<TypeParameter> list = new ParseList<TypeParameter>(parameter.getItem(), parameter.getItem().getParseInfo());
      return new ParseContainer<ParseList<TypeParameter>>(list, parameter.getParseInfo());
    }
    if (types == LIST_END_PRODUCTION)
    {
      @SuppressWarnings("unchecked")
      ParseList<TypeParameter> list = (ParseList<TypeParameter>) args[0];
      ParseInfo listParseInfo = list.getParseInfo();
      @SuppressWarnings("unchecked")
      ParseContainer<TypeParameter> parameter = (ParseContainer<TypeParameter>) args[2];
      list.addLast(parameter.getItem(), ParseInfo.combine(listParseInfo, (ParseInfo) args[1], parameter.getItem().getParseInfo()));
      return new ParseContainer<ParseList<TypeParameter>>(list, ParseInfo.combine(listParseInfo, (ParseInfo) args[1], parameter.getParseInfo()));
    }
    throw badTypeList();
  }

}
