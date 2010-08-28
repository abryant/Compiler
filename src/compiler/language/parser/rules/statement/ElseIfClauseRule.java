package compiler.language.parser.rules.statement;

import static compiler.language.parser.ParseType.BLOCK;
import static compiler.language.parser.ParseType.ELSE_IF_CLAUSE;
import static compiler.language.parser.ParseType.ELSE_KEYWORD;
import static compiler.language.parser.ParseType.EXPRESSION;
import static compiler.language.parser.ParseType.IF_KEYWORD;

import compiler.language.ast.ParseInfo;
import compiler.language.ast.expression.Expression;
import compiler.language.ast.statement.Block;
import compiler.language.ast.statement.ElseIfClause;
import compiler.parser.ParseException;
import compiler.parser.Rule;

/*
 * Created on 27 Aug 2010
 */

/**
 * @author Anthony Bryant
 */
public class ElseIfClauseRule extends Rule
{

  private static final Object[] PRODUCTION = new Object[] {ELSE_KEYWORD, IF_KEYWORD, EXPRESSION, BLOCK};

  public ElseIfClauseRule()
  {
    super(ELSE_IF_CLAUSE, PRODUCTION);
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
      Expression condition = (Expression) args[2];
      Block block = (Block) args[3];
      return new ElseIfClause(condition, block,
                              ParseInfo.combine((ParseInfo) args[0], (ParseInfo) args[1], condition.getParseInfo(), block.getParseInfo()));
    }
    throw badTypeList();
  }

}
