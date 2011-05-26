package compiler.language.parser.rules.type;

import static compiler.language.parser.ParseType.DOT;
import static compiler.language.parser.ParseType.HASH;
import static compiler.language.parser.ParseType.POINTER_TYPE_NO_TRAILING_PARAMS_NOT_QNAME;
import static compiler.language.parser.ParseType.POINTER_TYPE_TRAILING_PARAMS;
import static compiler.language.parser.ParseType.QNAME;
import parser.ParseException;
import parser.Production;
import parser.Rule;

import compiler.language.ast.ParseInfo;
import compiler.language.ast.misc.QNameAST;
import compiler.language.ast.terminal.NameAST;
import compiler.language.ast.type.PointerTypeAST;
import compiler.language.ast.type.TypeParameterAST;
import compiler.language.parser.ParseType;

/*
 * Created on 9 Aug 2010
 */

/**
 * @author Anthony Bryant
 */
public final class PointerTypeNoTrailingParamsNotQNameRule extends Rule<ParseType>
{
  private static final long serialVersionUID = 1L;

  private static final Production<ParseType> IMMUTABLE_PRODUCTION = new Production<ParseType>(HASH, QNAME);
  private static final Production<ParseType> TRAILING_PARAMS_QNAME_PRODUCTION = new Production<ParseType>(POINTER_TYPE_TRAILING_PARAMS, DOT, QNAME);

  @SuppressWarnings("unchecked")
  public PointerTypeNoTrailingParamsNotQNameRule()
  {
    super(POINTER_TYPE_NO_TRAILING_PARAMS_NOT_QNAME, IMMUTABLE_PRODUCTION, TRAILING_PARAMS_QNAME_PRODUCTION);
  }

  /**
   * {@inheritDoc}
   * @see parser.Rule#match(parser.Production, java.lang.Object[])
   */
  @Override
  public Object match(Production<ParseType> production, Object[] args) throws ParseException
  {
    if (IMMUTABLE_PRODUCTION.equals(production))
    {
      QNameAST qname = (QNameAST) args[1];
      NameAST[] names = qname.getNames();
      TypeParameterAST[][] typeParameterLists = new TypeParameterAST[names.length][];
      return new PointerTypeAST(true, names, typeParameterLists, ParseInfo.combine((ParseInfo) args[0], qname.getParseInfo()));
    }
    if (TRAILING_PARAMS_QNAME_PRODUCTION.equals(production))
    {
      PointerTypeAST oldType = (PointerTypeAST) args[0];
      QNameAST qname = (QNameAST) args[2];
      NameAST[] names = qname.getNames();
      TypeParameterAST[][] typeParameterLists = new TypeParameterAST[names.length][];
      return new PointerTypeAST(oldType, oldType.isImmutable(), names, typeParameterLists,
                             ParseInfo.combine(oldType.getParseInfo(), (ParseInfo) args[1], qname.getParseInfo()));
    }
    throw badTypeList();
  }

}
