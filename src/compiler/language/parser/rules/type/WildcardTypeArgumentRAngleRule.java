package compiler.language.parser.rules.type;

import static compiler.language.parser.ParseType.EXTENDS_KEYWORD;
import static compiler.language.parser.ParseType.QUESTION_MARK;
import static compiler.language.parser.ParseType.RANGLE;
import static compiler.language.parser.ParseType.SUPER_KEYWORD;
import static compiler.language.parser.ParseType.TYPE_BOUND_LIST;
import static compiler.language.parser.ParseType.TYPE_BOUND_LIST_RANGLE;
import static compiler.language.parser.ParseType.WILDCARD_TYPE_ARGUMENT_RANGLE;
import parser.ParseException;
import parser.Production;
import parser.Rule;

import compiler.language.ast.ParseInfo;
import compiler.language.ast.type.PointerTypeAST;
import compiler.language.ast.type.TypeArgumentAST;
import compiler.language.ast.type.WildcardTypeArgumentAST;
import compiler.language.parser.ParseContainer;
import compiler.language.parser.ParseList;
import compiler.language.parser.ParseType;

/*
 * Created on 10 Aug 2010
 */

/**
 * @author Anthony Bryant
 */
public final class WildcardTypeArgumentRAngleRule extends Rule<ParseType>
{
  private static final long serialVersionUID = 1L;

  private static final Production<ParseType> PRODUCTION               = new Production<ParseType>(QUESTION_MARK, RANGLE);
  private static final Production<ParseType> EXTENDS_PRODUCTION       = new Production<ParseType>(QUESTION_MARK, EXTENDS_KEYWORD, TYPE_BOUND_LIST_RANGLE);
  private static final Production<ParseType> SUPER_PRODUCTION         = new Production<ParseType>(QUESTION_MARK, SUPER_KEYWORD,   TYPE_BOUND_LIST_RANGLE);
  private static final Production<ParseType> EXTENDS_SUPER_PRODUCTION = new Production<ParseType>(QUESTION_MARK, EXTENDS_KEYWORD, TYPE_BOUND_LIST, SUPER_KEYWORD,   TYPE_BOUND_LIST_RANGLE);
  private static final Production<ParseType> SUPER_EXTENDS_PRODUCTION = new Production<ParseType>(QUESTION_MARK, SUPER_KEYWORD,   TYPE_BOUND_LIST, EXTENDS_KEYWORD, TYPE_BOUND_LIST_RANGLE);

  @SuppressWarnings("unchecked")
  public WildcardTypeArgumentRAngleRule()
  {
    super(WILDCARD_TYPE_ARGUMENT_RANGLE, PRODUCTION, EXTENDS_PRODUCTION, SUPER_PRODUCTION, EXTENDS_SUPER_PRODUCTION, SUPER_EXTENDS_PRODUCTION);
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
      WildcardTypeArgumentAST typeArgument = new WildcardTypeArgumentAST(new PointerTypeAST[0], new PointerTypeAST[0], (ParseInfo) args[0]);
      return new ParseContainer<TypeArgumentAST>(typeArgument, ParseInfo.combine((ParseInfo) args[0], (ParseInfo) args[1]));
    }
    if (EXTENDS_PRODUCTION.equals(production))
    {
      @SuppressWarnings("unchecked")
      ParseContainer<ParseList<PointerTypeAST>> container = (ParseContainer<ParseList<PointerTypeAST>>) args[2];
      ParseList<PointerTypeAST> superTypes = container.getItem();
      WildcardTypeArgumentAST typeArgument = new WildcardTypeArgumentAST(superTypes.toArray(new PointerTypeAST[0]), new PointerTypeAST[0],
                                                                      ParseInfo.combine((ParseInfo) args[0], (ParseInfo) args[1], superTypes.getParseInfo()));
      return new ParseContainer<TypeArgumentAST>(typeArgument, ParseInfo.combine((ParseInfo) args[0], (ParseInfo) args[1], container.getParseInfo()));
    }
    if (SUPER_PRODUCTION.equals(production))
    {
      @SuppressWarnings("unchecked")
      ParseContainer<ParseList<PointerTypeAST>> container = (ParseContainer<ParseList<PointerTypeAST>>) args[2];
      ParseList<PointerTypeAST> subTypes = container.getItem();
      WildcardTypeArgumentAST typeArgument = new WildcardTypeArgumentAST(new PointerTypeAST[0], subTypes.toArray(new PointerTypeAST[0]),
                                                                      ParseInfo.combine((ParseInfo) args[0], (ParseInfo) args[1], subTypes.getParseInfo()));
      return new ParseContainer<TypeArgumentAST>(typeArgument, ParseInfo.combine((ParseInfo) args[0], (ParseInfo) args[1], container.getParseInfo()));
    }
    if (EXTENDS_SUPER_PRODUCTION.equals(production))
    {
      @SuppressWarnings("unchecked")
      ParseContainer<ParseList<PointerTypeAST>> container = (ParseContainer<ParseList<PointerTypeAST>>) args[4];
      @SuppressWarnings("unchecked")
      ParseList<PointerTypeAST> superTypes = (ParseList<PointerTypeAST>) args[2];
      ParseList<PointerTypeAST> subTypes = container.getItem();
      WildcardTypeArgumentAST typeArgument = new WildcardTypeArgumentAST(superTypes.toArray(new PointerTypeAST[0]), subTypes.toArray(new PointerTypeAST[0]),
                                                  ParseInfo.combine((ParseInfo) args[0], (ParseInfo) args[1], superTypes.getParseInfo(),
                                                                    (ParseInfo) args[3], subTypes.getParseInfo()));
      return new ParseContainer<TypeArgumentAST>(typeArgument, ParseInfo.combine((ParseInfo) args[0], (ParseInfo) args[1], superTypes.getParseInfo(),
                                                                                        (ParseInfo) args[3], container.getParseInfo()));
    }
    if (SUPER_EXTENDS_PRODUCTION.equals(production))
    {
      @SuppressWarnings("unchecked")
      ParseContainer<ParseList<PointerTypeAST>> container = (ParseContainer<ParseList<PointerTypeAST>>) args[4];
      @SuppressWarnings("unchecked")
      ParseList<PointerTypeAST> subTypes = (ParseList<PointerTypeAST>) args[2];
      ParseList<PointerTypeAST> superTypes = container.getItem();
      WildcardTypeArgumentAST typeArgument = new WildcardTypeArgumentAST(superTypes.toArray(new PointerTypeAST[0]), subTypes.toArray(new PointerTypeAST[0]),
                                                  ParseInfo.combine((ParseInfo) args[0], (ParseInfo) args[1], subTypes.getParseInfo(),
                                                                    (ParseInfo) args[3], superTypes.getParseInfo()));
      return new ParseContainer<TypeArgumentAST>(typeArgument,
                   ParseInfo.combine((ParseInfo) args[0], (ParseInfo) args[1], subTypes.getParseInfo(), (ParseInfo) args[3], container.getParseInfo()));
    }
    throw badTypeList();
  }

}
