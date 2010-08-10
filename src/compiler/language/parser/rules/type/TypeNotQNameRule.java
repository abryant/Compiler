package compiler.language.parser.rules.type;

import static compiler.language.parser.ParseType.POINTER_TYPE_NOT_QNAME;
import static compiler.language.parser.ParseType.TYPE_NOT_POINTER_TYPE;
import static compiler.language.parser.ParseType.TYPE_NOT_QNAME;

import compiler.parser.ParseException;
import compiler.parser.Rule;

/*
 * Created on 8 Aug 2010
 */

/**
 * @author Anthony Bryant
 */
public class TypeNotQNameRule extends Rule
{

  private static final Object[] PRODUCTION = new Object[] {TYPE_NOT_POINTER_TYPE};
  private static final Object[] POINTER_TYPE_NOT_QNAME_PRODUCTION = new Object[] {POINTER_TYPE_NOT_QNAME};

  public TypeNotQNameRule()
  {
    super(TYPE_NOT_QNAME, PRODUCTION, POINTER_TYPE_NOT_QNAME_PRODUCTION);
  }

  /**
   * {@inheritDoc}
   * @see compiler.parser.Rule#match(java.lang.Object[], java.lang.Object[])
   */
  @Override
  public Object match(Object[] types, Object[] args) throws ParseException
  {
    if (types == PRODUCTION || types == POINTER_TYPE_NOT_QNAME_PRODUCTION)
    {
      // All types are actually subclasses of Type, so just return the argument
      return args[0];
    }
    throw badTypeList();
  }

}
