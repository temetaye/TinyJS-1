/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package tinyjs;

/**
 *
 * @author Alexy
 */
public class Token {

    enum Tokens{ 
        KEY_IF, KEY_FOR, KEY_ELSE, KEY_RETURN, KEY_VAR,KEY_FUNCTION, KEY_EVAL, KEY_TRUE , KEY_FALSE, 
        KEY_DO,KEY_SWITCH,KEY_CASE,KEY_DEFAULT,KEY_PRINTLN, KEY_WHILE,KEY_BREAK,KEY_CONTINUE,ID,
        OPEN_PAR, CLOSE_PAR, OPEN_BRACKET, CLOSE_BRACKET,
        COLON, EQUAL_SIGN,ASSIGN, GREATER_SIGN, LESS_SIGN, GREATER_EQUAL_SIGN, LESS_EQUAL_SIGN, NOT_EQUAL, 
        LOGICAL_AND, LOGICAL_OR, PLUS_SIGN, MINUS_SIGN, MULT_SIGN,NEGATION,
        DIV_SIGN, UNIT_INCR, ASSIGN_INCR, UNIT_DECR, ASSIGN_DECR, COMMA, SEMI_COLON, EOL, QUOTE,STRING,
        NUMBER, EOF, COMMENT, UNKNOWN, UNDEFINED
    }
}
