package compiler.language.parser.rules.type;

import static compiler.language.parser.ParseType.DOT;
import static compiler.language.parser.ParseType.HASH;
import static compiler.language.parser.ParseType.POINTER_TYPE_NO_TRAILING_ARGS_NOT_QNAME;
import static compiler.language.parser.ParseType.POINTER_TYPE_TRAILING_ARGS;
import static compiler.language.parser.ParseType.QNAME;
import parser.ParseException;
import parser.Production;
import parser.Rule;

import compiler.language.LexicalPhrase;
import compiler.language.QName;
import compiler.language.ast.type.PointerTypeAST;
import compiler.language.ast.type.TypeArgumentAST;
import compiler.language.parser.ParseType;

/*
 * Created on 9 Aug 2010
 */

/**
 * @author Anthony Bryant
 */
public final class PointerTypeNoTrailingArgsNotQNameRule extends Rule<ParseType>
{
  private static final long serialVersionUID = 1L;

  private static final Production<ParseType> IMMUTABLE_PRODUCTION = new Production<ParseType>(HASH, QNAME);
  private static final Production<ParseType> TRAILING_PARAMS_QNAME_PRODUCTION = new Production<ParseType>(POINTER_TYPE_TRAILING_ARGS, DOT, QNAME);

  @SuppressWarnings("unchecked")
  public PointerTypeNoTrailingArgsNotQNameRule()
  {
    super(POINTER_TYPE_NO_TRAILING_ARGS_NOT_QNAME, IMMUTABLE_PRODUCTION, TRAILING_PARAMS_QNAME_PRODUCTION);
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
      QName qname = (QName) args[1];
      TypeArgumentAST[][] typeArgumentLists = new TypeArgumentAST[qname.getLength()][];
      return new PointerTypeAST(true, qname, typeArgumentLists, LexicalPhrase.combine((LexicalPhrase) args[0], qname.getLexicalPhrase()));
    }
    if (TRAILING_PARAMS_QNAME_PRODUCTION.equals(production))
    {
      PointerTypeAST oldType = (PointerTypeAST) args[0];
      QName qname = (QName) args[2];
      TypeArgumentAST[][] typeArgumentLists = new TypeArgumentAST[qname.getLength()][];
      return new PointerTypeAST(oldType, oldType.isImmutable(), qname, typeArgumentLists,
                             LexicalPhrase.combine(oldType.getLexicalPhrase(), (LexicalPhrase) args[1], qname.getLexicalPhrase()));
    }
    throw badTypeList();
  }

}
