package compiler.language.parser.rules.type;

import static compiler.language.parser.ParseType.DOT;
import static compiler.language.parser.ParseType.HASH;
import static compiler.language.parser.ParseType.MUTABLE_POINTER_TYPE_TRAILING_PARAMS;
import static compiler.language.parser.ParseType.POINTER_TYPE_NOT_QNAME;
import static compiler.language.parser.ParseType.QNAME;

import compiler.language.ast.ParseInfo;
import compiler.language.ast.misc.QName;
import compiler.language.ast.terminal.Name;
import compiler.language.ast.type.PointerType;
import compiler.language.ast.type.TypeParameter;
import compiler.parser.Rule;

/*
 * Created on 8 Aug 2010
 */

/**
 * @author Anthony Bryant
 */
public class PointerTypeNotQNameRule extends Rule
{

  private static final Object[] PARAMETERS_PRODUCTION = new Object[] {MUTABLE_POINTER_TYPE_TRAILING_PARAMS};
  private static final Object[] PARAMETERS_TRAILING_QNAME_PRODUCTION = new Object[] {MUTABLE_POINTER_TYPE_TRAILING_PARAMS, DOT, QNAME};
  private static final Object[] IMMUTABLE_QNAME_PRODUCTION = new Object[] {HASH, QNAME};
  private static final Object[] IMMUTABLE_PARAMETERS_PRODUCTION = new Object[] {HASH, MUTABLE_POINTER_TYPE_TRAILING_PARAMS};
  private static final Object[] IMMUTABLE_PARAMETERS_TRAILING_QNAME_PRODUCTION = new Object[] {HASH, MUTABLE_POINTER_TYPE_TRAILING_PARAMS, DOT, QNAME};

  public PointerTypeNotQNameRule()
  {
    super(POINTER_TYPE_NOT_QNAME, PARAMETERS_PRODUCTION, PARAMETERS_TRAILING_QNAME_PRODUCTION, IMMUTABLE_QNAME_PRODUCTION,
                                  IMMUTABLE_PARAMETERS_PRODUCTION, IMMUTABLE_PARAMETERS_TRAILING_QNAME_PRODUCTION);
  }

  /**
   * {@inheritDoc}
   * @see compiler.parser.Rule#match(java.lang.Object[], java.lang.Object[])
   */
  @Override
  public Object match(Object[] types, Object[] args)
  {
    if (types == PARAMETERS_PRODUCTION)
    {
      // MUTABLE_POINTER_TYPE_TRAILING_PARAMS has already built a PointerType, so return it
      return args[0];
    }
    if (types == PARAMETERS_TRAILING_QNAME_PRODUCTION)
    {
      PointerType oldType = (PointerType) args[0];
      QName qname = (QName) args[2];
      Name[] addedNames = qname.getNames();
      TypeParameter[][] addedTypeParameterLists = new TypeParameter[addedNames.length][];
      for (int i = 0; i < addedTypeParameterLists.length; i++)
      {
        addedTypeParameterLists[i] = new TypeParameter[0];
      }
      return new PointerType(oldType, false, addedNames, addedTypeParameterLists, ParseInfo.combine(oldType.getParseInfo(), (ParseInfo) args[1], qname.getParseInfo()));
    }
    if (types == IMMUTABLE_QNAME_PRODUCTION)
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
    if (types == IMMUTABLE_PARAMETERS_PRODUCTION)
    {
      PointerType oldType = (PointerType) args[1];
      return new PointerType(true, oldType.getNames(), oldType.getTypeParameterLists(), ParseInfo.combine((ParseInfo) args[0], oldType.getParseInfo()));
    }
    if (types == IMMUTABLE_PARAMETERS_TRAILING_QNAME_PRODUCTION)
    {
      PointerType oldType = (PointerType) args[1];
      QName qname = (QName) args[3];
      Name[] addedNames = qname.getNames();
      TypeParameter[][] addedTypeParameterLists = new TypeParameter[addedNames.length][];
      for (int i = 0; i < addedTypeParameterLists.length; i++)
      {
        addedTypeParameterLists[i] = new TypeParameter[0];
      }
      return new PointerType(oldType, true, addedNames, addedTypeParameterLists, ParseInfo.combine((ParseInfo) args[0], oldType.getParseInfo(), (ParseInfo) args[2], qname.getParseInfo()));
    }
    throw badTypeList();
  }

}
