package compiler.language.parser.rules.type;

import static compiler.language.parser.ParseType.DOT;
import static compiler.language.parser.ParseType.HASH;
import static compiler.language.parser.ParseType.POINTER_TYPE_TRAILING_ARGS;
import static compiler.language.parser.ParseType.QNAME;
import static compiler.language.parser.ParseType.TYPE_ARGUMENTS;
import parser.ParseException;
import parser.Production;
import parser.Rule;

import compiler.language.LexicalPhrase;
import compiler.language.QName;
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
      QName qname = (QName) args[0];
      @SuppressWarnings("unchecked")
      ParseList<TypeArgumentAST> typeArguments = (ParseList<TypeArgumentAST>) args[1];
      TypeArgumentAST[][] typeArgumentLists = new TypeArgumentAST[qname.getLength()][];
      typeArgumentLists[typeArgumentLists.length - 1] = typeArguments.toArray(new TypeArgumentAST[typeArguments.size()]);
      return new PointerTypeAST(false, qname, typeArgumentLists, LexicalPhrase.combine(qname.getLexicalPhrase(), typeArguments.getLexicalPhrase()));
    }
    if (IMMUTABLE_START_PRODUCTION.equals(production))
    {
      QName qname = (QName) args[1];
      @SuppressWarnings("unchecked")
      ParseList<TypeArgumentAST> typeArguments = (ParseList<TypeArgumentAST>) args[2];
      TypeArgumentAST[][] typeArgumentLists = new TypeArgumentAST[qname.getLength()][];
      typeArgumentLists[typeArgumentLists.length - 1] = typeArguments.toArray(new TypeArgumentAST[typeArguments.size()]);
      return new PointerTypeAST(true, qname, typeArgumentLists, LexicalPhrase.combine((LexicalPhrase) args[0], qname.getLexicalPhrase(), typeArguments.getLexicalPhrase()));
    }
    if (CONTINUATION_PRODUCTION.equals(production))
    {
      PointerTypeAST oldType = (PointerTypeAST) args[0];
      QName qname = (QName) args[2];
      @SuppressWarnings("unchecked")
      ParseList<TypeArgumentAST> typeArguments = (ParseList<TypeArgumentAST>) args[3];
      TypeArgumentAST[][] typeArgumentLists = new TypeArgumentAST[qname.getLength()][];
      typeArgumentLists[typeArgumentLists.length - 1] = typeArguments.toArray(new TypeArgumentAST[typeArguments.size()]);
      return new PointerTypeAST(oldType, oldType.isImmutable(), qname, typeArgumentLists,
                             LexicalPhrase.combine(oldType.getLexicalPhrase(), (LexicalPhrase) args[1], qname.getLexicalPhrase(), typeArguments.getLexicalPhrase()));
    }
    throw badTypeList();
  }

}
