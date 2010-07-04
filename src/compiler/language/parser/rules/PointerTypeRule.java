package compiler.language.parser.rules;

import static compiler.language.parser.ParseType.HASH;
import static compiler.language.parser.ParseType.POINTER_TYPE;
import static compiler.language.parser.ParseType.QNAME;

import compiler.language.ast.PointerType;
import compiler.language.ast.QName;
import compiler.parser.Rule;

/*
 * Created on 4 Jul 2010
 */

/**
 * @author Anthony Bryant
 */
public class PointerTypeRule extends Rule
{

  private static final Object[] IMMUTABLE_PRODUCTION = new Object[] {HASH, QNAME};
  private static final Object[] PRODUCTION = new Object[] {QNAME};

  public PointerTypeRule()
  {
    super(POINTER_TYPE, PRODUCTION, IMMUTABLE_PRODUCTION);
  }

  /**
   * @see compiler.parser.Rule#match(java.lang.Object[], java.lang.Object[])
   */
  @Override
  public Object match(Object[] types, Object[] args)
  {
    if (types == IMMUTABLE_PRODUCTION)
    {
      return new PointerType((QName) args[1], true);
    }
    if (types == PRODUCTION)
    {
      return new PointerType((QName) args[0], false);
    }
    throw badTypeList();
  }

}
