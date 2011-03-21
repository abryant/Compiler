package compiler.language.parser.rules.misc;

import static compiler.language.parser.ParseType.LPAREN;
import static compiler.language.parser.ParseType.PARAMETERS;
import static compiler.language.parser.ParseType.PARAMETER_LIST;
import static compiler.language.parser.ParseType.RPAREN;
import parser.ParseException;
import parser.Production;
import parser.Rule;

import compiler.language.ast.ParseInfo;
import compiler.language.ast.misc.ParameterAST;
import compiler.language.parser.ParseList;
import compiler.language.parser.ParseType;

/*
 * Created on 31 Jul 2010
 */

/**
 * @author Anthony Bryant
 */
public final class ParametersRule extends Rule<ParseType>
{
  private static final long serialVersionUID = 1L;

  private static final Production<ParseType> EMPTY_PRODUCTION = new Production<ParseType>(LPAREN, RPAREN);
  private static final Production<ParseType> PRODUCTION = new Production<ParseType>(LPAREN, PARAMETER_LIST, RPAREN);

  @SuppressWarnings("unchecked")
  public ParametersRule()
  {
    super(PARAMETERS, EMPTY_PRODUCTION, PRODUCTION);
  }

  /**
   * @see parser.Rule#match(parser.Production, java.lang.Object[])
   */
  @Override
  public Object match(Production<ParseType> production, Object[] args) throws ParseException
  {
    if (EMPTY_PRODUCTION.equals(production))
    {
      return new ParseList<ParameterAST>(ParseInfo.combine((ParseInfo) args[0], (ParseInfo) args[1]));
    }
    if (PRODUCTION.equals(production))
    {
      @SuppressWarnings("unchecked")
      ParseList<ParameterAST> list = (ParseList<ParameterAST>) args[1];
      list.setParseInfo(ParseInfo.combine((ParseInfo) args[0], list.getParseInfo(), (ParseInfo) args[2]));
      return list;
    }
    throw badTypeList();
  }

}
