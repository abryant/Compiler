package compiler.language.parser.rules.statement;

import static compiler.language.parser.ParseType.BLOCK;
import static compiler.language.parser.ParseType.DO_KEYWORD;
import static compiler.language.parser.ParseType.DO_STATEMENT;
import static compiler.language.parser.ParseType.EXPRESSION;
import static compiler.language.parser.ParseType.SEMICOLON;
import static compiler.language.parser.ParseType.WHILE_KEYWORD;

import compiler.language.ast.ParseInfo;
import compiler.language.ast.expression.ExpressionAST;
import compiler.language.ast.statement.BlockAST;
import compiler.language.ast.statement.DoStatementAST;
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
public final class DoStatementRule extends Rule<ParseType>
{
  private static final long serialVersionUID = 1L;

  private static final Production<ParseType> PRODUCTION = new Production<ParseType>(DO_KEYWORD, BLOCK, WHILE_KEYWORD, EXPRESSION, SEMICOLON);

  @SuppressWarnings("unchecked")
  public DoStatementRule()
  {
    super(DO_STATEMENT, PRODUCTION);
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
      BlockAST block = (BlockAST) args[1];
      ExpressionAST condition = (ExpressionAST) args[3];
      return new DoStatementAST(block, condition,
                             ParseInfo.combine((ParseInfo) args[0], block.getParseInfo(), (ParseInfo) args[2], condition.getParseInfo(), (ParseInfo) args[4]));
    }
    throw badTypeList();
  }

}
