package compiler.language.parser.rules.expression;

import static compiler.language.parser.ParseType.DIMENSION_EXPRESSION;
import static compiler.language.parser.ParseType.DIMENSION_EXPRESSIONS;
import parser.ParseException;
import parser.Production;
import parser.Rule;

import compiler.language.LexicalPhrase;
import compiler.language.ast.expression.ExpressionAST;
import compiler.language.parser.ParseContainer;
import compiler.language.parser.ParseList;
import compiler.language.parser.ParseType;

/*
 * Created on 12 Aug 2010
 */

/**
 * @author Anthony Bryant
 */
public final class DimensionExpressionsRule extends Rule<ParseType>
{
  private static final long serialVersionUID = 1L;

  private static final Production<ParseType> START_PRODUCTION = new Production<ParseType>(DIMENSION_EXPRESSION);
  private static final Production<ParseType> CONTINUATION_PRODUCTION = new Production<ParseType>(DIMENSION_EXPRESSIONS, DIMENSION_EXPRESSION);

  @SuppressWarnings("unchecked")
  public DimensionExpressionsRule()
  {
    super(DIMENSION_EXPRESSIONS, START_PRODUCTION, CONTINUATION_PRODUCTION);
  }

  /**
   * {@inheritDoc}
   * @see parser.Rule#match(parser.Production, java.lang.Object[])
   */
  @Override
  public Object match(Production<ParseType> production, Object[] args) throws ParseException
  {
    if (START_PRODUCTION.equals(production))
    {
      @SuppressWarnings("unchecked")
      ParseContainer<ExpressionAST> container = (ParseContainer<ExpressionAST>) args[0];
      return new ParseList<ExpressionAST>(container.getItem(), container.getLexicalPhrase());
    }
    if (CONTINUATION_PRODUCTION.equals(production))
    {
      @SuppressWarnings("unchecked")
      ParseList<ExpressionAST> list = (ParseList<ExpressionAST>) args[0];
      @SuppressWarnings("unchecked")
      ParseContainer<ExpressionAST> container = (ParseContainer<ExpressionAST>) args[1];
      list.addLast(container.getItem(), LexicalPhrase.combine(list.getLexicalPhrase(), container.getLexicalPhrase()));
      return list;
    }
    throw badTypeList();
  }

}
