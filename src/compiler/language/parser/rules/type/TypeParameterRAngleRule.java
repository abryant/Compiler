package compiler.language.parser.rules.type;

import static compiler.language.parser.ParseType.EXTENDS_KEYWORD;
import static compiler.language.parser.ParseType.NAME;
import static compiler.language.parser.ParseType.RANGLE;
import static compiler.language.parser.ParseType.SUPER_KEYWORD;
import static compiler.language.parser.ParseType.TYPE_BOUND_LIST;
import static compiler.language.parser.ParseType.TYPE_BOUND_LIST_RANGLE;
import static compiler.language.parser.ParseType.TYPE_PARAMETER_RANGLE;
import parser.ParseException;
import parser.Production;
import parser.Rule;

import compiler.language.LexicalPhrase;
import compiler.language.ast.terminal.NameAST;
import compiler.language.ast.type.PointerTypeAST;
import compiler.language.ast.type.TypeParameterAST;
import compiler.language.parser.ParseContainer;
import compiler.language.parser.ParseList;
import compiler.language.parser.ParseType;

/*
 * Created on 10 Aug 2010
 */

/**
 * @author Anthony Bryant
 */
public final class TypeParameterRAngleRule extends Rule<ParseType>
{
  private static final long serialVersionUID = 1L;

  private static final Production<ParseType> NAME_PRODUCTION          = new Production<ParseType>(NAME, RANGLE);
  private static final Production<ParseType> EXTENDS_PRODUCTION       = new Production<ParseType>(NAME, EXTENDS_KEYWORD, TYPE_BOUND_LIST_RANGLE);
  private static final Production<ParseType> SUPER_PRODUCTION         = new Production<ParseType>(NAME, SUPER_KEYWORD,   TYPE_BOUND_LIST_RANGLE);
  private static final Production<ParseType> EXTENDS_SUPER_PRODUCTION = new Production<ParseType>(NAME, EXTENDS_KEYWORD, TYPE_BOUND_LIST, SUPER_KEYWORD,   TYPE_BOUND_LIST_RANGLE);
  private static final Production<ParseType> SUPER_EXTENDS_PRODUCTION = new Production<ParseType>(NAME, SUPER_KEYWORD,   TYPE_BOUND_LIST, EXTENDS_KEYWORD, TYPE_BOUND_LIST_RANGLE);

  @SuppressWarnings("unchecked")
  public TypeParameterRAngleRule()
  {
    super(TYPE_PARAMETER_RANGLE, NAME_PRODUCTION, EXTENDS_PRODUCTION, SUPER_PRODUCTION, EXTENDS_SUPER_PRODUCTION, SUPER_EXTENDS_PRODUCTION);
  }

  /**
   * {@inheritDoc}
   * @see parser.Rule#match(parser.Production, java.lang.Object[])
   */
  @Override
  public Object match(Production<ParseType> production, Object[] args) throws ParseException
  {
    // all productions have the first argument as a NameAST, so we can cast it early
    // (this assumes that we have the correct type list, and that the NAME type is only ever associated with a NameAST object)
    NameAST name = (NameAST) args[0];
    if (NAME_PRODUCTION.equals(production))
    {
      TypeParameterAST typeParameter = new TypeParameterAST(name, new PointerTypeAST[0], new PointerTypeAST[0], name.getLexicalPhrase());
      return new ParseContainer<TypeParameterAST>(typeParameter, LexicalPhrase.combine(typeParameter.getLexicalPhrase(), (LexicalPhrase) args[1]));
    }
    if (EXTENDS_PRODUCTION.equals(production))
    {
      @SuppressWarnings("unchecked")
      ParseContainer<ParseList<PointerTypeAST>> container = (ParseContainer<ParseList<PointerTypeAST>>) args[2];
      ParseList<PointerTypeAST> superTypes = container.getItem();
      TypeParameterAST typeParameter = new TypeParameterAST(name, superTypes.toArray(new PointerTypeAST[0]), new PointerTypeAST[0],
                                                            LexicalPhrase.combine(name.getLexicalPhrase(), (LexicalPhrase) args[1], superTypes.getLexicalPhrase()));
      return new ParseContainer<TypeParameterAST>(typeParameter, LexicalPhrase.combine(name.getLexicalPhrase(), (LexicalPhrase) args[1], container.getLexicalPhrase()));
    }
    if (SUPER_PRODUCTION.equals(production))
    {
      @SuppressWarnings("unchecked")
      ParseContainer<ParseList<PointerTypeAST>> container = (ParseContainer<ParseList<PointerTypeAST>>) args[2];
      ParseList<PointerTypeAST> subTypes = container.getItem();
      TypeParameterAST typeParameter = new TypeParameterAST(name, new PointerTypeAST[0], subTypes.toArray(new PointerTypeAST[0]),
                                                            LexicalPhrase.combine(name.getLexicalPhrase(), (LexicalPhrase) args[1], subTypes.getLexicalPhrase()));
      return new ParseContainer<TypeParameterAST>(typeParameter, LexicalPhrase.combine(name.getLexicalPhrase(), (LexicalPhrase) args[1], container.getLexicalPhrase()));
    }
    if (EXTENDS_SUPER_PRODUCTION.equals(production))
    {
      @SuppressWarnings("unchecked")
      ParseContainer<ParseList<PointerTypeAST>> container = (ParseContainer<ParseList<PointerTypeAST>>) args[4];
      @SuppressWarnings("unchecked")
      ParseList<PointerTypeAST> superTypes = (ParseList<PointerTypeAST>) args[2];
      ParseList<PointerTypeAST> subTypes = container.getItem();
      TypeParameterAST typeParameter = new TypeParameterAST(name, superTypes.toArray(new PointerTypeAST[0]), subTypes.toArray(new PointerTypeAST[0]),
                                                            LexicalPhrase.combine(name.getLexicalPhrase(), (LexicalPhrase) args[1], superTypes.getLexicalPhrase(),
                                                                                                   (LexicalPhrase) args[3], subTypes.getLexicalPhrase()));
      return new ParseContainer<TypeParameterAST>(typeParameter,
                                              LexicalPhrase.combine(name.getLexicalPhrase(), (LexicalPhrase) args[1], superTypes.getLexicalPhrase(),
                                                                                     (LexicalPhrase) args[3], container.getLexicalPhrase()));
    }
    if (SUPER_EXTENDS_PRODUCTION.equals(production))
    {
      @SuppressWarnings("unchecked")
      ParseContainer<ParseList<PointerTypeAST>> container = (ParseContainer<ParseList<PointerTypeAST>>) args[4];
      @SuppressWarnings("unchecked")
      ParseList<PointerTypeAST> subTypes = (ParseList<PointerTypeAST>) args[2];
      ParseList<PointerTypeAST> superTypes = container.getItem();
      TypeParameterAST typeParameter = new TypeParameterAST(name, superTypes.toArray(new PointerTypeAST[0]), subTypes.toArray(new PointerTypeAST[0]),
                                                            LexicalPhrase.combine(name.getLexicalPhrase(), (LexicalPhrase) args[1], subTypes.getLexicalPhrase(),
                                                                                                   (LexicalPhrase) args[3], superTypes.getLexicalPhrase()));
      return new ParseContainer<TypeParameterAST>(typeParameter,
                                              LexicalPhrase.combine(name.getLexicalPhrase(), (LexicalPhrase) args[1], subTypes.getLexicalPhrase(),
                                                                                     (LexicalPhrase) args[3], container.getLexicalPhrase()));
    }
    throw badTypeList();
  }

}
