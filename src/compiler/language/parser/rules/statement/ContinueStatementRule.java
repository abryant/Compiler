package compiler.language.parser.rules.statement;

import static compiler.language.parser.ParseType.CONTINUE_KEYWORD;
import static compiler.language.parser.ParseType.INTEGER_LITERAL;
import static compiler.language.parser.ParseType.SEMICOLON;
import parser.ParseException;
import parser.Production;
import parser.Rule;

import compiler.language.ast.ParseInfo;
import compiler.language.ast.statement.ContinueStatementAST;
import compiler.language.ast.terminal.IntegerLiteralAST;
import compiler.language.parser.ParseType;

/*
 * Created on 28 Aug 2010
 */

/**
 * @author Anthony Bryant
 */
public final class ContinueStatementRule extends Rule<ParseType>
{
  private static final long serialVersionUID = 1L;

  private static final Production<ParseType> PRODUCTION        = new Production<ParseType>(CONTINUE_KEYWORD,                  SEMICOLON);
  private static final Production<ParseType> NUMBER_PRODUCTION = new Production<ParseType>(CONTINUE_KEYWORD, INTEGER_LITERAL, SEMICOLON);

  @SuppressWarnings("unchecked")
  public ContinueStatementRule()
  {
    super(ParseType.CONTINUE_STATEMENT, PRODUCTION, NUMBER_PRODUCTION);
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
      return new ContinueStatementAST(null, ParseInfo.combine((ParseInfo) args[0], (ParseInfo) args[1]));
    }
    if (NUMBER_PRODUCTION.equals(production))
    {
      IntegerLiteralAST continueLevels = (IntegerLiteralAST) args[1];
      return new ContinueStatementAST(continueLevels, ParseInfo.combine((ParseInfo) args[0], continueLevels.getParseInfo(), (ParseInfo) args[2]));
    }
    throw badTypeList();
  }

}
