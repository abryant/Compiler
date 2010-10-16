package compiler.language.parser.rules.misc;

import static compiler.language.parser.ParseType.ARGUMENTS;
import static compiler.language.parser.ParseType.ARGUMENT_LIST;
import static compiler.language.parser.ParseType.LPAREN;
import static compiler.language.parser.ParseType.RPAREN;
import parser.ParseException;
import parser.Production;
import parser.Rule;

import compiler.language.ast.ParseInfo;
import compiler.language.ast.ParseList;
import compiler.language.ast.misc.ArgumentAST;
import compiler.language.ast.misc.ArgumentListAST;
import compiler.language.parser.ParseType;

/*
 * Created on 10 Jul 2010
 */

/**
 * @author Anthony Bryant
 */
public final class ArgumentsRule extends Rule<ParseType>
{
  private static final long serialVersionUID = 1L;

  private static final Production<ParseType> EMPTY_PRODUCTION = new Production<ParseType>(LPAREN, RPAREN);
  private static final Production<ParseType> PRODUCTION = new Production<ParseType>(LPAREN, ARGUMENT_LIST, RPAREN);

  @SuppressWarnings("unchecked")
  public ArgumentsRule()
  {
    super(ARGUMENTS, EMPTY_PRODUCTION, PRODUCTION);
  }

  /**
   * @see parser.Rule#match(parser.Production, java.lang.Object[])
   */
  @Override
  public Object match(Production<ParseType> production, Object[] args) throws ParseException
  {
    if (EMPTY_PRODUCTION.equals(production))
    {
      return new ArgumentListAST(new ArgumentAST[0], ParseInfo.combine((ParseInfo) args[0], (ParseInfo) args[1]));
    }
    if (PRODUCTION.equals(production))
    {
      @SuppressWarnings("unchecked")
      ParseList<ArgumentAST> arguments = (ParseList<ArgumentAST>) args[1];
      return new ArgumentListAST(arguments.toArray(new ArgumentAST[0]), ParseInfo.combine((ParseInfo) args[0], arguments.getParseInfo(), (ParseInfo) args[2]));
    }
    throw badTypeList();
  }

}
