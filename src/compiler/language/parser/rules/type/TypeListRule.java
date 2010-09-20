package compiler.language.parser.rules.type;

import static compiler.language.parser.ParseType.TYPE_LIST;

import compiler.language.ast.ParseList;
import compiler.language.ast.misc.QNameElement;
import compiler.language.ast.type.Type;
import compiler.language.parser.ParseType;
import compiler.parser.ParseException;
import compiler.parser.Production;
import compiler.parser.Rule;

/*
 * Created on 13 Jul 2010
 */

/**
 * @author Anthony Bryant
 */
public class TypeListRule extends Rule
{
  private static final long serialVersionUID = 1L;

  private static final Production TYPE_LIST_PRODUCTION = new Production(ParseType.TYPE_LIST_NOT_QNAME_LIST);
  private static final Production QNAME_LIST_PRODUCTION = new Production(ParseType.QNAME_LIST);

  public TypeListRule()
  {
    super(TYPE_LIST, TYPE_LIST_PRODUCTION, QNAME_LIST_PRODUCTION);
  }

  /**
   * @see compiler.parser.Rule#match(compiler.parser.Production, java.lang.Object[])
   */
  @Override
  public Object match(Production production, Object[] args) throws ParseException
  {
    if (TYPE_LIST_PRODUCTION.equals(production))
    {
      // return the existing ParseList<Type>
      return args[0];
    }
    if (QNAME_LIST_PRODUCTION.equals(production))
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
