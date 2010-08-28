package compiler.language.parser.rules.statement;

import static compiler.language.parser.ParseType.BLOCK;
import static compiler.language.parser.ParseType.EXPRESSION;
import static compiler.language.parser.ParseType.FOR_INIT;
import static compiler.language.parser.ParseType.FOR_KEYWORD;
import static compiler.language.parser.ParseType.FOR_STATEMENT;
import static compiler.language.parser.ParseType.FOR_UPDATE;
import static compiler.language.parser.ParseType.SEMICOLON;

import compiler.language.ast.ParseInfo;
import compiler.language.ast.expression.Expression;
import compiler.language.ast.statement.Block;
import compiler.language.ast.statement.ForStatement;
import compiler.language.ast.statement.Statement;
import compiler.parser.ParseException;
import compiler.parser.Rule;

/*
 * Created on 28 Aug 2010
 */

/**
 * @author Anthony Bryant
 */
public class ForStatementRule extends Rule
{

  private static final Object[] PRODUCTION               = new Object[] {FOR_KEYWORD, FOR_INIT, SEMICOLON, EXPRESSION, SEMICOLON, FOR_UPDATE, BLOCK};
  private static final Object[] NO_EXPRESSION_PRODUCTION = new Object[] {FOR_KEYWORD, FOR_INIT, SEMICOLON,             SEMICOLON, FOR_UPDATE, BLOCK};

  public ForStatementRule()
  {
    super(FOR_STATEMENT, PRODUCTION, NO_EXPRESSION_PRODUCTION);
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
      Statement init = (Statement) args[1];
      Expression condition = (Expression) args[3];
      Statement update = (Statement) args[5];
      Block block = (Block) args[6];
      return new ForStatement(init, condition, update, block,
                              ParseInfo.combine((ParseInfo) args[0],
                                                init != null ? init.getParseInfo() : null,
                                                (ParseInfo) args[2],
                                                condition.getParseInfo(),
                                                (ParseInfo) args[4],
                                                update != null ? update.getParseInfo() : null,
                                                block.getParseInfo()));
    }
    if (types == NO_EXPRESSION_PRODUCTION)
    {
      Statement init = (Statement) args[1];
      Statement update = (Statement) args[4];
      Block block = (Block) args[5];
      return new ForStatement(init, null, update, block,
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
