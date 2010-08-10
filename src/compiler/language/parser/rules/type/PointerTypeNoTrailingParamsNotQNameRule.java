package compiler.language.parser.rules.type;

import static compiler.language.parser.ParseType.DOT;
import static compiler.language.parser.ParseType.HASH;
import static compiler.language.parser.ParseType.POINTER_TYPE_NO_TRAILING_PARAMS_NOT_QNAME;
import static compiler.language.parser.ParseType.POINTER_TYPE_TRAILING_PARAMS;
import static compiler.language.parser.ParseType.QNAME;

import compiler.language.ast.ParseInfo;
import compiler.language.ast.misc.QName;
import compiler.language.ast.terminal.Name;
import compiler.language.ast.type.PointerType;
import compiler.language.ast.type.TypeParameter;
import compiler.parser.Rule;

/*
 * Created on 9 Aug 2010
 */

/**
 * @author Anthony Bryant
 */
public class PointerTypeNoTrailingParamsNotQNameRule extends Rule
{

  private static final Object[] IMMUTABLE_PRODUCTION = new Object[] {HASH, QNAME};
  private static final Object[] TRAILING_PARAMS_QNAME_PRODUCTION = new Object[] {POINTER_TYPE_TRAILING_PARAMS, DOT, QNAME};

  public PointerTypeNoTrailingParamsNotQNameRule()
  {
    super(POINTER_TYPE_NO_TRAILING_PARAMS_NOT_QNAME, IMMUTABLE_PRODUCTION, TRAILING_PARAMS_QNAME_PRODUCTION);
  }

  /**
   * {@inheritDoc}
   * @see compiler.parser.Rule#match(java.lang.Object[], java.lang.Object[])
   */
  @Override
  public Object match(Object[] types, Object[] args)
  {
    if (types == IMMUTABLE_PRODUCTION)
    {
      QName qname = (QName) args[1];
      Name[] names = qname.getNames();
      TypeParameter[][] typeParameterLists = new TypeParameter[names.length][];
      for (int i = 0; i < typeParameterLists.length; i++)
      {
        typeParameterLists[i] = new TypeParameter[0];
      }
      return new PointerType(true, names, typeParameterLists, ParseInfo.combine((ParseInfo) args[0], qname.getParseInfo()));
    }
    if (types == TRAILING_PARAMS_QNAME_PRODUCTION)
    {
      PointerType oldType = (PointerType) args[0];
      QName qname = (QName) args[2];
      Name[] names = qname.getNames();
      TypeParameter[][] typeParameterLists = new TypeParameter[names.length][];
      for (int i = 0; i < typeParameterLists.length; i++)
      {
        typeParameterLists[i] = new TypeParameter[0];
      }
      return new PointerType(oldType, oldType.isImmutable(), names, typeParameterLists,
                             ParseInfo.combine(oldType.getParseInfo(), (ParseInfo) args[1], qname.getParseInfo()));
    }
    throw badTypeList();
  }

}
