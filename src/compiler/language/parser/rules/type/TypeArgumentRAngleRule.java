package compiler.language.parser.rules.type;

import static compiler.language.parser.ParseType.EXTENDS_KEYWORD;
import static compiler.language.parser.ParseType.NAME;
import static compiler.language.parser.ParseType.RANGLE;
import static compiler.language.parser.ParseType.SUPER_KEYWORD;
import static compiler.language.parser.ParseType.TYPE_ARGUMENT_RANGLE;
import static compiler.language.parser.ParseType.TYPE_BOUND_LIST;
import static compiler.language.parser.ParseType.TYPE_BOUND_LIST_RANGLE;

import compiler.language.ast.ParseContainer;
import compiler.language.ast.ParseInfo;
import compiler.language.ast.ParseList;
import compiler.language.ast.terminal.NameAST;
import compiler.language.ast.type.PointerTypeAST;
import compiler.language.ast.type.TypeArgumentAST;
import compiler.language.parser.ParseType;
import compiler.parser.ParseException;
import compiler.parser.Production;
import compiler.parser.Rule;

/*
 * Created on 10 Aug 2010
 */

/**
 * @author Anthony Bryant
 */
public final class TypeArgumentRAngleRule extends Rule<ParseType>
{
  private static final long serialVersionUID = 1L;

  private static final Production<ParseType> NAME_PRODUCTION          = new Production<ParseType>(NAME, RANGLE);
  private static final Production<ParseType> EXTENDS_PRODUCTION       = new Production<ParseType>(NAME, EXTENDS_KEYWORD, TYPE_BOUND_LIST_RANGLE);
  private static final Production<ParseType> SUPER_PRODUCTION         = new Production<ParseType>(NAME, SUPER_KEYWORD,   TYPE_BOUND_LIST_RANGLE);
  private static final Production<ParseType> EXTENDS_SUPER_PRODUCTION = new Production<ParseType>(NAME, EXTENDS_KEYWORD, TYPE_BOUND_LIST, SUPER_KEYWORD,   TYPE_BOUND_LIST_RANGLE);
  private static final Production<ParseType> SUPER_EXTENDS_PRODUCTION = new Production<ParseType>(NAME, SUPER_KEYWORD,   TYPE_BOUND_LIST, EXTENDS_KEYWORD, TYPE_BOUND_LIST_RANGLE);

  @SuppressWarnings("unchecked")
  public TypeArgumentRAngleRule()
  {
    super(TYPE_ARGUMENT_RANGLE, NAME_PRODUCTION, EXTENDS_PRODUCTION, SUPER_PRODUCTION, EXTENDS_SUPER_PRODUCTION, SUPER_EXTENDS_PRODUCTION);
  }

  /**
   * {@inheritDoc}
   * @see compiler.parser.Rule#match(compiler.parser.Production, java.lang.Object[])
   */
  @Override
  public Object match(Production<ParseType> production, Object[] args) throws ParseException
  {
    // all productions have the first argument as a NameAST, so we can cast it early
    // (this assumes that we have the correct type list, and that the NAME type is only ever associated with a NameAST object)
    NameAST name = (NameAST) args[0];
    if (NAME_PRODUCTION.equals(production))
    {
      TypeArgumentAST typeArgument = new TypeArgumentAST(name, new PointerTypeAST[0], new PointerTypeAST[0], name.getParseInfo());
      return new ParseContainer<TypeArgumentAST>(typeArgument, ParseInfo.combine(typeArgument.getParseInfo(), (ParseInfo) args[1]));
    }
    if (EXTENDS_PRODUCTION.equals(production))
    {
      @SuppressWarnings("unchecked")
      ParseContainer<ParseList<PointerTypeAST>> container = (ParseContainer<ParseList<PointerTypeAST>>) args[2];
      ParseList<PointerTypeAST> superTypes = container.getItem();
      TypeArgumentAST typeArgument = new TypeArgumentAST(name, superTypes.toArray(new PointerTypeAST[0]), new PointerTypeAST[0],
                                                   ParseInfo.combine(name.getParseInfo(), (ParseInfo) args[1], superTypes.getParseInfo()));
      return new ParseContainer<TypeArgumentAST>(typeArgument, ParseInfo.combine(name.getParseInfo(), (ParseInfo) args[1], container.getParseInfo()));
    }
    if (SUPER_PRODUCTION.equals(production))
    {
      @SuppressWarnings("unchecked")
      ParseContainer<ParseList<PointerTypeAST>> container = (ParseContainer<ParseList<PointerTypeAST>>) args[2];
      ParseList<PointerTypeAST> subTypes = container.getItem();
      TypeArgumentAST typeArgument = new TypeArgumentAST(name, new PointerTypeAST[0], subTypes.toArray(new PointerTypeAST[0]),
                                                   ParseInfo.combine(name.getParseInfo(), (ParseInfo) args[1], subTypes.getParseInfo()));
      return new ParseContainer<TypeArgumentAST>(typeArgument, ParseInfo.combine(name.getParseInfo(), (ParseInfo) args[1], container.getParseInfo()));
    }
    if (EXTENDS_SUPER_PRODUCTION.equals(production))
    {
      @SuppressWarnings("unchecked")
      ParseContainer<ParseList<PointerTypeAST>> container = (ParseContainer<ParseList<PointerTypeAST>>) args[4];
      @SuppressWarnings("unchecked")
      ParseList<PointerTypeAST> superTypes = (ParseList<PointerTypeAST>) args[2];
      ParseList<PointerTypeAST> subTypes = container.getItem();
      TypeArgumentAST typeArgument = new TypeArgumentAST(name, superTypes.toArray(new PointerTypeAST[0]), subTypes.toArray(new PointerTypeAST[0]),
                                                   ParseInfo.combine(name.getParseInfo(), (ParseInfo) args[1], superTypes.getParseInfo(),
                                                                                          (ParseInfo) args[3], subTypes.getParseInfo()));
      return new ParseContainer<TypeArgumentAST>(typeArgument,
                                              ParseInfo.combine(name.getParseInfo(), (ParseInfo) args[1], superTypes.getParseInfo(),
                                                                                     (ParseInfo) args[3], container.getParseInfo()));
    }
    if (SUPER_EXTENDS_PRODUCTION.equals(production))
    {
      @SuppressWarnings("unchecked")
      ParseContainer<ParseList<PointerTypeAST>> container = (ParseContainer<ParseList<PointerTypeAST>>) args[4];
      @SuppressWarnings("unchecked")
      ParseList<PointerTypeAST> subTypes = (ParseList<PointerTypeAST>) args[2];
      ParseList<PointerTypeAST> superTypes = container.getItem();
      TypeArgumentAST typeArgument = new TypeArgumentAST(name, superTypes.toArray(new PointerTypeAST[0]), subTypes.toArray(new PointerTypeAST[0]),
                                                   ParseInfo.combine(name.getParseInfo(), (ParseInfo) args[1], subTypes.getParseInfo(),
                                                                                          (ParseInfo) args[3], superTypes.getParseInfo()));
      return new ParseContainer<TypeArgumentAST>(typeArgument,
                                              ParseInfo.combine(name.getParseInfo(), (ParseInfo) args[1], subTypes.getParseInfo(),
                                                                                     (ParseInfo) args[3], container.getParseInfo()));
    }
    throw badTypeList();
  }

}
