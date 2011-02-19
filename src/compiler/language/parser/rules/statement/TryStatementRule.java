package compiler.language.parser.rules.statement;

import static compiler.language.parser.ParseType.BLOCK;
import static compiler.language.parser.ParseType.CATCH_CLAUSES;
import static compiler.language.parser.ParseType.FINALLY_CLAUSE;
import static compiler.language.parser.ParseType.TRY_KEYWORD;
import static compiler.language.parser.ParseType.TRY_STATEMENT;
import parser.ParseException;
import parser.Production;
import parser.Rule;

import compiler.language.ast.ParseContainer;
import compiler.language.ast.ParseInfo;
import compiler.language.ast.ParseList;
import compiler.language.ast.statement.BlockAST;
import compiler.language.ast.statement.CatchClauseAST;
import compiler.language.ast.statement.TryStatementAST;
import compiler.language.parser.ParseType;

/*
 * Created on 29 Aug 2010
 */

/**
 * @author Anthony Bryant
 */
public final class TryStatementRule extends Rule<ParseType>
{
  private static final long serialVersionUID = 1L;

  private static final Production<ParseType> PRODUCTION = new Production<ParseType>(TRY_KEYWORD, BLOCK, CATCH_CLAUSES, FINALLY_CLAUSE);

  @SuppressWarnings("unchecked")
  public TryStatementRule()
  {
    super(TRY_STATEMENT, PRODUCTION);
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
      @SuppressWarnings("unchecked")
      ParseList<CatchClauseAST> catchClauses = (ParseList<CatchClauseAST>) args[2];
      @SuppressWarnings("unchecked")
      ParseContainer<BlockAST> finallyClause = (ParseContainer<BlockAST>) args[3];
      return new TryStatementAST(block, catchClauses.toArray(new CatchClauseAST[0]), finallyClause.getItem(),
                              ParseInfo.combine((ParseInfo) args[0], block.getParseInfo(), catchClauses.getParseInfo(), finallyClause.getParseInfo()));
    }
    throw badTypeList();
  }

}
