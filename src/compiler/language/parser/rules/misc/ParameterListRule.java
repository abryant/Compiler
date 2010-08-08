package compiler.language.parser.rules.misc;

import static compiler.language.parser.ParseType.COMMA;
import static compiler.language.parser.ParseType.PARAMETER;
import static compiler.language.parser.ParseType.PARAMETER_LIST;

import compiler.language.ast.ParseInfo;
import compiler.language.ast.ParseList;
import compiler.language.ast.misc.Parameter;
import compiler.parser.Rule;

/*
 * Created on 31 Jul 2010
 */

/**
 * @author Anthony Bryant
 */
public class ParameterListRule extends Rule
{

  private static final Object[] START_PRODUCTION = new Object[] {PARAMETER};
  private static final Object[] CONTINUATION_PRODUCTION = new Object[] {PARAMETER_LIST, COMMA, PARAMETER};

  public ParameterListRule()
  {
    super(PARAMETER_LIST, START_PRODUCTION, CONTINUATION_PRODUCTION);
  }

  /**
   * @see compiler.parser.Rule#match(java.lang.Object[], java.lang.Object[])
   */
  @Override
  public Object match(Object[] types, Object[] args)
  {
    if (types == START_PRODUCTION)
    {
      Parameter parameter = (Parameter) args[0];
      return new ParseList<Parameter>(parameter, parameter.getParseInfo());
    }
    if (types == CONTINUATION_PRODUCTION)
    {
      @SuppressWarnings("unchecked")
      ParseList<Parameter> list = (ParseList<Parameter>) args[0];
      Parameter parameter = (Parameter) args[2];
      list.addLast(parameter, ParseInfo.combine(list.getParseInfo(), (ParseInfo) args[1], parameter.getParseInfo()));
      return list;
    }
    throw badTypeList();
  }

}
