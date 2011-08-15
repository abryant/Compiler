package compiler.language.parser.rules.statement;

import static compiler.language.parser.ParseType.CATCH_CLAUSE;
import static compiler.language.parser.ParseType.CATCH_CLAUSES;
import parser.ParseException;
import parser.Production;
import parser.Rule;

import compiler.language.LexicalPhrase;
import compiler.language.ast.statement.CatchClauseAST;
import compiler.language.parser.ParseList;
import compiler.language.parser.ParseType;

/*
 * Created on 29 Aug 2010
 */

/**
 * @author Anthony Bryant
 */
public final class CatchClausesRule extends Rule<ParseType>
{
  private static final long serialVersionUID = 1L;

  private static final Production<ParseType> EMPTY_PRODUCTION = new Production<ParseType>();
  private static final Production<ParseType> LIST_PRODUCTION  = new Production<ParseType>(CATCH_CLAUSES, CATCH_CLAUSE);

  @SuppressWarnings("unchecked")
  public CatchClausesRule()
  {
    super(CATCH_CLAUSES, EMPTY_PRODUCTION, LIST_PRODUCTION);
  }

  /**
   * {@inheritDoc}
   * @see parser.Rule#match(parser.Production, java.lang.Object[])
   */
  @Override
  public Object match(Production<ParseType> production, Object[] args) throws ParseException
  {
    if (EMPTY_PRODUCTION.equals(production))
    {
      return new ParseList<CatchClauseAST>(null);
    }
    if (LIST_PRODUCTION.equals(production))
    {
      @SuppressWarnings("unchecked")
      ParseList<CatchClauseAST> list = (ParseList<CatchClauseAST>) args[0];
      CatchClauseAST clause = (CatchClauseAST) args[1];
      list.addLast(clause, LexicalPhrase.combine(list.getLexicalPhrase(), clause.getLexicalPhrase()));
      return list;
    }
    throw badTypeList();
  }

}
