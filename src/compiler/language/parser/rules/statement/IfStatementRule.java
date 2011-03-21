package compiler.language.parser.rules.statement;

import static compiler.language.parser.ParseType.BLOCK;
import static compiler.language.parser.ParseType.ELSE_CLAUSE;
import static compiler.language.parser.ParseType.ELSE_IF_CLAUSES;
import static compiler.language.parser.ParseType.EXPRESSION;
import static compiler.language.parser.ParseType.IF_KEYWORD;
import static compiler.language.parser.ParseType.IF_STATEMENT;
import parser.ParseException;
import parser.Production;
import parser.Rule;

import compiler.language.ast.ParseInfo;
import compiler.language.ast.expression.ExpressionAST;
import compiler.language.ast.statement.BlockAST;
import compiler.language.ast.statement.ElseIfClauseAST;
import compiler.language.ast.statement.IfStatementAST;
import compiler.language.parser.ParseContainer;
import compiler.language.parser.ParseList;
import compiler.language.parser.ParseType;

/*
 * Created on 27 Aug 2010
 */

/**
 * @author Anthony Bryant
 */
public final class IfStatementRule extends Rule<ParseType>
{
  private static final long serialVersionUID = 1L;

  private static final Production<ParseType> PRODUCTION = new Production<ParseType>(IF_KEYWORD, EXPRESSION, BLOCK, ELSE_IF_CLAUSES, ELSE_CLAUSE);

  @SuppressWarnings("unchecked")
  public IfStatementRule()
  {
    super(IF_STATEMENT, PRODUCTION);
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
      ExpressionAST condition = (ExpressionAST) args[1];
      BlockAST block = (BlockAST) args[2];
      @SuppressWarnings("unchecked")
      ParseList<ElseIfClauseAST> elseIfClauses = (ParseList<ElseIfClauseAST>) args[3];
      @SuppressWarnings("unchecked")
      ParseContainer<BlockAST> elseClause = (ParseContainer<BlockAST>) args[4];
      return new IfStatementAST(condition, block, elseIfClauses.toArray(new ElseIfClauseAST[0]), elseClause.getItem(),
                             ParseInfo.combine((ParseInfo) args[0], condition.getParseInfo(), block.getParseInfo(), elseIfClauses.getParseInfo(), elseClause.getParseInfo()));
    }
    throw badTypeList();
  }

}
