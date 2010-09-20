package compiler.language.parser.rules.statement;

import static compiler.language.parser.ParseType.STATEMENT;
import static compiler.language.parser.ParseType.STATEMENTS;

import compiler.language.ast.ParseInfo;
import compiler.language.ast.ParseList;
import compiler.language.ast.statement.Statement;
import compiler.parser.ParseException;
import compiler.parser.Production;
import compiler.parser.Rule;

/*
 * Created on 9 Jul 2010
 */

/**
 * @author Anthony Bryant
 */
public class StatementsRule extends Rule
{
  private static final long serialVersionUID = 1L;

  private static final Production START_PRODUCTION = new Production(STATEMENT);
  private static final Production CONTINUATION_PRODUCTION = new Production(STATEMENTS, STATEMENT);

  public StatementsRule()
  {
    super(STATEMENTS, START_PRODUCTION, CONTINUATION_PRODUCTION);
  }

  /**
   * @see compiler.parser.Rule#match(compiler.parser.Production, java.lang.Object[])
   */
  @Override
  public Object match(Production production, Object[] args) throws ParseException
  {
    if (START_PRODUCTION.equals(production))
    {
      Statement statement = (Statement) args[0];
      return new ParseList<Statement>(statement, statement.getParseInfo());
    }
    if (CONTINUATION_PRODUCTION.equals(production))
    {
      @SuppressWarnings("unchecked")
      ParseList<Statement> list = (ParseList<Statement>) args[0];
      Statement statement = (Statement) args[1];
      list.addLast(statement, ParseInfo.combine(list.getParseInfo(), statement.getParseInfo()));
      return list;
    }
    throw badTypeList();
  }

}
