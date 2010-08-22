package compiler.language.parser.rules.type;

import static compiler.language.parser.ParseType.COMMA;
import static compiler.language.parser.ParseType.NESTED_QNAME_LIST;
import static compiler.language.parser.ParseType.QNAME;
import static compiler.language.parser.ParseType.TYPE_PARAMETER_LIST;
import static compiler.language.parser.ParseType.TYPE_PARAMETER_NOT_QNAME_LIST;

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
 * Created on 13 Jul 2010
 */

/**
 * @author Anthony Bryant
 */
public class TypeParameterListRule extends Rule
{

  private static final Object[] TYPE_PARAMETER_PRODUCTION                 = new Object[] {TYPE_PARAMETER_NOT_QNAME_LIST};
  private static final Object[] QNAME_PRODUCTION                          = new Object[] {QNAME};
  private static final Object[] NESTED_QNAME_LIST_PRODUCTION              = new Object[] {NESTED_QNAME_LIST};
  private static final Object[] CONTINUATION_TYPE_PARAMETER_PRODUCTION    = new Object[] {TYPE_PARAMETER_NOT_QNAME_LIST, COMMA, TYPE_PARAMETER_LIST};
  private static final Object[] CONTINUATION_QNAME_PRODUCTION             = new Object[] {QNAME,                         COMMA, TYPE_PARAMETER_LIST};
  private static final Object[] CONTINUATION_NESTED_QNAME_LIST_PRODUCTION = new Object[] {NESTED_QNAME_LIST,             COMMA, TYPE_PARAMETER_LIST};

  public TypeParameterListRule()
  {
    super(TYPE_PARAMETER_LIST, TYPE_PARAMETER_PRODUCTION,              QNAME_PRODUCTION,              NESTED_QNAME_LIST_PRODUCTION,
                               CONTINUATION_TYPE_PARAMETER_PRODUCTION, CONTINUATION_QNAME_PRODUCTION, CONTINUATION_NESTED_QNAME_LIST_PRODUCTION);
  }

  /**
   * @see compiler.parser.Rule#match(java.lang.Object[], java.lang.Object[])
   */
  @Override
  public Object match(Object[] types, Object[] args) throws ParseException
  {
    if (types == TYPE_PARAMETER_PRODUCTION)
    {
      TypeParameter typeParameter = (TypeParameter) args[0];
      return new ParseList<TypeParameter>(typeParameter, typeParameter.getParseInfo());
    }
    if (types == QNAME_PRODUCTION)
    {
      QName qname = (QName) args[0];
      Type type = new PointerType(qname, qname.getParseInfo());
      TypeParameter parameter = new NormalTypeParameter(type, type.getParseInfo());
      return new ParseList<TypeParameter>(parameter, parameter.getParseInfo());
    }
    if (types == NESTED_QNAME_LIST_PRODUCTION)
    {
      QNameElement element = (QNameElement) args[0];
      Type type = element.toType();
      TypeParameter parameter = new NormalTypeParameter(type, type.getParseInfo());
      return new ParseList<TypeParameter>(parameter, parameter.getParseInfo());
    }
    if (types == CONTINUATION_TYPE_PARAMETER_PRODUCTION)
    {
      TypeParameter newTypeParameter = (TypeParameter) args[0];
      @SuppressWarnings("unchecked")
      ParseList<TypeParameter> list = (ParseList<TypeParameter>) args[2];
      list.addFirst(newTypeParameter, ParseInfo.combine(newTypeParameter.getParseInfo(), (ParseInfo) args[1], list.getParseInfo()));
      return list;
    }
    if (types == CONTINUATION_QNAME_PRODUCTION)
    {
      QName qname = (QName) args[0];
      PointerType type = new PointerType(qname, qname.getParseInfo());
      TypeParameter newTypeParameter = new NormalTypeParameter(type, type.getParseInfo());
      @SuppressWarnings("unchecked")
      ParseList<TypeParameter> list = (ParseList<TypeParameter>) args[2];
      list.addFirst(newTypeParameter, ParseInfo.combine(newTypeParameter.getParseInfo(), (ParseInfo) args[1], list.getParseInfo()));
      return list;
    }
    if (types == CONTINUATION_NESTED_QNAME_LIST_PRODUCTION)
    {
      QNameElement element = (QNameElement) args[0];
      Type type = element.toType();
      TypeParameter newTypeParameter = new NormalTypeParameter(type, type.getParseInfo());
      @SuppressWarnings("unchecked")
      ParseList<TypeParameter> list = (ParseList<TypeParameter>) args[2];
      list.addFirst(newTypeParameter, ParseInfo.combine(newTypeParameter.getParseInfo(), (ParseInfo) args[1], list.getParseInfo()));
      return list;
    }
    throw badTypeList();
  }

}
