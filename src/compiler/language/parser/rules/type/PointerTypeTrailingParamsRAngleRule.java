package compiler.language.parser.rules.type;

import static compiler.language.parser.ParseType.DOT;
import static compiler.language.parser.ParseType.HASH;
import static compiler.language.parser.ParseType.LANGLE;
import static compiler.language.parser.ParseType.POINTER_TYPE_TRAILING_PARAMS;
import static compiler.language.parser.ParseType.POINTER_TYPE_TRAILING_PARAMS_RANGLE;
import static compiler.language.parser.ParseType.QNAME;
import static compiler.language.parser.ParseType.RANGLE;
import static compiler.language.parser.ParseType.TYPE_PARAMETER_LIST_DOUBLE_RANGLE;

import compiler.language.ast.ParseContainer;
import compiler.language.ast.ParseInfo;
import compiler.language.ast.ParseList;
import compiler.language.ast.misc.QName;
import compiler.language.ast.terminal.Name;
import compiler.language.ast.type.PointerType;
import compiler.language.ast.type.TypeParameter;
import compiler.parser.ParseException;
import compiler.parser.Rule;

/*
 * Created on 9 Aug 2010
 */

/**
 * @author Anthony Bryant
 */
public class PointerTypeTrailingParamsRAngleRule extends Rule
{

  private static final Object[] MUTABLE_PRODUCTION         = new Object[] {      QNAME, LANGLE, TYPE_PARAMETER_LIST_DOUBLE_RANGLE};
  private static final Object[] IMMUTABLE_PRODUCTION       = new Object[] {HASH, QNAME, LANGLE, TYPE_PARAMETER_LIST_DOUBLE_RANGLE};
  private static final Object[] TRAILING_PARAMS_PRODUCTION = new Object[] {POINTER_TYPE_TRAILING_PARAMS, DOT, QNAME, LANGLE, TYPE_PARAMETER_LIST_DOUBLE_RANGLE};
  private static final Object[] RANGLE_PRODUCTION          = new Object[] {POINTER_TYPE_TRAILING_PARAMS, RANGLE};

  public PointerTypeTrailingParamsRAngleRule()
  {
    super(POINTER_TYPE_TRAILING_PARAMS_RANGLE, MUTABLE_PRODUCTION, IMMUTABLE_PRODUCTION, TRAILING_PARAMS_PRODUCTION, RANGLE_PRODUCTION);
  }

  /**
   * {@inheritDoc}
   * @see compiler.parser.Rule#match(java.lang.Object[], java.lang.Object[])
   */
  @Override
  public Object match(Object[] types, Object[] args) throws ParseException
  {
    if (types == MUTABLE_PRODUCTION)
    {
      QName qname = (QName) args[0];
      @SuppressWarnings("unchecked")
      ParseContainer<ParseContainer<ParseList<TypeParameter>>> typeParameters = (ParseContainer<ParseContainer<ParseList<TypeParameter>>>) args[2];
      Name[] names = qname.getNames();
      TypeParameter[][] typeParameterLists = new TypeParameter[names.length][];
      for (int i = 0; i < typeParameterLists.length - 1; i++)
      {
        typeParameterLists[i] = new TypeParameter[0];
      }
      typeParameterLists[typeParameterLists.length - 1] = typeParameters.getItem().getItem().toArray(new TypeParameter[0]);
      PointerType type = new PointerType(false, names, typeParameterLists,
                                         ParseInfo.combine(qname.getParseInfo(), (ParseInfo) args[1], typeParameters.getItem().getParseInfo()));
      return new ParseContainer<PointerType>(type, ParseInfo.combine(qname.getParseInfo(), (ParseInfo) args[1], typeParameters.getParseInfo()));
    }
    if (types == IMMUTABLE_PRODUCTION)
    {
      QName qname = (QName) args[1];
      @SuppressWarnings("unchecked")
      ParseContainer<ParseContainer<ParseList<TypeParameter>>> typeParameters = (ParseContainer<ParseContainer<ParseList<TypeParameter>>>) args[3];
      Name[] names = qname.getNames();
      TypeParameter[][] typeParameterLists = new TypeParameter[names.length][];
      for (int i = 0; i < typeParameterLists.length - 1; i++)
      {
        typeParameterLists[i] = new TypeParameter[0];
      }
      typeParameterLists[typeParameterLists.length - 1] = typeParameters.getItem().getItem().toArray(new TypeParameter[0]);
      PointerType type = new PointerType(true, names, typeParameterLists,
                                         ParseInfo.combine((ParseInfo) args[0], qname.getParseInfo(), (ParseInfo) args[2], typeParameters.getItem().getParseInfo()));
      return new ParseContainer<PointerType>(type, ParseInfo.combine((ParseInfo) args[0], qname.getParseInfo(), (ParseInfo) args[2], typeParameters.getParseInfo()));
    }
    if (types == TRAILING_PARAMS_PRODUCTION)
    {
      PointerType oldType = (PointerType) args[0];
      QName qname = (QName) args[2];
      @SuppressWarnings("unchecked")
      ParseContainer<ParseContainer<ParseList<TypeParameter>>> typeParameters = (ParseContainer<ParseContainer<ParseList<TypeParameter>>>) args[4];
      Name[] names = qname.getNames();
      TypeParameter[][] typeParameterLists = new TypeParameter[names.length][];
      for (int i = 0; i < typeParameterLists.length - 1; i++)
      {
        typeParameterLists[i] = new TypeParameter[0];
      }
      typeParameterLists[typeParameterLists.length - 1] = typeParameters.getItem().getItem().toArray(new TypeParameter[0]);
      PointerType type = new PointerType(oldType, oldType.isImmutable(), names, typeParameterLists,
                                         ParseInfo.combine(oldType.getParseInfo(), (ParseInfo) args[1], qname.getParseInfo(), (ParseInfo) args[3],
                                                           typeParameters.getItem().getParseInfo()));
      return new ParseContainer<PointerType>(type, ParseInfo.combine(oldType.getParseInfo(), (ParseInfo) args[1], qname.getParseInfo(), (ParseInfo) args[3],
                                                                     typeParameters.getItem().getParseInfo()));
    }
    if (types == RANGLE_PRODUCTION)
    {
      PointerType type = (PointerType) args[0];
      return new ParseContainer<PointerType>(type, ParseInfo.combine(type.getParseInfo(), (ParseInfo) args[1]));
    }
    throw badTypeList();
  }

}
