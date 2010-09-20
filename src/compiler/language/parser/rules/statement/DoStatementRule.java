package compiler.language.parser.rules.statement;

import static compiler.language.parser.ParseType.BLOCK;
import static compiler.language.parser.ParseType.DO_KEYWORD;
import static compiler.language.parser.ParseType.DO_STATEMENT;
import static compiler.language.parser.ParseType.EXPRESSION;
import static compiler.language.parser.ParseType.SEMICOLON;
import static compiler.language.parser.ParseType.WHILE_KEYWORD;

import compiler.language.ast.ParseInfo;
import compiler.language.ast.expression.Expression;
import compiler.language.ast.statement.Block;
import compiler.language.ast.statement.DoStatement;
import compiler.parser.ParseException;
import compiler.parser.Production;
import compiler.parser.Rule;


/*
 * Created on 28 Aug 2010
 */

/**
 * @author Anthony Bryant
 */
public class DoStatementRule extends Rule
{
  private static final long serialVersionUID = 1L;

  private static final Production PRODUCTION = new Production(DO_KEYWORD, BLOCK, WHILE_KEYWORD, EXPRESSION, SEMICOLON);

  public DoStatementRule()
  {
    super(DO_STATEMENT, PRODUCTION);
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
      Expression condition = (Expression) args[3];
      return new DoStatement(block, condition,
                             ParseInfo.combine((ParseInfo) args[0], block.getParseInfo(), (ParseInfo) args[2], condition.getParseInfo(), (ParseInfo) args[4]));
    }
    throw badTypeList();
  }

}
