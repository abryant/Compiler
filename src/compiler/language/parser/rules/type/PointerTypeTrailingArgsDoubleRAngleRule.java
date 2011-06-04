package compiler.language.parser.rules.type;

import static compiler.language.parser.ParseType.DOT;
import static compiler.language.parser.ParseType.DOUBLE_RANGLE;
import static compiler.language.parser.ParseType.HASH;
import static compiler.language.parser.ParseType.LANGLE;
import static compiler.language.parser.ParseType.POINTER_TYPE_TRAILING_ARGS;
import static compiler.language.parser.ParseType.POINTER_TYPE_TRAILING_ARGS_DOUBLE_RANGLE;
import static compiler.language.parser.ParseType.QNAME;
import static compiler.language.parser.ParseType.TYPE_ARGUMENT_LIST_TRIPLE_RANGLE;
import parser.ParseException;
import parser.Production;
import parser.Rule;

import compiler.language.ast.ParseInfo;
import compiler.language.ast.misc.QNameAST;
import compiler.language.ast.terminal.NameAST;
import compiler.language.ast.type.PointerTypeAST;
import compiler.language.ast.type.TypeArgumentAST;
import compiler.language.parser.ParseContainer;
import compiler.language.parser.ParseList;
import compiler.language.parser.ParseType;
import compiler.language.parser.ParseUtil;

/*
 * Created on 20 Aug 2010
 */

/**
 * @author Anthony Bryant
 */
public final class PointerTypeTrailingArgsDoubleRAngleRule extends Rule<ParseType>
{
  private static final long serialVersionUID = 1L;

  private static final Production<ParseType> MUTABLE_PRODUCTION = new Production<ParseType>(QNAME, LANGLE, TYPE_ARGUMENT_LIST_TRIPLE_RANGLE);
  private static final Production<ParseType> IMMUTABLE_PRODUCTION = new Production<ParseType>(HASH, QNAME, LANGLE, TYPE_ARGUMENT_LIST_TRIPLE_RANGLE);
  private static final Production<ParseType> TRAILING_ARGS_PRODUCTION = new Production<ParseType>(POINTER_TYPE_TRAILING_ARGS, DOT, QNAME, LANGLE, TYPE_ARGUMENT_LIST_TRIPLE_RANGLE);
  private static final Production<ParseType> DOUBLE_RANGLE_PRODUCTION = new Production<ParseType>(POINTER_TYPE_TRAILING_ARGS, DOUBLE_RANGLE);

  @SuppressWarnings("unchecked")
  public PointerTypeTrailingArgsDoubleRAngleRule()
  {
    super(POINTER_TYPE_TRAILING_ARGS_DOUBLE_RANGLE, MUTABLE_PRODUCTION, IMMUTABLE_PRODUCTION, TRAILING_ARGS_PRODUCTION, DOUBLE_RANGLE_PRODUCTION);
  }

  /**
   * {@inheritDoc}
   * @see parser.Rule#match(parser.Production, java.lang.Object[])
   */
  @Override
  public Object match(Production<ParseType> production, Object[] args) throws ParseException
  {
    if (DOUBLE_RANGLE_PRODUCTION.equals(production))
    {
      PointerTypeAST type = (PointerTypeAST) args[0];
      ParseInfo doubleRAngleInfo = (ParseInfo) args[1];
      ParseInfo firstRAngleInfo = ParseUtil.splitDoubleRAngle(doubleRAngleInfo);
      ParseContainer<PointerTypeAST> firstContainer = new ParseContainer<PointerTypeAST>(type, ParseInfo.combine(type.getParseInfo(), firstRAngleInfo));
      return new ParseContainer<ParseContainer<PointerTypeAST>>(firstContainer, ParseInfo.combine(type.getParseInfo(), doubleRAngleInfo));
    }

    // find the index of the TYPE_ARGUMENT_LIST_TRIPLE_RANGLE based on which production this is
    int argumentListIndex;
    if (MUTABLE_PRODUCTION.equals(production))
    {
      argumentListIndex = 2;
    }
    else if (IMMUTABLE_PRODUCTION.equals(production))
    {
      argumentListIndex = 3;
    }
    else if (TRAILING_ARGS_PRODUCTION.equals(production))
    {
      argumentListIndex = 4;
    }
    else
    {
      throw badTypeList();
    }

    // find the list of type arguments
    @SuppressWarnings("unchecked")
    ParseContainer<ParseContainer<ParseContainer<ParseList<TypeArgumentAST>>>> firstArgsContainer =
      (ParseContainer<ParseContainer<ParseContainer<ParseList<TypeArgumentAST>>>>) args[argumentListIndex];
    ParseContainer<ParseContainer<ParseList<TypeArgumentAST>>> secondArgsContainer = firstArgsContainer.getItem();
    ParseContainer<ParseList<TypeArgumentAST>> thirdArgsContainer = secondArgsContainer.getItem();
    ParseList<TypeArgumentAST> arguments = thirdArgsContainer.getItem();

    // create the PointerTypeAST to encapsulate, and the ParseInfo of everything but the trailing type argument list
    PointerTypeAST type;
    ParseInfo startInfo;
    if (MUTABLE_PRODUCTION.equals(production))
    {
      QNameAST qname = (QNameAST) args[0];

      NameAST[] names = qname.getNames();
      TypeArgumentAST[][] typeArgumentLists = new TypeArgumentAST[names.length][];
      typeArgumentLists[typeArgumentLists.length - 1] = arguments.toArray(new TypeArgumentAST[arguments.size()]);

      startInfo = ParseInfo.combine(qname.getParseInfo(), (ParseInfo) args[1]);
      type = new PointerTypeAST(false, names, typeArgumentLists,
                                ParseInfo.combine(startInfo, thirdArgsContainer.getParseInfo()));
    }
    else if (IMMUTABLE_PRODUCTION.equals(production))
    {
      QNameAST qname = (QNameAST) args[1];

      NameAST[] names = qname.getNames();
      TypeArgumentAST[][] typeArgumentLists = new TypeArgumentAST[names.length][];
      typeArgumentLists[typeArgumentLists.length - 1] = arguments.toArray(new TypeArgumentAST[arguments.size()]);

      startInfo = ParseInfo.combine((ParseInfo) args[0], qname.getParseInfo(), (ParseInfo) args[2]);
      type = new PointerTypeAST(true, names, typeArgumentLists,
                                ParseInfo.combine(startInfo, thirdArgsContainer.getParseInfo()));
    }
    else if (TRAILING_ARGS_PRODUCTION.equals(production))
    {
      PointerTypeAST baseType = (PointerTypeAST) args[0];
      QNameAST qname = (QNameAST) args[2];

      NameAST[] names = qname.getNames();
      TypeArgumentAST[][] typeArgumentLists = new TypeArgumentAST[names.length][];
      typeArgumentLists[typeArgumentLists.length - 1] = arguments.toArray(new TypeArgumentAST[arguments.size()]);

      startInfo = ParseInfo.combine(baseType.getParseInfo(), (ParseInfo) args[1], qname.getParseInfo(), (ParseInfo) args[3]);
      type = new PointerTypeAST(baseType, baseType.isImmutable(), names, typeArgumentLists,
                                ParseInfo.combine(startInfo, thirdArgsContainer.getParseInfo()));
    }
    else
    {
      throw badTypeList();
    }

    // encapsulate the result in two ParseContainers
    ParseContainer<PointerTypeAST> embeddedContainer = new ParseContainer<PointerTypeAST>(type,
                 ParseInfo.combine(startInfo, secondArgsContainer.getParseInfo()));
    return new ParseContainer<ParseContainer<PointerTypeAST>>(embeddedContainer,
                 ParseInfo.combine(startInfo, firstArgsContainer.getParseInfo()));
  }

}
