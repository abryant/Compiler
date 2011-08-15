package compiler.language.parser.rules.misc;

import static compiler.language.parser.ParseType.THROWS_CLAUSE;
import static compiler.language.parser.ParseType.THROWS_KEYWORD;
import static compiler.language.parser.ParseType.THROWS_LIST;
import parser.ParseException;
import parser.Production;
import parser.Rule;

import compiler.language.LexicalPhrase;
import compiler.language.ast.type.PointerTypeAST;
import compiler.language.parser.ParseList;
import compiler.language.parser.ParseType;

/*
 * Created on 8 Jul 2010
 */

/**
 * @author Anthony Bryant
 */
public final class ThrowsClauseRule extends Rule<ParseType>
{
  private static final long serialVersionUID = 1L;

  private static final Production<ParseType> EMPTY_PRODUCTION = new Production<ParseType>();
  private static final Production<ParseType> PRODUCTION = new Production<ParseType>(THROWS_KEYWORD, THROWS_LIST);

  @SuppressWarnings("unchecked")
  public ThrowsClauseRule()
  {
    super(THROWS_CLAUSE, EMPTY_PRODUCTION, PRODUCTION);
  }

  /**
   * @see parser.Rule#match(parser.Production, java.lang.Object[])
   */
  @Override
  public Object match(Production<ParseType> production, Object[] args) throws ParseException
  {
    if (EMPTY_PRODUCTION.equals(production))
    {
      return new ParseList<PointerTypeAST>(null);
    }
    if (PRODUCTION.equals(production))
    {
      @SuppressWarnings("unchecked")
      ParseList<PointerTypeAST> list = (ParseList<PointerTypeAST>) args[1];
      list.setLexicalPhrase(LexicalPhrase.combine((LexicalPhrase) args[0], list.getLexicalPhrase()));
      return list;
    }
    throw badTypeList();
  }

}
