//Konrad Sitek
import java.util.Scanner;

/*/
--------------------Konwersja miedzy notacja postfiksowa i infiksowa-------------------------------
Program pobiera wyrazenie w podanej notacji a nastepnie
usuwa zbedne znaki, przechodzi po kazdym elemencie wyrazenia sprawdzajac czy jest poprawne
ustala priorytet podanego operatora i dokonuje konwersji na wybrany system

/*/

//Implementacja stosu
class Stack
{
    private String arr[];
    private int top;
    private  int cap;


    Stack(int size)
    {
        arr = new String[size];
        cap = size;
        top = -1;
    }

    public void push(String n)
    {
        arr[++top] = n;
    }

    public boolean isEmpty()
    {
        return top == -1;
    }



    public String pop()
    {
        if(top>=0) return arr[top--];
        else return "";
    }

    public String peek()
    {
        if(top>=0) return arr[top];
        else return "0";

    }

    public int size()
    {
        return top+1;
    }




}

public class Source
{
    public static String makeSpaces(String instruction) //Funkcja dodajaca spacje miedzy wyrazami
    {
        String output = "";
        for(int i = 0 ; i < instruction.length(); i++) output = output+" "+instruction.charAt(i);


        return output;
    }

    public static String removeChars(String instruction, boolean ONP) //Funkcja usuwajaca zbedne znaki
    {
        String output = "";


        char[] correctChars = {'!','~','^','*','/','%','+','-','<','>','?','&','|','='};

        for(int i = 0 ; i < instruction.length(); i++)
        {

            if(Character.isLowerCase((instruction.charAt(i)))) output = output + instruction.charAt(i);
            else if(ONP == false && (instruction.charAt(i) == ')' || instruction.charAt(i) == '(')) output = output + instruction.charAt(i);
            else
            {
                for(int j = 0; j < correctChars.length ; j++)
                {
                    if(instruction.charAt(i) == correctChars[j])
                    {
                        output = output + instruction.charAt(i);
                        break;
                    }
                }
            }

        }

        return output;
    }

    public static String priority(char a, boolean ONP) //Funkcja nadajaca priorytet operatorom
    {
        String pr = "";


        if(a == '=') pr+="0";
        else if(a == '|') pr+="1";
        else if(a == '&') pr+="2";
        else if(a == '?') pr+="3";
        else if(a == '<' || a == '>') pr+="4";
        else if(a == '+' || a == '-') pr+="5";
        else if(a == '*' || a == '/' || a == '%') pr+="6";
        else if(a == '^') pr+="7";
        else if( a == '!' || a =='~') pr+="8";
        else if(ONP) pr="9";

        if(ONP == false) //Nadanie prirytetowi lewostronnosci lub prawostronnosci
        {
            if(pr.equals("0") || pr.equals("7") || pr.equals("8")) pr = pr + "R";
            else if(pr.equals("1") || pr.equals("2") || pr.equals("3") || pr.equals("4") || pr.equals("5") || pr.equals("6")) pr = pr + "L";
        }
        return pr;

    }

    public static String ONP_2_INF(String instruction)
    {
        Stack stack = new Stack(256); //Stos operatorow i operandow
        Stack priorities = new Stack(256); //Stos priorytetow
        String tmp = "";
        int p1 = 0, p2 = 0; //Liczcznik wystapien znakow bez nawiasow
        int operations = 0; //Licznik operacji


        for (int i = 0; i < instruction.length(); i++)
        {
            if(Character.isLowerCase(instruction.charAt(i))) operations++;
            else if (instruction.charAt(i) != '~' && instruction.charAt(i) != '!') operations--;


            if(operations == -1) return "INF: error";

            char x = instruction.charAt(i);

            if(Character.isLowerCase(x) || x =='!' || x == '~' || x == '^' || x == '*' || x == '/' || x == '%' || x == '+' || x == '-' || x == '<' || x == '>' || x == '?' || x == '&' || x == '|' || x == '=') p1++;


            if (Character.isLowerCase(x)) //Jezezeli operand
            {
                stack.push(""+x);
                priorities.push(""+priority(x,true));
            }
            else // Jezeli operator
            {
                tmp = "";

                if (x != '~' && x!='!') {
                    if (Integer.parseInt(priorities.peek()) <= Integer.parseInt(priority(x,true)) && x != '=') {
                        if (Integer.parseInt(priorities.peek()) == Integer.parseInt(priority(x,true)) && x == '^') {
                            tmp = stack.pop();
                        } else {
                            tmp = "(" + stack.pop() + ")";
                        }
                    } else {
                        tmp = stack.pop();
                    }

                    priorities.pop();

                    if (Integer.parseInt(priorities.peek()) < Integer.parseInt(priority(x,true))) {
                        tmp = "(" + stack.pop() + ")" + x + tmp;
                    } else {
                        tmp = stack.pop() + x + tmp;
                    }

                    priorities.pop();
                }
                else
                {
                    if (Integer.parseInt(priorities.peek())< Integer.parseInt((priority(x,true)))) {
                        tmp = x + "(" + stack.pop() + ")";
                    } else {
                        tmp = x + stack.pop();
                    }

                    priorities.pop();
                }

                stack.push(tmp);
                priorities.push(priority(x,true));
            }
        }

        String output = stack.pop();

        for(int i = 0 ; i < output.length() ; i++) //Licznik znakow bez nawiasow dla wyjscia
            if(Character.isLowerCase(output.charAt(i)) || output.charAt(i) =='!'
                || output.charAt(i) == '~' || output.charAt(i) == '^' || output.charAt(i) == '*'
                    || output.charAt(i) == '/' || output.charAt(i) == '%' || output.charAt(i) == '+'
                    || output.charAt(i) == '-' || output.charAt(i) == '<' || output.charAt(i) == '>'
                    || output.charAt(i) == '?' || output.charAt(i) == '&' || output.charAt(i) == '|'
                    || output.charAt(i) == '=') p2++;

        if (p1 == p2 && operations == 1) return "INF:"+makeSpaces(output);
         else return "INF: error";

    }

    public static String INF_2_ONP(String instruction)
    {

        int p = 0;
        int state = 0;//Stan poczatkowy
        char h = '=';


        String output = "";
        Stack stack = new Stack(256);

        for (int i = 0; i < instruction.length(); i++)
        {

            h = instruction.charAt(i);
            if (state == 0) //Przechodzenie kolejno po stanach
            {
                if (Character.isLowerCase(instruction.charAt(i))) state = 1;
                else if (instruction.charAt(i) == '~' || instruction.charAt(i) == '!' ) state = 2;
                else if (h == '(')
                {
                    if (p >= 0) p++;
                    else return "ONP: error";
                }
                else return "ONP: error";

            }
            else if (state == 1)
            {

                if (h != ')')
                {
                    if(instruction.charAt(i)=='^' || instruction.charAt(i)=='*' || instruction.charAt(i)=='/'
                            || instruction.charAt(i)=='%' || instruction.charAt(i)=='+' || instruction.charAt(i)=='-'
                            || instruction.charAt(i)=='<' || instruction.charAt(i)=='>' || instruction.charAt(i)=='?'
                            || instruction.charAt(i)=='&' || instruction.charAt(i)=='|' || instruction.charAt(i)=='=') state = 0;
                    else return "ONP: error";
                }
                else p--;


            }
            else if (state == 2)
            {

                if(h == '~' || h == '!') {}
                else
                {
                    if (h == '(')
                    {
                        state = 0;
                        if (p >= 0) p++;
                        else return "ONP: error";

                    }
                    else if (Character.isLowerCase(instruction.charAt(i))) state = 1;
                    else {
                        return "ONP: error";
                    }
                }
            }

            if(p==-1) return "ONP: error";

            if (Character.isLowerCase(instruction.charAt(i))) output += instruction.charAt(i); //Jezeli operand dodaj do wyjscia
             else
             {
                if (instruction.charAt(i) == '(') stack.push(""+instruction.charAt(i));
                else if (instruction.charAt(i) == ')')
                {

                    if (!stack.isEmpty() ) {
                        char x = stack.pop().charAt(0);
                        while (x != '(') {
                            output += x;
                            x = stack.pop().charAt(0);
                        }
                    }
                }
                else
                {
                    if (!stack.isEmpty()) //Jezeli stos nie jest pusty
                    {

                        String p1 = priority(instruction.charAt(i),false);
                        String p2 = priority(stack.peek().charAt(0),false);


                        while (p2 != "" && ((p1.charAt(1) == 'L' && p2.charAt(0) >= p1.charAt(0)) || (p1.charAt(1) == 'R' && p2.charAt(0) > p1.charAt(0)))) //Iteracja do pierwszego nawiasu
                        {
                            output += stack.pop();
                            if (stack.isEmpty()) break;
                            else p2  = priority(stack.peek().charAt(0),false);

                        }
                    }
                    stack.push(""+instruction.charAt(i));
                }
            }
        }

        if (p == 0)
        {
            if(h=='^' || h =='*' || h=='/'
                    || h =='%' || h =='+' || h =='-'
                    || h =='<' || h =='>' || h =='?'
                    || h =='&' || h =='|' || h =='='
                    || h == '~' || h =='!') return "ONP: error";
        }
        else return "ONP: error";


        while (!stack.isEmpty()) output += stack.pop(); //Sciaganiecie zawartosci stosu


        return "ONP:" + makeSpaces(output);
    }


    public static  void main(String[] args)
    {
        Scanner input = new Scanner(System.in);
        int n = Integer.parseInt(input.nextLine());



        for(int line = 0 ; line < n ; line++)
        {
            String command = input.nextLine();

            String operation = command.substring(0,3);

            String instruction = command.substring(4,command.length());


            if(operation.equals("ONP"))
            {
                instruction = removeChars(instruction,true); //Usuniecie blednych znakow

                System.out.println(ONP_2_INF(instruction).trim());


            }
            else if(operation.equals("INF"))
            {
                instruction = removeChars(instruction,false); //Usuniecie blednych znakow

                System.out.println(INF_2_ONP(instruction).trim());


            }
        }
    }
}


/*/

//input

61
INF: a)+(b
ONP: ab+a~a-+
INF: a+b+(~a-a)
INF: x=~~a+b*c
INF: t=~a<x<~b
INF: ~a-~~b<c+d&!p|!!q
INF: a^b*c-d<xp|q+x
INF: x=~a*b/c-d+e%~f
ONP: xabcdefg++++++=
ONP: ab+c+d+e+f+g+
ONP: abc++def++g++
ONP: abc++def++g+++
INF: x=a=b=c
ONP: xabc===
INF: x=a^b^c
INF: x=a=b=c^d^e
ONP: xabcde^^===
INF: x=(a=(b=c^(d^e)))
ONP: xa~~~~~~=
INF: x=~~~~a
INF: x=~(~(~(~a)))
ONP: xa~~~~=
INF: x=a+(b-c+d)
ONP: xabc-d++=
INF: x=a+(((a-b)+c))
ONP: xaab-c++=
INF: a)+(b
ONP: ab+a~a-+
INF: a+b+(~a-a)
INF: x=~~a+b*c
INF: t = ~ a < x < ~b
INF: ~a-~~b<c+d&!p|!!q
INF: a^b*c-d<xp|q+x
INF: x=~a*b/c-d+e%~f
ONP: xabcdefg++++++=
ONP: ab+c+d+e+f+g+
ONP: abc++def++g++
ONP: abc++def++g+++
INF: x=a=b=c
ONP: xabc===
INF: x=a^b^c
INF: x=a=b=c^d^e
ONP: xabcde^^===
INF: x=(a=(b=c^(d^e)))
ONP: a,b,. ^ .c;-,*
INF: (( a,+ b)/..[c3
INF:(a+b)*c+(d-a)*(f-b)
INF:~~~a
ONP:ab+c*da-fb-*+
ONP:ab*c*d*e*
ONP:(a # b .c* ) )*
ONP:ab<c/d+
INF:~ a, ( $ /(b*c^d)
ONP:a~~~
ONP:zab+*>!
INF:~a*(~b+~c)/~(~d-e)
ONP:ab~c+*de-~/
INF:)a+b(
INF:,z,a<x.>b!=
ONP:a*aa+
ONP: ( a,b,. ^ .c;-,*
INF: (( a,+ b)/.d.[c3)
INF:(a+b)*c+(d-a)*(f-b)
INF:a*b*c*d*e
ONP:ab+c*da-fb-*+
ONP:ab*c*d*e*
ONP:(a # b .c* ) )*
ONP:ab*cd


//output

ONP: error
INF: a + b + ( ~ a - a )
ONP: a b + a ~ a - +
ONP: x a ~ ~ b c * + =
ONP: t a ~ x < b ~ < =
ONP: error
ONP: error
ONP: x a ~ b * c / d - e f ~ % + =
INF: x = a + ( b + ( c + ( d + ( e + ( f + g ) ) ) ) )
INF: a + b + c + d + e + f + g
INF: a + ( b + c ) + ( d + ( e + f ) + g )
INF: error
ONP: x a b c = = =
INF: x = a = b = c
ONP: x a b c ^ ^ =
ONP: x a b c d e ^ ^ = = =
INF: x = a = b = c ^ d ^ e
ONP: x a b c d e ^ ^ = = =
INF: x = ~ ~ ~ ~ ~ ~ a
ONP: x a ~ ~ ~ ~ =
ONP: x a ~ ~ ~ ~ =
INF: x = ~ ~ ~ ~ a
ONP: x a b c - d + + =
INF: x = a + ( b - c + d )
ONP: x a a b - c + + =
INF: x = a + ( a - b + c )
ONP: error
INF: a + b + ( ~ a - a )
ONP: a b + a ~ a - +
ONP: x a ~ ~ b c * + =
ONP: t a ~ x < b ~ < =
ONP: error
ONP: error
ONP: x a ~ b * c / d - e f ~ % + =
INF: x = a + ( b + ( c + ( d + ( e + ( f + g ) ) ) ) )
INF: a + b + c + d + e + f + g
INF: a + ( b + c ) + ( d + ( e + f ) + g )
INF: error
ONP: x a b c = = =
INF: x = a = b = c
ONP: x a b c ^ ^ =
ONP: x a b c d e ^ ^ = = =
INF: x = a = b = c ^ d ^ e
ONP: x a b c d e ^ ^ = = =
INF: error
ONP: error
ONP: a b + c * d a - f b - * +
ONP: a ~ ~ ~
INF: ( a + b ) * c + ( d - a ) * ( f - b )
INF: a * b * c * d * e
INF: a * ( b * c )
INF: ( a < b ) / c + d
ONP: error
INF: ~ ~ ~ a
INF: error
ONP: a ~ b ~ c ~ + * d ~ e - ~ /
INF: a * ( ~ b + c ) / ~ ( d - e )
ONP: error
ONP: error
INF: error
INF: error
ONP: error
ONP: a b + c * d a - f b - * +
ONP: a b * c * d * e *
INF: ( a + b ) * c + ( d - a ) * ( f - b )
INF: a * b * c * d * e
INF: a * ( b * c )

*/
