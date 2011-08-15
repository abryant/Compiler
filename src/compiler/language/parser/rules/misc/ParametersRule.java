package compiler.language.parser.rules.misc;

import static compiler.language.parser.ParseType.PARAMETERS;
import static compiler.language.parser.ParseType.PARAMETER_LIST;
import static compiler.language.parser.ParseType.LPAREN;
import static compiler.language.parser.ParseType.RPAREN;
import parser.ParseException;
import parser.Production;
import parser.Rule;

import compiler.language.LexicalPhrase;
import compiler.language.ast.misc.ParameterAST;
import compiler.language.ast.misc.ParameterListAST;
import compiler.language.parser.ParseList;
import compiler.language.parser.ParseType;

/*
 * Created on 10 Jul 2010
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
      return new ParameterListAST(new ParameterAST[0], LexicalPhrase.combine((LexicalPhrase) args[0], (LexicalPhrase) args[1]));
    }
    if (PRODUCTION.equals(production))
    {
      @SuppressWarnings("unchecked")
      ParseList<ParameterAST> parameters = (ParseList<ParameterAST>) args[1];
      return new ParameterListAST(parameters.toArray(new ParameterAST[0]), LexicalPhrase.combine((LexicalPhrase) args[0], parameters.getLexicalPhrase(), (LexicalPhrase) args[2]));
    }
    throw badTypeList();
  }

}
