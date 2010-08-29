package compiler.language.parser.rules.statement;

import static compiler.language.parser.ParseType.CATCH_CLAUSE;
import static compiler.language.parser.ParseType.CATCH_CLAUSES;

import compiler.language.ast.ParseInfo;
import compiler.language.ast.ParseList;
import compiler.language.ast.statement.CatchClause;
import compiler.parser.ParseException;
import compiler.parser.Rule;

/*
 * Created on 29 Aug 2010
 */

/**
 * @author Anthony Bryant
 */
public class CatchClausesRule extends Rule
{

  private static final Object[] EMPTY_PRODUCTION = new Object[] {};
  private static final Object[] LIST_PRODUCTION  = new Object[] {CATCH_CLAUSES, CATCH_CLAUSE};

  public CatchClausesRule()
  {
    super(CATCH_CLAUSES, EMPTY_PRODUCTION, LIST_PRODUCTION);
  }

  /**
   * {@inheritDoc}
   * @see compiler.parser.Rule#match(java.lang.Object[], java.lang.Object[])
   */
  @Override
  public Object match(Object[] types, Object[] args) throws ParseException
  {
    if (types == EMPTY_PRODUCTION)
    {
      return new ParseList<CatchClause>(null);
    }
    if (types == LIST_PRODUCTION)
    {
      @SuppressWarnings("unchecked")
      ParseList<CatchClause> list = (ParseList<CatchClause>) args[0];
      CatchClause clause = (CatchClause) args[1];
      list.addLast(clause, ParseInfo.combine(list.getParseInfo(), clause.getParseInfo()));
      return list;
    }
    throw badTypeList();
  }

}
