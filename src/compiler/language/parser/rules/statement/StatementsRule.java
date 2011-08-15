package compiler.language.parser.rules.statement;

import static compiler.language.parser.ParseType.STATEMENT;
import static compiler.language.parser.ParseType.STATEMENTS;
import parser.ParseException;
import parser.Production;
import parser.Rule;

import compiler.language.LexicalPhrase;
import compiler.language.ast.statement.StatementAST;
import compiler.language.parser.ParseList;
import compiler.language.parser.ParseType;

/*
 * Created on 9 Jul 2010
 */

/**
 * @author Anthony Bryant
 */
public final class StatementsRule extends Rule<ParseType>
{
  private static final long serialVersionUID = 1L;

  private static final Production<ParseType> START_PRODUCTION = new Production<ParseType>(STATEMENT);
  private static final Production<ParseType> CONTINUATION_PRODUCTION = new Production<ParseType>(STATEMENTS, STATEMENT);

  @SuppressWarnings("unchecked")
  public StatementsRule()
  {
    super(STATEMENTS, START_PRODUCTION, CONTINUATION_PRODUCTION);
  }

  /**
   * @see parser.Rule#match(parser.Production, java.lang.Object[])
   */
  @Override
  public Object match(Production<ParseType> production, Object[] args) throws ParseException
  {
    if (START_PRODUCTION.equals(production))
    {
      StatementAST statement = (StatementAST) args[0];
      return new ParseList<StatementAST>(statement, statement.getLexicalPhrase());
    }
    if (CONTINUATION_PRODUCTION.equals(production))
    {
      @SuppressWarnings("unchecked")
      ParseList<StatementAST> list = (ParseList<StatementAST>) args[0];
      StatementAST statement = (StatementAST) args[1];
      list.addLast(statement, LexicalPhrase.combine(list.getLexicalPhrase(), statement.getLexicalPhrase()));
      return list;
    }
    throw badTypeList();
  }

}
