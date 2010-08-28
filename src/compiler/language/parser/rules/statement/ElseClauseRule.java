package compiler.language.parser.rules.statement;

import compiler.language.ast.ParseContainer;
import compiler.language.ast.ParseInfo;
import compiler.language.ast.statement.Block;
import compiler.language.parser.ParseType;
import compiler.parser.ParseException;
import compiler.parser.Rule;

/*
 * Created on 27 Aug 2010
 */

/**
 * @author Anthony Bryant
 */
public class ElseClauseRule extends Rule
{

  private static final Object[] EMPTY_PRODUCTION = new Object[] {};
  private static final Object[] PRODUCTION = new Object[] {ParseType.ELSE_KEYWORD, ParseType.BLOCK};

  public ElseClauseRule()
  {
    super(ParseType.ELSE_CLAUSE, EMPTY_PRODUCTION, PRODUCTION);
  }

  /**
   * {@inheritDoc}
   * @see compiler.parser.Rule#match(java.lang.Object[], java.lang.Object[])
   */
  @Override
  public Object match(Object[] types, Object[] args) throws ParseException
  {
    if (types == EMPTY_PRODUCTION)
    {
      return new ParseContainer<Block>(null, null);
    }
    if (types == PRODUCTION)
    {
      Block block = (Block) args[1];
      return new ParseContainer<Block>(block, ParseInfo.combine((ParseInfo) args[0], block.getParseInfo()));
    }
    throw badTypeList();
  }

}
