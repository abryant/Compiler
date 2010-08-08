package compiler.language.parser.rules.type;

import static compiler.language.parser.ParseType.EXTENDS_KEYWORD;
import static compiler.language.parser.ParseType.NAME;
import static compiler.language.parser.ParseType.POINTER_TYPE;
import static compiler.language.parser.ParseType.SUPER_KEYWORD;
import static compiler.language.parser.ParseType.TYPE_ARGUMENT;

import compiler.language.ast.ParseInfo;
import compiler.language.ast.terminal.Name;
import compiler.language.ast.type.PointerType;
import compiler.language.ast.type.TypeArgument;
import compiler.parser.Rule;

/*
 * Created on 3 Jul 2010
 */

/**
 * @author Anthony Bryant
 */
public class TypeArgumentRule extends Rule
{

  private static final Object[] NAME_PRODUCTION = new Object[] {NAME};
  private static final Object[] EXTENDS_PRODUCTION = new Object[] {NAME, EXTENDS_KEYWORD, POINTER_TYPE};
  private static final Object[] SUPER_PRODUCTION = new Object[] {NAME, SUPER_KEYWORD, POINTER_TYPE};
  private static final Object[] EXTENDS_SUPER_PRODUCTION = new Object[] {NAME, EXTENDS_KEYWORD, POINTER_TYPE, SUPER_KEYWORD, POINTER_TYPE};
  private static final Object[] SUPER_EXTENDS_PRODUCTION = new Object[] {NAME, SUPER_KEYWORD, POINTER_TYPE, EXTENDS_KEYWORD, POINTER_TYPE};

  public TypeArgumentRule()
  {
    super(TYPE_ARGUMENT, NAME_PRODUCTION, EXTENDS_PRODUCTION, SUPER_PRODUCTION, EXTENDS_SUPER_PRODUCTION, SUPER_EXTENDS_PRODUCTION);
  }

  /**
   * @see compiler.parser.Rule#match(java.lang.Object[], java.lang.Object[])
   */
  @Override
  public Object match(Object[] types, Object[] args)
  {
    // all productions have the first argument as a Name, so we can cast it early
    // (this assumes that we have the correct type list, and that the NAME type is only ever associated with a Name object)
    Name name = (Name) args[0];
    if (types == NAME_PRODUCTION)
    {
      return new TypeArgument(name, null, null, name.getParseInfo());
    }
    if (types == EXTENDS_PRODUCTION)
    {
      PointerType superType = (PointerType) args[2];
      return new TypeArgument(name, superType, null, ParseInfo.combine(name.getParseInfo(), (ParseInfo) args[1], superType.getParseInfo()));
    }
    if (types == SUPER_PRODUCTION)
    {
      PointerType subType = (PointerType) args[2];
      return new TypeArgument(name, null, subType, ParseInfo.combine(name.getParseInfo(), (ParseInfo) args[1], subType.getParseInfo()));
    }
    if (types == EXTENDS_SUPER_PRODUCTION)
    {
      PointerType superType = (PointerType) args[2];
      PointerType subType = (PointerType) args[4];
      return new TypeArgument(name, superType, subType, ParseInfo.combine(name.getParseInfo(), (ParseInfo) args[1], superType.getParseInfo(), (ParseInfo) args[3], subType.getParseInfo()));
    }
    if (types == SUPER_EXTENDS_PRODUCTION)
    {
      PointerType subType = (PointerType) args[2];
      PointerType superType = (PointerType) args[4];
      return new TypeArgument(name, superType, subType, ParseInfo.combine(name.getParseInfo(), (ParseInfo) args[1], subType.getParseInfo(), (ParseInfo) args[3], superType.getParseInfo()));
    }
    throw badTypeList();
  }

}
