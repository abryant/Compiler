package compiler.language.parser.rules.type;

import static compiler.language.parser.ParseType.DOT;
import static compiler.language.parser.ParseType.HASH;
import static compiler.language.parser.ParseType.LANGLE;
import static compiler.language.parser.ParseType.POINTER_TYPE_TRAILING_PARAMS;
import static compiler.language.parser.ParseType.POINTER_TYPE_TRAILING_PARAMS_RANGLE;
import static compiler.language.parser.ParseType.QNAME;
import static compiler.language.parser.ParseType.RANGLE;
import static compiler.language.parser.ParseType.TYPE_PARAMETER_LIST_DOUBLE_RANGLE;
import parser.ParseException;
import parser.Production;
import parser.Rule;

import compiler.language.ast.ParseInfo;
import compiler.language.ast.misc.QNameAST;
import compiler.language.ast.terminal.NameAST;
import compiler.language.ast.type.PointerTypeAST;
import compiler.language.ast.type.TypeParameterAST;
import compiler.language.parser.ParseContainer;
import compiler.language.parser.ParseList;
import compiler.language.parser.ParseType;

/*
 * Created on 9 Aug 2010
 */

/**
 * @author Anthony Bryant
 */
public final class PointerTypeTrailingParamsRAngleRule extends Rule<ParseType>
{
  private static final long serialVersionUID = 1L;

  private static final Production<ParseType> MUTABLE_PRODUCTION         = new Production<ParseType>(      QNAME, LANGLE, TYPE_PARAMETER_LIST_DOUBLE_RANGLE);
  private static final Production<ParseType> IMMUTABLE_PRODUCTION       = new Production<ParseType>(HASH, QNAME, LANGLE, TYPE_PARAMETER_LIST_DOUBLE_RANGLE);
  private static final Production<ParseType> TRAILING_PARAMS_PRODUCTION = new Production<ParseType>(POINTER_TYPE_TRAILING_PARAMS, DOT, QNAME, LANGLE, TYPE_PARAMETER_LIST_DOUBLE_RANGLE);
  private static final Production<ParseType> RANGLE_PRODUCTION          = new Production<ParseType>(POINTER_TYPE_TRAILING_PARAMS, RANGLE);

  @SuppressWarnings("unchecked")
  public PointerTypeTrailingParamsRAngleRule()
  {
    super(POINTER_TYPE_TRAILING_PARAMS_RANGLE, MUTABLE_PRODUCTION, IMMUTABLE_PRODUCTION, TRAILING_PARAMS_PRODUCTION, RANGLE_PRODUCTION);
  }

  /**
   * {@inheritDoc}
   * @see parser.Rule#match(parser.Production, java.lang.Object[])
   */
  @Override
  public Object match(Production<ParseType> production, Object[] args) throws ParseException
  {
    if (MUTABLE_PRODUCTION.equals(production))
    {
      QNameAST qname = (QNameAST) args[0];
      @SuppressWarnings("unchecked")
      ParseContainer<ParseContainer<ParseList<TypeParameterAST>>> typeParameters = (ParseContainer<ParseContainer<ParseList<TypeParameterAST>>>) args[2];
      NameAST[] names = qname.getNames();
      TypeParameterAST[][] typeParameterLists = new TypeParameterAST[names.length][];
      typeParameterLists[typeParameterLists.length - 1] = typeParameters.getItem().getItem().toArray(new TypeParameterAST[0]);
      PointerTypeAST type = new PointerTypeAST(false, names, typeParameterLists,
                                         ParseInfo.combine(qname.getParseInfo(), (ParseInfo) args[1], typeParameters.getItem().getParseInfo()));
      return new ParseContainer<PointerTypeAST>(type, ParseInfo.combine(qname.getParseInfo(), (ParseInfo) args[1], typeParameters.getParseInfo()));
    }
    if (IMMUTABLE_PRODUCTION.equals(production))
    {
      QNameAST qname = (QNameAST) args[1];
      @SuppressWarnings("unchecked")
      ParseContainer<ParseContainer<ParseList<TypeParameterAST>>> typeParameters = (ParseContainer<ParseContainer<ParseList<TypeParameterAST>>>) args[3];
      NameAST[] names = qname.getNames();
      TypeParameterAST[][] typeParameterLists = new TypeParameterAST[names.length][];
      typeParameterLists[typeParameterLists.length - 1] = typeParameters.getItem().getItem().toArray(new TypeParameterAST[0]);
      PointerTypeAST type = new PointerTypeAST(true, names, typeParameterLists,
                                         ParseInfo.combine((ParseInfo) args[0], qname.getParseInfo(), (ParseInfo) args[2], typeParameters.getItem().getParseInfo()));
      return new ParseContainer<PointerTypeAST>(type, ParseInfo.combine((ParseInfo) args[0], qname.getParseInfo(), (ParseInfo) args[2], typeParameters.getParseInfo()));
    }
    if (TRAILING_PARAMS_PRODUCTION.equals(production))
    {
      PointerTypeAST oldType = (PointerTypeAST) args[0];
      QNameAST qname = (QNameAST) args[2];
      @SuppressWarnings("unchecked")
      ParseContainer<ParseContainer<ParseList<TypeParameterAST>>> typeParameters = (ParseContainer<ParseContainer<ParseList<TypeParameterAST>>>) args[4];
      NameAST[] names = qname.getNames();
      TypeParameterAST[][] typeParameterLists = new TypeParameterAST[names.length][];
      typeParameterLists[typeParameterLists.length - 1] = typeParameters.getItem().getItem().toArray(new TypeParameterAST[0]);
      PointerTypeAST type = new PointerTypeAST(oldType, oldType.isImmutable(), names, typeParameterLists,
                                         ParseInfo.combine(oldType.getParseInfo(), (ParseInfo) args[1], qname.getParseInfo(), (ParseInfo) args[3],
                                                           typeParameters.getItem().getParseInfo()));
      return new ParseContainer<PointerTypeAST>(type, ParseInfo.combine(oldType.getParseInfo(), (ParseInfo) args[1], qname.getParseInfo(), (ParseInfo) args[3],
                                                                     typeParameters.getItem().getParseInfo()));
    }
    if (RANGLE_PRODUCTION.equals(production))
    {
      PointerTypeAST type = (PointerTypeAST) args[0];
      return new ParseContainer<PointerTypeAST>(type, ParseInfo.combine(type.getParseInfo(), (ParseInfo) args[1]));
    }
    throw badTypeList();
  }

}
