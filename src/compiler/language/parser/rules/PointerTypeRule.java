package compiler.language.parser.rules;

import static compiler.language.parser.ParseType.HASH;
import static compiler.language.parser.ParseType.POINTER_TYPE;
import static compiler.language.parser.ParseType.QNAME;
import static compiler.language.parser.ParseType.TYPE_PARAMETERS;

import compiler.language.ast.PointerType;
import compiler.language.ast.QName;
import compiler.language.ast.TypeParameter;
import compiler.parser.Rule;

/*
 * Created on 4 Jul 2010
 */

/**
 * @author Anthony Bryant
 */
public class PointerTypeRule extends Rule
{

  private static final Object[] PRODUCTION = new Object[] {QNAME};
  private static final Object[] IMMUTABLE_PRODUCTION = new Object[] {HASH, QNAME};
  private static final Object[] TYPE_PARAMETERS_PRODUCTION = new Object[] {QNAME, TYPE_PARAMETERS};
  private static final Object[] IMMUTABLE_TYPE_PARAMETERS_PRODUCTION = new Object[] {HASH, QNAME, TYPE_PARAMETERS};

  public PointerTypeRule()
  {
    super(POINTER_TYPE, PRODUCTION, IMMUTABLE_PRODUCTION, TYPE_PARAMETERS_PRODUCTION, IMMUTABLE_TYPE_PARAMETERS_PRODUCTION);
  }

  /**
   * @see compiler.parser.Rule#match(java.lang.Object[], java.lang.Object[])
   */
  @Override
  public Object match(Object[] types, Object[] args)
  {
    if (types == PRODUCTION)
    {
      return new PointerType((QName) args[0], false, new TypeParameter[0]);
    }
    if (types == IMMUTABLE_PRODUCTION)
    {
      return new PointerType((QName) args[1], true, new TypeParameter[0]);
    }
    if (types == TYPE_PARAMETERS_PRODUCTION)
    {
      return new PointerType((QName) args[0], false, (TypeParameter[]) args[1]);
    }
    if (types == IMMUTABLE_TYPE_PARAMETERS_PRODUCTION)
    {
      return new PointerType((QName) args[1], true, (TypeParameter[]) args[2]);
    }
    throw badTypeList();
  }

}
