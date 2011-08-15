package compiler.language.parser.rules.typeDefinition;

import static compiler.language.parser.ParseType.CLASS_DEFINITION;
import static compiler.language.parser.ParseType.CLASS_EXTENDS_CLAUSE;
import static compiler.language.parser.ParseType.CLASS_KEYWORD;
import static compiler.language.parser.ParseType.IMPLEMENTS_CLAUSE;
import static compiler.language.parser.ParseType.LBRACE;
import static compiler.language.parser.ParseType.MEMBER_HEADER;
import static compiler.language.parser.ParseType.MEMBER_LIST;
import static compiler.language.parser.ParseType.NAME;
import static compiler.language.parser.ParseType.RBRACE;
import static compiler.language.parser.ParseType.TYPE_PARAMETERS;
import parser.ParseException;
import parser.Production;
import parser.Rule;

import compiler.language.LexicalPhrase;
import compiler.language.ast.member.MemberAST;
import compiler.language.ast.member.MemberHeaderAST;
import compiler.language.ast.terminal.NameAST;
import compiler.language.ast.type.PointerTypeAST;
import compiler.language.ast.type.TypeParameterAST;
import compiler.language.ast.typeDefinition.ClassDefinitionAST;
import compiler.language.parser.ParseList;
import compiler.language.parser.ParseType;

/*
 * Created on 1 Jul 2010
 */

/**
 * @author Anthony Bryant
 */
public final class ClassDefinitionRule extends Rule<ParseType>
{
  private static final long serialVersionUID = 1L;

  private static final Production<ParseType> PRODUCTION = new Production<ParseType>(MEMBER_HEADER, CLASS_KEYWORD, NAME, CLASS_EXTENDS_CLAUSE, IMPLEMENTS_CLAUSE, LBRACE, MEMBER_LIST, RBRACE);
  private static final Production<ParseType> TYPE_PARAMETERS_PRODUCTION = new Production<ParseType>(MEMBER_HEADER, CLASS_KEYWORD, NAME, TYPE_PARAMETERS, CLASS_EXTENDS_CLAUSE, IMPLEMENTS_CLAUSE, LBRACE, MEMBER_LIST, RBRACE);

  @SuppressWarnings("unchecked")
  public ClassDefinitionRule()
  {
    super(CLASS_DEFINITION, PRODUCTION, TYPE_PARAMETERS_PRODUCTION);
  }

  /**
   * @see parser.Rule#match(parser.Production, java.lang.Object[])
   */
  @Override
  public Object match(Production<ParseType> production, Object[] args) throws ParseException
  {
    if (PRODUCTION.equals(production))
    {
      MemberHeaderAST header = (MemberHeaderAST) args[0];
      NameAST name = (NameAST) args[2];
      PointerTypeAST superType = (PointerTypeAST) args[3];
      @SuppressWarnings("unchecked")
      ParseList<PointerTypeAST> interfaces = (ParseList<PointerTypeAST>) args[4];
      @SuppressWarnings("unchecked")
      ParseList<MemberAST> members = (ParseList<MemberAST>) args[6];
      return new ClassDefinitionAST(header.getAccessSpecifier(), header.getModifiers(), name, new TypeParameterAST[0], superType, interfaces.toArray(new PointerTypeAST[0]), members.toArray(new MemberAST[0]),
                                 LexicalPhrase.combine(header.getLexicalPhrase(), (LexicalPhrase) args[1], name.getLexicalPhrase(),
                                                   superType != null ? superType.getLexicalPhrase() : null,
                                                   interfaces.getLexicalPhrase(),
                                                   (LexicalPhrase) args[5], members.getLexicalPhrase(), (LexicalPhrase) args[7]));
    }
    if (TYPE_PARAMETERS_PRODUCTION.equals(production))
    {
      MemberHeaderAST header = (MemberHeaderAST) args[0];
      NameAST name = (NameAST) args[2];
      @SuppressWarnings("unchecked")
      ParseList<TypeParameterAST> typeParameters = (ParseList<TypeParameterAST>) args[3];
      PointerTypeAST superType = (PointerTypeAST) args[4];
      @SuppressWarnings("unchecked")
      ParseList<PointerTypeAST> interfaces = (ParseList<PointerTypeAST>) args[5];
      @SuppressWarnings("unchecked")
      ParseList<MemberAST> members = (ParseList<MemberAST>) args[7];
      return new ClassDefinitionAST(header.getAccessSpecifier(), header.getModifiers(), name, typeParameters.toArray(new TypeParameterAST[0]), superType, interfaces.toArray(new PointerTypeAST[0]), members.toArray(new MemberAST[0]),
                                 LexicalPhrase.combine(header.getLexicalPhrase(), (LexicalPhrase) args[1], name.getLexicalPhrase(), typeParameters.getLexicalPhrase(),
                                                   superType != null ? superType.getLexicalPhrase() : null,
                                                   interfaces.getLexicalPhrase(),
                                                   (LexicalPhrase) args[6], members.getLexicalPhrase(), (LexicalPhrase) args[8]));
    }
    throw badTypeList();
  }

}
