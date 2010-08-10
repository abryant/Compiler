package compiler.language.parser.rules.statement;

import static compiler.language.parser.ParseType.STATEMENT;
import static compiler.language.parser.ParseType.STATEMENTS;

import compiler.language.ast.ParseInfo;
import compiler.language.ast.ParseList;
import compiler.language.ast.statement.Statement;
import compiler.parser.ParseException;
import compiler.parser.Rule;

/*
 * Created on 9 Jul 2010
 */

/**
 * @author Anthony Bryant
 */
public class StatementsRule extends Rule
{

  private static final Object[] START_PRODUCTION = new Object[] {STATEMENT};
  private static final Object[] CONTINUATION_PRODUCTION = new Object[] {STATEMENTS, STATEMENT};

  public StatementsRule()
  {
    super(STATEMENTS, START_PRODUCTION, CONTINUATION_PRODUCTION);
  }

  /**
   * @see compiler.parser.Rule#match(java.lang.Object[], java.lang.Object[])
   */
  @Override
  public Object match(Object[] types, Object[] args) throws ParseException
  {
    if (types == START_PRODUCTION)
    {
      Statement statement = (Statement) args[0];
      return new ParseList<Statement>(statement, statement.getParseInfo());
    }
    if (types == CONTINUATION_PRODUCTION)
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
