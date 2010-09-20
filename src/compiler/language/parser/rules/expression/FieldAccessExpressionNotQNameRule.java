package compiler.language.parser.rules.expression;

import static compiler.language.parser.ParseType.DOT;
import static compiler.language.parser.ParseType.FIELD_ACCESS_EXPRESSION_NOT_QNAME;
import static compiler.language.parser.ParseType.NAME;
import static compiler.language.parser.ParseType.PRIMARY;

import compiler.language.ast.ParseInfo;
import compiler.language.ast.expression.Expression;
import compiler.language.ast.expression.FieldAccessExpression;
import compiler.language.ast.terminal.Name;
import compiler.language.parser.ParseType;
import compiler.parser.ParseException;
import compiler.parser.Production;
import compiler.parser.Rule;

/*
 * Created on 11 Aug 2010
 */

/**
 * @author Anthony Bryant
 */
public final class FieldAccessExpressionNotQNameRule extends Rule<ParseType>
{
  private static final long serialVersionUID = 1L;

  private static final Production<ParseType> PRIMARY_PRODUCTION = new Production<ParseType>(PRIMARY, DOT, NAME);

  @SuppressWarnings("unchecked")
  public FieldAccessExpressionNotQNameRule()
  {
    super(FIELD_ACCESS_EXPRESSION_NOT_QNAME, PRIMARY_PRODUCTION);
  }

  /**
   * {@inheritDoc}
   * @see compiler.parser.Rule#match(compiler.parser.Production, java.lang.Object[])
   */
  @Override
  public Object match(Production<ParseType> production, Object[] args) throws ParseException
  {
    if (PRIMARY_PRODUCTION.equals(production))
    {
      Expression expression = (Expression) args[0];
      Name name = (Name) args[2];
      return new FieldAccessExpression(expression, name, ParseInfo.combine(expression.getParseInfo(), (ParseInfo) args[1], name.getParseInfo()));
    }
    throw badTypeList();
  }

}
