package compiler.language.parser.rules.statement;

import static compiler.language.parser.ParseType.BLOCK;
import static compiler.language.parser.ParseType.LBRACE;
import static compiler.language.parser.ParseType.RBRACE;
import static compiler.language.parser.ParseType.STATEMENTS;

import compiler.language.ast.ParseInfo;
import compiler.language.ast.ParseList;
import compiler.language.ast.statement.Block;
import compiler.language.ast.statement.Statement;
import compiler.parser.ParseException;
import compiler.parser.Rule;

/*
 * Created on 9 Jul 2010
 */

/**
 * @author Anthony Bryant
 */
public class BlockRule extends Rule
{

  private static final Object[] EMPTY_PRODUCTION = new Object[] {LBRACE, RBRACE};
  private static final Object[] PRODUCTION = new Object[] {LBRACE, STATEMENTS, RBRACE};

  public BlockRule()
  {
    super(BLOCK, EMPTY_PRODUCTION, PRODUCTION);
  }

  /**
   * @see compiler.parser.Rule#match(java.lang.Object[], java.lang.Object[])
   */
  @Override
  public Object match(Object[] types, Object[] args) throws ParseException
  {
    if (types == EMPTY_PRODUCTION)
    {
      return new Block(new Statement[0], ParseInfo.combine((ParseInfo) args[0], (ParseInfo) args[1]));
    }
    if (types == PRODUCTION)
    {
      @SuppressWarnings("unchecked")
      ParseList<Statement> statements = (ParseList<Statement>) args[1];
      return new Block(statements.toArray(new Statement[0]), ParseInfo.combine((ParseInfo) args[0], statements.getParseInfo(), (ParseInfo) args[2]));
    }
    throw badTypeList();
  }

}
