package compiler.language.parser.rules.statement;

import static compiler.language.parser.ParseType.BLOCK;
import static compiler.language.parser.ParseType.FINALLY_CLAUSE;
import static compiler.language.parser.ParseType.FINALLY_KEYWORD;

import compiler.language.ast.ParseContainer;
import compiler.language.ast.ParseInfo;
import compiler.language.ast.statement.Block;
import compiler.parser.ParseException;
import compiler.parser.Rule;

/*
 * Created on 29 Aug 2010
 */

/**
 * @author Anthony Bryant
 */
public class FinallyClauseRule extends Rule
{

  private static final Object[] PRODUCTION = new Object[] {FINALLY_KEYWORD, BLOCK};

  public FinallyClauseRule()
  {
    super(FINALLY_CLAUSE, PRODUCTION);
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
      Block block = (Block) args[1];
      return new ParseContainer<Block>(block, ParseInfo.combine((ParseInfo) args[0], block.getParseInfo()));
    }
    throw badTypeList();
  }

}
