package compiler.language.parser.rules.statement;

import static compiler.language.parser.ParseType.BREAK_KEYWORD;
import static compiler.language.parser.ParseType.BREAK_STATEMENT;
import static compiler.language.parser.ParseType.INTEGER_LITERAL;
import static compiler.language.parser.ParseType.SEMICOLON;
import parser.ParseException;
import parser.Production;
import parser.Rule;

import compiler.language.ast.ParseInfo;
import compiler.language.ast.statement.BreakStatementAST;
import compiler.language.ast.terminal.IntegerLiteralAST;
import compiler.language.parser.ParseType;

/*
 * Created on 28 Aug 2010
 */

/**
 * @author Anthony Bryant
 */
public final class BreakStatementRule extends Rule<ParseType>
{
  private static final long serialVersionUID = 1L;

  private static final Production<ParseType> PRODUCTION        = new Production<ParseType>(BREAK_KEYWORD,                  SEMICOLON);
  private static final Production<ParseType> NUMBER_PRODUCTION = new Production<ParseType>(BREAK_KEYWORD, INTEGER_LITERAL, SEMICOLON);

  @SuppressWarnings("unchecked")
  public BreakStatementRule()
  {
    super(BREAK_STATEMENT, PRODUCTION, NUMBER_PRODUCTION);
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
      return new BreakStatementAST(null, ParseInfo.combine((ParseInfo) args[0], (ParseInfo) args[1]));
    }
    if (NUMBER_PRODUCTION.equals(production))
    {
      IntegerLiteralAST breakLevels = (IntegerLiteralAST) args[1];
      return new BreakStatementAST(breakLevels, ParseInfo.combine((ParseInfo) args[0], breakLevels.getParseInfo(), (ParseInfo) args[2]));
    }
    throw badTypeList();
  }

}
