package compiler.language.parser.rules.statement;

import static compiler.language.parser.ParseType.BLOCK;
import static compiler.language.parser.ParseType.FINALLY_CLAUSE;
import static compiler.language.parser.ParseType.FINALLY_KEYWORD;
import parser.ParseException;
import parser.Production;
import parser.Rule;

import compiler.language.ast.ParseInfo;
import compiler.language.ast.statement.BlockAST;
import compiler.language.parser.ParseContainer;
import compiler.language.parser.ParseType;

/*
 * Created on 29 Aug 2010
 */

/**
 * @author Anthony Bryant
 */
public final class FinallyClauseRule extends Rule<ParseType>
{
  private static final long serialVersionUID = 1L;

  private static final Production<ParseType> PRODUCTION = new Production<ParseType>(FINALLY_KEYWORD, BLOCK);
  private static final Production<ParseType> EMPTY_PRODUCTION = new Production<ParseType>();

  @SuppressWarnings("unchecked")
  public FinallyClauseRule()
  {
    super(FINALLY_CLAUSE, PRODUCTION, EMPTY_PRODUCTION);
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
      BlockAST block = (BlockAST) args[1];
      return new ParseContainer<BlockAST>(block, ParseInfo.combine((ParseInfo) args[0], block.getParseInfo()));
    }
    if (EMPTY_PRODUCTION.equals(production))
    {
      return new ParseContainer<BlockAST>(null, null);
    }
    throw badTypeList();
  }

}
