package compiler.language.parser.rules.misc;

import static compiler.language.parser.ParseType.DOT;
import static compiler.language.parser.ParseType.NAME;
import static compiler.language.parser.ParseType.QNAME;
import parser.ParseException;
import parser.Production;
import parser.Rule;

import compiler.language.QName;
import compiler.language.ast.terminal.NameAST;
import compiler.language.parser.ParseType;

/*
 * Created on 30 Jun 2010
 */

/**
 * @author Anthony Bryant
 */
public final class QNameRule extends Rule<ParseType>
{
  private static final long serialVersionUID = 1L;

  private static final Production<ParseType> START_PRODUCTION = new Production<ParseType>(NAME);
  private static final Production<ParseType> CONTINUATION_PRODUCTION = new Production<ParseType>(QNAME, DOT, NAME);

  @SuppressWarnings("unchecked")
  public QNameRule()
  {
    super(QNAME, START_PRODUCTION, CONTINUATION_PRODUCTION);
  }

  /**
   * @see parser.Rule#match(parser.Production, java.lang.Object[])
   */
  @Override
  public Object match(Production<ParseType> production, Object[] args) throws ParseException
  {
    if (START_PRODUCTION.equals(production))
    {
      NameAST name = (NameAST) args[0];
      return new QName(name.getName(), name.getLexicalPhrase());
    }
    if (CONTINUATION_PRODUCTION.equals(production))
    {
      QName qname = (QName) args[0];
      NameAST name = (NameAST) args[2];
      return new QName(qname, name.getName(), name.getLexicalPhrase());
    }
    throw badTypeList();
  }

}
