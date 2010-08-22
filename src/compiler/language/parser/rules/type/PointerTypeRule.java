package compiler.language.parser.rules.type;

import static compiler.language.parser.ParseType.POINTER_TYPE;
import static compiler.language.parser.ParseType.POINTER_TYPE_NOT_QNAME;
import static compiler.language.parser.ParseType.QNAME;

import compiler.language.ast.misc.QName;
import compiler.language.ast.type.PointerType;
import compiler.parser.ParseException;
import compiler.parser.Rule;

/*
 * Created on 4 Jul 2010
 */

/**
 * @author Anthony Bryant
 */
public class PointerTypeRule extends Rule
{

  private static final Object[] QNAME_PRODUCTION = new Object[] {QNAME};
  private static final Object[] PRODUCTION = new Object[] {POINTER_TYPE_NOT_QNAME};

  public PointerTypeRule()
  {
    super(POINTER_TYPE, QNAME_PRODUCTION, PRODUCTION);
  }

  /**
   * @see compiler.parser.Rule#match(java.lang.Object[], java.lang.Object[])
   */
  @Override
  public Object match(Object[] types, Object[] args) throws ParseException
  {
    if (types == QNAME_PRODUCTION)
    {
      QName qname = (QName) args[0];
      return new PointerType(qname, qname.getParseInfo());
    }
    if (types == PRODUCTION)
    {
      // POINTER_TYPE_NOT_QNAME has already built a PointerType, so return it
      return args[0];
    }
    throw badTypeList();
  }

}
