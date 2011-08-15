package compiler.language.parser.rules.statement;

import static compiler.language.parser.ParseType.FALLTHROUGH_KEYWORD;
import static compiler.language.parser.ParseType.FALLTHROUGH_STATEMENT;
import static compiler.language.parser.ParseType.INTEGER_LITERAL;
import static compiler.language.parser.ParseType.SEMICOLON;
import parser.ParseException;
import parser.Production;
import parser.Rule;

import compiler.language.LexicalPhrase;
import compiler.language.ast.statement.FallthroughStatementAST;
import compiler.language.ast.terminal.IntegerLiteralAST;
import compiler.language.parser.ParseType;

/*
 * Created on 28 Aug 2010
 */

/**
 * @author Anthony Bryant
 */
public final class FallthroughStatementRule extends Rule<ParseType>
{
  private static final long serialVersionUID = 1L;

  private static final Production<ParseType> PRODUCTION        = new Production<ParseType>(FALLTHROUGH_KEYWORD,                  SEMICOLON);
  private static final Production<ParseType> NUMBER_PRODUCTION = new Production<ParseType>(FALLTHROUGH_KEYWORD, INTEGER_LITERAL, SEMICOLON);

  @SuppressWarnings("unchecked")
  public FallthroughStatementRule()
  {
    super(FALLTHROUGH_STATEMENT, PRODUCTION, NUMBER_PRODUCTION);
  }

  /**
   * {@inheritDoc}
   * @see parser.Rule#match(parser.Production, java.lang.Object[])
   */
  @Override
  public Object match(Production<ParseType> production, Object[] args) throws ParseException
  {
    if (PRODUCTION.equals(production))
    {
      return new FallthroughStatementAST(null, LexicalPhrase.combine((LexicalPhrase) args[0], (LexicalPhrase) args[1]));
    }
    if (NUMBER_PRODUCTION.equals(production))
    {
      IntegerLiteralAST fallthroughLevels = (IntegerLiteralAST) args[1];
      return new FallthroughStatementAST(fallthroughLevels, LexicalPhrase.combine((LexicalPhrase) args[0], fallthroughLevels.getLexicalPhrase(), (LexicalPhrase) args[2]));
    }
    throw badTypeList();
  }

}
