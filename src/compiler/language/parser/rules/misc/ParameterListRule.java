package compiler.language.parser.rules.misc;

import static compiler.language.parser.ParseType.COMMA;
import static compiler.language.parser.ParseType.PARAMETER;
import static compiler.language.parser.ParseType.PARAMETER_LIST;

import compiler.language.ast.ParseInfo;
import compiler.language.ast.ParseList;
import compiler.language.ast.misc.Parameter;
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
public final class ParameterListRule extends Rule<ParseType>
{
  private static final long serialVersionUID = 1L;

  private static final Production<ParseType> START_PRODUCTION = new Production<ParseType>(PARAMETER);
  private static final Production<ParseType> CONTINUATION_PRODUCTION = new Production<ParseType>(PARAMETER_LIST, COMMA, PARAMETER);

  @SuppressWarnings("unchecked")
  public ParameterListRule()
  {
    super(PARAMETER_LIST, START_PRODUCTION, CONTINUATION_PRODUCTION);
  }

  /**
   * @see compiler.parser.Rule#match(compiler.parser.Production, java.lang.Object[])
   */
  @Override
  public Object match(Production<ParseType> production, Object[] args) throws ParseException
  {
    if (START_PRODUCTION.equals(production))
    {
      Parameter parameter = (Parameter) args[0];
      return new ParseList<Parameter>(parameter, parameter.getParseInfo());
    }
    if (CONTINUATION_PRODUCTION.equals(production))
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
