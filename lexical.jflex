import java_cup.runtime.*;

%%

%class Lexer
%unicode
%cup
%line
%column

%{
  StringBuffer string = new StringBuffer();

  private Symbol symbol(int type) {
    return new Symbol(type, yyline, yycolumn);
  }
  private Symbol symbol(int type, Object value) {
    return new Symbol(type, yyline, yycolumn, value);
  }
%}

//Macros from http://www.quut.com/c/ANSI-C-grammar-l-2011.html with minor changes
O = [0-7]
D = [0-9]
NZ = [1-9]
L = [a-zA-Z_]
A = [a-zA-Z_0-9]
H = [a-fA-F0-9]
HP = (0[xX])
E = ([Ee][+-]?{D}+)
P = ([Pp][+-]?{D}+)
FS = (f|F|l|L)
IS = (((u|U)(l|L|ll|LL)?)|((l|L|ll|LL)(u|U)?))
CP = (u|U|L)
SP = (u8|u|U|L)
ES = (\\([\'\"\?\\abfnrtv]|[0-7]{1,3}|x[a-fA-F0-9]+))
WS = \r|\n|\r\n

Identifier = {L}{A}*
InputCharacter = [^\r\n]
DecIntegerLiteral = 0 | {NZ}{D}*

/* comments */
Comment = {TraditionalComment} | {EndOfLineComment}

TraditionalComment   = "/*" [^*] ~"*/" | "/*" "*"+ "/"
// Comment can be the last line of the file, without line terminator.
EndOfLineComment     = "//" {InputCharacter}* {WS}?

%%

<YYINITIAL> {
  /* identifiers */ 
  {Identifier}                      { return symbol(sym.IDENTIFIER); }
 
  /* literals */
  {DecIntegerLiteral}               { return symbol(sym.INTEGER_LITERAL); }
  ({SP}?\"([^\"\\\n]|{ES})*\"{WS}*)+ { return symbol(sym.STRING_LITERAL); }

  /* operators */
  "="                               { return symbol(sym.EQ); }
  "=="                              { return symbol(sym.EQEQ); }
  "+"                               { return symbol(sym.PLUS); }

  /* comments */
  {Comment}                         { /* ignore */ }
 
  /* whitespace */
  {WS}                              { /* ignore */ }
}