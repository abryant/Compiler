package compiler.language.parser.rules.expression;

import static compiler.language.parser.ParseType.COMMA;
import static compiler.language.parser.ParseType.INLINE_IF_EXPRESSION;
import static compiler.language.parser.ParseType.LANGLE;
import static compiler.language.parser.ParseType.NESTED_QNAME_LIST;
import static compiler.language.parser.ParseType.QNAME;
import static compiler.language.parser.ParseType.QNAME_LIST;
import static compiler.language.parser.ParseType.RELATIONAL_EXPRESSION_LESS_THAN_QNAME;
import static compiler.language.parser.ParseType.TUPLE_EXPRESSION;
import parser.ParseException;
import parser.Production;
import parser.Rule;

import compiler.language.LexicalPhrase;
import compiler.language.ast.expression.ExpressionAST;
import compiler.language.ast.expression.FieldAccessExpressionAST;
import compiler.language.ast.expression.RelationalExpressionAST;
import compiler.language.ast.expression.RelationalExpressionTypeAST;
import compiler.language.ast.expression.TupleExpressionAST;
import compiler.language.ast.misc.QNameAST;
import compiler.language.parser.ParseList;
import compiler.language.parser.ParseType;
import compiler.language.parser.QNameElementAST;

/*
 * Created on 4 Aug 2010
 */

/**
 * @author Anthony Bryant
 */
public final class TupleExpressionRule extends Rule<ParseType>
{
  private static final long serialVersionUID = 1L;

  private static final Production<ParseType> QNAME_PRODUCTION                               = new Production<ParseType>(QNAME,                                        COMMA, TUPLE_EXPRESSION);
  private static final Production<ParseType> NESTED_QNAME_LIST_PRODUCTION                   = new Production<ParseType>(NESTED_QNAME_LIST,                            COMMA, TUPLE_EXPRESSION);
  private static final Production<ParseType> QNAME_LESS_THAN_QNAME_PRODUCTION               = new Production<ParseType>(QNAME,             LANGLE, QNAME,             COMMA, TUPLE_EXPRESSION);
  private static final Production<ParseType> QNAME_LESS_THAN_NESTED_QNAME_PRODUCTION        = new Production<ParseType>(QNAME,             LANGLE, NESTED_QNAME_LIST, COMMA, TUPLE_EXPRESSION);
  private static final Production<ParseType> NESTED_QNAME_LESS_THAN_QNAME_PRODUCTION        = new Production<ParseType>(NESTED_QNAME_LIST, LANGLE, QNAME,             COMMA, TUPLE_EXPRESSION);
  private static final Production<ParseType> NESTED_QNAME_LESS_THAN_NESTED_QNAME_PRODUCTION = new Production<ParseType>(NESTED_QNAME_LIST, LANGLE, NESTED_QNAME_LIST, COMMA, TUPLE_EXPRESSION);
  private static final Production<ParseType> INLINE_IF_TUPLE_PRODUCTION                     = new Production<ParseType>(INLINE_IF_EXPRESSION,                         COMMA, TUPLE_EXPRESSION);
  private static final Production<ParseType> INLINE_IF_QNAME_LIST_PRODUCTION                = new Production<ParseType>(INLINE_IF_EXPRESSION,                         COMMA, QNAME_LIST);
  private static final Production<ParseType> INLINE_IF_PRODUCTION                           = new Production<ParseType>(INLINE_IF_EXPRESSION);
  private static final Production<ParseType> LESS_THAN_PRODUCTION                           = new Production<ParseType>(RELATIONAL_EXPRESSION_LESS_THAN_QNAME);

  @SuppressWarnings("unchecked")
  public TupleExpressionRule()
  {
    super(TUPLE_EXPRESSION, QNAME_PRODUCTION,
                            NESTED_QNAME_LIST_PRODUCTION,
                            QNAME_LESS_THAN_QNAME_PRODUCTION,
                            QNAME_LESS_THAN_NESTED_QNAME_PRODUCTION,
                            NESTED_QNAME_LESS_THAN_QNAME_PRODUCTION,
                            NESTED_QNAME_LESS_THAN_NESTED_QNAME_PRODUCTION,
                            INLINE_IF_TUPLE_PRODUCTION,
                            INLINE_IF_QNAME_LIST_PRODUCTION,
                            INLINE_IF_PRODUCTION,
                            LESS_THAN_PRODUCTION);
  }

  /**
   * {@inheritDoc}
   * @see parser.Rule#match(parser.Production, java.lang.Object[])
   */
  @Override
  public Object match(Production<ParseType> production, Object[] args) throws ParseException
  {
    if (INLINE_IF_PRODUCTION.equals(production) || LESS_THAN_PRODUCTION.equals(production))
    {
      // return the existing expression
      return args[0];
    }
    if (INLINE_IF_QNAME_LIST_PRODUCTION.equals(production))
    {
      ExpressionAST firstExpression = (ExpressionAST) args[0];
      @SuppressWarnings("unchecked")
      ParseList<QNameElementAST> qnameList = (ParseList<QNameElementAST>) args[2];

      QNameElementAST[] elements = qnameList.toArray(new QNameElementAST[0]);
      ExpressionAST[] expressions = new ExpressionAST[elements.length + 1];
      expressions[0] = firstExpression;
      for (int i = 0; i < elements.length; i++)
      {
        expressions[i + 1] = elements[i].toExpression();
      }
      return new TupleExpressionAST(expressions, LexicalPhrase.combine(firstExpression.getLexicalPhrase(), (LexicalPhrase) args[1], qnameList.getLexicalPhrase()));
    }

    // the rest of the productions are in the form: X COMMA TUPLE_EXPRESSION,
    // so find the expressions and LexicalPhrase based on the production, and share
    // code for combining it with the rest of the production
    ExpressionAST firstExpression;
    ExpressionAST secondExpression;
    LexicalPhrase finalLexicalPhrase;
    if (QNAME_PRODUCTION.equals(production))
    {
      QNameAST qname = (QNameAST) args[0];
      firstExpression = new FieldAccessExpressionAST(qname, qname.getLexicalPhrase());
      secondExpression = (ExpressionAST) args[2];
      finalLexicalPhrase = LexicalPhrase.combine(firstExpression.getLexicalPhrase(), (LexicalPhrase) args[1], secondExpression.getLexicalPhrase());
    }
    else if (NESTED_QNAME_LIST_PRODUCTION.equals(production))
    {
      QNameElementAST element = (QNameElementAST) args[0];
      firstExpression = element.toExpression();
      secondExpression = (ExpressionAST) args[2];
      finalLexicalPhrase = LexicalPhrase.combine(firstExpression.getLexicalPhrase(), (LexicalPhrase) args[1], secondExpression.getLexicalPhrase());
    }
    else if (INLINE_IF_TUPLE_PRODUCTION.equals(production))
    {
      firstExpression = (ExpressionAST) args[0];
      secondExpression = (ExpressionAST) args[2];
      finalLexicalPhrase = LexicalPhrase.combine(firstExpression.getLexicalPhrase(), (LexicalPhrase) args[1], secondExpression.getLexicalPhrase());
    }
    else if (QNAME_LESS_THAN_QNAME_PRODUCTION.equals(production))
    {
      QNameAST firstQName = (QNameAST) args[0];
      QNameAST secondQName = (QNameAST) args[2];
      ExpressionAST leftExpression = new FieldAccessExpressionAST(firstQName, firstQName.getLexicalPhrase());
      ExpressionAST rightExpression = new FieldAccessExpressionAST(secondQName, secondQName.getLexicalPhrase());
      firstExpression = new RelationalExpressionAST(leftExpression, RelationalExpressionTypeAST.LESS_THAN, rightExpression,
                              LexicalPhrase.combine(leftExpression.getLexicalPhrase(), (LexicalPhrase) args[1], rightExpression.getLexicalPhrase()));
      secondExpression = (ExpressionAST) args[4];
      finalLexicalPhrase = LexicalPhrase.combine(firstExpression.getLexicalPhrase(), (LexicalPhrase) args[3], secondExpression.getLexicalPhrase());
    }
    else if (QNAME_LESS_THAN_NESTED_QNAME_PRODUCTION.equals(production))
    {
      QNameAST qname = (QNameAST) args[0];
      QNameElementAST element = (QNameElementAST) args[2];
      ExpressionAST leftExpression = new FieldAccessExpressionAST(qname, qname.getLexicalPhrase());
      ExpressionAST rightExpression = element.toExpression();
      firstExpression = new RelationalExpressionAST(leftExpression, RelationalExpressionTypeAST.LESS_THAN, rightExpression,
                              LexicalPhrase.combine(leftExpression.getLexicalPhrase(), (LexicalPhrase) args[1], rightExpression.getLexicalPhrase()));
      secondExpression = (ExpressionAST) args[4];
      finalLexicalPhrase = LexicalPhrase.combine(firstExpression.getLexicalPhrase(), (LexicalPhrase) args[3], secondExpression.getLexicalPhrase());
    }
    else if (NESTED_QNAME_LESS_THAN_QNAME_PRODUCTION.equals(production))
    {
      QNameElementAST element = (QNameElementAST) args[0];
      QNameAST qname = (QNameAST) args[2];
      ExpressionAST leftExpression = element.toExpression();
      ExpressionAST rightExpression = new FieldAccessExpressionAST(qname, qname.getLexicalPhrase());
      firstExpression = new RelationalExpressionAST(leftExpression, RelationalExpressionTypeAST.LESS_THAN, rightExpression,
                              LexicalPhrase.combine(leftExpression.getLexicalPhrase(), (LexicalPhrase) args[1], rightExpression.getLexicalPhrase()));
      secondExpression = (ExpressionAST) args[4];
      finalLexicalPhrase = LexicalPhrase.combine(firstExpression.getLexicalPhrase(), (LexicalPhrase) args[3], secondExpression.getLexicalPhrase());
    }
    else if (NESTED_QNAME_LESS_THAN_NESTED_QNAME_PRODUCTION.equals(production))
    {
      QNameElementAST firstElement = (QNameElementAST) args[0];
      QNameElementAST secondElement = (QNameElementAST) args[2];
      ExpressionAST leftExpression = firstElement.toExpression();
      ExpressionAST rightExpression = secondElement.toExpression();
      firstExpression = new RelationalExpressionAST(leftExpression, RelationalExpressionTypeAST.LESS_THAN, rightExpression,
                              LexicalPhrase.combine(leftExpression.getLexicalPhrase(), (LexicalPhrase) args[1], rightExpression.getLexicalPhrase()));
      secondExpression = (ExpressionAST) args[4];
      finalLexicalPhrase = LexicalPhrase.combine(firstExpression.getLexicalPhrase(), (LexicalPhrase) args[3], secondExpression.getLexicalPhrase());
    }
    else
    {
      throw badTypeList();
    }

    if (secondExpression instanceof TupleExpressionAST)
    {
      TupleExpressionAST tupleExpression = (TupleExpressionAST) secondExpression;
      ExpressionAST[] oldExpressions = tupleExpression.getExpressions();
      ExpressionAST[] newExpressions = new ExpressionAST[oldExpressions.length + 1];
      System.arraycopy(oldExpressions, 0, newExpressions, 1, oldExpressions.length);
      newExpressions[0] = firstExpression;
      return new TupleExpressionAST(newExpressions, finalLexicalPhrase);
    }
    ExpressionAST[] expressions = new ExpressionAST[] {firstExpression, secondExpression};
    return new TupleExpressionAST(expressions, finalLexicalPhrase);
  }

}
