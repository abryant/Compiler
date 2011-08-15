package compiler.language.parser.rules.expression;

import static compiler.language.parser.ParseType.BOOLEAN_LITERAL_EXPRESSION;
import static compiler.language.parser.ParseType.FALSE_KEYWORD;
import static compiler.language.parser.ParseType.TRUE_KEYWORD;
import parser.ParseException;
import parser.Production;
import parser.Rule;

import compiler.language.LexicalPhrase;
import compiler.language.ast.expression.BooleanLiteralExpressionAST;
import compiler.language.parser.ParseType;

/*
 * Created on 27 Aug 2010
 */

/**
 * @author Anthony Bryant
 */
public final class BooleanLiteralExpressionRule extends Rule<ParseType>
{
  private static final long serialVersionUID = 1L;

  private static final Production<ParseType> TRUE_PRODUCTION = new Production<ParseType>(TRUE_KEYWORD);
  private static final Production<ParseType> FALSE_PRODUCTION = new Production<ParseType>(FALSE_KEYWORD);

  @SuppressWarnings("unchecked")
  public BooleanLiteralExpressionRule()
  {
    super(BOOLEAN_LITERAL_EXPRESSION, TRUE_PRODUCTION, FALSE_PRODUCTION);
  }

  /**
   * {@inheritDoc}
   * @see parser.Rule#match(parser.Production, java.lang.Object[])
   */
  @Override
  public Object match(Production<ParseType> production, Object[] args) throws ParseException
  {
    if (TRUE_PRODUCTION.equals(production))
    {
      return new BooleanLiteralExpressionAST(true, (LexicalPhrase) args[0]);
    }
    if (FALSE_PRODUCTION.equals(production))
    {
      return new BooleanLiteralExpressionAST(false, (LexicalPhrase) args[0]);
    }
    throw badTypeList();
  }

}
