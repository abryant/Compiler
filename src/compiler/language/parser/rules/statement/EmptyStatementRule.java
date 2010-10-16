package compiler.language.parser.rules.statement;

import static compiler.language.parser.ParseType.EMPTY_STATEMENT;
import static compiler.language.parser.ParseType.SEMICOLON;
import parser.ParseException;
import parser.Production;
import parser.Rule;

import compiler.language.ast.ParseInfo;
import compiler.language.ast.statement.EmptyStatementAST;
import compiler.language.parser.ParseType;

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
   * @see parser.Rule#match(parser.Production, java.lang.Object[])
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
