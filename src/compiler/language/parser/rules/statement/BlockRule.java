package compiler.language.parser.rules.statement;

import static compiler.language.parser.ParseType.BLOCK;
import static compiler.language.parser.ParseType.LBRACE;
import static compiler.language.parser.ParseType.RBRACE;
import static compiler.language.parser.ParseType.STATEMENTS;

import compiler.language.ast.ParseInfo;
import compiler.language.ast.ParseList;
import compiler.language.ast.statement.Block;
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
public class BlockRule extends Rule
{
  private static final long serialVersionUID = 1L;

  private static final Production EMPTY_PRODUCTION = new Production(LBRACE, RBRACE);
  private static final Production PRODUCTION = new Production(LBRACE, STATEMENTS, RBRACE);

  public BlockRule()
  {
    super(BLOCK, EMPTY_PRODUCTION, PRODUCTION);
  }

  /**
   * @see compiler.parser.Rule#match(compiler.parser.Production, java.lang.Object[])
   */
  @Override
  public Object match(Production production, Object[] args) throws ParseException
  {
    if (EMPTY_PRODUCTION.equals(production))
    {
      return new Block(new Statement[0], ParseInfo.combine((ParseInfo) args[0], (ParseInfo) args[1]));
    }
    if (PRODUCTION.equals(production))
    {
      @SuppressWarnings("unchecked")
      ParseList<Statement> statements = (ParseList<Statement>) args[1];
      return new Block(statements.toArray(new Statement[0]), ParseInfo.combine((ParseInfo) args[0], statements.getParseInfo(), (ParseInfo) args[2]));
    }
    throw badTypeList();
  }

}
