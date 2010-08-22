package compiler.language.parser.rules.type;

import static compiler.language.parser.ParseType.NESTED_QNAME_LIST;
import static compiler.language.parser.ParseType.TYPE_NOT_QNAME;
import static compiler.language.parser.ParseType.TYPE_NOT_QNAME_LIST;

import compiler.language.ast.misc.QNameElement;
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

  private static final Object[] PRODUCTION = new Object[] {TYPE_NOT_QNAME_LIST};
  private static final Object[] QNAME_LIST_PRODUCTION = new Object[] {NESTED_QNAME_LIST};

  public TypeNotQNameRule()
  {
    super(TYPE_NOT_QNAME, PRODUCTION, QNAME_LIST_PRODUCTION);
  }

  /**
   * {@inheritDoc}
   * @see compiler.parser.Rule#match(java.lang.Object[], java.lang.Object[])
   */
  @Override
  public Object match(Object[] types, Object[] args) throws ParseException
  {
    if (types == PRODUCTION)
    {
      // All types are actually subclasses of Type, so just return the argument
      return args[0];
    }
    if (types == QNAME_LIST_PRODUCTION)
    {
      QNameElement element = (QNameElement) args[0];
      return element.toType();
    }
    throw badTypeList();
  }

}
