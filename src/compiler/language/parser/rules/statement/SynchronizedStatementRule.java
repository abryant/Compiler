package compiler.language.parser.rules.statement;

import static compiler.language.parser.ParseType.BLOCK;
import static compiler.language.parser.ParseType.EXPRESSION;
import static compiler.language.parser.ParseType.SYNCHRONIZED_KEYWORD;
import static compiler.language.parser.ParseType.SYNCHRONIZED_STATEMENT;

import compiler.language.ast.ParseInfo;
import compiler.language.ast.expression.Expression;
import compiler.language.ast.statement.Block;
import compiler.language.ast.statement.SynchronizedStatement;
import compiler.parser.ParseException;
import compiler.parser.Rule;

/*
 * Created on 28 Aug 2010
 */

/**
 * @author Anthony Bryant
 */
public class SynchronizedStatementRule extends Rule
{

  private static final Object[] PRODUCTION               = new Object[] {SYNCHRONIZED_KEYWORD, EXPRESSION, BLOCK};
  private static final Object[] NO_EXPRESSION_PRODUCTION = new Object[] {SYNCHRONIZED_KEYWORD,             BLOCK};

  public SynchronizedStatementRule()
  {
    super(SYNCHRONIZED_STATEMENT, PRODUCTION/* TODO:, NO_EXPRESSION_PRODUCTION*/);
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
      Expression expression = (Expression) args[1];
      Block block = (Block) args[2];
      return new SynchronizedStatement(expression, block,
                                       ParseInfo.combine((ParseInfo) args[0], expression.getParseInfo(), block.getParseInfo()));
    }
    if (types == NO_EXPRESSION_PRODUCTION)
    {
      Block block = (Block) args[1];
      return new SynchronizedStatement(null, block,
                                       ParseInfo.combine((ParseInfo) args[0], block.getParseInfo()));
    }
    throw badTypeList();
  }

}
