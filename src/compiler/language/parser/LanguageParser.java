package compiler.language.parser;

import parser.BadTokenException;
import parser.ParseException;
import parser.Token;

import compiler.language.ast.ParseInfo;
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
      printParseError(e.getMessage(), e.getParseInfo());
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
      ParseInfo parseInfo;
      if (token.getType() == null)
      {
        message = "Unexpected end of input, expected one of: " + buildStringList(e.getExpectedTokenTypes());
        parseInfo = (ParseInfo) token.getValue();
      }
      else
      {
        message = "Unexpected " + token.getType() + ", expected one of: " + buildStringList(e.getExpectedTokenTypes());
        // extract the ParseInfo from the token's value
        // this is simply a matter of casting in most cases, but for literals it must be extracted differently
        if (token.getType() == ParseType.NAME)
        {
          parseInfo = ((NameAST) token.getValue()).getParseInfo();
        }
        else if (token.getType() == ParseType.INTEGER_LITERAL)
        {
          parseInfo = ((IntegerLiteralAST) token.getValue()).getParseInfo();
        }
        else if (token.getType() == ParseType.FLOATING_LITERAL)
        {
          parseInfo = ((FloatingLiteralAST) token.getValue()).getParseInfo();
        }
        else if (token.getType() == ParseType.CHARACTER_LITERAL)
        {
          parseInfo = ((CharacterLiteralAST) token.getValue()).getParseInfo();
        }
        else if (token.getType() == ParseType.STRING_LITERAL)
        {
          parseInfo = ((StringLiteralAST) token.getValue()).getParseInfo();
        }
        else if (token.getType() == ParseType.SINCE_SPECIFIER)
        {
          parseInfo = ((SinceSpecifierAST) token.getValue()).getParseInfo();
        }
        else if (token.getValue() instanceof ParseInfo)
        {
          parseInfo = (ParseInfo) token.getValue();
        }
        else
        {
          parseInfo = null;
        }
      }
      printParseError(message, parseInfo);
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
   * Prints a parse error with the specified message and representing the location(s) that the ParseInfos store.
   * @param message - the message to print
   * @param parseInfo - the ParseInfo representing the location in the input where the error occurred, or null if the location is the end of input
   */
  private static void printParseError(String message, ParseInfo... parseInfos)
  {
    if (parseInfos == null || parseInfos.length < 1)
    {
      System.err.println(message);
      return;
    }
    // make a String representation of the ParseInfos' character ranges
    StringBuffer buffer = new StringBuffer();
    for (int i = 0; i < parseInfos.length; i++)
    {
      // line:start-end
      if (parseInfos[i] == null)
      {
        buffer.append("<Unknown Location>");
      }
      else
      {
        buffer.append(parseInfos[i].getLocationText());
      }
      if (i != parseInfos.length - 1)
      {
        buffer.append(", ");
      }
    }
    if (parseInfos.length == 1)
    {
      System.err.println(buffer + ": " + message);
      if (parseInfos[0] != null)
      {
        System.err.println(parseInfos[0].getHighlightedLine());
      }
    }
    else
    {
      System.err.println(buffer + ": " + message);
      for (ParseInfo info : parseInfos)
      {
        System.err.println(info.getHighlightedLine());
      }
    }
  }

}
