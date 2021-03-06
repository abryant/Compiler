package compiler.language.parser.rules.misc;

import static compiler.language.parser.ParseType.COMMA;
import static compiler.language.parser.ParseType.PARAMETER;
import static compiler.language.parser.ParseType.PARAMETER_LIST;
import parser.ParseException;
import parser.Production;
import parser.Rule;

import compiler.language.LexicalPhrase;
import compiler.language.ast.misc.ParameterAST;
import compiler.language.parser.ParseList;
import compiler.language.parser.ParseType;

/*
 * Created on 10 Jul 2010
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
   * @see parser.Rule#match(parser.Production, java.lang.Object[])
   */
  @Override
  public Object match(Production<ParseType> production, Object[] args) throws ParseException
  {
    if (START_PRODUCTION.equals(production))
    {
      ParameterAST parameter = (ParameterAST) args[0];
      return new ParseList<ParameterAST>(parameter, parameter.getLexicalPhrase());
    }
    if (CONTINUATION_PRODUCTION.equals(production))
    {
      @SuppressWarnings("unchecked")
      ParseList<ParameterAST> list = (ParseList<ParameterAST>) args[0];
      ParameterAST parameter = (ParameterAST) args[2];
      list.addLast(parameter, LexicalPhrase.combine(list.getLexicalPhrase(), (LexicalPhrase) args[1], parameter.getLexicalPhrase()));
      return list;
    }
    throw badTypeList();
  }

}
