/*
 *     ICE (Interval Calculator for Engineer) is a programmable calculator working on intervals.
 *     Copyright (C) 2009  Simone Pernice
 *
 *     This program is free software: you can redistribute it and/or modify
 *     it under the terms of the GNU General Public License as published by
 *     the Free Software Foundation, either version 3 of the License, or
 *     (at your option) any later version.
 *
 *     This program is distributed in the hope that it will be useful,
 *     but WITHOUT ANY WARRANTY; without even the implied warranty of
 *     MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *     GNU General Public License for more details.
 *
 *     You should have received a copy of the GNU General Public License
 *     along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package engcalculatorConsole.iceJTextArea;

import engcalculator.AsynchronousOutput;
import engcalculator.expression.token.TknFunctionInfixPrefix;
import engcalculator.expression.token.TknTerminal;
import engcalculator.function.Function;
import engcalculator.function.infix.FunctionInfix;
import engcalculator.function.prefix.FunctionPrefix;
import engcalculator.function.variable.FunctionVariable;
import java.awt.Color;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.LinkedList;
import javax.swing.JTextArea;
import javax.swing.text.BadLocationException;
import javax.swing.text.DefaultHighlighter;
import javax.swing.text.Highlighter;

/**
 *
 * @author Simone Pernice <pernice@libero.it>
 */
public class ICEJTextArea extends JTextArea implements KeyListener, MouseListener {

    public final static int RB = 0, SB = 1, CB = 2, AP = 3;

    public static int[] countBracketsBalance(String input) {
        int[] res = new int[4];

        for (char c : input.toCharArray()) {
            switch (c) {
                case '(':
                    ++res[RB];
                    break;
                case ')':
                    --res[RB];
                    break;
                case '[':
                    ++res[SB];
                    break;
                case ']':
                    --res[SB];
                    break;
                case '{':
                    ++res[CB];
                    break;
                case '}':
                    --res[CB];
                    break;
                case '\'':
                    ++res[AP];
                    break;
            }
        }

        return res;
    }

    public static int addMissingBrackets(int[] brackets, StringBuilder inp, int place) {
        int oldPlace = place;

        while (brackets[SB] > 0) {
            inp.insert(place, ']');
            --brackets[SB];
            ++place;
        }

        while (brackets[CB] > 0) {
            inp.insert(place, '}');
            --brackets[CB];
            ++place;
        }

        while (brackets[RB] > 0) {
            inp.insert(place, ')');
            --brackets[RB];
            ++place;
        }

        return place - oldPlace;
    }

    private final AsynchronousOutput output;
    private final KeyListener keyListener;
    private final EditStepStorage editStepStorage;
    private boolean preprocessor;

    public ICEJTextArea(AsynchronousOutput output, KeyListener keyListener) {
        this.preprocessor = true;
        this.output = output;
        this.keyListener = keyListener;
        editStepStorage = new EditStepStorage();
        addKeyListener(this);
        addMouseListener(this);
    }
    
    public void clearUndo () {
        editStepStorage.clear();
    }

    public void setPreprocessor(boolean preprocessor) {
        this.preprocessor = preprocessor;
    }

    public void keyPressed(KeyEvent evt) {
        if (keyListener != null) {
            keyListener.keyPressed(evt);
        }
    }

    public void keyReleased(KeyEvent evt) {
        if (evt.isControlDown()) {
            switch (evt.getKeyCode()) {
                case KeyEvent.VK_E:
                    printLastFunctionHelp();
                    break;
                case KeyEvent.VK_Z:
                    undo();
                    break;
                case KeyEvent.VK_Y:
                    redo();
                    break;
            }
        } else {
            highlightMatchingBrackets();
            editStepStorage.addUndoStep(new EditStep(getText(), getCaretPosition()));
        }

        if (keyListener != null) {
            keyListener.keyReleased(evt);
        }
    }

    public void keyTyped(KeyEvent evt) {
        if (!evt.isControlDown()) {
            switch (evt.getKeyChar()) {
                case '\t':
                    suggestFunctionOrVariable();
                    break;
                case '(':
                    addCharacter(')');
                    break;
                case '\'':
                    addCharacter('\'');
                    break;
                case '[':
                    addCharacter(']');
                    break;
                case '{':
                    addCharacter('}');
                    break;
            }
        }
        if (keyListener != null) {
            keyListener.keyTyped(evt);
        }
    }

    public void undo() {
        EditStep es = editStepStorage.undoStep();
        if (es != null) {
            setText(es.getText());
            setCaretPosition(es.getCaret());
        }
    }

    public void redo() {
        EditStep es = editStepStorage.redoStep();
        if (es != null) {
            setText(es.getText());
            setCaretPosition(es.getCaret());
        }
    }

    public void printLastFunctionHelp() {
        if (output == null) {
            return;
        }
        final String it = getText();
        if (it.length() == 0) {
            output.printOnOutputAreaInItalic("No help found for: ''\n");
            return;
        }
        int pos = getCaretPosition();

        //If it is after the end of a function, it goes to the end of first function (not terminal chars) on the left
        final String TKN = new StringBuilder(TknTerminal.TERMINALS).append(" '$").toString();
        --pos;
        while (pos >= 0) {
            if (TKN.indexOf(it.charAt(pos)) == -1) {
                break;
            }
            --pos;
        }
        if (pos < 0) {
            pos = 0;
        }
        final char c = it.charAt(pos);

        //It goes back to the start of current function from pos
        int s = 0, tmp;
        while ((tmp = (s + pos - 1)) >= 0) {
            if (!isFunctionCharAndSameType(c, it.charAt(tmp))) {
                break;
            }
            --s;
        }

        //It goes forward to the end of current function from pos
        int e = 1;
        while ((tmp = e + pos) < it.length()) {
            if (!isFunctionCharAndSameType(c, it.charAt(tmp))) {
                break;
            }
            ++e;
        }

        final String func = it.substring(pos + s, pos + e);

        Function f;
        boolean found = false;

        f = FunctionPrefix.getFunction(func);
        if (f != null) {
            output.printOnOutputAreaInItalic(f.getHelp());
            found = true;
        }

        f = FunctionInfix.getFunction(func);
        if (f != null) {
            output.printOnOutputAreaInItalic(f.getHelp());
            found = true;
        }

        f = FunctionVariable.getFunction(func);
        if (f != null) {
            output.printOnOutputAreaInItalic(f.getHelp());
            found = true;
        }

        if (!found) {
            output.printOnOutputAreaInItalic("No help found for: '" + func + "'\n");
        }
    }

    private boolean isFunctionCharAndSameType(char a, char b) {
        if (Character.isLetterOrDigit(a) && Character.isLetterOrDigit(b)) {
            return true;
        }
        return TknFunctionInfixPrefix.isOperator(a) && TknFunctionInfixPrefix.isOperator(b);
    }

    private void addCharacter(char c) {
        if (!preprocessor) {
            return;
        }
        String input = getText();
        switch (c) {
            case ')':
                if (countBracketsBalance(input)[RB] < 0) {
                    return;
                }
                break;
            case ']':
                if (countBracketsBalance(input)[SB] < 0) {
                    return;
                }
                break;
            case '}':
                if (countBracketsBalance(input)[CB] < 0) {
                    return;
                }
                break;
            case '\'':
                if ((countBracketsBalance(input)[AP] & 1) != 0) {
                    return;
                }
                break;
        }
        StringBuilder out = new StringBuilder(input);
        int cp = getCaretPosition();
        out.insert(cp, c);
        setText(out.toString());
        setCaretPosition(cp);
    }

    public void suggestFunctionOrVariable() {
        String inputline, outputline = "", subset;
        String textleft, textright;
        LinkedList<String> matchingList;
        int i, j, k;
        LinkedList<String> dataList = Function.getFunctionSymbols(false);

        //find the input string indexes
        textleft = getText();
        j = textleft.indexOf("\t");
        k = 1;
        if (j == -1) {//there is not any tab, the function was recalled by menu
            j = getCaretPosition();
            k = 0;
        }
        textright = textleft.substring(j + k);
        textleft = textleft.substring(0, j);

        j = textleft.length();
        i = j - 1;
        if (i >= 0) {
            char c1 = textleft.charAt(i);
            while (i >= 0) {
                if (!isFunctionCharAndSameType(c1, textleft.charAt(i))) {
                    break;
                }
                --i;
            }
        }

        inputline = textleft.substring(i + 1, j).trim();
        matchingList = getMatch(inputline, dataList, true);

        if (matchingList.size() == 1) {
            outputline = matchingList.getFirst();
            int pos; //1 == end, 0 a char before end, ...
            if (outputline.charAt(outputline.length() - 1) == '(') {
                if (i > -1 && textleft.charAt(i) == '$') {//remove ending ( because begins with $
                    outputline = outputline.substring(0, outputline.length() - 1);
                    pos = 1;
                } else {
                    outputline += ')';
                    pos = 0;
                }
                printOnInputArea(i, outputline.concat(textright));
            } else {
                printOnInputArea(i, outputline.concat(textright));
                pos = 1;
            }
            setCaretPosition(pos + i + outputline.length());
        } else if (matchingList.size() > 0) {
            subset = getSubset(inputline, matchingList);
            if (subset.length() == inputline.length()) {
                for (k = 0; k < matchingList.size(); ++k) {
                    String s1 = matchingList.get(k).concat("   ");
                    if (s1 != null) {
                        outputline = outputline.concat(s1);
                    }
                }
                output.printOnOutputAreaInItalic("SUGGESTIONS: " + outputline + "\n");
                printOnInputArea(-1, textleft.concat(textright));
                setCaretPosition(textleft.length());
            } else {
                printOnInputArea(i, subset.concat(textright));
                setCaretPosition(1 + i + subset.length());
            }

        } else {
            printOnInputArea(-1, textleft.concat(textright));
            setCaretPosition(textleft.length());
        }

    }

    public void printOnInputArea(int start, String in) {
        String inputline;

        inputline = getText().substring(0, start + 1);

        setText(inputline + in);
    }

    private Object firstBracketHighlight, secondBracketHighlight;
    private final static DefaultHighlighter.DefaultHighlightPainter HIGHLIGHTER = new DefaultHighlighter.DefaultHighlightPainter(Color.YELLOW);

    private void highlightMatchingBrackets() {
        Highlighter h = getHighlighter();
        if (firstBracketHighlight != null) {
            h.removeHighlight(firstBracketHighlight);
            firstBracketHighlight = null;
        }
        if (secondBracketHighlight != null) {
            h.removeHighlight(secondBracketHighlight);
            secondBracketHighlight = null;
        }

        String inputText = getText();
        if (inputText.length() < 2) {
            return;
        }

        int pos = getCaretPosition() - 1;
        if (pos < 0) {
            return;
        }

        char bra = inputText.charAt(pos);
        int dir;
        char braOpp;
        switch (bra) {
            case ')':
                braOpp = '(';
                dir = -1;
                break;
            case ']':
                braOpp = '[';
                dir = -1;
                break;
            case '}':
                braOpp = '{';
                dir = -1;
                break;
            case '(':
                braOpp = ')';
                dir = 1;
                break;
            case '[':
                braOpp = ']';
                dir = 1;
                break;
            case '{':
                braOpp = '}';
                dir = 1;
                break;
            default:
                return;
        }

        int deep = 1;
        int i = pos;
        i += dir;
        while (i >= 0 && i < inputText.length()) {
            char c = inputText.charAt(i);
            if (c == bra) {
                ++deep;
                if (deep == 0) {
                    break;
                }
            } else if (c == braOpp) {
                --deep;
                if (deep == 0) {
                    break;
                }
            }

            i += dir;
        }
        if (deep != 0) {
            return;
        }

        try {
            firstBracketHighlight = h.addHighlight(pos, pos + 1, HIGHLIGHTER);
            secondBracketHighlight = h.addHighlight(i, i + 1, HIGHLIGHTER);
        } catch (BadLocationException ex) {
            //not possible to highlight
        }
    }

    private LinkedList<String> getMatch(String s, LinkedList<String> dataList, boolean isCaseSensitive) {
        LinkedList<String> mList = new LinkedList<String>();

        if (!isCaseSensitive) {
            s = s.toLowerCase();
        }

        for (String s1 : dataList) {
            if (s1 != null) {
                if (!isCaseSensitive) {
                    s1 = s1.toLowerCase();
                }
                if (s1.startsWith(s)) {
                    mList.add(s1);
                }
            }
        }

        return mList;
    }

    private String getSubset(String inputline, LinkedList<String> matchingList) {
        String s1 = inputline;
        boolean equal = true;

        int j = inputline.length();
        while (equal) {
            if (j > matchingList.getFirst().length()) {
                ++j;
                break;
            }

            s1 = matchingList.getFirst().substring(0, j);
            ++j;
            for (int k = 0; k < matchingList.size(); ++k) {
                if (matchingList.get(k).startsWith(s1)) {
                    equal = true;
                } else {
                    equal = false;
                    break;
                }
            }
        }

        return s1.substring(0, j - 2);
    }

    public void mouseClicked(MouseEvent e) {
    }

    public void mousePressed(MouseEvent e) {
    }

    public void mouseReleased(MouseEvent e) {
        highlightMatchingBrackets();
    }

    public void mouseEntered(MouseEvent e) {
    }

    public void mouseExited(MouseEvent e) {
    }

}
