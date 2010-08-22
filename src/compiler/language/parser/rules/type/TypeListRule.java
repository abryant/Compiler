package compiler.language.parser.rules.type;

import static compiler.language.parser.ParseType.TYPE_LIST;

import compiler.language.ast.ParseList;
import compiler.language.ast.misc.QNameElement;
import compiler.language.ast.type.Type;
import compiler.language.parser.ParseType;
import compiler.parser.ParseException;
import compiler.parser.Rule;

/*
 * Created on 13 Jul 2010
 */

/**
 * @author Anthony Bryant
 */
public class TypeListRule extends Rule
{

  private static final Object[] TYPE_LIST_PRODUCTION = new Object[] {ParseType.TYPE_LIST_NOT_QNAME_LIST};
  private static final Object[] QNAME_LIST_PRODUCTION = new Object[] {ParseType.QNAME_LIST};

  public TypeListRule()
  {
    super(TYPE_LIST, TYPE_LIST_PRODUCTION, QNAME_LIST_PRODUCTION);
  }

  /**
   * @see compiler.parser.Rule#match(java.lang.Object[], java.lang.Object[])
   */
  @Override
  public Object match(Object[] types, Object[] args) throws ParseException
  {
    if (types == TYPE_LIST_PRODUCTION)
    {
      // return the existing ParseList<Type>
      return args[0];
    }
    if (types == QNAME_LIST_PRODUCTION)
    {
      @SuppressWarnings("unchecked")
      ParseList<QNameElement> elements = (ParseList<QNameElement>) args[0];
      QNameElement[] elems = elements.toArray(new QNameElement[0]);
      Type[] typeArray = new Type[elems.length];
      for (int i = 0; i < elems.length; i++)
      {
        typeArray[i] = elems[i].toType();
      }
      return new ParseList<Type>(typeArray, elements.getParseInfo());
    }
    throw badTypeList();
  }

}
