package compiler.language.parser.rules;

import compiler.language.ast.Name;
import compiler.language.ast.QName;
import compiler.language.parser.Type;
import compiler.parser.Rule;

/*
 * Created on 30 Jun 2010
 */

/**
 * @author Anthony Bryant
 */
public class QNameRule extends Rule
{

  private static final Object[] START_PRODUCTION = new Object[] {Type.NAME};
  private static final Object[] CONTINUATION_PRODUCTION = new Object[] {Type.QNAME, Type.DOT, Type.NAME};

  public QNameRule()
  {
    super(Type.QNAME, START_PRODUCTION, CONTINUATION_PRODUCTION);
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
    throw new IllegalArgumentException("Invalid type list passed to match()");
  }

}
