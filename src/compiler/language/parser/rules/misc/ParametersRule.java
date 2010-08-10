package compiler.language.parser.rules.misc;

import static compiler.language.parser.ParseType.LPAREN;
import static compiler.language.parser.ParseType.PARAMETERS;
import static compiler.language.parser.ParseType.PARAMETER_LIST;
import static compiler.language.parser.ParseType.RPAREN;

import compiler.language.ast.ParseInfo;
import compiler.language.ast.ParseList;
import compiler.language.ast.misc.Parameter;
import compiler.parser.ParseException;
import compiler.parser.Rule;

/*
 * Created on 31 Jul 2010
 */

/**
 * @author Anthony Bryant
 */
public class ParametersRule extends Rule
{

  private static final Object[] EMPTY_PRODUCTION = new Object[] {LPAREN, RPAREN};
  private static final Object[] PRODUCTION = new Object[] {LPAREN, PARAMETER_LIST, RPAREN};

  public ParametersRule()
  {
    super(PARAMETERS, EMPTY_PRODUCTION, PRODUCTION);
  }

  /**
   * @see compiler.parser.Rule#match(java.lang.Object[], java.lang.Object[])
   */
  @Override
  public Object match(Object[] types, Object[] args) throws ParseException
  {
    if (types == EMPTY_PRODUCTION)
    {
      return new ParseList<Parameter>(ParseInfo.combine((ParseInfo) args[0], (ParseInfo) args[1]));
    }
    if (types == PRODUCTION)
    {
      @SuppressWarnings("unchecked")
      ParseList<Parameter> list = (ParseList<Parameter>) args[1];
      list.setParseInfo(ParseInfo.combine((ParseInfo) args[0], list.getParseInfo(), (ParseInfo) args[2]));
      return list;
    }
    throw badTypeList();
  }

}
