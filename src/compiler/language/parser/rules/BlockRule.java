package compiler.language.parser.rules;

import static compiler.language.parser.ParseType.BLOCK;
import static compiler.language.parser.ParseType.LBRACE;
import static compiler.language.parser.ParseType.RBRACE;
import static compiler.language.parser.ParseType.STATEMENTS;

import compiler.language.ast.statement.Block;
import compiler.language.ast.statement.Statement;
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
  public Object match(Object[] types, Object[] args)
  {
    if (types == EMPTY_PRODUCTION)
    {
      return new Block(new Statement[0]);
    }
    if (types == PRODUCTION)
    {
      return new Block((Statement[]) args[1]);
    }
    throw badTypeList();
  }

}
