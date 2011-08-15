package compiler.language.parser.rules.expression;

import static compiler.language.parser.ParseType.METHOD_CALL_EXPRESSION;
import static compiler.language.parser.ParseType.ARGUMENTS;
import static compiler.language.parser.ParseType.PRIMARY;
import static compiler.language.parser.ParseType.QNAME_EXPRESSION;
import parser.ParseException;
import parser.Production;
import parser.Rule;

import compiler.language.LexicalPhrase;
import compiler.language.ast.expression.ExpressionAST;
import compiler.language.ast.expression.MethodCallExpressionAST;
import compiler.language.ast.misc.ArgumentAST;
import compiler.language.parser.ParseList;
import compiler.language.parser.ParseType;

/*
 * Created on 11 Aug 2010
 */

/**
 * @author Anthony Bryant
 */
public final class MethodCallExpressionRule extends Rule<ParseType>
{
  private static final long serialVersionUID = 1L;

  private static final Production<ParseType> PRODUCTION = new Production<ParseType>(PRIMARY, ARGUMENTS);
  private static final Production<ParseType> QNAME_PRODUCTION = new Production<ParseType>(QNAME_EXPRESSION, ARGUMENTS);

  @SuppressWarnings("unchecked")
  public MethodCallExpressionRule()
  {
    super(METHOD_CALL_EXPRESSION, PRODUCTION, QNAME_PRODUCTION);
  }

  /**
   * {@inheritDoc}
   * @see parser.Rule#match(parser.Production, java.lang.Object[])
   */
  @Override
  public Object match(Production<ParseType> production, Object[] args) throws ParseException
  {
    if (PRODUCTION.equals(production) || QNAME_PRODUCTION.equals(production))
    {
      // both of the productions produce the same types of results, so we can
      // use the same code to generate the MethodCallExpressionAST
      ExpressionAST expression = (ExpressionAST) args[0];
      @SuppressWarnings("unchecked")
      ParseList<ArgumentAST> arguments = (ParseList<ArgumentAST>) args[1];
      return new MethodCallExpressionAST(expression, arguments.toArray(new ArgumentAST[0]),
                                      LexicalPhrase.combine(expression.getLexicalPhrase(), arguments.getLexicalPhrase()));
    }
    throw badTypeList();
  }

}
