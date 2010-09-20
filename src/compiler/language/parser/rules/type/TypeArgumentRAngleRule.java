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
import compiler.language.ast.terminal.Name;
import compiler.language.ast.type.PointerType;
import compiler.language.ast.type.TypeArgument;
import compiler.parser.ParseException;
import compiler.parser.Production;
import compiler.parser.Rule;

/*
 * Created on 10 Aug 2010
 */

/**
 * @author Anthony Bryant
 */
public class TypeArgumentRAngleRule extends Rule
{
  private static final long serialVersionUID = 1L;

  private static final Production NAME_PRODUCTION          = new Production(NAME, RANGLE);
  private static final Production EXTENDS_PRODUCTION       = new Production(NAME, EXTENDS_KEYWORD, TYPE_BOUND_LIST_RANGLE);
  private static final Production SUPER_PRODUCTION         = new Production(NAME, SUPER_KEYWORD,   TYPE_BOUND_LIST_RANGLE);
  private static final Production EXTENDS_SUPER_PRODUCTION = new Production(NAME, EXTENDS_KEYWORD, TYPE_BOUND_LIST, SUPER_KEYWORD,   TYPE_BOUND_LIST_RANGLE);
  private static final Production SUPER_EXTENDS_PRODUCTION = new Production(NAME, SUPER_KEYWORD,   TYPE_BOUND_LIST, EXTENDS_KEYWORD, TYPE_BOUND_LIST_RANGLE);

  public TypeArgumentRAngleRule()
  {
    super(TYPE_ARGUMENT_RANGLE, NAME_PRODUCTION, EXTENDS_PRODUCTION, SUPER_PRODUCTION, EXTENDS_SUPER_PRODUCTION, SUPER_EXTENDS_PRODUCTION);
  }

  /**
   * {@inheritDoc}
   * @see compiler.parser.Rule#match(compiler.parser.Production, java.lang.Object[])
   */
  @Override
  public Object match(Production production, Object[] args) throws ParseException
  {
    // all productions have the first argument as a Name, so we can cast it early
    // (this assumes that we have the correct type list, and that the NAME type is only ever associated with a Name object)
    Name name = (Name) args[0];
    if (NAME_PRODUCTION.equals(production))
    {
      TypeArgument typeArgument = new TypeArgument(name, new PointerType[0], new PointerType[0], name.getParseInfo());
      return new ParseContainer<TypeArgument>(typeArgument, ParseInfo.combine(typeArgument.getParseInfo(), (ParseInfo) args[1]));
    }
    if (EXTENDS_PRODUCTION.equals(production))
    {
      @SuppressWarnings("unchecked")
      ParseContainer<ParseList<PointerType>> container = (ParseContainer<ParseList<PointerType>>) args[2];
      ParseList<PointerType> superTypes = container.getItem();
      TypeArgument typeArgument = new TypeArgument(name, superTypes.toArray(new PointerType[0]), new PointerType[0],
                                                   ParseInfo.combine(name.getParseInfo(), (ParseInfo) args[1], superTypes.getParseInfo()));
      return new ParseContainer<TypeArgument>(typeArgument, ParseInfo.combine(name.getParseInfo(), (ParseInfo) args[1], container.getParseInfo()));
    }
    if (SUPER_PRODUCTION.equals(production))
    {
      @SuppressWarnings("unchecked")
      ParseContainer<ParseList<PointerType>> container = (ParseContainer<ParseList<PointerType>>) args[2];
      ParseList<PointerType> subTypes = container.getItem();
      TypeArgument typeArgument = new TypeArgument(name, new PointerType[0], subTypes.toArray(new PointerType[0]),
                                                   ParseInfo.combine(name.getParseInfo(), (ParseInfo) args[1], subTypes.getParseInfo()));
      return new ParseContainer<TypeArgument>(typeArgument, ParseInfo.combine(name.getParseInfo(), (ParseInfo) args[1], container.getParseInfo()));
    }
    if (EXTENDS_SUPER_PRODUCTION.equals(production))
    {
      @SuppressWarnings("unchecked")
      ParseContainer<ParseList<PointerType>> container = (ParseContainer<ParseList<PointerType>>) args[4];
      @SuppressWarnings("unchecked")
      ParseList<PointerType> superTypes = (ParseList<PointerType>) args[2];
      ParseList<PointerType> subTypes = container.getItem();
      TypeArgument typeArgument = new TypeArgument(name, superTypes.toArray(new PointerType[0]), subTypes.toArray(new PointerType[0]),
                                                   ParseInfo.combine(name.getParseInfo(), (ParseInfo) args[1], superTypes.getParseInfo(),
                                                                                          (ParseInfo) args[3], subTypes.getParseInfo()));
      return new ParseContainer<TypeArgument>(typeArgument,
                                              ParseInfo.combine(name.getParseInfo(), (ParseInfo) args[1], superTypes.getParseInfo(),
                                                                                     (ParseInfo) args[3], container.getParseInfo()));
    }
    if (SUPER_EXTENDS_PRODUCTION.equals(production))
    {
      @SuppressWarnings("unchecked")
      ParseContainer<ParseList<PointerType>> container = (ParseContainer<ParseList<PointerType>>) args[4];
      @SuppressWarnings("unchecked")
      ParseList<PointerType> subTypes = (ParseList<PointerType>) args[2];
      ParseList<PointerType> superTypes = container.getItem();
      TypeArgument typeArgument = new TypeArgument(name, superTypes.toArray(new PointerType[0]), subTypes.toArray(new PointerType[0]),
                                                   ParseInfo.combine(name.getParseInfo(), (ParseInfo) args[1], subTypes.getParseInfo(),
                                                                                          (ParseInfo) args[3], superTypes.getParseInfo()));
      return new ParseContainer<TypeArgument>(typeArgument,
                                              ParseInfo.combine(name.getParseInfo(), (ParseInfo) args[1], subTypes.getParseInfo(),
                                                                                     (ParseInfo) args[3], container.getParseInfo()));
    }
    throw badTypeList();
  }

}
