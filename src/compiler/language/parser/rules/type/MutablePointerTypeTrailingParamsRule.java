package compiler.language.parser.rules.type;

import static compiler.language.parser.ParseType.DOT;
import static compiler.language.parser.ParseType.MUTABLE_POINTER_TYPE_TRAILING_PARAMS;
import static compiler.language.parser.ParseType.QNAME;
import static compiler.language.parser.ParseType.TYPE_PARAMETERS;

import compiler.language.ast.ParseInfo;
import compiler.language.ast.ParseList;
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
public class MutablePointerTypeTrailingParamsRule extends Rule
{

  private static final Object[] START_PRODUCTION = new Object[] {QNAME, TYPE_PARAMETERS};
  private static final Object[] CONTINUATION_PRODUCTION = new Object[] {MUTABLE_POINTER_TYPE_TRAILING_PARAMS, DOT, QNAME, TYPE_PARAMETERS};

  public MutablePointerTypeTrailingParamsRule()
  {
    super(MUTABLE_POINTER_TYPE_TRAILING_PARAMS, START_PRODUCTION, CONTINUATION_PRODUCTION);
  }

  /**
   * {@inheritDoc}
   * @see compiler.parser.Rule#match(java.lang.Object[], java.lang.Object[])
   */
  @Override
  public Object match(Object[] types, Object[] args)
  {
    if (types == START_PRODUCTION)
    {
      QName qname = (QName) args[0];
      @SuppressWarnings("unchecked")
      ParseList<TypeParameter> typeParameters = (ParseList<TypeParameter>) args[1];
      Name[] names = qname.getNames();
      TypeParameter[][] typeParameterLists = new TypeParameter[names.length][];
      for (int i = 0; i < typeParameterLists.length - 1; i++)
      {
        typeParameterLists[i] = new TypeParameter[0];
      }
      typeParameterLists[typeParameterLists.length - 1] = typeParameters.toArray(new TypeParameter[0]);
      return new PointerType(false, names, typeParameterLists, ParseInfo.combine(qname.getParseInfo(), typeParameters.getParseInfo()));
    }
    if (types == CONTINUATION_PRODUCTION)
    {
      PointerType oldType = (PointerType) args[0];
      QName qname = (QName) args[2];
      @SuppressWarnings("unchecked")
      ParseList<TypeParameter> typeParameters = (ParseList<TypeParameter>) args[3];
      Name[] addedNames = qname.getNames();
      TypeParameter[][] addedTypeParameterLists = new TypeParameter[addedNames.length][];
      for (int i = 0; i < addedTypeParameterLists.length - 1; i++)
      {
        addedTypeParameterLists[i] = new TypeParameter[0];
      }
      addedTypeParameterLists[addedTypeParameterLists.length - 1] = typeParameters.toArray(new TypeParameter[0]);
      return new PointerType(oldType, false, addedNames, addedTypeParameterLists,
                             ParseInfo.combine(oldType.getParseInfo(), (ParseInfo) args[1], qname.getParseInfo(), typeParameters.getParseInfo()));
    }
    throw badTypeList();
  }

}