package compiler.language.parser.rules.type;

import static compiler.language.parser.ParseType.LANGLE;
import static compiler.language.parser.ParseType.RANGLE;
import static compiler.language.parser.ParseType.TYPE_PARAMETERS;
import static compiler.language.parser.ParseType.TYPE_PARAMETER_LIST;

import compiler.language.ast.ParseInfo;
import compiler.language.ast.ParseList;
import compiler.language.ast.type.TypeParameter;
import compiler.parser.Rule;

/*
 * Created on 13 Jul 2010
 */

/**
 * @author Anthony Bryant
 */
public class TypeParametersRule extends Rule
{

  private static final Object[] PRODUCTION = new Object[] {LANGLE, TYPE_PARAMETER_LIST, RANGLE};

  public TypeParametersRule()
  {
    super(TYPE_PARAMETERS, PRODUCTION);
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
      ParseList<TypeParameter> list = (ParseList<TypeParameter>) args[1];
      list.setParseInfo(ParseInfo.combine((ParseInfo) args[0], list.getParseInfo(), (ParseInfo) args[2]));
      return list;
    }
    throw badTypeList();
  }

}
