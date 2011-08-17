package compiler.language.parser.rules.expression;

import static compiler.language.parser.ParseType.ADDITIVE_EXPRESSION;
import static compiler.language.parser.ParseType.DOUBLE_LANGLE;
import static compiler.language.parser.ParseType.DOUBLE_RANGLE;
import static compiler.language.parser.ParseType.NESTED_QNAME_LIST;
import static compiler.language.parser.ParseType.QNAME;
import static compiler.language.parser.ParseType.QNAME_EXPRESSION;
import static compiler.language.parser.ParseType.SHIFT_EXPRESSION;
import static compiler.language.parser.ParseType.TRIPLE_RANGLE;
import parser.ParseException;
import parser.Production;
import parser.Rule;

import compiler.language.LexicalPhrase;
import compiler.language.QName;
import compiler.language.ast.expression.ExpressionAST;
import compiler.language.ast.expression.FieldAccessExpressionAST;
import compiler.language.ast.expression.ShiftExpressionAST;
import compiler.language.ast.expression.ShiftExpressionTypeAST;
import compiler.language.parser.ParseType;
import compiler.language.parser.QNameElementAST;

/*
 * Created on 8 Aug 2010
 */

/**
 * @author Anthony Bryant
 */
public final class ShiftExpressionRule extends Rule<ParseType>
{
  private static final long serialVersionUID = 1L;

  private static final Production<ParseType> START_PRODUCTION                                     = new Production<ParseType>(ADDITIVE_EXPRESSION);
  private static final Production<ParseType> LEFT_SHIFT_PRODUCTION                                = new Production<ParseType>(SHIFT_EXPRESSION,  DOUBLE_LANGLE, ADDITIVE_EXPRESSION);
  private static final Production<ParseType> LEFT_SHIFT_QNAME_PRODUCTION                          = new Production<ParseType>(SHIFT_EXPRESSION,  DOUBLE_LANGLE, QNAME_EXPRESSION);
  private static final Production<ParseType> QNAME_LEFT_SHIFT_PRODUCTION                          = new Production<ParseType>(QNAME_EXPRESSION,  DOUBLE_LANGLE, ADDITIVE_EXPRESSION);
  private static final Production<ParseType> QNAME_LEFT_SHIFT_QNAME_PRODUCTION                    = new Production<ParseType>(QNAME_EXPRESSION,  DOUBLE_LANGLE, QNAME_EXPRESSION);
  private static final Production<ParseType> ARITHMETIC_RIGHT_SHIFT_PRODUCTION                    = new Production<ParseType>(SHIFT_EXPRESSION,  DOUBLE_RANGLE, ADDITIVE_EXPRESSION);
  private static final Production<ParseType> ARITHMETIC_RIGHT_SHIFT_QNAME_PRODUCTION              = new Production<ParseType>(SHIFT_EXPRESSION,  DOUBLE_RANGLE, QNAME_EXPRESSION);
  private static final Production<ParseType> QNAME_ARITHMETIC_RIGHT_SHIFT_PRODUCTION              = new Production<ParseType>(QNAME,             DOUBLE_RANGLE, ADDITIVE_EXPRESSION);
  private static final Production<ParseType> QNAME_ARITHMETIC_RIGHT_SHIFT_QNAME_PRODUCTION        = new Production<ParseType>(QNAME,             DOUBLE_RANGLE, QNAME_EXPRESSION);
  private static final Production<ParseType> NESTED_QNAME_ARITHMETIC_RIGHT_SHIFT_PRODUCTION       = new Production<ParseType>(NESTED_QNAME_LIST, DOUBLE_RANGLE, ADDITIVE_EXPRESSION);
  private static final Production<ParseType> NESTED_QNAME_ARITHMETIC_RIGHT_SHIFT_QNAME_PRODUCTION = new Production<ParseType>(NESTED_QNAME_LIST, DOUBLE_RANGLE, QNAME_EXPRESSION);
  private static final Production<ParseType> LOGICAL_RIGHT_SHIFT_PRODUCTION                       = new Production<ParseType>(SHIFT_EXPRESSION,  TRIPLE_RANGLE, ADDITIVE_EXPRESSION);
  private static final Production<ParseType> LOGICAL_RIGHT_SHIFT_QNAME_PRODUCTION                 = new Production<ParseType>(SHIFT_EXPRESSION,  TRIPLE_RANGLE, QNAME_EXPRESSION);
  private static final Production<ParseType> QNAME_LOGICAL_RIGHT_SHIFT_PRODUCTION                 = new Production<ParseType>(QNAME,             TRIPLE_RANGLE, ADDITIVE_EXPRESSION);
  private static final Production<ParseType> QNAME_LOGICAL_RIGHT_SHIFT_QNAME_PRODUCTION           = new Production<ParseType>(QNAME,             TRIPLE_RANGLE, QNAME_EXPRESSION);
  private static final Production<ParseType> NESTED_QNAME_LOGICAL_RIGHT_SHIFT_PRODUCTION          = new Production<ParseType>(NESTED_QNAME_LIST, TRIPLE_RANGLE, ADDITIVE_EXPRESSION);
  private static final Production<ParseType> NESTED_QNAME_LOGICAL_RIGHT_SHIFT_QNAME_PRODUCTION    = new Production<ParseType>(NESTED_QNAME_LIST, TRIPLE_RANGLE, QNAME_EXPRESSION);

  @SuppressWarnings("unchecked")
  public ShiftExpressionRule()
  {
    super(SHIFT_EXPRESSION, START_PRODUCTION,
                            LEFT_SHIFT_PRODUCTION,                          LEFT_SHIFT_QNAME_PRODUCTION,
                            QNAME_LEFT_SHIFT_PRODUCTION,                    QNAME_LEFT_SHIFT_QNAME_PRODUCTION,
                            ARITHMETIC_RIGHT_SHIFT_PRODUCTION,              ARITHMETIC_RIGHT_SHIFT_QNAME_PRODUCTION,
                            QNAME_ARITHMETIC_RIGHT_SHIFT_PRODUCTION,        QNAME_ARITHMETIC_RIGHT_SHIFT_QNAME_PRODUCTION,
                            NESTED_QNAME_ARITHMETIC_RIGHT_SHIFT_PRODUCTION, NESTED_QNAME_ARITHMETIC_RIGHT_SHIFT_QNAME_PRODUCTION,
                            LOGICAL_RIGHT_SHIFT_PRODUCTION,                 LOGICAL_RIGHT_SHIFT_QNAME_PRODUCTION,
                            QNAME_LOGICAL_RIGHT_SHIFT_PRODUCTION,           QNAME_LOGICAL_RIGHT_SHIFT_QNAME_PRODUCTION,
                            NESTED_QNAME_LOGICAL_RIGHT_SHIFT_PRODUCTION,    NESTED_QNAME_LOGICAL_RIGHT_SHIFT_QNAME_PRODUCTION);
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
      // return the existing ExpressionAST
      return args[0];
    }

    ExpressionAST firstExpression;
    ShiftExpressionTypeAST separator;
    if (LEFT_SHIFT_PRODUCTION.equals(production)       || LEFT_SHIFT_QNAME_PRODUCTION.equals(production) ||
        QNAME_LEFT_SHIFT_PRODUCTION.equals(production) || QNAME_LEFT_SHIFT_QNAME_PRODUCTION.equals(production))
    {
      firstExpression = (ExpressionAST) args[0];
      separator = ShiftExpressionTypeAST.LEFT_SHIFT;
    }
    else if (ARITHMETIC_RIGHT_SHIFT_PRODUCTION.equals(production)              || ARITHMETIC_RIGHT_SHIFT_QNAME_PRODUCTION.equals(production))
    {
      firstExpression = (ExpressionAST) args[0];
      separator = ShiftExpressionTypeAST.ARITHMETIC_RIGHT_SHIFT;
    }
    else if (QNAME_ARITHMETIC_RIGHT_SHIFT_PRODUCTION.equals(production)        || QNAME_ARITHMETIC_RIGHT_SHIFT_QNAME_PRODUCTION.equals(production))
    {
      QName qname = (QName) args[0];
      firstExpression = new FieldAccessExpressionAST(qname, qname.getLexicalPhrase());
      separator = ShiftExpressionTypeAST.ARITHMETIC_RIGHT_SHIFT;
    }
    else if (NESTED_QNAME_ARITHMETIC_RIGHT_SHIFT_PRODUCTION.equals(production) || NESTED_QNAME_ARITHMETIC_RIGHT_SHIFT_QNAME_PRODUCTION.equals(production))
    {
      QNameElementAST element = (QNameElementAST) args[0];
      firstExpression = element.toExpression();
      separator = ShiftExpressionTypeAST.ARITHMETIC_RIGHT_SHIFT;
    }
    else if (LOGICAL_RIGHT_SHIFT_PRODUCTION.equals(production)                 || LOGICAL_RIGHT_SHIFT_QNAME_PRODUCTION.equals(production))
    {
      firstExpression = (ExpressionAST) args[0];
      separator = ShiftExpressionTypeAST.LOGICAL_RIGHT_SHIFT;
    }
    else if (QNAME_LOGICAL_RIGHT_SHIFT_PRODUCTION.equals(production)           || QNAME_LOGICAL_RIGHT_SHIFT_QNAME_PRODUCTION.equals(production))
    {
      QName qname = (QName) args[0];
      firstExpression = new FieldAccessExpressionAST(qname, qname.getLexicalPhrase());
      separator = ShiftExpressionTypeAST.LOGICAL_RIGHT_SHIFT;
    }
    else if (NESTED_QNAME_LOGICAL_RIGHT_SHIFT_PRODUCTION.equals(production)    || NESTED_QNAME_LOGICAL_RIGHT_SHIFT_QNAME_PRODUCTION.equals(production))
    {
      QNameElementAST element = (QNameElementAST) args[0];
      firstExpression = element.toExpression();
      separator = ShiftExpressionTypeAST.LOGICAL_RIGHT_SHIFT;
    }
    else
    {
      throw badTypeList();
    }


    ExpressionAST secondExpression = (ExpressionAST) args[2];
    if (firstExpression instanceof ShiftExpressionAST)
    {
      // continue the existing ShiftExpressionAST if we've already started one
      ShiftExpressionAST startExpression = (ShiftExpressionAST) args[0];
      return new ShiftExpressionAST(startExpression, separator, secondExpression,
                                 LexicalPhrase.combine(startExpression.getLexicalPhrase(), (LexicalPhrase) args[1], secondExpression.getLexicalPhrase()));
    }
    return new ShiftExpressionAST(firstExpression, separator, secondExpression,
                               LexicalPhrase.combine(firstExpression.getLexicalPhrase(), (LexicalPhrase) args[1], secondExpression.getLexicalPhrase()));
  }

}
