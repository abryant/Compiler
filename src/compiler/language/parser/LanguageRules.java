package compiler.language.parser;

import compiler.language.parser.rules.expression.BitwiseAndExpressionRule;
import compiler.language.parser.rules.expression.BitwiseOrExpressionRule;
import compiler.language.parser.rules.expression.BitwiseXorExpressionRule;
import compiler.language.parser.rules.expression.BooleanAndExpressionRule;
import compiler.language.parser.rules.expression.BooleanOrExpressionRule;
import compiler.language.parser.rules.expression.BooleanXorExpressionRule;
import compiler.language.parser.rules.expression.EqualityExpressionRule;
import compiler.language.parser.rules.expression.ExpressionNoTupleRule;
import compiler.language.parser.rules.expression.ExpressionRule;
import compiler.language.parser.rules.expression.InlineIfExpressionRule;
import compiler.language.parser.rules.expression.RelationalExpressionRule;
import compiler.language.parser.rules.expression.StatementExpressionRule;
import compiler.language.parser.rules.expression.TupleExpressionRule;
import compiler.language.parser.rules.member.AccessSpecifierRule;
import compiler.language.parser.rules.member.ConstructorRule;
import compiler.language.parser.rules.member.FieldRule;
import compiler.language.parser.rules.member.MemberHeaderRule;
import compiler.language.parser.rules.member.MemberListRule;
import compiler.language.parser.rules.member.MemberRule;
import compiler.language.parser.rules.member.MethodRule;
import compiler.language.parser.rules.member.ModifierRule;
import compiler.language.parser.rules.member.ModifiersRule;
import compiler.language.parser.rules.member.NativeSpecifierRule;
import compiler.language.parser.rules.member.PropertyRule;
import compiler.language.parser.rules.member.SinceSpecifierRule;
import compiler.language.parser.rules.member.StaticInitializerRule;
import compiler.language.parser.rules.misc.ArgumentListRule;
import compiler.language.parser.rules.misc.ArgumentRule;
import compiler.language.parser.rules.misc.ArgumentsRule;
import compiler.language.parser.rules.misc.AssigneeListRule;
import compiler.language.parser.rules.misc.AssigneeRule;
import compiler.language.parser.rules.misc.ParameterListRule;
import compiler.language.parser.rules.misc.ParameterRule;
import compiler.language.parser.rules.misc.ParametersRule;
import compiler.language.parser.rules.misc.QNameRule;
import compiler.language.parser.rules.misc.ThrowsClauseRule;
import compiler.language.parser.rules.misc.ThrowsListRule;
import compiler.language.parser.rules.misc.VersionNumberRule;
import compiler.language.parser.rules.statement.BlockRule;
import compiler.language.parser.rules.statement.EmptyStatementRule;
import compiler.language.parser.rules.statement.OptionalBlockRule;
import compiler.language.parser.rules.statement.StatementRule;
import compiler.language.parser.rules.statement.StatementsRule;
import compiler.language.parser.rules.topLevel.CompilationUnitRule;
import compiler.language.parser.rules.topLevel.ImportDeclarationRule;
import compiler.language.parser.rules.topLevel.PackageDeclarationRule;
import compiler.language.parser.rules.topLevel.TypeDefinitionRule;
import compiler.language.parser.rules.type.BooleanTypeRule;
import compiler.language.parser.rules.type.CharacterTypeRule;
import compiler.language.parser.rules.type.ClosureTypeRule;
import compiler.language.parser.rules.type.FloatingTypeRule;
import compiler.language.parser.rules.type.IntegerTypeRule;
import compiler.language.parser.rules.type.NormalTypeParameterRule;
import compiler.language.parser.rules.type.PointerTypeRule;
import compiler.language.parser.rules.type.PrimitiveTypeRule;
import compiler.language.parser.rules.type.TupleTypeRule;
import compiler.language.parser.rules.type.TypeArgumentListRule;
import compiler.language.parser.rules.type.TypeArgumentRule;
import compiler.language.parser.rules.type.TypeArgumentsRule;
import compiler.language.parser.rules.type.TypeListRule;
import compiler.language.parser.rules.type.TypeParameterListRule;
import compiler.language.parser.rules.type.TypeParameterRule;
import compiler.language.parser.rules.type.TypeParametersRule;
import compiler.language.parser.rules.type.TypeRule;
import compiler.language.parser.rules.type.VoidTypeRule;
import compiler.language.parser.rules.type.WildcardTypeParameterRule;
import compiler.language.parser.rules.typeDefinition.ClassDefinitionRule;
import compiler.language.parser.rules.typeDefinition.ClassExtendsClauseRule;
import compiler.language.parser.rules.typeDefinition.EnumConstantListRule;
import compiler.language.parser.rules.typeDefinition.EnumConstantRule;
import compiler.language.parser.rules.typeDefinition.EnumConstantsRule;
import compiler.language.parser.rules.typeDefinition.EnumDefinitionRule;
import compiler.language.parser.rules.typeDefinition.ImplementsClauseRule;
import compiler.language.parser.rules.typeDefinition.InterfaceDefinitionRule;
import compiler.language.parser.rules.typeDefinition.InterfaceExtendsClauseRule;
import compiler.language.parser.rules.typeDefinition.InterfaceListRule;
import compiler.parser.Rule;
import compiler.parser.lalr.LALRRuleSet;

/*
 * Created on 30 Jun 2010
 */

/**
 * Manages the set of rules used by the language parser.
 *
 * @author Anthony Bryant
 */
public class LanguageRules
{

  private static final Rule startRule = new CompilationUnitRule();
  private static final Rule[] rules = new Rule[]
  {
    // top level
    // startRule (does not need to be included here): new CompilationUnitRule(),
    new ImportDeclarationRule(),
    new PackageDeclarationRule(),
    new TypeDefinitionRule(),

    // type definitions
    new ClassDefinitionRule(),
    new ClassExtendsClauseRule(),
    new EnumConstantListRule(),
    new EnumConstantRule(),
    new EnumConstantsRule(),
    new EnumDefinitionRule(),
    new ImplementsClauseRule(),
    new InterfaceDefinitionRule(),
    new InterfaceExtendsClauseRule(),
    new InterfaceListRule(),

    // members
    new AccessSpecifierRule(),
    new ConstructorRule(),
    new FieldRule(),
    new MemberHeaderRule(),
    new MemberListRule(),
    new MemberRule(),
    new MethodRule(),
    new ModifierRule(),
    new ModifiersRule(),
    new NativeSpecifierRule(),
    new PropertyRule(),
    new SinceSpecifierRule(),
    new StaticInitializerRule(),

    // statements
    new BlockRule(),
    new EmptyStatementRule(),
    new OptionalBlockRule(),
    new StatementRule(),
    new StatementsRule(),

    // expressions
    new BitwiseAndExpressionRule(),
    new BitwiseOrExpressionRule(),
    new BitwiseXorExpressionRule(),
    new BooleanAndExpressionRule(),
    new BooleanOrExpressionRule(),
    new BooleanXorExpressionRule(),
    new EqualityExpressionRule(),
    new ExpressionNoTupleRule(),
    new ExpressionRule(),
    new InlineIfExpressionRule(),
    new RelationalExpressionRule(),
    new StatementExpressionRule(),
    new TupleExpressionRule(),

    // types
    new BooleanTypeRule(),
    new CharacterTypeRule(),
    new ClosureTypeRule(),
    new FloatingTypeRule(),
    new IntegerTypeRule(),
    new NormalTypeParameterRule(),
    new PointerTypeRule(),
    new PrimitiveTypeRule(),
    new TupleTypeRule(),
    new TypeArgumentListRule(),
    new TypeArgumentRule(),
    new TypeArgumentsRule(),
    new TypeListRule(),
    new TypeParameterListRule(),
    new TypeParameterRule(),
    new TypeParametersRule(),
    new TypeRule(),
    new VoidTypeRule(),
    new WildcardTypeParameterRule(),

    // miscellaneous
    new ArgumentListRule(),
    new ArgumentRule(),
    new ArgumentsRule(),
    new AssigneeListRule(),
    new AssigneeRule(),
    new ParameterListRule(),
    new ParameterRule(),
    new ParametersRule(),
    new QNameRule(),
    new ThrowsClauseRule(),
    new ThrowsListRule(),
    new VersionNumberRule(),
  };

  /**
   * @return an LALRRuleSet containing all of the rules needed to parse this language
   */
  public static LALRRuleSet getRuleSet()
  {
    LALRRuleSet ruleSet = new LALRRuleSet();
    ruleSet.addStartRule(startRule);
    for (Rule rule : rules)
    {
      ruleSet.addRule(rule);
    }
    return ruleSet;
  }
}
