package compiler.language.parser.rules.type;

import static compiler.language.parser.ParseType.DOT;
import static compiler.language.parser.ParseType.HASH;
import static compiler.language.parser.ParseType.POINTER_TYPE_TRAILING_PARAMS;
import static compiler.language.parser.ParseType.QNAME;
import static compiler.language.parser.ParseType.TYPE_PARAMETERS;

import compiler.language.ast.ParseInfo;
import compiler.language.ast.ParseList;
import compiler.language.ast.misc.QName;
import compiler.language.ast.terminal.Name;
import compiler.language.ast.type.PointerType;
import compiler.language.ast.type.TypeParameter;
import compiler.language.parser.ParseType;
import compiler.parser.ParseException;
import compiler.parser.Production;
import compiler.parser.Rule;

/*
 * Created on 9 Aug 2010
 */

/**
 * @author Anthony Bryant
 */
public final class PointerTypeTrailingParamsRule extends Rule<ParseType>
{
  private static final long serialVersionUID = 1L;

  private static final Production<ParseType> START_PRODUCTION = new Production<ParseType>(QNAME, TYPE_PARAMETERS);
  private static final Production<ParseType> IMMUTABLE_START_PRODUCTION = new Production<ParseType>(HASH, QNAME, TYPE_PARAMETERS);
  private static final Production<ParseType> CONTINUATION_PRODUCTION = new Production<ParseType>(POINTER_TYPE_TRAILING_PARAMS, DOT, QNAME, TYPE_PARAMETERS);

  @SuppressWarnings("unchecked")
  public PointerTypeTrailingParamsRule()
  {
    super(POINTER_TYPE_TRAILING_PARAMS, START_PRODUCTION, IMMUTABLE_START_PRODUCTION, CONTINUATION_PRODUCTION);
  }

  /**
   * {@inheritDoc}
   * @see compiler.parser.Rule#match(compiler.parser.Production, java.lang.Object[])
   */
  @Override
  public Object match(Production<ParseType> production, Object[] args) throws ParseException
  {
    if (START_PRODUCTION.equals(production))
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
    if (IMMUTABLE_START_PRODUCTION.equals(production))
    {
      QName qname = (QName) args[1];
      @SuppressWarnings("unchecked")
      ParseList<TypeParameter> typeParameters = (ParseList<TypeParameter>) args[2];
      Name[] names = qname.getNames();
      TypeParameter[][] typeParameterLists = new TypeParameter[names.length][];
      for (int i = 0; i < typeParameterLists.length - 1; i++)
      {
        typeParameterLists[i] = new TypeParameter[0];
      }
      typeParameterLists[typeParameterLists.length - 1] = typeParameters.toArray(new TypeParameter[0]);
      return new PointerType(true, names, typeParameterLists, ParseInfo.combine((ParseInfo) args[0], qname.getParseInfo(), typeParameters.getParseInfo()));
    }
    if (CONTINUATION_PRODUCTION.equals(production))
    {
      PointerType oldType = (PointerType) args[0];
      QName qname = (QName) args[2];
      @SuppressWarnings("unchecked")
      ParseList<TypeParameter> typeParameters = (ParseList<TypeParameter>) args[3];
      Name[] names = qname.getNames();
      TypeParameter[][] typeParameterLists = new TypeParameter[names.length][];
      for (int i = 0; i < typeParameterLists.length - 1; i++)
      {
        typeParameterLists[i] = new TypeParameter[0];
      }
      typeParameterLists[typeParameterLists.length - 1] = typeParameters.toArray(new TypeParameter[0]);
      return new PointerType(oldType, oldType.isImmutable(), names, typeParameterLists,
                             ParseInfo.combine(oldType.getParseInfo(), (ParseInfo) args[1], qname.getParseInfo(), typeParameters.getParseInfo()));
    }
    throw badTypeList();
  }

}
