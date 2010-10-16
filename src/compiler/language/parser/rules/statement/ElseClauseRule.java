package compiler.language.parser.rules.statement;

import parser.ParseException;
import parser.Production;
import parser.Rule;
import compiler.language.ast.ParseContainer;
import compiler.language.ast.ParseInfo;
import compiler.language.ast.statement.BlockAST;
import compiler.language.parser.ParseType;

/*
 * Created on 27 Aug 2010
 */

/**
 * @author Anthony Bryant
 */
public final class ElseClauseRule extends Rule<ParseType>
{
  private static final long serialVersionUID = 1L;

  private static final Production<ParseType> EMPTY_PRODUCTION = new Production<ParseType>();
  private static final Production<ParseType> PRODUCTION = new Production<ParseType>(ParseType.ELSE_KEYWORD, ParseType.BLOCK);

  @SuppressWarnings("unchecked")
  public ElseClauseRule()
  {
    super(ParseType.ELSE_CLAUSE, EMPTY_PRODUCTION, PRODUCTION);
  }

  /**
   * {@inheritDoc}
   * @see parser.Rule#match(parser.Production, java.lang.Object[])
   */
  @Override
  public Object match(Production<ParseType> production, Object[] args) throws ParseException
  {
    if (EMPTY_PRODUCTION.equals(production))
    {
      return new ParseContainer<BlockAST>(null, null);
    }
    if (PRODUCTION.equals(production))
    {
      BlockAST block = (BlockAST) args[1];
      return new ParseContainer<BlockAST>(block, ParseInfo.combine((ParseInfo) args[0], block.getParseInfo()));
    }
    throw badTypeList();
  }

}
