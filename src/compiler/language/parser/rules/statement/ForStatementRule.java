package compiler.language.parser.rules.statement;

import static compiler.language.parser.ParseType.BLOCK;
import static compiler.language.parser.ParseType.EXPRESSION;
import static compiler.language.parser.ParseType.FOR_INIT;
import static compiler.language.parser.ParseType.FOR_KEYWORD;
import static compiler.language.parser.ParseType.FOR_STATEMENT;
import static compiler.language.parser.ParseType.FOR_UPDATE;
import static compiler.language.parser.ParseType.SEMICOLON;
import parser.ParseException;
import parser.Production;
import parser.Rule;

import compiler.language.ast.ParseInfo;
import compiler.language.ast.expression.ExpressionAST;
import compiler.language.ast.statement.BlockAST;
import compiler.language.ast.statement.ForStatementAST;
import compiler.language.ast.statement.StatementAST;
import compiler.language.parser.ParseType;

/*
 * Created on 28 Aug 2010
 */

/**
 * @author Anthony Bryant
 */
public final class ForStatementRule extends Rule<ParseType>
{
  private static final long serialVersionUID = 1L;

  private static final Production<ParseType> PRODUCTION               = new Production<ParseType>(FOR_KEYWORD, FOR_INIT, SEMICOLON, EXPRESSION, SEMICOLON, FOR_UPDATE, BLOCK);
  private static final Production<ParseType> NO_EXPRESSION_PRODUCTION = new Production<ParseType>(FOR_KEYWORD, FOR_INIT, SEMICOLON,             SEMICOLON, FOR_UPDATE, BLOCK);

  @SuppressWarnings("unchecked")
  public ForStatementRule()
  {
    super(FOR_STATEMENT, PRODUCTION, NO_EXPRESSION_PRODUCTION);
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
      StatementAST init = (StatementAST) args[1];
      ExpressionAST condition = (ExpressionAST) args[3];
      StatementAST update = (StatementAST) args[5];
      BlockAST block = (BlockAST) args[6];
      return new ForStatementAST(init, condition, update, block,
                              ParseInfo.combine((ParseInfo) args[0],
                                                init != null ? init.getParseInfo() : null,
                                                (ParseInfo) args[2],
                                                condition.getParseInfo(),
                                                (ParseInfo) args[4],
                                                update != null ? update.getParseInfo() : null,
                                                block.getParseInfo()));
    }
    if (NO_EXPRESSION_PRODUCTION.equals(production))
    {
      StatementAST init = (StatementAST) args[1];
      StatementAST update = (StatementAST) args[4];
      BlockAST block = (BlockAST) args[5];
      return new ForStatementAST(init, null, update, block,
                              ParseInfo.combine((ParseInfo) args[0],
                                                init != null ? init.getParseInfo() : null,
                                                (ParseInfo) args[2],
                                                (ParseInfo) args[3],
                                                update != null ? update.getParseInfo() : null,
                                                block.getParseInfo()));
    }
    throw badTypeList();
  }

}
