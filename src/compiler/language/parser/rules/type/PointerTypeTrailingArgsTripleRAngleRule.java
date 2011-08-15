package compiler.language.parser.rules.type;

import static compiler.language.parser.ParseType.POINTER_TYPE_TRAILING_ARGS;
import static compiler.language.parser.ParseType.POINTER_TYPE_TRAILING_ARGS_TRIPLE_RANGLE;
import static compiler.language.parser.ParseType.TRIPLE_RANGLE;
import parser.ParseException;
import parser.Production;
import parser.Rule;

import compiler.language.LexicalPhrase;
import compiler.language.ast.type.PointerTypeAST;
import compiler.language.parser.ParseContainer;
import compiler.language.parser.ParseType;
import compiler.language.parser.ParseUtil;

/*
 * Created on 22 Aug 2010
 */

/**
 * @author Anthony Bryant
 */
public final class PointerTypeTrailingArgsTripleRAngleRule extends Rule<ParseType>
{
  private static final long serialVersionUID = 1L;

  private static final Production<ParseType> TRAILING_ARGS_PRODUCTION = new Production<ParseType>(POINTER_TYPE_TRAILING_ARGS, TRIPLE_RANGLE);

  @SuppressWarnings("unchecked")
  public PointerTypeTrailingArgsTripleRAngleRule()
  {
    super(POINTER_TYPE_TRAILING_ARGS_TRIPLE_RANGLE, TRAILING_ARGS_PRODUCTION);
  }

  /**
   * {@inheritDoc}
   * @see parser.Rule#match(parser.Production, java.lang.Object[])
   */
  @Override
  public Object match(Production<ParseType> production, Object[] args) throws ParseException
  {
    if (TRAILING_ARGS_PRODUCTION.equals(production))
    {
      PointerTypeAST type = (PointerTypeAST) args[0];
      LexicalPhrase tripleRAnglePhrase = (LexicalPhrase) args[1];
      LexicalPhrase firstRAnglePhrase = ParseUtil.splitTripleRAngleFirst(tripleRAnglePhrase);
      LexicalPhrase firstTwoRAnglesPhrase  = ParseUtil.splitTripleRAngleFirstTwo(tripleRAnglePhrase);

      ParseContainer<PointerTypeAST> firstContainer = new ParseContainer<PointerTypeAST>(type,
          LexicalPhrase.combine(type.getLexicalPhrase(), firstRAnglePhrase));
      ParseContainer<ParseContainer<PointerTypeAST>> secondContainer = new ParseContainer<ParseContainer<PointerTypeAST>>(firstContainer,
          LexicalPhrase.combine(type.getLexicalPhrase(), firstTwoRAnglesPhrase));
      return new ParseContainer<ParseContainer<ParseContainer<PointerTypeAST>>>(secondContainer,
          LexicalPhrase.combine(type.getLexicalPhrase(), tripleRAnglePhrase));
    }
    throw badTypeList();
  }

}
