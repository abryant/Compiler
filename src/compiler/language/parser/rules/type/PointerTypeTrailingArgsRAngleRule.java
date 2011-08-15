package compiler.language.parser.rules.type;

import static compiler.language.parser.ParseType.DOT;
import static compiler.language.parser.ParseType.HASH;
import static compiler.language.parser.ParseType.LANGLE;
import static compiler.language.parser.ParseType.POINTER_TYPE_TRAILING_ARGS;
import static compiler.language.parser.ParseType.POINTER_TYPE_TRAILING_ARGS_RANGLE;
import static compiler.language.parser.ParseType.QNAME;
import static compiler.language.parser.ParseType.RANGLE;
import static compiler.language.parser.ParseType.TYPE_ARGUMENT_LIST_DOUBLE_RANGLE;
import parser.ParseException;
import parser.Production;
import parser.Rule;

import compiler.language.LexicalPhrase;
import compiler.language.ast.misc.QNameAST;
import compiler.language.ast.terminal.NameAST;
import compiler.language.ast.type.PointerTypeAST;
import compiler.language.ast.type.TypeArgumentAST;
import compiler.language.parser.ParseContainer;
import compiler.language.parser.ParseList;
import compiler.language.parser.ParseType;

/*
 * Created on 9 Aug 2010
 */

/**
 * @author Anthony Bryant
 */
public final class PointerTypeTrailingArgsRAngleRule extends Rule<ParseType>
{
  private static final long serialVersionUID = 1L;

  private static final Production<ParseType> MUTABLE_PRODUCTION         = new Production<ParseType>(      QNAME, LANGLE, TYPE_ARGUMENT_LIST_DOUBLE_RANGLE);
  private static final Production<ParseType> IMMUTABLE_PRODUCTION       = new Production<ParseType>(HASH, QNAME, LANGLE, TYPE_ARGUMENT_LIST_DOUBLE_RANGLE);
  private static final Production<ParseType> TRAILING_PARAMS_PRODUCTION = new Production<ParseType>(POINTER_TYPE_TRAILING_ARGS, DOT, QNAME, LANGLE, TYPE_ARGUMENT_LIST_DOUBLE_RANGLE);
  private static final Production<ParseType> RANGLE_PRODUCTION          = new Production<ParseType>(POINTER_TYPE_TRAILING_ARGS, RANGLE);

  @SuppressWarnings("unchecked")
  public PointerTypeTrailingArgsRAngleRule()
  {
    super(POINTER_TYPE_TRAILING_ARGS_RANGLE, MUTABLE_PRODUCTION, IMMUTABLE_PRODUCTION, TRAILING_PARAMS_PRODUCTION, RANGLE_PRODUCTION);
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
      ParseContainer<ParseContainer<ParseList<TypeArgumentAST>>> typeArgumentss = (ParseContainer<ParseContainer<ParseList<TypeArgumentAST>>>) args[2];
      NameAST[] names = qname.getNames();
      TypeArgumentAST[][] typeArgumentLists = new TypeArgumentAST[names.length][];
      typeArgumentLists[typeArgumentLists.length - 1] = typeArgumentss.getItem().getItem().toArray(new TypeArgumentAST[0]);
      PointerTypeAST type = new PointerTypeAST(false, names, typeArgumentLists,
                                               LexicalPhrase.combine(qname.getLexicalPhrase(), (LexicalPhrase) args[1], typeArgumentss.getItem().getLexicalPhrase()));
      return new ParseContainer<PointerTypeAST>(type, LexicalPhrase.combine(qname.getLexicalPhrase(), (LexicalPhrase) args[1], typeArgumentss.getLexicalPhrase()));
    }
    if (IMMUTABLE_PRODUCTION.equals(production))
    {
      QNameAST qname = (QNameAST) args[1];
      @SuppressWarnings("unchecked")
      ParseContainer<ParseContainer<ParseList<TypeArgumentAST>>> typeArguments = (ParseContainer<ParseContainer<ParseList<TypeArgumentAST>>>) args[3];
      NameAST[] names = qname.getNames();
      TypeArgumentAST[][] typeArgumentLists = new TypeArgumentAST[names.length][];
      typeArgumentLists[typeArgumentLists.length - 1] = typeArguments.getItem().getItem().toArray(new TypeArgumentAST[0]);
      PointerTypeAST type = new PointerTypeAST(true, names, typeArgumentLists,
                                               LexicalPhrase.combine((LexicalPhrase) args[0], qname.getLexicalPhrase(), (LexicalPhrase) args[2], typeArguments.getItem().getLexicalPhrase()));
      return new ParseContainer<PointerTypeAST>(type, LexicalPhrase.combine((LexicalPhrase) args[0], qname.getLexicalPhrase(), (LexicalPhrase) args[2], typeArguments.getLexicalPhrase()));
    }
    if (TRAILING_PARAMS_PRODUCTION.equals(production))
    {
      PointerTypeAST oldType = (PointerTypeAST) args[0];
      QNameAST qname = (QNameAST) args[2];
      @SuppressWarnings("unchecked")
      ParseContainer<ParseContainer<ParseList<TypeArgumentAST>>> typeArguments = (ParseContainer<ParseContainer<ParseList<TypeArgumentAST>>>) args[4];
      NameAST[] names = qname.getNames();
      TypeArgumentAST[][] typeArgumentLists = new TypeArgumentAST[names.length][];
      typeArgumentLists[typeArgumentLists.length - 1] = typeArguments.getItem().getItem().toArray(new TypeArgumentAST[0]);
      PointerTypeAST type = new PointerTypeAST(oldType, oldType.isImmutable(), names, typeArgumentLists,
                                               LexicalPhrase.combine(oldType.getLexicalPhrase(), (LexicalPhrase) args[1], qname.getLexicalPhrase(), (LexicalPhrase) args[3],
                                                                 typeArguments.getItem().getLexicalPhrase()));
      return new ParseContainer<PointerTypeAST>(type, LexicalPhrase.combine(oldType.getLexicalPhrase(), (LexicalPhrase) args[1], qname.getLexicalPhrase(), (LexicalPhrase) args[3],
                                                                        typeArguments.getItem().getLexicalPhrase()));
    }
    if (RANGLE_PRODUCTION.equals(production))
    {
      PointerTypeAST type = (PointerTypeAST) args[0];
      return new ParseContainer<PointerTypeAST>(type, LexicalPhrase.combine(type.getLexicalPhrase(), (LexicalPhrase) args[1]));
    }
    throw badTypeList();
  }

}
