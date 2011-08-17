package compiler.language.parser.rules.expression;

import static compiler.language.parser.ParseType.DOT;
import static compiler.language.parser.ParseType.QNAME;
import static compiler.language.parser.ParseType.THIS_ACCESS_EXPRESSION;
import static compiler.language.parser.ParseType.THIS_KEYWORD;
import parser.ParseException;
import parser.Production;
import parser.Rule;

import compiler.language.LexicalPhrase;
import compiler.language.QName;
import compiler.language.ast.expression.ThisAccessExpressionAST;
import compiler.language.parser.ParseType;

/*
 * Created on 11 Aug 2010
 */

/**
 * @author Anthony Bryant
 */
public final class ThisAccessExpressionRule extends Rule<ParseType>
{
  private static final long serialVersionUID = 1L;

  private static final Production<ParseType> THIS_PRODUCTION = new Production<ParseType>(THIS_KEYWORD);
  private static final Production<ParseType> QNAME_THIS_PRODUCTION = new Production<ParseType>(QNAME, DOT, THIS_KEYWORD);

  @SuppressWarnings("unchecked")
  public ThisAccessExpressionRule()
  {
    super(THIS_ACCESS_EXPRESSION, THIS_PRODUCTION, QNAME_THIS_PRODUCTION);
  }

  /**
   * {@inheritDoc}
   * @see parser.Rule#match(parser.Production, java.lang.Object[])
   */
  @Override
  public Object match(Production<ParseType> production, Object[] args) throws ParseException
  {
    if (THIS_PRODUCTION.equals(production))
    {
      return new ThisAccessExpressionAST(null, (LexicalPhrase) args[0]);
    }
    if (QNAME_THIS_PRODUCTION.equals(production))
    {
      QName qname = (QName) args[0];
      return new ThisAccessExpressionAST(qname, LexicalPhrase.combine(qname.getLexicalPhrase(), (LexicalPhrase) args[1], (LexicalPhrase) args[2]));
    }
    throw badTypeList();
  }

}
