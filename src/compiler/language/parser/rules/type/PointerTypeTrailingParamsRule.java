package compiler.language.parser.rules.type;

import static compiler.language.parser.ParseType.DOT;
import static compiler.language.parser.ParseType.HASH;
import static compiler.language.parser.ParseType.POINTER_TYPE_TRAILING_PARAMS;
import static compiler.language.parser.ParseType.QNAME;
import static compiler.language.parser.ParseType.TYPE_PARAMETERS;
import parser.ParseException;
import parser.Production;
import parser.Rule;

import compiler.language.ast.ParseInfo;
import compiler.language.ast.misc.QNameAST;
import compiler.language.ast.terminal.NameAST;
import compiler.language.ast.type.PointerTypeAST;
import compiler.language.ast.type.TypeParameterAST;
import compiler.language.parser.ParseList;
import compiler.language.parser.ParseType;

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
   * @see parser.Rule#match(parser.Production, java.lang.Object[])
   */
  @Override
  public Object match(Production<ParseType> production, Object[] args) throws ParseException
  {
    if (START_PRODUCTION.equals(production))
    {
      QNameAST qname = (QNameAST) args[0];
      @SuppressWarnings("unchecked")
      ParseList<TypeParameterAST> typeParameters = (ParseList<TypeParameterAST>) args[1];
      NameAST[] names = qname.getNames();
      TypeParameterAST[][] typeParameterLists = new TypeParameterAST[names.length][];
      for (int i = 0; i < typeParameterLists.length - 1; i++)
      {
        typeParameterLists[i] = new TypeParameterAST[0];
      }
      typeParameterLists[typeParameterLists.length - 1] = typeParameters.toArray(new TypeParameterAST[0]);
      return new PointerTypeAST(false, names, typeParameterLists, ParseInfo.combine(qname.getParseInfo(), typeParameters.getParseInfo()));
    }
    if (IMMUTABLE_START_PRODUCTION.equals(production))
    {
      QNameAST qname = (QNameAST) args[1];
      @SuppressWarnings("unchecked")
      ParseList<TypeParameterAST> typeParameters = (ParseList<TypeParameterAST>) args[2];
      NameAST[] names = qname.getNames();
      TypeParameterAST[][] typeParameterLists = new TypeParameterAST[names.length][];
      for (int i = 0; i < typeParameterLists.length - 1; i++)
      {
        typeParameterLists[i] = new TypeParameterAST[0];
      }
      typeParameterLists[typeParameterLists.length - 1] = typeParameters.toArray(new TypeParameterAST[0]);
      return new PointerTypeAST(true, names, typeParameterLists, ParseInfo.combine((ParseInfo) args[0], qname.getParseInfo(), typeParameters.getParseInfo()));
    }
    if (CONTINUATION_PRODUCTION.equals(production))
    {
      PointerTypeAST oldType = (PointerTypeAST) args[0];
      QNameAST qname = (QNameAST) args[2];
      @SuppressWarnings("unchecked")
      ParseList<TypeParameterAST> typeParameters = (ParseList<TypeParameterAST>) args[3];
      NameAST[] names = qname.getNames();
      TypeParameterAST[][] typeParameterLists = new TypeParameterAST[names.length][];
      for (int i = 0; i < typeParameterLists.length - 1; i++)
      {
        typeParameterLists[i] = new TypeParameterAST[0];
      }
      typeParameterLists[typeParameterLists.length - 1] = typeParameters.toArray(new TypeParameterAST[0]);
      return new PointerTypeAST(oldType, oldType.isImmutable(), names, typeParameterLists,
                             ParseInfo.combine(oldType.getParseInfo(), (ParseInfo) args[1], qname.getParseInfo(), typeParameters.getParseInfo()));
    }
    throw badTypeList();
  }

}
