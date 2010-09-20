package compiler.language.parser.rules.statement;

import static compiler.language.parser.ParseType.BLOCK;
import static compiler.language.parser.ParseType.EXPRESSION;
import static compiler.language.parser.ParseType.WHILE_KEYWORD;
import static compiler.language.parser.ParseType.WHILE_STATEMENT;

import compiler.language.ast.ParseInfo;
import compiler.language.ast.expression.Expression;
import compiler.language.ast.statement.Block;
import compiler.language.ast.statement.WhileStatement;
import compiler.parser.ParseException;
import compiler.parser.Production;
import compiler.parser.Rule;

/*
 * Created on 28 Aug 2010
 */

/**
 * @author Anthony Bryant
 */
public class WhileStatementRule extends Rule
{
  private static final long serialVersionUID = 1L;

  private static final Production PRODUCTION = new Production(WHILE_KEYWORD, EXPRESSION, BLOCK);

  public WhileStatementRule()
  {
    super(WHILE_STATEMENT, PRODUCTION);
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
      return new WhileStatement(condition, block,
                                ParseInfo.combine((ParseInfo) args[0], condition.getParseInfo(), block.getParseInfo()));
    }
    throw badTypeList();
  }

}
