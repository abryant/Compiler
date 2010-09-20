package compiler.language.parser.rules.type;

import static compiler.language.parser.ParseType.NESTED_QNAME_LIST;
import static compiler.language.parser.ParseType.POINTER_TYPE_RANGLE;
import static compiler.language.parser.ParseType.RANGLE;
import static compiler.language.parser.ParseType.TUPLE_TYPE_NOT_QNAME_LIST;
import static compiler.language.parser.ParseType.TYPE_NOT_POINTER_TYPE_NOT_TUPLE_TYPE;
import static compiler.language.parser.ParseType.TYPE_RANGLE;

import compiler.language.ast.ParseContainer;
import compiler.language.ast.ParseInfo;
import compiler.language.ast.misc.QNameElement;
import compiler.language.ast.type.PointerType;
import compiler.language.ast.type.Type;
import compiler.parser.ParseException;
import compiler.parser.Production;
import compiler.parser.Rule;

/*
 * Created on 10 Aug 2010
 */

/**
 * @author Anthony Bryant
 */
public class TypeRAngleRule extends Rule
{
  private static final long serialVersionUID = 1L;

  private static final Production PRODUCTION = new Production(TYPE_NOT_POINTER_TYPE_NOT_TUPLE_TYPE, RANGLE);
  private static final Production POINTER_TYPE_PRODUCTION = new Production(POINTER_TYPE_RANGLE);
  private static final Production TUPLE_TYPE_PRODUCTION = new Production(TUPLE_TYPE_NOT_QNAME_LIST, RANGLE);
  private static final Production NESTED_QNAME_LIST_PRODUCTION = new Production(NESTED_QNAME_LIST, RANGLE);

  public TypeRAngleRule()
  {
    super(TYPE_RANGLE, PRODUCTION, POINTER_TYPE_PRODUCTION, TUPLE_TYPE_PRODUCTION, NESTED_QNAME_LIST_PRODUCTION);
  }

  /**
   * {@inheritDoc}
   * @see compiler.parser.Rule#match(compiler.parser.Production, java.lang.Object[])
   */
  @Override
  public Object match(Production production, Object[] args) throws ParseException
  {
    if (PRODUCTION.equals(production) || TUPLE_TYPE_PRODUCTION.equals(production))
    {
      Type type = (Type) args[0];
      return new ParseContainer<Type>(type, ParseInfo.combine(type.getParseInfo(), (ParseInfo) args[1]));
    }
    if (POINTER_TYPE_PRODUCTION.equals(production))
    {
      // change the ParseContainer<PointerType> for a ParseContainer<Type>
      @SuppressWarnings("unchecked")
      ParseContainer<PointerType> oldContainer = (ParseContainer<PointerType>) args[0];
      return new ParseContainer<Type>(oldContainer.getItem(), oldContainer.getParseInfo());
    }
    if (NESTED_QNAME_LIST_PRODUCTION.equals(production))
    {
      QNameElement element = (QNameElement) args[0];
      Type type = element.toType();
      return new ParseContainer<Type>(type, ParseInfo.combine(type.getParseInfo(), (ParseInfo) args[1]));
    }
    throw badTypeList();
  }

}
