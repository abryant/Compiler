package compiler.language.parser.rules.misc;

import static compiler.language.parser.ParseType.ARGUMENT;
import static compiler.language.parser.ParseType.ARGUMENT_LIST;
import static compiler.language.parser.ParseType.COMMA;

import compiler.language.ast.ParseInfo;
import compiler.language.ast.ParseList;
import compiler.language.ast.misc.Argument;
import compiler.parser.ParseException;
import compiler.parser.Rule;

/*
 * Created on 10 Jul 2010
 */

/**
 * @author Anthony Bryant
 */
public class ArgumentListRule extends Rule
{

  private static final Object[] START_PRODUCTION = new Object[] {ARGUMENT};
  private static final Object[] CONTINUATION_PRODUCTION = new Object[] {ARGUMENT_LIST, COMMA, ARGUMENT};

  public ArgumentListRule()
  {
    super(ARGUMENT_LIST, START_PRODUCTION, CONTINUATION_PRODUCTION);
  }

  /**
   * @see compiler.parser.Rule#match(java.lang.Object[], java.lang.Object[])
   */
  @Override
  public Object match(Object[] types, Object[] args) throws ParseException
  {
    if (types == START_PRODUCTION)
    {
      Argument argument = (Argument) args[0];
      return new ParseList<Argument>(argument, argument.getParseInfo());
    }
    if (types == CONTINUATION_PRODUCTION)
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
