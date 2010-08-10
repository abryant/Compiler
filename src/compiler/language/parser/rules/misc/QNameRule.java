package compiler.language.parser.rules.misc;

import static compiler.language.parser.ParseType.DOT;
import static compiler.language.parser.ParseType.NAME;
import static compiler.language.parser.ParseType.QNAME;

import compiler.language.ast.ParseInfo;
import compiler.language.ast.misc.QName;
import compiler.language.ast.terminal.Name;
import compiler.parser.ParseException;
import compiler.parser.Rule;

/*
 * Created on 30 Jun 2010
 */

/**
 * @author Anthony Bryant
 */
public class QNameRule extends Rule
{

  private static final Object[] START_PRODUCTION = new Object[] {NAME};
  private static final Object[] CONTINUATION_PRODUCTION = new Object[] {QNAME, DOT, NAME};

  public QNameRule()
  {
    super(QNAME, START_PRODUCTION, CONTINUATION_PRODUCTION);
  }

  /**
   * @see compiler.parser.Rule#match(java.lang.Object[], java.lang.Object[])
   */
  @Override
  public Object match(Object[] types, Object[] args) throws ParseException
  {
    if (types == START_PRODUCTION)
    {
      Name name = (Name) args[0];
      return new QName(name, name.getParseInfo());
    }
    if (types == CONTINUATION_PRODUCTION)
    {
      QName qname = (QName) args[0];
      Name name = (Name) args[2];
      return new QName(qname, name, ParseInfo.combine(qname.getParseInfo(), (ParseInfo) args[1], name.getParseInfo()));
    }
    throw badTypeList();
  }

}
