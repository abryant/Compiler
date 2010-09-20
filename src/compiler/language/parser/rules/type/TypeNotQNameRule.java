package compiler.language.parser.rules.type;

import static compiler.language.parser.ParseType.NESTED_QNAME_LIST;
import static compiler.language.parser.ParseType.TYPE_NOT_QNAME;
import static compiler.language.parser.ParseType.TYPE_NOT_QNAME_LIST;

import compiler.language.ast.misc.QNameElement;
import compiler.parser.ParseException;
import compiler.parser.Production;
import compiler.parser.Rule;

/*
 * Created on 8 Aug 2010
 */

/**
 * @author Anthony Bryant
 */
public class TypeNotQNameRule extends Rule
{
  private static final long serialVersionUID = 1L;

  private static final Production PRODUCTION = new Production(TYPE_NOT_QNAME_LIST);
  private static final Production QNAME_LIST_PRODUCTION = new Production(NESTED_QNAME_LIST);

  public TypeNotQNameRule()
  {
    super(TYPE_NOT_QNAME, PRODUCTION, QNAME_LIST_PRODUCTION);
  }

  /**
   * {@inheritDoc}
   * @see compiler.parser.Rule#match(compiler.parser.Production, java.lang.Object[])
   */
  @Override
  public Object match(Production production, Object[] args) throws ParseException
  {
    if (PRODUCTION.equals(production))
    {
      // All types are actually subclasses of Type, so just return the argument
      return args[0];
    }
    if (QNAME_LIST_PRODUCTION.equals(production))
    {
      QNameElement element = (QNameElement) args[0];
      return element.toType();
    }
    throw badTypeList();
  }

}
