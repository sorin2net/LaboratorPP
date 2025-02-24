import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.*;
import java.util.Stack;

public class Calculator extends JFrame{
    JButton digits[] ={
            new JButton(" 0 "),
            new JButton(" 1 "),
            new JButton(" 2 "),
            new JButton(" 3 "),
            new JButton(" 4 "),
            new JButton(" 5 "),
            new JButton(" 6 "),
            new JButton(" 7 "),
            new JButton(" 8 "),
            new JButton(" 9 ")
    };

    JButton operators[] ={
            new JButton(" + "), 
            new JButton(" - "), 
            new JButton(" * "), 
            new JButton(" / "), 
            new JButton(" = "), 
            new JButton(" C "), 
            new JButton(" ( "), 
            new JButton(" ) ") 
    };

    String oper_values[] ={"+", "-", "*", "/", "=", "", "(", ")"};

    JTextArea area = new JTextArea(3, 5);

    public static void main(String[] args){
        Calculator calculator = new Calculator();
        calculator.setSize(300, 300);
        calculator.setTitle(" Java-Calc, PP Lab1 ");
        calculator.setResizable(false);
        calculator.setVisible(true);
        calculator.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    public Calculator(){
        add(new JScrollPane(area), BorderLayout.NORTH);
        JPanel buttonpanel = new JPanel();
        buttonpanel.setLayout(new FlowLayout());

        for (int i = 0; i < 10; i++)
            buttonpanel.add(digits[i]);

        for (int i = 0; i < 8; i++)
            buttonpanel.add(operators[i]);

        add(buttonpanel, BorderLayout.CENTER);
        area.setForeground(Color.BLACK);
        area.setBackground(Color.WHITE);
        area.setLineWrap(true);
        area.setWrapStyleWord(true);
        area.setEditable(false);

        for (int i = 0; i < 10; i++){
            int finalI = i;
            digits[i].addActionListener(new ActionListener(){
                @Override
                public void actionPerformed(ActionEvent actionEvent){
                    area.append(Integer.toString(finalI));
                }
            });
        }

        for (int i = 0; i < 8; i++){
            int finalI = i;
            operators[i].addActionListener(new ActionListener(){
                @Override
                public void actionPerformed(ActionEvent actionEvent){
                    if (finalI == 5) // Butonul "C"
                        area.setText("");
                    else if (finalI == 4){ // Butonul "="
                        try{
                            String expresie = area.getText();
                            String postfinalIx = postfinalIx(expresie); //convertire
                            double rez = calcul(postfinalIx);           //calcul
                            area.append(" = " + rez);
                        } catch (Exception e){
                            area.setText(" !!!Probleme!!! ");
                        }
                    } else{
                        area.append(oper_values[finalI]); // Butonul (
                    }
                }
            });
        }
    }

    // Prelucram din infixata in postfixata
    public String postfinalIx(String expresie){
        StringBuilder rez=new StringBuilder();
        Stack<Character> stack=new Stack<>();
        for (int i=0;i<expresie.length();i++)
        {
            char c=expresie.charAt(i);
            if (Character.isDigit(c))
                rez.append(c);
            else if (c=='(')
            {
                stack.push(c);
            }
            else if (c==')')
            {
                while (!stack.isEmpty() && stack.peek()!='(')
                {
                    rez.append(" ").append(stack.pop());
                }
                stack.pop();
            }
            else if (esteoperator(c))
            {
                rez.append(" ");
                while (!stack.isEmpty() && prioritate(stack.peek())>=prioritate(c))
                {
                    rez.append(stack.pop()).append(" ");
                }
                stack.push(c);
            }
        }

        while (!stack.isEmpty())
        {
            rez.append(" ").append(stack.pop());
        }

        return rez.toString();
    }
    public int prioritate(char caracter){
        switch (caracter){
            case '+':
                return 1;
            case '-':
                return 1;
            case '*':
                return 2;
            case '/':
                return 2;
            default:
                return -1;
        }
    }
    //Calculeaza rezultatul din forma postfixata
    public double calcul(String expresie){
        Stack<Double> stack=new Stack<>();
        String[] elemente=expresie.split(" ");

        for (String elementcurent : elemente)
        {
            if (Character.isDigit(elementcurent.charAt(0)))
                stack.push(Double.valueOf(elementcurent));
            else if (esteoperator(elementcurent.charAt(0)))
            {
                double x=stack.pop();
                double y=stack.pop();
                switch (elementcurent.charAt(0))
                {
                    case '+':
                        stack.push(x + y);
                        break;
                    case '-':
                        stack.push(x - y);
                        break;
                    case '*':
                        stack.push(x * y);
                        break;
                    case '/':
                        stack.push(x / y);
                        break;
                }
            }
        }
        return stack.pop();
    }

    public boolean esteoperator(char caracter){
        return caracter=='+' || caracter=='-' || caracter=='*' || caracter=='/';
    }
}
