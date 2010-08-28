package compiler.language.parser.rules.statement;

import static compiler.language.parser.ParseType.BREAK_KEYWORD;
import static compiler.language.parser.ParseType.BREAK_STATEMENT;
import static compiler.language.parser.ParseType.INTEGER_LITERAL;
import static compiler.language.parser.ParseType.SEMICOLON;

import compiler.language.ast.ParseInfo;
import compiler.language.ast.statement.BreakStatement;
import compiler.language.ast.terminal.IntegerLiteral;
import compiler.parser.ParseException;
import compiler.parser.Rule;

/*
 * Created on 28 Aug 2010
 */

/**
 * @author Anthony Bryant
 */
public class BreakStatementRule extends Rule
{

  private static final Object[] PRODUCTION        = new Object[] {BREAK_KEYWORD,                  SEMICOLON};
  private static final Object[] NUMBER_PRODUCTION = new Object[] {BREAK_KEYWORD, INTEGER_LITERAL, SEMICOLON};

  public BreakStatementRule()
  {
    super(BREAK_STATEMENT, PRODUCTION, NUMBER_PRODUCTION);
  }

  /**
   * {@inheritDoc}
   * @see compiler.parser.Rule#match(java.lang.Object[], java.lang.Object[])
   */
  @Override
  public Object match(Object[] types, Object[] args) throws ParseException
  {
    if (types == PRODUCTION)
    {
      return new BreakStatement(null, ParseInfo.combine((ParseInfo) args[0], (ParseInfo) args[1]));
    }
    if (types == NUMBER_PRODUCTION)
    {
      IntegerLiteral breakLevels = (IntegerLiteral) args[1];
      return new BreakStatement(breakLevels, ParseInfo.combine((ParseInfo) args[0], breakLevels.getParseInfo(), (ParseInfo) args[2]));
    }
    throw badTypeList();
  }

}
