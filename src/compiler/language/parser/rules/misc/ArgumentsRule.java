package compiler.language.parser.rules.misc;

import static compiler.language.parser.ParseType.ARGUMENTS;
import static compiler.language.parser.ParseType.ARGUMENT_LIST;
import static compiler.language.parser.ParseType.LPAREN;
import static compiler.language.parser.ParseType.RPAREN;

import compiler.language.ast.ParseInfo;
import compiler.language.ast.ParseList;
import compiler.language.ast.misc.Argument;
import compiler.language.ast.misc.ArgumentList;
import compiler.language.parser.ParseType;
import compiler.parser.ParseException;
import compiler.parser.Production;
import compiler.parser.Rule;

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
   * @see compiler.parser.Rule#match(compiler.parser.Production, java.lang.Object[])
   */
  @Override
  public Object match(Production<ParseType> production, Object[] args) throws ParseException
  {
    if (EMPTY_PRODUCTION.equals(production))
    {
      return new ArgumentList(new Argument[0], ParseInfo.combine((ParseInfo) args[0], (ParseInfo) args[1]));
    }
    if (PRODUCTION.equals(production))
    {
      @SuppressWarnings("unchecked")
      ParseList<Argument> arguments = (ParseList<Argument>) args[1];
      return new ArgumentList(arguments.toArray(new Argument[0]), ParseInfo.combine((ParseInfo) args[0], arguments.getParseInfo(), (ParseInfo) args[2]));
    }
    throw badTypeList();
  }

}
