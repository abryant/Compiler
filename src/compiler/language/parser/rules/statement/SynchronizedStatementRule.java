package compiler.language.parser.rules.statement;

import static compiler.language.parser.ParseType.BLOCK;
import static compiler.language.parser.ParseType.EXPRESSION;
import static compiler.language.parser.ParseType.SYNCHRONIZED_KEYWORD;
import static compiler.language.parser.ParseType.SYNCHRONIZED_STATEMENT;
import parser.ParseException;
import parser.Production;
import parser.Rule;

import compiler.language.ast.ParseInfo;
import compiler.language.ast.expression.ExpressionAST;
import compiler.language.ast.statement.BlockAST;
import compiler.language.ast.statement.SynchronizedStatementAST;
import compiler.language.parser.ParseType;

/*
 * Created on 28 Aug 2010
 */

/**
 * @author Anthony Bryant
 */
public final class SynchronizedStatementRule extends Rule<ParseType>
{
  private static final long serialVersionUID = 1L;

  private static final Production<ParseType> PRODUCTION               = new Production<ParseType>(SYNCHRONIZED_KEYWORD, EXPRESSION, BLOCK);
  private static final Production<ParseType> NO_EXPRESSION_PRODUCTION = new Production<ParseType>(SYNCHRONIZED_KEYWORD,             BLOCK);

  @SuppressWarnings("unchecked")
  public SynchronizedStatementRule()
  {
    super(SYNCHRONIZED_STATEMENT, PRODUCTION, NO_EXPRESSION_PRODUCTION);
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
      ExpressionAST expression = (ExpressionAST) args[1];
      BlockAST block = (BlockAST) args[2];
      return new SynchronizedStatementAST(expression, block,
                                       ParseInfo.combine((ParseInfo) args[0], expression.getParseInfo(), block.getParseInfo()));
    }
    if (NO_EXPRESSION_PRODUCTION.equals(production))
    {
      BlockAST block = (BlockAST) args[1];
      return new SynchronizedStatementAST(null, block,
                                       ParseInfo.combine((ParseInfo) args[0], block.getParseInfo()));
    }
    throw badTypeList();
  }

}
