package compiler.language.parser.rules.statement;

import compiler.language.ast.ParseContainer;
import compiler.language.ast.ParseInfo;
import compiler.language.ast.statement.Block;
import compiler.language.parser.ParseType;
import compiler.parser.ParseException;
import compiler.parser.Production;
import compiler.parser.Rule;

/*
 * Created on 27 Aug 2010
 */

/**
 * @author Anthony Bryant
 */
public class ElseClauseRule extends Rule
{
  private static final long serialVersionUID = 1L;

  private static final Production EMPTY_PRODUCTION = new Production();
  private static final Production PRODUCTION = new Production(ParseType.ELSE_KEYWORD, ParseType.BLOCK);

  public ElseClauseRule()
  {
    super(ParseType.ELSE_CLAUSE, EMPTY_PRODUCTION, PRODUCTION);
  }

  /**
   * {@inheritDoc}
   * @see compiler.parser.Rule#match(compiler.parser.Production, java.lang.Object[])
   */
  @Override
  public Object match(Production production, Object[] args) throws ParseException
  {
    if (EMPTY_PRODUCTION.equals(production))
    {
      return new ParseContainer<Block>(null, null);
    }
    if (PRODUCTION.equals(production))
    {
      Block block = (Block) args[1];
      return new ParseContainer<Block>(block, ParseInfo.combine((ParseInfo) args[0], block.getParseInfo()));
    }
    throw badTypeList();
  }

}
