package compiler.language.parser.rules.statement;

import static compiler.language.parser.ParseType.BLOCK;
import static compiler.language.parser.ParseType.ELSE_CLAUSE;
import static compiler.language.parser.ParseType.ELSE_IF_CLAUSES;
import static compiler.language.parser.ParseType.EXPRESSION;
import static compiler.language.parser.ParseType.IF_KEYWORD;
import static compiler.language.parser.ParseType.IF_STATEMENT;

import compiler.language.ast.ParseContainer;
import compiler.language.ast.ParseInfo;
import compiler.language.ast.ParseList;
import compiler.language.ast.expression.Expression;
import compiler.language.ast.statement.Block;
import compiler.language.ast.statement.ElseIfClause;
import compiler.language.ast.statement.IfStatement;
import compiler.parser.ParseException;
import compiler.parser.Production;
import compiler.parser.Rule;

/*
 * Created on 27 Aug 2010
 */

/**
 * @author Anthony Bryant
 */
public class IfStatementRule extends Rule
{
  private static final long serialVersionUID = 1L;

  private static final Production PRODUCTION = new Production(IF_KEYWORD, EXPRESSION, BLOCK, ELSE_IF_CLAUSES, ELSE_CLAUSE);

  public IfStatementRule()
  {
    super(IF_STATEMENT, PRODUCTION);
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
      Expression condition = (Expression) args[1];
      Block block = (Block) args[2];
      @SuppressWarnings("unchecked")
      ParseList<ElseIfClause> elseIfClauses = (ParseList<ElseIfClause>) args[3];
      @SuppressWarnings("unchecked")
      ParseContainer<Block> elseClause = (ParseContainer<Block>) args[4];
      return new IfStatement(condition, block, elseIfClauses.toArray(new ElseIfClause[0]), elseClause.getItem(),
                             ParseInfo.combine((ParseInfo) args[0], condition.getParseInfo(), block.getParseInfo(), elseIfClauses.getParseInfo(), elseClause.getParseInfo()));
    }
    throw badTypeList();
  }

}
