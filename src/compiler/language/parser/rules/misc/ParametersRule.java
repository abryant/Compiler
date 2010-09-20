package compiler.language.parser.rules.misc;

import static compiler.language.parser.ParseType.LPAREN;
import static compiler.language.parser.ParseType.PARAMETERS;
import static compiler.language.parser.ParseType.PARAMETER_LIST;
import static compiler.language.parser.ParseType.RPAREN;

import compiler.language.ast.ParseInfo;
import compiler.language.ast.ParseList;
import compiler.language.ast.misc.Parameter;
import compiler.parser.ParseException;
import compiler.parser.Production;
import compiler.parser.Rule;

/*
 * Created on 31 Jul 2010
 */

/**
 * @author Anthony Bryant
 */
public class ParametersRule extends Rule
{
  private static final long serialVersionUID = 1L;

  private static final Production EMPTY_PRODUCTION = new Production(LPAREN, RPAREN);
  private static final Production PRODUCTION = new Production(LPAREN, PARAMETER_LIST, RPAREN);

  public ParametersRule()
  {
    super(PARAMETERS, EMPTY_PRODUCTION, PRODUCTION);
  }

  /**
   * @see compiler.parser.Rule#match(compiler.parser.Production, java.lang.Object[])
   */
  @Override
  public Object match(Production production, Object[] args) throws ParseException
  {
    if (EMPTY_PRODUCTION.equals(production))
    {
      return new ParseList<Parameter>(ParseInfo.combine((ParseInfo) args[0], (ParseInfo) args[1]));
    }
    if (PRODUCTION.equals(production))
    {
      @SuppressWarnings("unchecked")
      ParseList<Parameter> list = (ParseList<Parameter>) args[1];
      list.setParseInfo(ParseInfo.combine((ParseInfo) args[0], list.getParseInfo(), (ParseInfo) args[2]));
      return list;
    }
    throw badTypeList();
  }

}
