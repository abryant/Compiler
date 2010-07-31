package compiler.language.parser.rules;

import static compiler.language.parser.ParseType.ARROW;
import static compiler.language.parser.ParseType.CLOSURE_TYPE;
import static compiler.language.parser.ParseType.LBRACE;
import static compiler.language.parser.ParseType.RBRACE;
import static compiler.language.parser.ParseType.THROWS_CLAUSE;
import static compiler.language.parser.ParseType.TYPE_LIST;

import compiler.language.ast.type.ClosureType;
import compiler.language.ast.type.PointerType;
import compiler.language.ast.type.Type;
import compiler.parser.Rule;

/*
 * Created on 13 Jul 2010
 */

/**
 * @author Anthony Bryant
 */
public class ClosureTypeRule extends Rule
{

  private static final Object[] PRODUCTION = new Object[] {LBRACE, TYPE_LIST, ARROW, TYPE_LIST, THROWS_CLAUSE, RBRACE};

  public ClosureTypeRule()
  {
    super(CLOSURE_TYPE, PRODUCTION);
  }

  /**
   * @see compiler.parser.Rule#match(java.lang.Object[], java.lang.Object[])
   */
  @Override
  public Object match(Object[] types, Object[] args)
  {
    if (types == PRODUCTION)
    {
      return new ClosureType((Type[]) args[1], (Type[]) args[3], (PointerType[]) args[4]);
    }
    throw badTypeList();
  }

}
