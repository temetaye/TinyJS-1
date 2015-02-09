/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package tinyjs;

import java.io.IOException;
import java.io.Reader;
import java.io.StreamTokenizer;
import tinyjs.Token.Tokens;
//import java.util.regex.Matcher;
//import java.util.regex.Pattern;

/**
 *
 * @author Alexy
 */
public class Tokenizer {
    
 //   public final Pattern regex;
 //   public final int token;
    private StreamTokenizer stream;
    private Tokens nextToken;

    Tokenizer(Reader r) {
        stream = new StreamTokenizer(r);
        stream.resetSyntax();
        stream.eolIsSignificant(false);
        stream.parseNumbers();

        stream.wordChars('a', 'z');
        stream.wordChars('A', 'Z');
        stream.wordChars('0', '9');

        stream.whitespaceChars('\u0000', '\u0020');

        stream.ordinaryChar(',');
        stream.ordinaryChar(':');
        stream.ordinaryChar(';');
        stream.ordinaryChar('+');
        stream.ordinaryChar('-');
        stream.ordinaryChar('*');
        stream.ordinaryChar('/');
        stream.ordinaryChar('{');
        stream.ordinaryChar('}');
        stream.ordinaryChar('(');
        stream.ordinaryChar(')');
        stream.ordinaryChar('&');
        stream.ordinaryChar('|');
        stream.ordinaryChar('=');
        stream.ordinaryChar('!');
        stream.ordinaryChar('\n');

    }

    String getStringValue() {

        if (stream.ttype == StreamTokenizer.TT_WORD) {
            return stream.sval;
        } else if (stream.ttype == StreamTokenizer.TT_NUMBER) {
            return Double.toString(stream.nval);
        }/*else if(stream.sval.matches(""[^"]*"", 1 ) ) {
            
        } */else { 
            return String.valueOf((char) stream.ttype);
            
        }
    }

    Tokens getNextToken() {

        try {
            switch (stream.nextToken()) {
                case StreamTokenizer.TT_EOF:
                    nextToken = Tokens.EOF;
                    break;
                case StreamTokenizer.TT_WORD:
                    String value = getStringValue();
                    if (value.equals("if")) {
                        nextToken = Tokens.KEY_IF;
                    } else if (value.equals("for")) {
                        nextToken = Tokens.KEY_FOR;
                    } else if (value.equals("else")) {
                        nextToken = Tokens.KEY_ELSE;
                    } else if (value.equals("return")) {
                        nextToken = Tokens.KEY_RETURN;
                    } else if (value.equals("var")) {
                        nextToken = Tokens.KEY_VAR;
                    } else if (value.equals("function")) {
                        nextToken = Tokens.KEY_FUNCTION;
                    } else if (value.equals("eval")) {
                        nextToken = Tokens.KEY_EVAL;
                    } else if (value.equals("else")) {
                        nextToken = Tokens.KEY_ELSE;
                    } else if (value.equals("true")) {
                        nextToken = Tokens.KEY_TRUE;
                    } else if (value.equals("false")) {
                        nextToken = Tokens.KEY_FALSE;
                    } else if (value.equals("do")) {
                        nextToken = Tokens.KEY_DO;
                    } else if (value.equals("switch")) {
                        nextToken = Tokens.KEY_SWITCH;
                    } else if (value.equals("case")) {
                        nextToken = Tokens.KEY_CASE;
                    } else if (value.equals("default")) {
                        nextToken = Tokens.KEY_DEFAULT;
                    } else if (value.equals("println")) {
                        nextToken = Tokens.KEY_PRINTLN;
                    } else if (value.equals("while")) {
                        nextToken = Tokens.KEY_WHILE;
                    } else if (value.equals("break")) {
                        nextToken = Tokens.KEY_BREAK;
                    } else if (value.equals("continue")) {
                        nextToken = Tokens.KEY_CONTINUE;
                    } else {
                        nextToken = Tokens.ID;
                    }
                    break;
                case StreamTokenizer.TT_NUMBER:
                    nextToken = Tokens.NUMBER;
                    break;
            //    case '"([^\"]*"':
              //      nextToken = Tokens.STRING;
                //    break;
                case '{':
                    nextToken = Tokens.OPEN_PAR;
                    break;
                case '}':
                    nextToken = Tokens.CLOSE_PAR;
                    break;
                case '(':
                    nextToken = Tokens.OPEN_BRACKET;
                    break;
                case ')':
                    nextToken = Tokens.CLOSE_BRACKET;
                    break;
                case ':':
                    nextToken = Tokens.COLON;
                    break;
                case '=':
                    if (stream.nextToken() == '=') {
                        nextToken = Tokens.EQUAL_SIGN;
                    } else {
                        stream.pushBack();
                        nextToken = Tokens.ASSIGN;
                    }
                    break;
                case '>':
                    if (stream.nextToken() == '=') {
                        nextToken = Tokens.GREATER_EQUAL_SIGN;
                    } else {
                        stream.pushBack();
                        nextToken = Tokens.GREATER_SIGN;
                    }
                    break;
                case '<':
                    if (stream.nextToken() == '=') {

                        nextToken = Tokens.LESS_EQUAL_SIGN;
                    } else {
                        stream.pushBack();
                        nextToken = Tokens.LESS_SIGN;
                    }
                    break;
                case '!':
                    if (stream.nextToken() == '=') {
                        nextToken = Tokens.NOT_EQUAL;
                    } else {
                        stream.pushBack();
                        nextToken = Tokens.NEGATION;
                    }
                    break;
                case '&':
                    if (stream.nextToken() == '&') {
                        nextToken = Tokens.LOGICAL_AND;
                    }
                    break;
                case '|':
                    if (stream.nextToken() == '|') {
                        nextToken = Tokens.LOGICAL_OR;
                    }
                    break;
                case '+':
                    int newValToken = stream.nextToken();
                    if (newValToken == '+') {
                        nextToken = Tokens.UNIT_INCR;
                    } else if (newValToken == '=') {
                        nextToken = Tokens.ASSIGN_INCR;
                    } else {
                        nextToken = Tokens.PLUS_SIGN;
                        stream.pushBack();
                    }
                    break;
                case '-':
                    int newValTokenMinus = stream.nextToken();
                    if (newValTokenMinus == '-') {
                        nextToken = Tokens.UNIT_DECR;
                    } else if (newValTokenMinus == '=') {
                        nextToken = Tokens.ASSIGN_DECR;
                    } else {
                        stream.pushBack();
                        nextToken = Tokens.MINUS_SIGN;
                    }
                    break;
                case '*':
                    nextToken = Tokens.MULT_SIGN;
                    break;
                case '/':
                    if (stream.nextToken() == '/') {
                        nextToken = Tokens.COMMENT;
                    } else {
                        stream.pushBack();
                        nextToken = Tokens.DIV_SIGN;
                    }
                    break;
                case '\n':
                    nextToken = Tokens.EOL;
                    break;
                case ',':
                    nextToken = Tokens.COMMA;
                    break;
                case ';':
                    nextToken = Tokens.SEMI_COLON;
                    break;
                case '"':
                    nextToken = Tokens.QUOTE;
                    break;
                default:
                    nextToken = Tokens.UNKNOWN;
            }
        } catch (IOException e) {
            nextToken = Tokens.EOF;

        }
        return nextToken;

    }

    public static boolean isStm(Tokens t) {
        switch (t) {
            case KEY_IF:
            //case KEY_ELSE:
            case KEY_WHILE:
            case KEY_FOR:
            case KEY_DO:
            case KEY_BREAK:
            case KEY_CONTINUE:
            case KEY_PRINTLN:
            case KEY_RETURN:
            case KEY_SWITCH:
            case KEY_EVAL:
            case ID:
            case NUMBER: //Needs revision
                return true;
            default:
                return false;
        }
    }

}
