package compiler.language.ast;

/*
 * Created on 16 Jul 2010
 */

/**
 * @author Anthony Bryant
 */
public class EnumDefinition extends TypeDefinition
{

  private AccessSpecifier accessSpecifier;
  private Modifier[] modifiers;
  private Name name;
  private PointerType baseClass;
  private PointerType[] interfaces;
  private EnumConstant[] constants;
  private Member[] members;

  // TODO: finish
}
