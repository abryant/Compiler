package compiler.language.parser.rules.statement;

import static compiler.language.parser.ParseType.BLOCK;
import static compiler.language.parser.ParseType.CATCH_CLAUSES;
import static compiler.language.parser.ParseType.FINALLY_CLAUSE;
import static compiler.language.parser.ParseType.TRY_KEYWORD;
import static compiler.language.parser.ParseType.TRY_STATEMENT;

import compiler.language.ast.ParseContainer;
import compiler.language.ast.ParseInfo;
import compiler.language.ast.ParseList;
import compiler.language.ast.statement.Block;
import compiler.language.ast.statement.CatchClause;
import compiler.language.ast.statement.TryStatement;
import compiler.parser.ParseException;
import compiler.parser.Production;
import compiler.parser.Rule;

/*
 * Created on 29 Aug 2010
 */

/**
 * @author Anthony Bryant
 */
public class TryStatementRule extends Rule
{
  private static final long serialVersionUID = 1L;

  private static final Production PRODUCTION = new Production(TRY_KEYWORD, BLOCK, CATCH_CLAUSES, FINALLY_CLAUSE);

  public TryStatementRule()
  {
    super(TRY_STATEMENT, PRODUCTION);
  }

  /**
   * {@inheritDoc}
   * @see compiler.parser.Rule#match(compiler.parser.Production, java.lang.Object[])
   */
  @Override
  public Object match(Production production, Object[] args) throws ParseException
  {
    if (PRODUCTION.equals(production))
    {
      Block block = (Block) args[1];
      @SuppressWarnings("unchecked")
      ParseList<CatchClause> catchClauses = (ParseList<CatchClause>) args[2];
      @SuppressWarnings("unchecked")
      ParseContainer<Block> finallyClause = (ParseContainer<Block>) args[3];
      return new TryStatement(block, catchClauses.toArray(new CatchClause[0]), finallyClause.getItem(),
                              ParseInfo.combine((ParseInfo) args[0], block.getParseInfo(), catchClauses.getParseInfo(), finallyClause.getParseInfo()));
    }
    throw badTypeList();
  }

}
