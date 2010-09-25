package compiler.language.parser.rules.type;

import static compiler.language.parser.ParseType.EXTENDS_KEYWORD;
import static compiler.language.parser.ParseType.NAME;
import static compiler.language.parser.ParseType.SUPER_KEYWORD;
import static compiler.language.parser.ParseType.TYPE_ARGUMENT;
import static compiler.language.parser.ParseType.TYPE_BOUND_LIST;

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
 * Created on 3 Jul 2010
 */

/**
 * @author Anthony Bryant
 */
public final class TypeArgumentRule extends Rule<ParseType>
{
  private static final long serialVersionUID = 1L;

  private static final Production<ParseType> NAME_PRODUCTION          = new Production<ParseType>(NAME);
  private static final Production<ParseType> EXTENDS_PRODUCTION       = new Production<ParseType>(NAME, EXTENDS_KEYWORD, TYPE_BOUND_LIST);
  private static final Production<ParseType> SUPER_PRODUCTION         = new Production<ParseType>(NAME, SUPER_KEYWORD,   TYPE_BOUND_LIST);
  private static final Production<ParseType> EXTENDS_SUPER_PRODUCTION = new Production<ParseType>(NAME, EXTENDS_KEYWORD, TYPE_BOUND_LIST, SUPER_KEYWORD,   TYPE_BOUND_LIST);
  private static final Production<ParseType> SUPER_EXTENDS_PRODUCTION = new Production<ParseType>(NAME, SUPER_KEYWORD,   TYPE_BOUND_LIST, EXTENDS_KEYWORD, TYPE_BOUND_LIST);

  @SuppressWarnings("unchecked")
  public TypeArgumentRule()
  {
    super(TYPE_ARGUMENT, NAME_PRODUCTION, EXTENDS_PRODUCTION, SUPER_PRODUCTION, EXTENDS_SUPER_PRODUCTION, SUPER_EXTENDS_PRODUCTION);
  }

  /**
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
      return new TypeArgumentAST(name, new PointerTypeAST[0], new PointerTypeAST[0], name.getParseInfo());
    }
    if (EXTENDS_PRODUCTION.equals(production))
    {
      @SuppressWarnings("unchecked")
      ParseList<PointerTypeAST> superTypes = (ParseList<PointerTypeAST>) args[2];
      return new TypeArgumentAST(name, superTypes.toArray(new PointerTypeAST[0]), new PointerTypeAST[0],
                              ParseInfo.combine(name.getParseInfo(), (ParseInfo) args[1], superTypes.getParseInfo()));
    }
    if (SUPER_PRODUCTION.equals(production))
    {
      @SuppressWarnings("unchecked")
      ParseList<PointerTypeAST> subTypes = (ParseList<PointerTypeAST>) args[2];
      return new TypeArgumentAST(name, new PointerTypeAST[0], subTypes.toArray(new PointerTypeAST[0]),
                              ParseInfo.combine(name.getParseInfo(), (ParseInfo) args[1], subTypes.getParseInfo()));
    }
    if (EXTENDS_SUPER_PRODUCTION.equals(production))
    {
      @SuppressWarnings("unchecked")
      ParseList<PointerTypeAST> superTypes = (ParseList<PointerTypeAST>) args[2];
      @SuppressWarnings("unchecked")
      ParseList<PointerTypeAST> subTypes = (ParseList<PointerTypeAST>) args[4];
      return new TypeArgumentAST(name, superTypes.toArray(new PointerTypeAST[0]), subTypes.toArray(new PointerTypeAST[0]),
                              ParseInfo.combine(name.getParseInfo(), (ParseInfo) args[1], superTypes.getParseInfo(), (ParseInfo) args[3], subTypes.getParseInfo()));
    }
    if (SUPER_EXTENDS_PRODUCTION.equals(production))
    {
      @SuppressWarnings("unchecked")
      ParseList<PointerTypeAST> subTypes = (ParseList<PointerTypeAST>) args[2];
      @SuppressWarnings("unchecked")
      ParseList<PointerTypeAST> superTypes = (ParseList<PointerTypeAST>) args[4];
      return new TypeArgumentAST(name, superTypes.toArray(new PointerTypeAST[0]), subTypes.toArray(new PointerTypeAST[0]),
                              ParseInfo.combine(name.getParseInfo(), (ParseInfo) args[1], subTypes.getParseInfo(), (ParseInfo) args[3], superTypes.getParseInfo()));
    }
    throw badTypeList();
  }

}
