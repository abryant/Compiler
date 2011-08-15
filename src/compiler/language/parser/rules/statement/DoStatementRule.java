package compiler.language.parser.rules.statement;

import static compiler.language.parser.ParseType.BLOCK;
import static compiler.language.parser.ParseType.DO_KEYWORD;
import static compiler.language.parser.ParseType.DO_STATEMENT;
import static compiler.language.parser.ParseType.EXPRESSION;
import static compiler.language.parser.ParseType.SEMICOLON;
import static compiler.language.parser.ParseType.WHILE_KEYWORD;
import parser.ParseException;
import parser.Production;
import parser.Rule;

import compiler.language.LexicalPhrase;
import compiler.language.ast.expression.ExpressionAST;
import compiler.language.ast.statement.BlockAST;
import compiler.language.ast.statement.DoStatementAST;
import compiler.language.parser.ParseType;


/*
 * Created on 28 Aug 2010
 */

/**
 * @author Anthony Bryant
 */
public final class DoStatementRule extends Rule<ParseType>
{
  private static final long serialVersionUID = 1L;

  private static final Production<ParseType> PRODUCTION = new Production<ParseType>(DO_KEYWORD, BLOCK, WHILE_KEYWORD, EXPRESSION, SEMICOLON);

  @SuppressWarnings("unchecked")
  public DoStatementRule()
  {
    super(DO_STATEMENT, PRODUCTION);
  }

  /**
   * {@inheritDoc}
   * @see parser.Rule#match(parser.Production, java.lang.Object[])
   */
  @Override
  public Object match(Production<ParseType> production, Object[] args) throws ParseException
  {
    if (PRODUCTION.equals(production))
    {
      BlockAST block = (BlockAST) args[1];
      ExpressionAST condition = (ExpressionAST) args[3];
      return new DoStatementAST(block, condition,
                             LexicalPhrase.combine((LexicalPhrase) args[0], block.getLexicalPhrase(), (LexicalPhrase) args[2], condition.getLexicalPhrase(), (LexicalPhrase) args[4]));
    }
    throw badTypeList();
  }

}
