/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package tinyjs;

import java.io.BufferedReader;
import java.io.FileReader;
import tinyjs.Token.Tokens;

/**
 *
 * @author kebe
 */
public class P1 {

    private Tokenizer lookahead;
    private Tokens token;
    private Tokens test;

    public P1() {
        // init parser ... 
        try {
            FileReader fr = new FileReader("test.txt");
            BufferedReader in = new BufferedReader(fr);
            lookahead = new Tokenizer(in);
            token = lookahead.getNextToken();

        } catch (Exception ex) {
            System.out.println("Error " + ex.getMessage());
        }
    }

    public void startParsing() {
        while (token != Tokens.EOF) {
            try {
                mixStatments();
            } catch (Exception ex) {

            }
        }
    }

    private void mixStatments() throws ParserException {

        if (Tokenizer.isStm(token)) {

            statement();
            next();
            if (Tokenizer.isStm(token) || token == Tokens.KEY_VAR || token == Tokens.KEY_FUNCTION) {
                mixStatments();

            }

        } else if (token == Tokens.KEY_VAR) {
            next();
            parseVarDecAssign();
            if (Tokenizer.isStm(token) || token == Tokens.KEY_VAR || token == Tokens.KEY_FUNCTION) {
                
                mixStatments();

            }

        } else if(token == Tokens.KEY_FUNCTION){
            System.out.println("Function found");
            next();
            parseFunction();
            if (Tokenizer.isStm(token) || token == Tokens.KEY_VAR || token == Tokens.KEY_FUNCTION) {
                 next();
                mixStatments();

            }
        }
        

    }

    private void parseVarDecAssign() throws ParserException {
        expect(Tokens.ID);
        System.out.println("var name " + lookahead.getStringValue().toString());
        next();
        declaration();
        expect(Tokens.SEMI_COLON);
        next();
    }

    /*
     *  Program: MixStatements
     Block: { MixStatements }
     MixStatements : Statement MixStatements | "var" Identifier Declaration MixStatements | Function MixStatements | "ε"
     Function: "function" Identifier ( ParameterList ) Block
     ParameterList: Identifier MoreParams 
     MoreParams: "," ParameterList | "ε"
     */
    private void statement() throws ParserException {
        switch (token) {
            case KEY_IF:
                System.out.println("IF Found");
                next();
                condition();
                block();
                //next();

                if (token == Tokens.KEY_ELSE) {
                    next();
                    block();
                   // next();

                }

//                Node ifNode = new Node(Node.NodeType.IF);
//                ifNode.addChild(exp);
//                ifNode.addChild(thenB);
//                ifNode.addChild(elseB);
//                n.addChild(ifNode);
                break;

            case KEY_WHILE:
                System.out.println("entering while loop");
                next();
                condition();
                block();
                break;
            case KEY_DO:
                System.out.println("entering do while loop");
                next();
                block();
                expect(Tokens.KEY_WHILE);
                next();
                condition();
                expect(Tokens.SEMI_COLON);
                break;           

            case KEY_FOR:
                System.out.println("For loop found");
                next();
                parseForLoop();
                break;
            case KEY_SWITCH:
                System.out.println("parsing switch cases....");
                next();
                parseSwitch();
                break;
            case KEY_BREAK:
                next();
                expect(Tokens.SEMI_COLON);
                System.out.println("breaking out.....");
                break;
            case KEY_CONTINUE:
                next();
                expect(Tokens.SEMI_COLON);
                System.out.println("continueing to the next .....");
                break;
            case KEY_RETURN:
                next();
                expression();
                expect(Tokens.SEMI_COLON);
                System.out.println("returning results....   ");
                break;
            case KEY_PRINTLN:
                System.out.println("printing output ...");
                next();
                expect(Tokens.OPEN_BRACKET);
                next();
                expression();
                expect(Tokens.CLOSE_BRACKET);
                next();
                expect(Tokens.SEMI_COLON);
                break;
            case ID:
                next();
                expect(Tokens.ASSIGN);
                next();
                expression();
                expect(Tokens.SEMI_COLON);
                break;
            case KEY_EVAL:
                next();
                parseEval();
                expect(Tokens.SEMI_COLON);
                break;
            default:
                break;
        }
    }

    private void parseForLoop() throws ParserException {
        expect(Tokens.OPEN_BRACKET);
        next();
        expect(Tokens.ID);
        next();
        expect(Tokens.ASSIGN);
        next();
        expression();
        expect(Tokens.SEMI_COLON);
        next();
        expression();
        expect(Tokens.SEMI_COLON);
        next();
        expect(Tokens.ID);
        next();
        expect(Tokens.ASSIGN);
        System.out.println("Assign found ");
        next();
        expression();  // expression has next inside
        expect(Tokens.CLOSE_BRACKET);
        next();
        block();

    }

    // private Node Condition() throws ParserException {
    private void condition() throws ParserException {
        expect(Tokens.OPEN_BRACKET);
        next();

        // Node exp = Exp();
        expression(); // go to expresssion (x==y)

        expect(Tokens.CLOSE_BRACKET);
        System.out.println("Close brace is done and condtion is full we think !");
        next();
        //  return exp;
    }

    private void expression() throws ParserException {

        andExpression(); // naming prob
        // next();
        if (token == Tokens.LOGICAL_OR) {
            moreAndExps();
        }
        /* check more expression*/
    }

    public void andExpression() throws ParserException {
        uniaryRelExp();
        // next();
        if (token == Tokens.LOGICAL_AND) {
            next();
            moreUnaryRelExp();
        }
    }

    private void uniaryRelExp() throws ParserException {
        if (token == Tokens.NOT_EQUAL) {
            next();
            uniaryRelExp();
        } else {
            realEXP();
        }
    }

    private void realEXP() throws ParserException {
        sumExp();
        // next();
        moreSumExp();
    }

    private void next() {
        token = lookahead.getNextToken();
    }

    private void expect(Tokens nextToken) throws ParserException {
        if (token != nextToken) {
            throw new ParserException("Error: " + nextToken + " expected" + " but found " + token);
        }
    }

    private void moreAndExps()
            throws ParserException {
        next();
        andExpression();
    }

    private void moreUnaryRelExp() throws ParserException {
        // next();
        uniaryRelExp();
    }

    private void sumExp() throws ParserException {
        term();
        // next();
        if (token == Tokens.PLUS_SIGN || token == Tokens.MINUS_SIGN) {

            System.out.println(" plus value is recevied " + token);
            next();
            moreTerms();
        }
    }

    private void moreSumExp() throws ParserException {
        switch (token) {
            case LESS_EQUAL_SIGN:
            case LESS_SIGN:
            case GREATER_EQUAL_SIGN:
            case GREATER_SIGN:
            case EQUAL_SIGN:
            case NOT_EQUAL:

                System.out.println("Token relational token like equal qual " + Tokens.valueOf(token.toString()));
                next();
                sumExp();
        }
    }

    private void term() throws ParserException {
        unaryExp();
        // next();
        if (token == Tokens.MULT_SIGN || token == Tokens.DIV_SIGN) {
            next();
            moreUnaryExps();
        }
    }

    private void moreTerms() throws ParserException {
        //next();
        term();
    }

    private void unaryExp() throws ParserException {
        if (token == Tokens.MINUS_SIGN) {
            next();
            unaryExp();
        } else {
            factor();
        }
    }

    private void moreUnaryExps() throws ParserException {
        //  next();
        unaryExp();
    }

    private void factor() throws ParserException {
        if (token == Tokens.OPEN_BRACKET) {
            next();
            expression();
            next();   //  we will check if the expression is next or not 
            expect(Tokens.CLOSE_BRACKET);
        } else if (token == Tokens.NUMBER || token == Tokens.KEY_TRUE || token == Tokens.KEY_FALSE) { // no String for now
            parseConstants();
            System.out.println("Constant found and the value is  " + Tokens.valueOf(token.toString()));
            next();
        } else if (token == Tokens.ID) {
            System.out.println("IDT is found " + token + " value " + lookahead.getStringValue());
            next();
            if (token == Tokens.OPEN_BRACKET) {
                next();
                if (token == Tokens.CLOSE_BRACKET) {
                    System.out.println("Function with out params");
                    next();
                    expect(Tokens.SEMI_COLON);
                } else {
                    params();
                }
            }

        }

    }

    private void params() throws ParserException {
        paramList();
    }

    private void paramList() throws ParserException {
        expression();
        if (token == Tokens.COMMA) {
            next();
            paramList();
        } else {
            System.out.println("Function with parameters is found" + token.toString() + lookahead.getStringValue());
            next();
            expect(Tokens.SEMI_COLON);
        }
    }

    private void declaration() throws ParserException {
        if (token == Tokens.COMMA) {
            moreVar();
        } else if (token == Tokens.ASSIGN) {
            next();
            expression();
        }

    }

    private void moreVar() throws ParserException {
        expect(Tokens.ID);
        declaration();
    }

    private void block() throws ParserException {
        expect(Tokens.OPEN_PAR);
        next();
        mixStatments();
       // next();
        expect(Tokens.CLOSE_PAR);
        next();
    }

    private void parseFunction() throws ParserException{
        expect(Tokens.ID);
        next();
        expect(Tokens.OPEN_BRACKET);
        next();
        if(token == Tokens.CLOSE_BRACKET){
            System.out.println("function with out params" );
            next();
            block();
        }else{
            System.out.println("function with params found");
            parseParameter();
            if(token == Tokens.CLOSE_BRACKET){
                next();
                System.out.println("parameters are parsed ");
                block();
            }
            
        } 
            
    }

    private void parseParameter()throws ParserException{
        expect(Tokens.ID);
        next();
        if(token == Tokens.COMMA){
            moreParams();
        }
        
    }

    private void moreParams()throws ParserException{
        parseParameter();
    
    }

    private void parseSwitch() throws ParserException{
        expect(Tokens.OPEN_BRACKET);
        next();
        expect(Tokens.ID);
        next();
        expect(Tokens.CLOSE_BRACKET);
        next();
        expect(Tokens.OPEN_PAR);
        next();
        ParseCaseBlock();
        expect(Tokens.CLOSE_PAR);
        
    }

    private void ParseCaseBlock()throws ParserException{
        if(token == Tokens.KEY_CASE){
            next();
            caseClouses();
        }else if (token == Tokens.KEY_DEFAULT){
            next();
            defaultClause();
        }
    }

    private void caseClouses() throws ParserException{
        caseClouse();
        if(token == Tokens.KEY_CASE){
            next();
            caseClouses();
        } else if(token == Tokens.KEY_DEFAULT){
            next();
            defaultClause();
        }
    }
    private void caseClouse() throws ParserException{
        System.out.println("parsing case ......"+token);
        parseConstants();
        next();
        expect(Tokens.COLON);
        next();
        mixStatments();
        next();
        
        
        
    }
    private void defaultClause()throws ParserException{
        System.out.println("parsing the default case ..." + token);
        expect(Tokens.COLON);
        next();
        mixStatments();
        next();
        if(token == Tokens.KEY_CASE){
            next();
            caseClouses();
        }
        
    }

    private void parseConstants() throws ParserException{
        if (token == Tokens.NUMBER || token == Tokens.KEY_TRUE || token == Tokens.KEY_FALSE) {
            
        }else {
            System.out.println("Error: unsupported case value is used "+ token);
            
        }
    }

    private void parseEval() throws ParserException{
        System.out.println("parsing eval expression ....");
        expect(Tokens.OPEN_BRACKET);
        next();
        evalParam();
        expect(Tokens.CLOSE_BRACKET);
        next();
        
        
    }

    private void evalParam()throws ParserException{
        System.out.println("this is eval param...." + lookahead.getStringValue());
        expect(Tokens.ID);
        next();
    }

   
}
