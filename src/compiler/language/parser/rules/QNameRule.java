package compiler.language.parser.rules;

import static compiler.language.parser.ParseType.DOT;
import static compiler.language.parser.ParseType.NAME;
import static compiler.language.parser.ParseType.QNAME;

import compiler.language.ast.misc.QName;
import compiler.language.ast.terminal.Name;
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
  public Object match(Object[] types, Object[] args)
  {
    if (types == START_PRODUCTION)
    {
      return new QName((Name) args[0]);
    }
    if (types == CONTINUATION_PRODUCTION)
    {
      return new QName((QName) args[0], (Name) args[2]);
    }
    throw badTypeList();
  }

}
