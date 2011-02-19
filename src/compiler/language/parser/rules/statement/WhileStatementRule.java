package compiler.language.parser.rules.statement;

import static compiler.language.parser.ParseType.BLOCK;
import static compiler.language.parser.ParseType.EXPRESSION;
import static compiler.language.parser.ParseType.WHILE_KEYWORD;
import static compiler.language.parser.ParseType.WHILE_STATEMENT;
import parser.ParseException;
import parser.Production;
import parser.Rule;

import compiler.language.ast.ParseInfo;
import compiler.language.ast.expression.ExpressionAST;
import compiler.language.ast.statement.BlockAST;
import compiler.language.ast.statement.WhileStatementAST;
import compiler.language.parser.ParseType;

/*
 * Created on 28 Aug 2010
 */

/**
 * @author Anthony Bryant
 */
public final class WhileStatementRule extends Rule<ParseType>
{
  private static final long serialVersionUID = 1L;

  private static final Production<ParseType> PRODUCTION = new Production<ParseType>(WHILE_KEYWORD, EXPRESSION, BLOCK);

  @SuppressWarnings("unchecked")
  public WhileStatementRule()
  {
    super(WHILE_STATEMENT, PRODUCTION);
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
      return new WhileStatementAST(condition, block,
                                ParseInfo.combine((ParseInfo) args[0], condition.getParseInfo(), block.getParseInfo()));
    }
    throw badTypeList();
  }

}
