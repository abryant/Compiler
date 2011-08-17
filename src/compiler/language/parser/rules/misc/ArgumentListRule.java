package compiler.language.parser.rules.misc;

import static compiler.language.parser.ParseType.ARGUMENT;
import static compiler.language.parser.ParseType.ARGUMENT_LIST;
import static compiler.language.parser.ParseType.COMMA;
import parser.ParseException;
import parser.Production;
import parser.Rule;

import compiler.language.LexicalPhrase;
import compiler.language.ast.misc.ArgumentAST;
import compiler.language.parser.ParseList;
import compiler.language.parser.ParseType;

/*
 * Created on 31 Jul 2010
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
   * @see parser.Rule#match(parser.Production, java.lang.Object[])
   */
  @Override
  public Object match(Production<ParseType> production, Object[] args) throws ParseException
  {
    if (START_PRODUCTION.equals(production))
    {
      ArgumentAST argument = (ArgumentAST) args[0];
      return new ParseList<ArgumentAST>(argument, argument.getLexicalPhrase());
    }
    if (CONTINUATION_PRODUCTION.equals(production))
    {
      @SuppressWarnings("unchecked")
      ParseList<ArgumentAST> list = (ParseList<ArgumentAST>) args[0];
      ArgumentAST argument = (ArgumentAST) args[2];
      list.addLast(argument, LexicalPhrase.combine(list.getLexicalPhrase(), (LexicalPhrase) args[1], argument.getLexicalPhrase()));
      return list;
    }
    throw badTypeList();
  }

}
