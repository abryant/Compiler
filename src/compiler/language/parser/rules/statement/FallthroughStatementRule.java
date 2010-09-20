package compiler.language.parser.rules.statement;

import static compiler.language.parser.ParseType.FALLTHROUGH_KEYWORD;
import static compiler.language.parser.ParseType.FALLTHROUGH_STATEMENT;
import static compiler.language.parser.ParseType.INTEGER_LITERAL;
import static compiler.language.parser.ParseType.SEMICOLON;

import compiler.language.ast.ParseInfo;
import compiler.language.ast.statement.FallthroughStatement;
import compiler.language.ast.terminal.IntegerLiteral;
import compiler.language.parser.ParseType;
import compiler.parser.ParseException;
import compiler.parser.Production;
import compiler.parser.Rule;

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
   * @see compiler.parser.Rule#match(compiler.parser.Production, java.lang.Object[])
   */
  @Override
  public Object match(Production<ParseType> production, Object[] args) throws ParseException
  {
    if (PRODUCTION.equals(production))
    {
      return new FallthroughStatement(null, ParseInfo.combine((ParseInfo) args[0], (ParseInfo) args[1]));
    }
    if (NUMBER_PRODUCTION.equals(production))
    {
      IntegerLiteral fallthroughLevels = (IntegerLiteral) args[1];
      return new FallthroughStatement(fallthroughLevels, ParseInfo.combine((ParseInfo) args[0], fallthroughLevels.getParseInfo(), (ParseInfo) args[2]));
    }
    throw badTypeList();
  }

}
