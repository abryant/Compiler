package compiler.language.parser.rules.type;

import static compiler.language.parser.ParseType.COMMA;
import static compiler.language.parser.ParseType.NESTED_QNAME_LIST;
import static compiler.language.parser.ParseType.QNAME;
import static compiler.language.parser.ParseType.TYPE_PARAMETER_LIST_TRIPLE_RANGLE;
import static compiler.language.parser.ParseType.TYPE_PARAMETER_NOT_QNAME_LIST;
import static compiler.language.parser.ParseType.TYPE_PARAMETER_TRIPLE_RANGLE;

import compiler.language.ast.ParseContainer;
import compiler.language.ast.ParseInfo;
import compiler.language.ast.ParseList;
import compiler.language.ast.misc.QName;
import compiler.language.ast.misc.QNameElement;
import compiler.language.ast.type.NormalTypeParameter;
import compiler.language.ast.type.PointerType;
import compiler.language.ast.type.Type;
import compiler.language.ast.type.TypeParameter;
import compiler.parser.ParseException;
import compiler.parser.Rule;

/*
 * Created on 21 Aug 2010
 */

/**
 * @author Anthony Bryant
 */
public class TypeParameterListTripleRAngleRule extends Rule
{

  private static final Object[] TYPE_PARAMETER_PRODUCTION      = new Object[] {TYPE_PARAMETER_TRIPLE_RANGLE};
  private static final Object[] TYPE_PARAMETER_LIST_PRODUCTION = new Object[] {TYPE_PARAMETER_NOT_QNAME_LIST, COMMA, TYPE_PARAMETER_LIST_TRIPLE_RANGLE};
  private static final Object[] QNAME_LIST_PRODUCTION          = new Object[] {QNAME,                         COMMA, TYPE_PARAMETER_LIST_TRIPLE_RANGLE};
  private static final Object[] NESTED_QNAME_LIST_PRODUCTION   = new Object[] {NESTED_QNAME_LIST,             COMMA, TYPE_PARAMETER_LIST_TRIPLE_RANGLE};

  public TypeParameterListTripleRAngleRule()
  {
    super(TYPE_PARAMETER_LIST_TRIPLE_RANGLE, TYPE_PARAMETER_PRODUCTION, TYPE_PARAMETER_LIST_PRODUCTION, QNAME_LIST_PRODUCTION, NESTED_QNAME_LIST_PRODUCTION);
  }

  /**
   * {@inheritDoc}
   * @see compiler.parser.Rule#match(java.lang.Object[], java.lang.Object[])
   */
  @Override
  public Object match(Object[] types, Object[] args) throws ParseException
  {
    if (types == TYPE_PARAMETER_PRODUCTION)
    {
      @SuppressWarnings("unchecked")
      ParseContainer<ParseContainer<ParseContainer<TypeParameter>>> parameter = (ParseContainer<ParseContainer<ParseContainer<TypeParameter>>>) args[0];
      ParseList<TypeParameter> list = new ParseList<TypeParameter>(parameter.getItem().getItem().getItem(), parameter.getItem().getItem().getItem().getParseInfo());
      ParseContainer<ParseList<TypeParameter>> firstContainer = new ParseContainer<ParseList<TypeParameter>>(list, parameter.getItem().getItem().getParseInfo());
      ParseContainer<ParseContainer<ParseList<TypeParameter>>> secondContainer =
        new ParseContainer<ParseContainer<ParseList<TypeParameter>>>(firstContainer, parameter.getItem().getParseInfo());
      return new ParseContainer<ParseContainer<ParseContainer<ParseList<TypeParameter>>>>(secondContainer, parameter.getParseInfo());
    }

    TypeParameter parameter;
    if (types == TYPE_PARAMETER_LIST_PRODUCTION)
    {
      parameter = (TypeParameter) args[0];
    }
    else if (types == QNAME_LIST_PRODUCTION)
    {
      QName qname = (QName) args[0];
      Type type = new PointerType(qname, qname.getParseInfo());
      parameter = new NormalTypeParameter(type, type.getParseInfo());
    }
    else if (types == NESTED_QNAME_LIST_PRODUCTION)
    {
      QNameElement element = (QNameElement) args[0];
      Type type = element.toType();
      parameter = new NormalTypeParameter(type, type.getParseInfo());
    }
    else
    {
      throw badTypeList();
    }

    @SuppressWarnings("unchecked")
    ParseContainer<ParseContainer<ParseContainer<ParseList<TypeParameter>>>> oldContainer = (ParseContainer<ParseContainer<ParseContainer<ParseList<TypeParameter>>>>) args[2];
    ParseList<TypeParameter> list = oldContainer.getItem().getItem().getItem();
    list.addFirst(parameter, ParseInfo.combine(parameter.getParseInfo(), (ParseInfo) args[1], list.getParseInfo()));
    ParseContainer<ParseList<TypeParameter>> firstContainer =
           new ParseContainer<ParseList<TypeParameter>>(list,
                 ParseInfo.combine(parameter.getParseInfo(), (ParseInfo) args[1], oldContainer.getItem().getItem().getParseInfo()));
    ParseContainer<ParseContainer<ParseList<TypeParameter>>> secondContainer =
           new ParseContainer<ParseContainer<ParseList<TypeParameter>>>(firstContainer,
                 ParseInfo.combine(parameter.getParseInfo(), (ParseInfo) args[1], oldContainer.getItem().getParseInfo()));
    return new ParseContainer<ParseContainer<ParseContainer<ParseList<TypeParameter>>>>(secondContainer,
                 ParseInfo.combine(parameter.getParseInfo(), (ParseInfo) args[1], oldContainer.getParseInfo()));
  }

}
