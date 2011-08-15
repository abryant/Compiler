package compiler.language.parser.rules.type;

import static compiler.language.parser.ParseType.AMPERSAND;
import static compiler.language.parser.ParseType.POINTER_TYPE_TRIPLE_RANGLE;
import static compiler.language.parser.ParseType.TYPE_BOUND_LIST;
import static compiler.language.parser.ParseType.TYPE_BOUND_LIST_TRIPLE_RANGLE;
import parser.ParseException;
import parser.Production;
import parser.Rule;

import compiler.language.LexicalPhrase;
import compiler.language.ast.type.PointerTypeAST;
import compiler.language.parser.ParseContainer;
import compiler.language.parser.ParseList;
import compiler.language.parser.ParseType;

/*
 * Created on 30 Aug 2010
 */

/**
 * @author Anthony Bryant
 */
public final class TypeBoundListTripleRAngleRule extends Rule<ParseType>
{
  private static final long serialVersionUID = 1L;

  private static final Production<ParseType> PRODUCTION      = new Production<ParseType>(POINTER_TYPE_TRIPLE_RANGLE);
  private static final Production<ParseType> LIST_PRODUCTION = new Production<ParseType>(TYPE_BOUND_LIST, AMPERSAND, POINTER_TYPE_TRIPLE_RANGLE);

  @SuppressWarnings("unchecked")
  public TypeBoundListTripleRAngleRule()
  {
    super(TYPE_BOUND_LIST_TRIPLE_RANGLE, PRODUCTION, LIST_PRODUCTION);
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
      @SuppressWarnings("unchecked")
      ParseContainer<ParseContainer<ParseContainer<PointerTypeAST>>> container = (ParseContainer<ParseContainer<ParseContainer<PointerTypeAST>>>) args[0];
      ParseList<PointerTypeAST> list = new ParseList<PointerTypeAST>(container.getItem().getItem().getItem(), container.getItem().getItem().getItem().getLexicalPhrase());

      ParseContainer<ParseList<PointerTypeAST>> firstContainer =
             new ParseContainer<ParseList<PointerTypeAST>>(list, container.getItem().getItem().getLexicalPhrase());
      ParseContainer<ParseContainer<ParseList<PointerTypeAST>>> secondContainer =
             new ParseContainer<ParseContainer<ParseList<PointerTypeAST>>>(firstContainer, container.getItem().getLexicalPhrase());
      return new ParseContainer<ParseContainer<ParseContainer<ParseList<PointerTypeAST>>>>(secondContainer, container.getLexicalPhrase());
    }
    if (LIST_PRODUCTION.equals(production))
    {
      @SuppressWarnings("unchecked")
      ParseList<PointerTypeAST> list = (ParseList<PointerTypeAST>) args[0];
      @SuppressWarnings("unchecked")
      ParseContainer<ParseContainer<ParseContainer<PointerTypeAST>>> container = (ParseContainer<ParseContainer<ParseContainer<PointerTypeAST>>>) args[2];
      LexicalPhrase listLexicalPhrase = list.getLexicalPhrase();

      list.addLast(container.getItem().getItem().getItem(), LexicalPhrase.combine(listLexicalPhrase, (LexicalPhrase) args[1], container.getItem().getItem().getItem().getLexicalPhrase()));

      ParseContainer<ParseList<PointerTypeAST>> firstContainer = new ParseContainer<ParseList<PointerTypeAST>>(list,
                   LexicalPhrase.combine(listLexicalPhrase, (LexicalPhrase) args[1], container.getItem().getItem().getLexicalPhrase()));
      ParseContainer<ParseContainer<ParseList<PointerTypeAST>>> secondContainer = new ParseContainer<ParseContainer<ParseList<PointerTypeAST>>>(firstContainer,
                   LexicalPhrase.combine(listLexicalPhrase, (LexicalPhrase) args[1], container.getItem().getLexicalPhrase()));
      return new ParseContainer<ParseContainer<ParseContainer<ParseList<PointerTypeAST>>>>(secondContainer,
                   LexicalPhrase.combine(listLexicalPhrase, (LexicalPhrase) args[1], container.getLexicalPhrase()));
    }
    throw badTypeList();
  }

}
