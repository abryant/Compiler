package compiler.language.parser.rules.expression;

import static compiler.language.parser.ParseType.DOT;
import static compiler.language.parser.ParseType.QNAME;
import static compiler.language.parser.ParseType.SUPER_ACCESS_EXPRESSION;
import static compiler.language.parser.ParseType.SUPER_KEYWORD;
import parser.ParseException;
import parser.Production;
import parser.Rule;

import compiler.language.LexicalPhrase;
import compiler.language.QName;
import compiler.language.ast.expression.SuperAccessExpressionAST;
import compiler.language.parser.ParseType;

/*
 * Created on 15 Aug 2010
 */

/**
 * @author Anthony Bryant
 */
public final class SuperAccessExpressionRule extends Rule<ParseType>
{
  private static final long serialVersionUID = 1L;

  private static final Production<ParseType> SUPER_PRODUCTION = new Production<ParseType>(SUPER_KEYWORD);
  private static final Production<ParseType> QNAME_SUPER_PRODUCTION = new Production<ParseType>(QNAME, DOT, SUPER_KEYWORD);

  @SuppressWarnings("unchecked")
  public SuperAccessExpressionRule()
  {
    super(SUPER_ACCESS_EXPRESSION, SUPER_PRODUCTION, QNAME_SUPER_PRODUCTION);
  }

  /**
   * {@inheritDoc}
   * @see parser.Rule#match(parser.Production, java.lang.Object[])
   */
  @Override
  public Object match(Production<ParseType> production, Object[] args) throws ParseException
  {
    if (SUPER_PRODUCTION.equals(production))
    {
      return new SuperAccessExpressionAST(null, (LexicalPhrase) args[0]);
    }
    if (QNAME_SUPER_PRODUCTION.equals(production))
    {
      QName qname = (QName) args[0];
      return new SuperAccessExpressionAST(qname, LexicalPhrase.combine(qname.getLexicalPhrase(), (LexicalPhrase) args[1], (LexicalPhrase) args[2]));
    }
    throw badTypeList();
  }

}
