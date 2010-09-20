package compiler.language.parser.rules.statement;

import static compiler.language.parser.ParseType.EMPTY_STATEMENT;
import static compiler.language.parser.ParseType.SEMICOLON;

import compiler.language.ast.ParseInfo;
import compiler.language.ast.statement.EmptyStatement;
import compiler.parser.ParseException;
import compiler.parser.Production;
import compiler.parser.Rule;

/*
 * Created on 14 Jul 2010
 */

/**
 * @author Anthony Bryant
 */
public class EmptyStatementRule extends Rule
{
  private static final long serialVersionUID = 1L;

  // TODO: decide whether to keep this
  private static final Production PRODUCTION = new Production(SEMICOLON);

  public EmptyStatementRule()
  {
    super(EMPTY_STATEMENT, PRODUCTION);
  }

  /**
   * @see compiler.parser.Rule#match(compiler.parser.Production, java.lang.Object[])
   */
  @Override
  public Object match(Production production, Object[] args) throws ParseException
  {
    if (PRODUCTION.equals(production))
    {
      return new EmptyStatement((ParseInfo) args[0]);
    }
    throw badTypeList();
  }

}
