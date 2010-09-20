package compiler.language.parser.rules.misc;

import static compiler.language.parser.ParseType.DOT;
import static compiler.language.parser.ParseType.NAME;
import static compiler.language.parser.ParseType.QNAME;

import compiler.language.ast.ParseInfo;
import compiler.language.ast.misc.QName;
import compiler.language.ast.terminal.Name;
import compiler.parser.ParseException;
import compiler.parser.Production;
import compiler.parser.Rule;

/*
 * Created on 30 Jun 2010
 */

/**
 * @author Anthony Bryant
 */
public class QNameRule extends Rule
{
  private static final long serialVersionUID = 1L;

  private static final Production START_PRODUCTION = new Production(NAME);
  private static final Production CONTINUATION_PRODUCTION = new Production(QNAME, DOT, NAME);

  public QNameRule()
  {
    super(QNAME, START_PRODUCTION, CONTINUATION_PRODUCTION);
  }

  /**
   * @see compiler.parser.Rule#match(compiler.parser.Production, java.lang.Object[])
   */
  @Override
  public Object match(Production production, Object[] args) throws ParseException
  {
    if (START_PRODUCTION.equals(production))
    {
      Name name = (Name) args[0];
      return new QName(name, name.getParseInfo());
    }
    if (CONTINUATION_PRODUCTION.equals(production))
    {
      QName qname = (QName) args[0];
      Name name = (Name) args[2];
      return new QName(qname, name, ParseInfo.combine(qname.getParseInfo(), (ParseInfo) args[1], name.getParseInfo()));
    }
    throw badTypeList();
  }

}
