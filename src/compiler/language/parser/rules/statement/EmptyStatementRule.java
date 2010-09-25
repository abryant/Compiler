package compiler.language.parser.rules.statement;

import static compiler.language.parser.ParseType.EMPTY_STATEMENT;
import static compiler.language.parser.ParseType.SEMICOLON;

import compiler.language.ast.ParseInfo;
import compiler.language.ast.statement.EmptyStatementAST;
import compiler.language.parser.ParseType;
import compiler.parser.ParseException;
import compiler.parser.Production;
import compiler.parser.Rule;

/*
 * Created on 14 Jul 2010
 */

/**
 * @author Anthony Bryant
 */
public final class EmptyStatementRule extends Rule<ParseType>
{
  private static final long serialVersionUID = 1L;

  // TODO: decide whether to keep this
  private static final Production<ParseType> PRODUCTION = new Production<ParseType>(SEMICOLON);

  @SuppressWarnings("unchecked")
  public EmptyStatementRule()
  {
    super(EMPTY_STATEMENT, PRODUCTION);
  }

  /**
   * @see compiler.parser.Rule#match(compiler.parser.Production, java.lang.Object[])
   */
  @Override
  public Object match(Production<ParseType> production, Object[] args) throws ParseException
  {
    if (PRODUCTION.equals(production))
    {
      return new EmptyStatementAST((ParseInfo) args[0]);
    }
    throw badTypeList();
  }

}
