package compiler.language.parser.rules.type;

import static compiler.language.parser.ParseType.EXTENDS_KEYWORD;
import static compiler.language.parser.ParseType.NAME;
import static compiler.language.parser.ParseType.SUPER_KEYWORD;
import static compiler.language.parser.ParseType.TYPE_ARGUMENT;
import static compiler.language.parser.ParseType.TYPE_BOUND_LIST;

import compiler.language.ast.ParseInfo;
import compiler.language.ast.ParseList;
import compiler.language.ast.terminal.Name;
import compiler.language.ast.type.PointerType;
import compiler.language.ast.type.TypeArgument;
import compiler.parser.ParseException;
import compiler.parser.Rule;

/*
 * Created on 3 Jul 2010
 */

/**
 * @author Anthony Bryant
 */
public class TypeArgumentRule extends Rule
{

  private static final Object[] NAME_PRODUCTION          = new Object[] {NAME};
  private static final Object[] EXTENDS_PRODUCTION       = new Object[] {NAME, EXTENDS_KEYWORD, TYPE_BOUND_LIST};
  private static final Object[] SUPER_PRODUCTION         = new Object[] {NAME, SUPER_KEYWORD,   TYPE_BOUND_LIST};
  private static final Object[] EXTENDS_SUPER_PRODUCTION = new Object[] {NAME, EXTENDS_KEYWORD, TYPE_BOUND_LIST, SUPER_KEYWORD,   TYPE_BOUND_LIST};
  private static final Object[] SUPER_EXTENDS_PRODUCTION = new Object[] {NAME, SUPER_KEYWORD,   TYPE_BOUND_LIST, EXTENDS_KEYWORD, TYPE_BOUND_LIST};

  public TypeArgumentRule()
  {
    super(TYPE_ARGUMENT, NAME_PRODUCTION, EXTENDS_PRODUCTION, SUPER_PRODUCTION, EXTENDS_SUPER_PRODUCTION, SUPER_EXTENDS_PRODUCTION);
  }

  /**
   * @see compiler.parser.Rule#match(java.lang.Object[], java.lang.Object[])
   */
  @Override
  public Object match(Object[] types, Object[] args) throws ParseException
  {
    // all productions have the first argument as a Name, so we can cast it early
    // (this assumes that we have the correct type list, and that the NAME type is only ever associated with a Name object)
    Name name = (Name) args[0];
    if (types == NAME_PRODUCTION)
    {
      return new TypeArgument(name, new PointerType[0], new PointerType[0], name.getParseInfo());
    }
    if (types == EXTENDS_PRODUCTION)
    {
      @SuppressWarnings("unchecked")
      ParseList<PointerType> superTypes = (ParseList<PointerType>) args[2];
      return new TypeArgument(name, superTypes.toArray(new PointerType[0]), new PointerType[0],
                              ParseInfo.combine(name.getParseInfo(), (ParseInfo) args[1], superTypes.getParseInfo()));
    }
    if (types == SUPER_PRODUCTION)
    {
      @SuppressWarnings("unchecked")
      ParseList<PointerType> subTypes = (ParseList<PointerType>) args[2];
      return new TypeArgument(name, new PointerType[0], subTypes.toArray(new PointerType[0]),
                              ParseInfo.combine(name.getParseInfo(), (ParseInfo) args[1], subTypes.getParseInfo()));
    }
    if (types == EXTENDS_SUPER_PRODUCTION)
    {
      @SuppressWarnings("unchecked")
      ParseList<PointerType> superTypes = (ParseList<PointerType>) args[2];
      @SuppressWarnings("unchecked")
      ParseList<PointerType> subTypes = (ParseList<PointerType>) args[4];
      return new TypeArgument(name, superTypes.toArray(new PointerType[0]), subTypes.toArray(new PointerType[0]),
                              ParseInfo.combine(name.getParseInfo(), (ParseInfo) args[1], superTypes.getParseInfo(), (ParseInfo) args[3], subTypes.getParseInfo()));
    }
    if (types == SUPER_EXTENDS_PRODUCTION)
    {
      @SuppressWarnings("unchecked")
      ParseList<PointerType> subTypes = (ParseList<PointerType>) args[2];
      @SuppressWarnings("unchecked")
      ParseList<PointerType> superTypes = (ParseList<PointerType>) args[4];
      return new TypeArgument(name, superTypes.toArray(new PointerType[0]), subTypes.toArray(new PointerType[0]),
                              ParseInfo.combine(name.getParseInfo(), (ParseInfo) args[1], subTypes.getParseInfo(), (ParseInfo) args[3], superTypes.getParseInfo()));
    }
    throw badTypeList();
  }

}
