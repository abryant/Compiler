package compiler.language.parser.rules.misc;

import static compiler.language.parser.ParseType.COMMA;
import static compiler.language.parser.ParseType.POINTER_TYPE;
import static compiler.language.parser.ParseType.THROWS_LIST;
import parser.ParseException;
import parser.Production;
import parser.Rule;

import compiler.language.LexicalPhrase;
import compiler.language.ast.type.PointerTypeAST;
import compiler.language.parser.ParseList;
import compiler.language.parser.ParseType;

/*
 * Created on 8 Jul 2010
 */

/**
 * @author Anthony Bryant
 */
public final class ThrowsListRule extends Rule<ParseType>
{
  private static final long serialVersionUID = 1L;

  private static final Production<ParseType> START_PRODUCTION = new Production<ParseType>(POINTER_TYPE);
  private static final Production<ParseType> CONTINUATION_PRODUCTION = new Production<ParseType>(THROWS_LIST, COMMA, POINTER_TYPE);

  @SuppressWarnings("unchecked")
  public ThrowsListRule()
  {
    super(THROWS_LIST, START_PRODUCTION, CONTINUATION_PRODUCTION);
  }

  /**
   * @see parser.Rule#match(parser.Production, java.lang.Object[])
   */
  @Override
  public Object match(Production<ParseType> production, Object[] args) throws ParseException
  {
    if (START_PRODUCTION.equals(production))
    {
      PointerTypeAST type = (PointerTypeAST) args[0];
      return new ParseList<PointerTypeAST>(type, type.getLexicalPhrase());
    }
    if (CONTINUATION_PRODUCTION.equals(production))
    {
      @SuppressWarnings("unchecked")
      ParseList<PointerTypeAST> list = (ParseList<PointerTypeAST>) args[0];
      PointerTypeAST type = (PointerTypeAST) args[2];
      list.addLast(type, LexicalPhrase.combine(list.getLexicalPhrase(), (LexicalPhrase) args[1], type.getLexicalPhrase()));
      return list;
    }
    throw badTypeList();
  }

}
