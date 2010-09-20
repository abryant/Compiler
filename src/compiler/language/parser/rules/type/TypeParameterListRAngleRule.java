package compiler.language.parser.rules.type;

import static compiler.language.parser.ParseType.COMMA;
import static compiler.language.parser.ParseType.NESTED_QNAME_LIST;
import static compiler.language.parser.ParseType.QNAME;
import static compiler.language.parser.ParseType.TYPE_PARAMETER_LIST_RANGLE;
import static compiler.language.parser.ParseType.TYPE_PARAMETER_NOT_QNAME_LIST;
import static compiler.language.parser.ParseType.TYPE_PARAMETER_RANGLE;

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
import compiler.parser.Production;
import compiler.parser.Rule;

/*
 * Created on 10 Aug 2010
 */

/**
 * @author Anthony Bryant
 */
public class TypeParameterListRAngleRule extends Rule
{
  private static final long serialVersionUID = 1L;

  private static final Production TYPE_PARAMETER_PRODUCTION      = new Production(TYPE_PARAMETER_RANGLE);
  private static final Production TYPE_PARAMETER_LIST_PRODUCTION = new Production(TYPE_PARAMETER_NOT_QNAME_LIST, COMMA, TYPE_PARAMETER_LIST_RANGLE);
  private static final Production QNAME_LIST_PRODUCTION          = new Production(QNAME,                         COMMA, TYPE_PARAMETER_LIST_RANGLE);
  private static final Production NESTED_QNAME_LIST_PRODUCTION   = new Production(NESTED_QNAME_LIST,             COMMA, TYPE_PARAMETER_LIST_RANGLE);

  public TypeParameterListRAngleRule()
  {
    super(TYPE_PARAMETER_LIST_RANGLE, TYPE_PARAMETER_PRODUCTION, TYPE_PARAMETER_LIST_PRODUCTION, QNAME_LIST_PRODUCTION, NESTED_QNAME_LIST_PRODUCTION);
  }

  /**
   * {@inheritDoc}
   * @see compiler.parser.Rule#match(compiler.parser.Production, java.lang.Object[])
   */
  @Override
  public Object match(Production production, Object[] args) throws ParseException
  {
    if (TYPE_PARAMETER_PRODUCTION.equals(production))
    {
      @SuppressWarnings("unchecked")
      ParseContainer<TypeParameter> parameter = (ParseContainer<TypeParameter>) args[0];
      ParseList<TypeParameter> list = new ParseList<TypeParameter>(parameter.getItem(), parameter.getItem().getParseInfo());
      return new ParseContainer<ParseList<TypeParameter>>(list, parameter.getParseInfo());
    }
    if (TYPE_PARAMETER_LIST_PRODUCTION.equals(production))
    {
      TypeParameter parameter = (TypeParameter) args[0];
      @SuppressWarnings("unchecked")
      ParseContainer<ParseList<TypeParameter>> container = (ParseContainer<ParseList<TypeParameter>>) args[2];
      ParseList<TypeParameter> list = container.getItem();

      list.addFirst(parameter, ParseInfo.combine(parameter.getParseInfo(), (ParseInfo) args[1], list.getParseInfo()));
      return new ParseContainer<ParseList<TypeParameter>>(list, ParseInfo.combine(parameter.getParseInfo(), (ParseInfo) args[1], container.getParseInfo()));
    }
    if (QNAME_LIST_PRODUCTION.equals(production))
    {
      QName qname = (QName) args[0];
      Type type = new PointerType(qname, qname.getParseInfo());
      TypeParameter parameter = new NormalTypeParameter(type, type.getParseInfo());
      @SuppressWarnings("unchecked")
      ParseContainer<ParseList<TypeParameter>> container = (ParseContainer<ParseList<TypeParameter>>) args[2];
      ParseList<TypeParameter> list = container.getItem();

      list.addFirst(parameter, ParseInfo.combine(parameter.getParseInfo(), (ParseInfo) args[1], list.getParseInfo()));
      return new ParseContainer<ParseList<TypeParameter>>(list, ParseInfo.combine(parameter.getParseInfo(), (ParseInfo) args[1], container.getParseInfo()));
    }
    if (NESTED_QNAME_LIST_PRODUCTION.equals(production))
    {
      QNameElement element = (QNameElement) args[0];
      Type type = element.toType();
      TypeParameter parameter = new NormalTypeParameter(type, type.getParseInfo());
      @SuppressWarnings("unchecked")
      ParseContainer<ParseList<TypeParameter>> container = (ParseContainer<ParseList<TypeParameter>>) args[2];
      ParseList<TypeParameter> list = container.getItem();

      list.addFirst(parameter, ParseInfo.combine(parameter.getParseInfo(), (ParseInfo) args[1], list.getParseInfo()));
      return new ParseContainer<ParseList<TypeParameter>>(list, ParseInfo.combine(parameter.getParseInfo(), (ParseInfo) args[1], container.getParseInfo()));
    }
    throw badTypeList();
  }

}
