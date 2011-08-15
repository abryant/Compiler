package compiler.language.parser;

import parser.BadTokenException;
import parser.ParseException;
import parser.Token;

import compiler.language.LexicalPhrase;
import compiler.language.ast.terminal.CharacterLiteralAST;
import compiler.language.ast.terminal.FloatingLiteralAST;
import compiler.language.ast.terminal.IntegerLiteralAST;
import compiler.language.ast.terminal.NameAST;
import compiler.language.ast.terminal.SinceSpecifierAST;
import compiler.language.ast.terminal.StringLiteralAST;
import compiler.language.ast.topLevel.CompilationUnitAST;

/*
 * Created on 30 Jun 2010
 */

/**
 * Provides an easy interface for parsing something into a CompilationUnitAST given a tokenizer.
 * Also prints out its own error messages.
 * @author Anthony Bryant
 */
public class LanguageParser
{

  public LanguageParser()
  {
    // do nothing
  }

  /**
   * Parses the output of the specified tokenizer.
   * @param tokenizer - the tokenizer to parse the output of
   * @return the CompilationUnitAST generated, or null if there was an error while parsing (which will have been printed out)
   */
  public CompilationUnitAST parse(LanguageTokenizer tokenizer)
  {
    GeneratedLanguageParser parser = new GeneratedLanguageParser(tokenizer);
    try
    {
      Token<ParseType> result = parser.parse();

      return (CompilationUnitAST) result.getValue();
    }
    catch (LanguageParseException e)
    {
      printParseError(e.getMessage(), e.getLexicalPhrase());
    }
    catch (ParseException e)
    {
      e.printStackTrace();
    }
    catch (BadTokenException e)
    {
      // TODO: should the error message show a list of expected token types here? they seem to not correspond to what should actually be expected
      Token<ParseType> token = e.getBadToken();
      String message;
      LexicalPhrase lexicalPhrase;
      if (token.getType() == null)
      {
        message = "Unexpected end of input, expected one of: " + buildStringList(e.getExpectedTokenTypes());
        lexicalPhrase = (LexicalPhrase) token.getValue();
      }
      else
      {
        message = "Unexpected " + token.getType() + ", expected one of: " + buildStringList(e.getExpectedTokenTypes());
        // extract the LexicalPhrase from the token's value
        // this is simply a matter of casting in most cases, but for literals it must be extracted differently
        if (token.getType() == ParseType.NAME)
        {
          lexicalPhrase = ((NameAST) token.getValue()).getLexicalPhrase();
        }
        else if (token.getType() == ParseType.INTEGER_LITERAL)
        {
          lexicalPhrase = ((IntegerLiteralAST) token.getValue()).getLexicalPhrase();
        }
        else if (token.getType() == ParseType.FLOATING_LITERAL)
        {
          lexicalPhrase = ((FloatingLiteralAST) token.getValue()).getLexicalPhrase();
        }
        else if (token.getType() == ParseType.CHARACTER_LITERAL)
        {
          lexicalPhrase = ((CharacterLiteralAST) token.getValue()).getLexicalPhrase();
        }
        else if (token.getType() == ParseType.STRING_LITERAL)
        {
          lexicalPhrase = ((StringLiteralAST) token.getValue()).getLexicalPhrase();
        }
        else if (token.getType() == ParseType.SINCE_SPECIFIER)
        {
          lexicalPhrase = ((SinceSpecifierAST) token.getValue()).getLexicalPhrase();
        }
        else if (token.getValue() instanceof LexicalPhrase)
        {
          lexicalPhrase = (LexicalPhrase) token.getValue();
        }
        else
        {
          lexicalPhrase = null;
        }
      }
      printParseError(message, lexicalPhrase);
    }

    return null;
  }

  /**
   * Builds a string representing a list of the specified objects, separated by commas.
   * @param objects - the objects to convert to Strings and add to the list
   * @return the String representation of the list
   */
  private static String buildStringList(Object[] objects)
  {
    StringBuffer buffer = new StringBuffer();
    for (int i = 0; i < objects.length; i++)
    {
      buffer.append(objects[i]);
      if (i != objects.length - 1)
      {
        buffer.append(", ");
      }
    }
    return buffer.toString();
  }

  /**
   * Prints a parse error with the specified message and representing the location(s) that the LexicalPhrases store.
   * @param message - the message to print
   * @param lexicalPhrases - the LexicalPhrases representing the location in the input where the error occurred, or null if the location is the end of input
   */
  private static void printParseError(String message, LexicalPhrase... lexicalPhrases)
  {
    if (lexicalPhrases == null || lexicalPhrases.length < 1)
    {
      System.err.println(message);
      return;
    }
    // make a String representation of the LexicalPhrases' character ranges
    StringBuffer buffer = new StringBuffer();
    for (int i = 0; i < lexicalPhrases.length; i++)
    {
      // line:start-end
      if (lexicalPhrases[i] == null)
      {
        buffer.append("<Unknown Location>");
      }
      else
      {
        buffer.append(lexicalPhrases[i].getLocationText());
      }
      if (i != lexicalPhrases.length - 1)
      {
        buffer.append(", ");
      }
    }
    if (lexicalPhrases.length == 1)
    {
      System.err.println(buffer + ": " + message);
      if (lexicalPhrases[0] != null)
      {
        System.err.println(lexicalPhrases[0].getHighlightedLine());
      }
    }
    else
    {
      System.err.println(buffer + ": " + message);
      for (LexicalPhrase phrase : lexicalPhrases)
      {
        System.err.println(phrase.getHighlightedLine());
      }
    }
  }

}
