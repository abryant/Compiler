package compiler.language.parser.rules.expression;

import static compiler.language.parser.ParseType.FIELD_ACCESS_EXPRESSION;
import static compiler.language.parser.ParseType.FIELD_ACCESS_EXPRESSION_NOT_QNAME;
import static compiler.language.parser.ParseType.QNAME;
import parser.ParseException;
import parser.Production;
import parser.Rule;

import compiler.language.ast.expression.FieldAccessExpressionAST;
import compiler.language.ast.misc.QNameAST;
import compiler.language.parser.ParseType;

/*
 * Created on 11 Aug 2010
 */

/**
 * @author Anthony Bryant
 */
public final class FieldAccessExpressionRule extends Rule<ParseType>
{
  private static final long serialVersionUID = 1L;

  private static final Production<ParseType> QNAME_PRODUCTION = new Production<ParseType>(QNAME);
  private static final Production<ParseType> PRODUCTION = new Production<ParseType>(FIELD_ACCESS_EXPRESSION_NOT_QNAME);

  @SuppressWarnings("unchecked")
  public FieldAccessExpressionRule()
  {
    super(FIELD_ACCESS_EXPRESSION, QNAME_PRODUCTION, PRODUCTION);
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
    if (PRODUCTION.equals(production))
    {
      // return the existing FieldAccessExpressionAST
      return args[0];
    }
    throw badTypeList();
  }

}
