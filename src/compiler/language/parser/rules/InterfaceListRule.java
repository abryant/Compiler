package compiler.language.parser.rules;

import static compiler.language.parser.ParseType.COMMA;
import static compiler.language.parser.ParseType.INTERFACE_LIST;
import static compiler.language.parser.ParseType.REFERENCE_TYPE;

import compiler.language.ast.ReferenceType;
import compiler.parser.Rule;

/*
 * Created on 1 Jul 2010
 */

/**
 * @author Anthony Bryant
 */
public class InterfaceListRule extends Rule
{

  private static final Object[] START_PRODUCTION = new Object[] {REFERENCE_TYPE};
  private static final Object[] CONTINUATION_PRODUCTION = new Object[] {INTERFACE_LIST, COMMA, REFERENCE_TYPE};

  public InterfaceListRule()
  {
    super(INTERFACE_LIST, START_PRODUCTION, CONTINUATION_PRODUCTION);
  }

  /**
   * @see compiler.parser.Rule#match(java.lang.Object[], java.lang.Object[])
   */
  @Override
  public Object match(Object[] types, Object[] args)
  {
    if (types == START_PRODUCTION)
    {
      return new ReferenceType[] {(ReferenceType) args[0]};
    }
    if (types == CONTINUATION_PRODUCTION)
    {
      ReferenceType[] oldList = (ReferenceType[]) args[0];
      ReferenceType[] newList = new ReferenceType[oldList.length + 1];
      System.arraycopy(oldList, 0, newList, 0, oldList.length);
      newList[oldList.length] = (ReferenceType) args[2];
    }
    throw badTypeList();
  }

}
