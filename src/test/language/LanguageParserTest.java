package test.language;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;

import compiler.language.ast.topLevel.CompilationUnit;
import compiler.language.parser.LanguageParser;
import compiler.language.parser.LanguageTokenizer;

/*
 * Created on 19 Sep 2010
 */

/**
 * @author Anthony Bryant
 */
public class LanguageParserTest
{

  public static void main(String[] args) throws IOException
  {
    if (args.length < 1)
    {
      System.err.println("Usage: java test.language.LanguageParserTest <SourceFile>");
      System.exit(1);
    }

    File file = new File(args[0]);
    if (!file.isFile())
    {
      System.err.println("Could not find source file: " + file.getAbsolutePath());
      System.exit(1);
    }
    LanguageTokenizer tokenizer = new LanguageTokenizer(new FileReader(file));

    LanguageParser parser = new LanguageParser();

    CompilationUnit compilationUnit = parser.parse(tokenizer);
    tokenizer.close();

    if (compilationUnit != null)
    {
      System.out.println("Successfully parsed " + file.getAbsolutePath());
      System.out.println(compilationUnit);
    }
  }

}
