package compiler.language.parser.rules.misc;

import static compiler.language.parser.ParseType.ARGUMENT;
import static compiler.language.parser.ParseType.ARGUMENT_LIST;
import static compiler.language.parser.ParseType.COMMA;

import compiler.language.ast.ParseInfo;
import compiler.language.ast.ParseList;
import compiler.language.ast.misc.Argument;
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
public final class ArgumentListRule extends Rule<ParseType>
{
  private static final long serialVersionUID = 1L;

  private static final Production<ParseType> START_PRODUCTION = new Production<ParseType>(ARGUMENT);
  private static final Production<ParseType> CONTINUATION_PRODUCTION = new Production<ParseType>(ARGUMENT_LIST, COMMA, ARGUMENT);

  @SuppressWarnings("unchecked")
  public ArgumentListRule()
  {
    super(ARGUMENT_LIST, START_PRODUCTION, CONTINUATION_PRODUCTION);
  }

  /**
   * @see compiler.parser.Rule#match(compiler.parser.Production, java.lang.Object[])
   */
  @Override
  public Object match(Production<ParseType> production, Object[] args) throws ParseException
  {
    if (START_PRODUCTION.equals(production))
    {
      Argument argument = (Argument) args[0];
      return new ParseList<Argument>(argument, argument.getParseInfo());
    }
    if (CONTINUATION_PRODUCTION.equals(production))
    {
      @SuppressWarnings("unchecked")
      ParseList<Argument> list = (ParseList<Argument>) args[0];
      Argument argument = (Argument) args[2];
      list.addLast(argument, ParseInfo.combine(list.getParseInfo(), (ParseInfo) args[1], argument.getParseInfo()));
      return list;
    }
    throw badTypeList();
  }

}
