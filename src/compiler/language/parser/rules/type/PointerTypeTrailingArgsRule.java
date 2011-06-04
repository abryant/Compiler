package compiler.language.parser.rules.type;

import static compiler.language.parser.ParseType.DOT;
import static compiler.language.parser.ParseType.HASH;
import static compiler.language.parser.ParseType.POINTER_TYPE_TRAILING_ARGS;
import static compiler.language.parser.ParseType.QNAME;
import static compiler.language.parser.ParseType.TYPE_ARGUMENTS;
import parser.ParseException;
import parser.Production;
import parser.Rule;

import compiler.language.ast.ParseInfo;
import compiler.language.ast.misc.QNameAST;
import compiler.language.ast.terminal.NameAST;
import compiler.language.ast.type.PointerTypeAST;
import compiler.language.ast.type.TypeArgumentAST;
import compiler.language.parser.ParseList;
import compiler.language.parser.ParseType;

/*
 * Created on 9 Aug 2010
 */

/**
 * @author Anthony Bryant
 */
public final class PointerTypeTrailingArgsRule extends Rule<ParseType>
{
  private static final long serialVersionUID = 1L;

  private static final Production<ParseType> START_PRODUCTION = new Production<ParseType>(QNAME, TYPE_ARGUMENTS);
  private static final Production<ParseType> IMMUTABLE_START_PRODUCTION = new Production<ParseType>(HASH, QNAME, TYPE_ARGUMENTS);
  private static final Production<ParseType> CONTINUATION_PRODUCTION = new Production<ParseType>(POINTER_TYPE_TRAILING_ARGS, DOT, QNAME, TYPE_ARGUMENTS);

  @SuppressWarnings("unchecked")
  public PointerTypeTrailingArgsRule()
  {
    super(POINTER_TYPE_TRAILING_ARGS, START_PRODUCTION, IMMUTABLE_START_PRODUCTION, CONTINUATION_PRODUCTION);
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
      ParseList<TypeArgumentAST> typeArguments = (ParseList<TypeArgumentAST>) args[1];
      NameAST[] names = qname.getNames();
      TypeArgumentAST[][] typeArgumentLists = new TypeArgumentAST[names.length][];
      typeArgumentLists[typeArgumentLists.length - 1] = typeArguments.toArray(new TypeArgumentAST[typeArguments.size()]);
      return new PointerTypeAST(false, names, typeArgumentLists, ParseInfo.combine(qname.getParseInfo(), typeArguments.getParseInfo()));
    }
    if (IMMUTABLE_START_PRODUCTION.equals(production))
    {
      QNameAST qname = (QNameAST) args[1];
      @SuppressWarnings("unchecked")
      ParseList<TypeArgumentAST> typeArguments = (ParseList<TypeArgumentAST>) args[2];
      NameAST[] names = qname.getNames();
      TypeArgumentAST[][] typeArgumentLists = new TypeArgumentAST[names.length][];
      typeArgumentLists[typeArgumentLists.length - 1] = typeArguments.toArray(new TypeArgumentAST[typeArguments.size()]);
      return new PointerTypeAST(true, names, typeArgumentLists, ParseInfo.combine((ParseInfo) args[0], qname.getParseInfo(), typeArguments.getParseInfo()));
    }
    if (CONTINUATION_PRODUCTION.equals(production))
    {
      PointerTypeAST oldType = (PointerTypeAST) args[0];
      QNameAST qname = (QNameAST) args[2];
      @SuppressWarnings("unchecked")
      ParseList<TypeArgumentAST> typeArguments = (ParseList<TypeArgumentAST>) args[3];
      NameAST[] names = qname.getNames();
      TypeArgumentAST[][] typeArgumentLists = new TypeArgumentAST[names.length][];
      typeArgumentLists[typeArgumentLists.length - 1] = typeArguments.toArray(new TypeArgumentAST[typeArguments.size()]);
      return new PointerTypeAST(oldType, oldType.isImmutable(), names, typeArgumentLists,
                             ParseInfo.combine(oldType.getParseInfo(), (ParseInfo) args[1], qname.getParseInfo(), typeArguments.getParseInfo()));
    }
    throw badTypeList();
  }

}
