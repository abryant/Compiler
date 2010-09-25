package compiler.language.parser.rules.statement;

import static compiler.language.parser.ParseType.BLOCK;
import static compiler.language.parser.ParseType.LBRACE;
import static compiler.language.parser.ParseType.RBRACE;
import static compiler.language.parser.ParseType.STATEMENTS;

import compiler.language.ast.ParseInfo;
import compiler.language.ast.ParseList;
import compiler.language.ast.statement.BlockAST;
import compiler.language.ast.statement.StatementAST;
import compiler.language.parser.ParseType;
import compiler.parser.ParseException;
import compiler.parser.Production;
import compiler.parser.Rule;

/*
 * Created on 9 Jul 2010
 */

/**
 * @author Anthony Bryant
 */
public final class BlockRule extends Rule<ParseType>
{
  private static final long serialVersionUID = 1L;

  private static final Production<ParseType> EMPTY_PRODUCTION = new Production<ParseType>(LBRACE, RBRACE);
  private static final Production<ParseType> PRODUCTION = new Production<ParseType>(LBRACE, STATEMENTS, RBRACE);

  @SuppressWarnings("unchecked")
  public BlockRule()
  {
    super(BLOCK, EMPTY_PRODUCTION, PRODUCTION);
  }

  /**
   * @see compiler.parser.Rule#match(compiler.parser.Production, java.lang.Object[])
   */
  @Override
  public Object match(Production<ParseType> production, Object[] args) throws ParseException
  {
    if (EMPTY_PRODUCTION.equals(production))
    {
      return new BlockAST(new StatementAST[0], ParseInfo.combine((ParseInfo) args[0], (ParseInfo) args[1]));
    }
    if (PRODUCTION.equals(production))
    {
      @SuppressWarnings("unchecked")
      ParseList<StatementAST> statements = (ParseList<StatementAST>) args[1];
      return new BlockAST(statements.toArray(new StatementAST[0]), ParseInfo.combine((ParseInfo) args[0], statements.getParseInfo(), (ParseInfo) args[2]));
    }
    throw badTypeList();
  }

}
