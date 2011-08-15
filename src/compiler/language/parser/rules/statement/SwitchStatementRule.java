package compiler.language.parser.rules.statement;

import static compiler.language.parser.ParseType.EXPRESSION;
import static compiler.language.parser.ParseType.LBRACE;
import static compiler.language.parser.ParseType.RBRACE;
import static compiler.language.parser.ParseType.SWITCH_CASES;
import static compiler.language.parser.ParseType.SWITCH_KEYWORD;
import static compiler.language.parser.ParseType.SWITCH_STATEMENT;
import parser.ParseException;
import parser.Production;
import parser.Rule;

import compiler.language.LexicalPhrase;
import compiler.language.ast.expression.ExpressionAST;
import compiler.language.ast.statement.SwitchCaseAST;
import compiler.language.ast.statement.SwitchStatementAST;
import compiler.language.parser.ParseList;
import compiler.language.parser.ParseType;

/*
 * Created on 28 Aug 2010
 */

/**
 * @author Anthony Bryant
 */
public final class SwitchStatementRule extends Rule<ParseType>
{
  private static final long serialVersionUID = 1L;

  private static final Production<ParseType> PRODUCTION               = new Production<ParseType>(SWITCH_KEYWORD, EXPRESSION, LBRACE, SWITCH_CASES, RBRACE);
  private static final Production<ParseType> NO_EXPRESSION_PRODUCTION = new Production<ParseType>(SWITCH_KEYWORD,             LBRACE, SWITCH_CASES, RBRACE);

  @SuppressWarnings("unchecked")
  public SwitchStatementRule()
  {
    super(SWITCH_STATEMENT, PRODUCTION, NO_EXPRESSION_PRODUCTION);
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
      @SuppressWarnings("unchecked")
      ParseList<SwitchCaseAST> cases = (ParseList<SwitchCaseAST>) args[3];
      return new SwitchStatementAST(expression, cases.toArray(new SwitchCaseAST[0]),
                                 LexicalPhrase.combine((LexicalPhrase) args[0], expression.getLexicalPhrase(),
                                                   (LexicalPhrase) args[2], cases.getLexicalPhrase(), (LexicalPhrase) args[4]));
    }
    if (NO_EXPRESSION_PRODUCTION.equals(production))
    {
      @SuppressWarnings("unchecked")
      ParseList<SwitchCaseAST> cases = (ParseList<SwitchCaseAST>) args[2];
      return new SwitchStatementAST(null, cases.toArray(new SwitchCaseAST[0]),
                                 LexicalPhrase.combine((LexicalPhrase) args[0], (LexicalPhrase) args[1], cases.getLexicalPhrase(), (LexicalPhrase) args[3]));
    }
    throw badTypeList();
  }

}
