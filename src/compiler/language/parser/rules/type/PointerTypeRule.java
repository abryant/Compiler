package compiler.language.parser.rules.type;

import static compiler.language.parser.ParseType.HASH;
import static compiler.language.parser.ParseType.POINTER_TYPE;
import static compiler.language.parser.ParseType.QNAME;
import static compiler.language.parser.ParseType.TYPE_PARAMETERS;

import compiler.language.ast.ParseInfo;
import compiler.language.ast.ParseList;
import compiler.language.ast.misc.QName;
import compiler.language.ast.type.PointerType;
import compiler.language.ast.type.TypeParameter;
import compiler.parser.Rule;

/*
 * Created on 4 Jul 2010
 */

/**
 * @author Anthony Bryant
 */
public class PointerTypeRule extends Rule
{

  private static final Object[] PRODUCTION = new Object[] {QNAME};
  private static final Object[] IMMUTABLE_PRODUCTION = new Object[] {HASH, QNAME};
  private static final Object[] TYPE_PARAMETERS_PRODUCTION = new Object[] {QNAME, TYPE_PARAMETERS};
  private static final Object[] IMMUTABLE_TYPE_PARAMETERS_PRODUCTION = new Object[] {HASH, QNAME, TYPE_PARAMETERS};

  public PointerTypeRule()
  {
    super(POINTER_TYPE, PRODUCTION, IMMUTABLE_PRODUCTION, TYPE_PARAMETERS_PRODUCTION, IMMUTABLE_TYPE_PARAMETERS_PRODUCTION);
  }

  /**
   * @see compiler.parser.Rule#match(java.lang.Object[], java.lang.Object[])
   */
  @Override
  public Object match(Object[] types, Object[] args)
  {
    if (types == PRODUCTION)
    {
      QName qname = (QName) args[0];
      return new PointerType(qname, false, new TypeParameter[0], qname.getParseInfo());
    }
    if (types == IMMUTABLE_PRODUCTION)
    {
      QName qname = (QName) args[1];
      return new PointerType(qname, true, new TypeParameter[0], ParseInfo.combine((ParseInfo) args[0], qname.getParseInfo()));
    }
    if (types == TYPE_PARAMETERS_PRODUCTION)
    {
      QName qname = (QName) args[0];
      @SuppressWarnings("unchecked")
      ParseList<TypeParameter> typeParameters = (ParseList<TypeParameter>) args[1];
      return new PointerType(qname, false, typeParameters.toArray(new TypeParameter[0]), ParseInfo.combine(qname.getParseInfo(), typeParameters.getParseInfo()));
    }
    if (types == IMMUTABLE_TYPE_PARAMETERS_PRODUCTION)
    {
      QName qname = (QName) args[1];
      @SuppressWarnings("unchecked")
      ParseList<TypeParameter> typeParameters = (ParseList<TypeParameter>) args[2];
      return new PointerType(qname, true, typeParameters.toArray(new TypeParameter[0]), ParseInfo.combine((ParseInfo) args[0], qname.getParseInfo(), typeParameters.getParseInfo()));
    }
    throw badTypeList();
  }

}
