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
public final class TypeParameterListRule extends Rule<ParseType>
{
  private static final long serialVersionUID = 1L;

  private static final Production<ParseType> TYPE_PARAMETER_PRODUCTION                 = new Production<ParseType>(TYPE_PARAMETER_NOT_QNAME_LIST);
  private static final Production<ParseType> QNAME_PRODUCTION                          = new Production<ParseType>(QNAME);
  private static final Production<ParseType> NESTED_QNAME_LIST_PRODUCTION              = new Production<ParseType>(NESTED_QNAME_LIST);
  private static final Production<ParseType> CONTINUATION_TYPE_PARAMETER_PRODUCTION    = new Production<ParseType>(TYPE_PARAMETER_NOT_QNAME_LIST, COMMA, TYPE_PARAMETER_LIST);
  private static final Production<ParseType> CONTINUATION_QNAME_PRODUCTION             = new Production<ParseType>(QNAME,                         COMMA, TYPE_PARAMETER_LIST);
  private static final Production<ParseType> CONTINUATION_NESTED_QNAME_LIST_PRODUCTION = new Production<ParseType>(NESTED_QNAME_LIST,             COMMA, TYPE_PARAMETER_LIST);

  @SuppressWarnings("unchecked")
  public TypeParameterListRule()
  {
    super(TYPE_PARAMETER_LIST, TYPE_PARAMETER_PRODUCTION,              QNAME_PRODUCTION,              NESTED_QNAME_LIST_PRODUCTION,
                               CONTINUATION_TYPE_PARAMETER_PRODUCTION, CONTINUATION_QNAME_PRODUCTION, CONTINUATION_NESTED_QNAME_LIST_PRODUCTION);
  }

  /**
   * @see compiler.parser.Rule#match(compiler.parser.Production, java.lang.Object[])
   */
  @Override
  public Object match(Production<ParseType> production, Object[] args) throws ParseException
  {
    if (TYPE_PARAMETER_PRODUCTION.equals(production))
    {
      TypeParameter typeParameter = (TypeParameter) args[0];
      return new ParseList<TypeParameter>(typeParameter, typeParameter.getParseInfo());
    }
    if (QNAME_PRODUCTION.equals(production))
    {
      QName qname = (QName) args[0];
      Type type = new PointerType(qname, qname.getParseInfo());
      TypeParameter parameter = new NormalTypeParameter(type, type.getParseInfo());
      return new ParseList<TypeParameter>(parameter, parameter.getParseInfo());
    }
    if (NESTED_QNAME_LIST_PRODUCTION.equals(production))
    {
      QNameElement element = (QNameElement) args[0];
      Type type = element.toType();
      TypeParameter parameter = new NormalTypeParameter(type, type.getParseInfo());
      return new ParseList<TypeParameter>(parameter, parameter.getParseInfo());
    }
    if (CONTINUATION_TYPE_PARAMETER_PRODUCTION.equals(production))
    {
      TypeParameter newTypeParameter = (TypeParameter) args[0];
      @SuppressWarnings("unchecked")
      ParseList<TypeParameter> list = (ParseList<TypeParameter>) args[2];
      list.addFirst(newTypeParameter, ParseInfo.combine(newTypeParameter.getParseInfo(), (ParseInfo) args[1], list.getParseInfo()));
      return list;
    }
    if (CONTINUATION_QNAME_PRODUCTION.equals(production))
    {
      QName qname = (QName) args[0];
      PointerType type = new PointerType(qname, qname.getParseInfo());
      TypeParameter newTypeParameter = new NormalTypeParameter(type, type.getParseInfo());
      @SuppressWarnings("unchecked")
      ParseList<TypeParameter> list = (ParseList<TypeParameter>) args[2];
      list.addFirst(newTypeParameter, ParseInfo.combine(newTypeParameter.getParseInfo(), (ParseInfo) args[1], list.getParseInfo()));
      return list;
    }
    if (CONTINUATION_NESTED_QNAME_LIST_PRODUCTION.equals(production))
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
