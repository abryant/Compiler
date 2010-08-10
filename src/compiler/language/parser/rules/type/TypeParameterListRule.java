package compiler.language.parser.rules.type;

import static compiler.language.parser.ParseType.COMMA;
import static compiler.language.parser.ParseType.TYPE_PARAMETER;
import static compiler.language.parser.ParseType.TYPE_PARAMETER_LIST;

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
public class TypeParameterListRule extends Rule
{

  private static final Object[] START_PRODUCTION = new Object[] {TYPE_PARAMETER};
  private static final Object[] CONTINUATION_PRODUCTION = new Object[] {TYPE_PARAMETER_LIST, COMMA, TYPE_PARAMETER};

  public TypeParameterListRule()
  {
    super(TYPE_PARAMETER_LIST, START_PRODUCTION, CONTINUATION_PRODUCTION);
  }

  /**
   * @see compiler.parser.Rule#match(java.lang.Object[], java.lang.Object[])
   */
  @Override
  public Object match(Object[] types, Object[] args) throws ParseException
  {
    if (types == START_PRODUCTION)
    {
      TypeParameter typeParameter = (TypeParameter) args[0];
      return new ParseList<TypeParameter>(typeParameter, typeParameter.getParseInfo());
    }
    if (types == CONTINUATION_PRODUCTION)
    {
      @SuppressWarnings("unchecked")
      ParseList<TypeParameter> list = (ParseList<TypeParameter>) args[0];
      TypeParameter newTypeParameter = (TypeParameter) args[2];
      list.addLast(newTypeParameter, ParseInfo.combine(list.getParseInfo(), (ParseInfo) args[1], newTypeParameter.getParseInfo()));
      return list;
    }
    throw badTypeList();
  }

}
