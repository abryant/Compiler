package compiler.language.parser.rules.type;

import static compiler.language.parser.ParseType.POINTER_TYPE_RANGLE;
import static compiler.language.parser.ParseType.RANGLE;
import static compiler.language.parser.ParseType.TYPE_NOT_POINTER_TYPE;
import static compiler.language.parser.ParseType.TYPE_RANGLE;

import compiler.language.ast.ParseContainer;
import compiler.language.ast.ParseInfo;
import compiler.language.ast.type.PointerType;
import compiler.language.ast.type.Type;
import compiler.parser.Rule;

/*
 * Created on 10 Aug 2010
 */

/**
 * @author Anthony Bryant
 */
public class TypeRAngleRule extends Rule
{

  private static final Object[] PRODUCTION = new Object[] {TYPE_NOT_POINTER_TYPE, RANGLE};
  private static final Object[] POINTER_TYPE_PRODUCTION = new Object[] {POINTER_TYPE_RANGLE};

  public TypeRAngleRule()
  {
    super(TYPE_RANGLE, PRODUCTION, POINTER_TYPE_PRODUCTION);
  }

  /**
   * {@inheritDoc}
   * @see compiler.parser.Rule#match(java.lang.Object[], java.lang.Object[])
   */
  @Override
  public Object match(Object[] types, Object[] args)
  {
    if (types == PRODUCTION)
    {
      Type type = (Type) args[0];
      return new ParseContainer<Type>(type, ParseInfo.combine(type.getParseInfo(), (ParseInfo) args[1]));
    }
    if (types == POINTER_TYPE_PRODUCTION)
    {
      // change the ParseContainer<PointerType> for a ParseContainer<Type>
      @SuppressWarnings("unchecked")
      ParseContainer<PointerType> oldContainer = (ParseContainer<PointerType>) args[0];
      return new ParseContainer<Type>(oldContainer.getItem(), oldContainer.getParseInfo());
    }
    throw badTypeList();
  }

}
