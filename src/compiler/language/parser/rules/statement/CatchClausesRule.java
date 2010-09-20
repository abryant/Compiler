package compiler.language.parser.rules.statement;

import static compiler.language.parser.ParseType.CATCH_CLAUSE;
import static compiler.language.parser.ParseType.CATCH_CLAUSES;

import compiler.language.ast.ParseInfo;
import compiler.language.ast.ParseList;
import compiler.language.ast.statement.CatchClause;
import compiler.parser.ParseException;
import compiler.parser.Production;
import compiler.parser.Rule;

/*
 * Created on 29 Aug 2010
 */

/**
 * @author Anthony Bryant
 */
public class CatchClausesRule extends Rule
{
  private static final long serialVersionUID = 1L;

  private static final Production EMPTY_PRODUCTION = new Production();
  private static final Production LIST_PRODUCTION  = new Production(CATCH_CLAUSES, CATCH_CLAUSE);

  public CatchClausesRule()
  {
    super(CATCH_CLAUSES, EMPTY_PRODUCTION, LIST_PRODUCTION);
  }

  /**
   * {@inheritDoc}
   * @see compiler.parser.Rule#match(compiler.parser.Production, java.lang.Object[])
   */
  @Override
  public Object match(Production production, Object[] args) throws ParseException
  {
    if (EMPTY_PRODUCTION.equals(production))
    {
      return new ParseList<CatchClause>(null);
    }
    if (LIST_PRODUCTION.equals(production))
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
