package compiler.language.parser.rules.statement;

import static compiler.language.parser.ParseType.CASE_KEYWORD;
import static compiler.language.parser.ParseType.COLON;
import static compiler.language.parser.ParseType.DEFAULT_KEYWORD;
import static compiler.language.parser.ParseType.EXPRESSION;
import static compiler.language.parser.ParseType.STATEMENTS;
import static compiler.language.parser.ParseType.SWITCH_CASE;
import parser.ParseException;
import parser.Production;
import parser.Rule;

import compiler.language.LexicalPhrase;
import compiler.language.ast.expression.ExpressionAST;
import compiler.language.ast.statement.StatementAST;
import compiler.language.ast.statement.SwitchCaseAST;
import compiler.language.parser.ParseList;
import compiler.language.parser.ParseType;

/*
 * Created on 28 Aug 2010
 */

/**
 * @author Anthony Bryant
 */
public final class SwitchCaseRule extends Rule<ParseType>
{
  private static final long serialVersionUID = 1L;

  private static final Production<ParseType> PRODUCTION                    = new Production<ParseType>(CASE_KEYWORD,    EXPRESSION, COLON);
  private static final Production<ParseType> STATEMENTS_PRODUCTION         = new Production<ParseType>(CASE_KEYWORD,    EXPRESSION, COLON, STATEMENTS);
  private static final Production<ParseType> DEFAULT_PRODUCTION            = new Production<ParseType>(DEFAULT_KEYWORD,             COLON);
  private static final Production<ParseType> DEFAULT_STATEMENTS_PRODUCTION = new Production<ParseType>(DEFAULT_KEYWORD,             COLON, STATEMENTS);

  @SuppressWarnings("unchecked")
  public SwitchCaseRule()
  {
    super(SWITCH_CASE, PRODUCTION, STATEMENTS_PRODUCTION, DEFAULT_PRODUCTION, DEFAULT_STATEMENTS_PRODUCTION);
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
      ExpressionAST expression = (ExpressionAST) args[1];
      return new SwitchCaseAST(expression, new StatementAST[0],
                            LexicalPhrase.combine((LexicalPhrase) args[0], expression.getLexicalPhrase(), (LexicalPhrase) args[2]));
    }
    if (STATEMENTS_PRODUCTION.equals(production))
    {
      ExpressionAST expression = (ExpressionAST) args[1];
      @SuppressWarnings("unchecked")
      ParseList<StatementAST> statements = (ParseList<StatementAST>) args[3];
      return new SwitchCaseAST(expression, statements.toArray(new StatementAST[0]),
                            LexicalPhrase.combine((LexicalPhrase) args[0], expression.getLexicalPhrase(), (LexicalPhrase) args[2], statements.getLexicalPhrase()));
    }
    if (DEFAULT_PRODUCTION.equals(production))
    {
      return new SwitchCaseAST(null, new StatementAST[0],
                            LexicalPhrase.combine((LexicalPhrase) args[0], (LexicalPhrase) args[1]));
    }
    if (DEFAULT_STATEMENTS_PRODUCTION.equals(production))
    {
      @SuppressWarnings("unchecked")
      ParseList<StatementAST> statements = (ParseList<StatementAST>) args[2];
      return new SwitchCaseAST(null, statements.toArray(new StatementAST[0]),
                            LexicalPhrase.combine((LexicalPhrase) args[0], (LexicalPhrase) args[1], statements.getLexicalPhrase()));
    }
    throw badTypeList();
  }

}
