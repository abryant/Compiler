package compiler.language.parser.rules;

import static compiler.language.parser.ParseType.COMMA;
import static compiler.language.parser.ParseType.ENUM_CONSTANT;
import static compiler.language.parser.ParseType.ENUM_CONSTANT_LIST;

import compiler.language.ast.EnumConstant;
import compiler.parser.Rule;

/*
 * Created on 31 Jul 2010
 */

/**
 * @author Anthony Bryant
 */
public class EnumConstantListRule extends Rule
{

  private static final Object[] START_PRODUCTION = new Object[] {ENUM_CONSTANT};
  private static final Object[] CONTINUATION_PRODUCTION = new Object[] {ENUM_CONSTANT_LIST, COMMA, ENUM_CONSTANT};

  public EnumConstantListRule()
  {
    super(ENUM_CONSTANT_LIST, START_PRODUCTION, CONTINUATION_PRODUCTION);
  }

  /**
   * @see compiler.parser.Rule#match(java.lang.Object[], java.lang.Object[])
   */
  @Override
  public Object match(Object[] types, Object[] args)
  {
    if (types == START_PRODUCTION)
    {
      return new EnumConstant[] {(EnumConstant) args[0]};
    }
    if (types == CONTINUATION_PRODUCTION)
    {
      EnumConstant[] oldList = (EnumConstant[]) args[0];
      EnumConstant[] newList = new EnumConstant[oldList.length + 1];
      System.arraycopy(oldList, 0, newList, 0, oldList.length);
      newList[oldList.length] = (EnumConstant) args[2];
      return newList;
    }
    throw badTypeList();
  }

}
