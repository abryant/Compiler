package compiler.language.parser.rules.expression;

import parser.ParseException;
import parser.Production;
import parser.Rule;
import compiler.language.ast.expression.FieldAccessExpressionAST;
import compiler.language.ast.misc.QNameAST;
import compiler.language.parser.ParseType;
import compiler.language.parser.QNameElementAST;

/*
 * Created on 17 Aug 2010
 */

/**
 * @author Anthony Bryant
 */
public final class QNameExpressionRule extends Rule<ParseType>
{
  private static final long serialVersionUID = 1L;

  private static final Production<ParseType> QNAME_PRODUCTION = new Production<ParseType>(ParseType.QNAME);
  private static final Production<ParseType> NESTED_QNAME_PRODUCTION = new Production<ParseType>(ParseType.NESTED_QNAME_LIST);

  @SuppressWarnings("unchecked")
  public QNameExpressionRule()
  {
    super(ParseType.QNAME_EXPRESSION, QNAME_PRODUCTION, NESTED_QNAME_PRODUCTION);
  }

  /**
   * {@inheritDoc}
   * @see parser.Rule#match(parser.Production, java.lang.Object[])
   */
  @Override
  public Object match(Production<ParseType> production, Object[] args) throws ParseException
  {
    if (QNAME_PRODUCTION.equals(production))
    {
      QNameAST qname = (QNameAST) args[0];
      return new FieldAccessExpressionAST(qname, qname.getParseInfo());
    }
    if (NESTED_QNAME_PRODUCTION.equals(production))
    {
      QNameElementAST element = (QNameElementAST) args[0];
      return element.toExpression();
    }
    throw badTypeList();
  }

}
