package compiler.language.parser.rules.statement;

import static compiler.language.parser.ParseType.EMPTY_STATEMENT;
import static compiler.language.parser.ParseType.SEMICOLON;

import compiler.language.ast.statement.EmptyStatement;
import compiler.parser.Rule;

/*
 * Created on 14 Jul 2010
 */

/**
 * @author Anthony Bryant
 */
public class EmptyStatementRule extends Rule
{
  // TODO: decide whether to keep this
  private static final Object[] PRODUCTION = new Object[] {SEMICOLON};

  public EmptyStatementRule()
  {
    super(EMPTY_STATEMENT, PRODUCTION);
  }

  /**
   * @see compiler.parser.Rule#match(java.lang.Object[], java.lang.Object[])
   */
  @Override
  public Object match(Object[] types, Object[] args)
  {
    if (types == PRODUCTION)
    {
      return new EmptyStatement();
    }
    throw badTypeList();
  }

}
