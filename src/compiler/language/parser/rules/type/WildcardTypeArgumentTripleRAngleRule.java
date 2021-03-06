package compiler.language.parser.rules.type;

import static compiler.language.parser.ParseType.EXTENDS_KEYWORD;
import static compiler.language.parser.ParseType.QUESTION_MARK;
import static compiler.language.parser.ParseType.SUPER_KEYWORD;
import static compiler.language.parser.ParseType.TRIPLE_RANGLE;
import static compiler.language.parser.ParseType.TYPE_BOUND_LIST;
import static compiler.language.parser.ParseType.TYPE_BOUND_LIST_TRIPLE_RANGLE;
import static compiler.language.parser.ParseType.WILDCARD_TYPE_ARGUMENT_TRIPLE_RANGLE;
import parser.ParseException;
import parser.Production;
import parser.Rule;

import compiler.language.LexicalPhrase;
import compiler.language.ast.type.PointerTypeAST;
import compiler.language.ast.type.TypeArgumentAST;
import compiler.language.ast.type.WildcardTypeArgumentAST;
import compiler.language.parser.ParseContainer;
import compiler.language.parser.ParseList;
import compiler.language.parser.ParseType;
import compiler.language.parser.ParseUtil;

/*
 * Created on 21 Aug 2010
 */

/**
 * @author Anthony Bryant
 */
public final class WildcardTypeArgumentTripleRAngleRule extends Rule<ParseType>
{
  private static final long serialVersionUID = 1L;

  private static final Production<ParseType> PRODUCTION               = new Production<ParseType>(QUESTION_MARK, TRIPLE_RANGLE);
  private static final Production<ParseType> EXTENDS_PRODUCTION       = new Production<ParseType>(QUESTION_MARK, EXTENDS_KEYWORD, TYPE_BOUND_LIST_TRIPLE_RANGLE);
  private static final Production<ParseType> SUPER_PRODUCTION         = new Production<ParseType>(QUESTION_MARK, SUPER_KEYWORD,   TYPE_BOUND_LIST_TRIPLE_RANGLE);
  private static final Production<ParseType> EXTENDS_SUPER_PRODUCTION = new Production<ParseType>(QUESTION_MARK, EXTENDS_KEYWORD, TYPE_BOUND_LIST, SUPER_KEYWORD,   TYPE_BOUND_LIST_TRIPLE_RANGLE);
  private static final Production<ParseType> SUPER_EXTENDS_PRODUCTION = new Production<ParseType>(QUESTION_MARK, SUPER_KEYWORD,   TYPE_BOUND_LIST, EXTENDS_KEYWORD, TYPE_BOUND_LIST_TRIPLE_RANGLE);

  @SuppressWarnings("unchecked")
  public WildcardTypeArgumentTripleRAngleRule()
  {
    super(WILDCARD_TYPE_ARGUMENT_TRIPLE_RANGLE, PRODUCTION, EXTENDS_PRODUCTION, SUPER_PRODUCTION, EXTENDS_SUPER_PRODUCTION, SUPER_EXTENDS_PRODUCTION);
  }

  /**
   * {@inheritDoc}
   * @see parser.Rule#match(parser.Production, java.lang.Object[])
   */
  @Override
  public Object match(Production<ParseType> production, Object[] args) throws ParseException
  {
    if (PRODUCTION.equals(production))
    {
      TypeArgumentAST argument = new WildcardTypeArgumentAST(new PointerTypeAST[0], new PointerTypeAST[0], (LexicalPhrase) args[0]);
      LexicalPhrase tripleRAnglePhrase = (LexicalPhrase) args[1];
      LexicalPhrase firstRAnglePhrase = ParseUtil.splitTripleRAngleFirst(tripleRAnglePhrase);
      LexicalPhrase firstTwoRAnglesPhrase = ParseUtil.splitTripleRAngleFirstTwo(tripleRAnglePhrase);
      ParseContainer<TypeArgumentAST> firstContainer = new ParseContainer<TypeArgumentAST>(argument,
                   LexicalPhrase.combine((LexicalPhrase) args[0], firstRAnglePhrase));
      ParseContainer<ParseContainer<TypeArgumentAST>> secondContainer = new ParseContainer<ParseContainer<TypeArgumentAST>>(firstContainer,
                   LexicalPhrase.combine((LexicalPhrase) args[0], firstTwoRAnglesPhrase));
      return new ParseContainer<ParseContainer<ParseContainer<TypeArgumentAST>>>(secondContainer,
                   LexicalPhrase.combine((LexicalPhrase) args[0], tripleRAnglePhrase));
    }
    if (EXTENDS_PRODUCTION.equals(production))
    {
      @SuppressWarnings("unchecked")
      ParseContainer<ParseContainer<ParseContainer<ParseList<PointerTypeAST>>>> oldContainer = (ParseContainer<ParseContainer<ParseContainer<ParseList<PointerTypeAST>>>>) args[2];
      ParseList<PointerTypeAST> superTypes = oldContainer.getItem().getItem().getItem();
      TypeArgumentAST typeArgument = new WildcardTypeArgumentAST(superTypes.toArray(new PointerTypeAST[0]), new PointerTypeAST[0],
                   LexicalPhrase.combine((LexicalPhrase) args[0], (LexicalPhrase) args[1], superTypes.getLexicalPhrase()));
      ParseContainer<TypeArgumentAST> firstContainer = new ParseContainer<TypeArgumentAST>(typeArgument,
                   LexicalPhrase.combine((LexicalPhrase) args[0], (LexicalPhrase) args[1], oldContainer.getItem().getItem().getLexicalPhrase()));
      ParseContainer<ParseContainer<TypeArgumentAST>> secondContainer = new ParseContainer<ParseContainer<TypeArgumentAST>>(firstContainer,
                   LexicalPhrase.combine((LexicalPhrase) args[0], (LexicalPhrase) args[1], oldContainer.getItem().getLexicalPhrase()));
      return new ParseContainer<ParseContainer<ParseContainer<TypeArgumentAST>>>(secondContainer,
                   LexicalPhrase.combine((LexicalPhrase) args[0], (LexicalPhrase) args[1], oldContainer.getLexicalPhrase()));
    }
    if (SUPER_PRODUCTION.equals(production))
    {
      @SuppressWarnings("unchecked")
      ParseContainer<ParseContainer<ParseContainer<ParseList<PointerTypeAST>>>> oldContainer = (ParseContainer<ParseContainer<ParseContainer<ParseList<PointerTypeAST>>>>) args[2];
      ParseList<PointerTypeAST> subTypes = oldContainer.getItem().getItem().getItem();
      TypeArgumentAST typeArgument = new WildcardTypeArgumentAST(new PointerTypeAST[0], subTypes.toArray(new PointerTypeAST[0]),
                   LexicalPhrase.combine((LexicalPhrase) args[0], (LexicalPhrase) args[1], subTypes.getLexicalPhrase()));
      ParseContainer<TypeArgumentAST> firstContainer = new ParseContainer<TypeArgumentAST>(typeArgument,
                   LexicalPhrase.combine((LexicalPhrase) args[0], (LexicalPhrase) args[1], oldContainer.getItem().getItem().getLexicalPhrase()));
      ParseContainer<ParseContainer<TypeArgumentAST>> secondContainer = new ParseContainer<ParseContainer<TypeArgumentAST>>(firstContainer,
                   LexicalPhrase.combine((LexicalPhrase) args[0], (LexicalPhrase) args[1], oldContainer.getItem().getLexicalPhrase()));
      return new ParseContainer<ParseContainer<ParseContainer<TypeArgumentAST>>>(secondContainer,
                   LexicalPhrase.combine((LexicalPhrase) args[0], (LexicalPhrase) args[1], oldContainer.getLexicalPhrase()));
    }
    if (EXTENDS_SUPER_PRODUCTION.equals(production))
    {
      @SuppressWarnings("unchecked")
      ParseList<PointerTypeAST> superTypes = (ParseList<PointerTypeAST>) args[2];
      @SuppressWarnings("unchecked")
      ParseContainer<ParseContainer<ParseContainer<ParseList<PointerTypeAST>>>> oldContainer = (ParseContainer<ParseContainer<ParseContainer<ParseList<PointerTypeAST>>>>) args[4];
      ParseList<PointerTypeAST> subTypes = oldContainer.getItem().getItem().getItem();
      TypeArgumentAST typeArgument = new WildcardTypeArgumentAST(superTypes.toArray(new PointerTypeAST[0]), subTypes.toArray(new PointerTypeAST[0]),
                   LexicalPhrase.combine((LexicalPhrase) args[0], (LexicalPhrase) args[1], superTypes.getLexicalPhrase(), (LexicalPhrase) args[3], subTypes.getLexicalPhrase()));
      ParseContainer<TypeArgumentAST> firstContainer = new ParseContainer<TypeArgumentAST>(typeArgument,
                   LexicalPhrase.combine((LexicalPhrase) args[0], (LexicalPhrase) args[1], superTypes.getLexicalPhrase(), (LexicalPhrase) args[3], oldContainer.getItem().getItem().getLexicalPhrase()));
      ParseContainer<ParseContainer<TypeArgumentAST>> secondContainer = new ParseContainer<ParseContainer<TypeArgumentAST>>(firstContainer,
                   LexicalPhrase.combine((LexicalPhrase) args[0], (LexicalPhrase) args[1], superTypes.getLexicalPhrase(), (LexicalPhrase) args[3], oldContainer.getItem().getLexicalPhrase()));
      return new ParseContainer<ParseContainer<ParseContainer<TypeArgumentAST>>>(secondContainer,
                   LexicalPhrase.combine((LexicalPhrase) args[0], (LexicalPhrase) args[1], superTypes.getLexicalPhrase(), (LexicalPhrase) args[3], oldContainer.getLexicalPhrase()));
    }
    if (SUPER_EXTENDS_PRODUCTION.equals(production))
    {
      @SuppressWarnings("unchecked")
      ParseList<PointerTypeAST> subTypes = (ParseList<PointerTypeAST>) args[2];
      @SuppressWarnings("unchecked")
      ParseContainer<ParseContainer<ParseContainer<ParseList<PointerTypeAST>>>> oldContainer = (ParseContainer<ParseContainer<ParseContainer<ParseList<PointerTypeAST>>>>) args[4];
      ParseList<PointerTypeAST> superTypes = oldContainer.getItem().getItem().getItem();
      TypeArgumentAST typeArgument = new WildcardTypeArgumentAST(superTypes.toArray(new PointerTypeAST[0]), subTypes.toArray(new PointerTypeAST[0]),
                   LexicalPhrase.combine((LexicalPhrase) args[0], (LexicalPhrase) args[1], subTypes.getLexicalPhrase(), (LexicalPhrase) args[3], superTypes.getLexicalPhrase()));
      ParseContainer<TypeArgumentAST> firstContainer = new ParseContainer<TypeArgumentAST>(typeArgument,
                   LexicalPhrase.combine((LexicalPhrase) args[0], (LexicalPhrase) args[1], subTypes.getLexicalPhrase(), (LexicalPhrase) args[3], oldContainer.getItem().getItem().getLexicalPhrase()));
      ParseContainer<ParseContainer<TypeArgumentAST>> secondContainer = new ParseContainer<ParseContainer<TypeArgumentAST>>(firstContainer,
                   LexicalPhrase.combine((LexicalPhrase) args[0], (LexicalPhrase) args[1], subTypes.getLexicalPhrase(), (LexicalPhrase) args[3], oldContainer.getItem().getLexicalPhrase()));
      return new ParseContainer<ParseContainer<ParseContainer<TypeArgumentAST>>>(secondContainer,
                   LexicalPhrase.combine((LexicalPhrase) args[0], (LexicalPhrase) args[1], subTypes.getLexicalPhrase(), (LexicalPhrase) args[3], oldContainer.getLexicalPhrase()));
    }
    throw badTypeList();
  }

}
