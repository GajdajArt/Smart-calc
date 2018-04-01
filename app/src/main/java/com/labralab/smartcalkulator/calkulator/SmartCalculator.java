package com.labralab.smartcalkulator.calkulator;

import java.util.LinkedList;
import java.util.Queue;
import java.util.Stack;

/**
 * Created by pc on 07.09.2017.
 */

public class SmartCalculator {


    //Метод возвращает true, если проверяемый символ - разделитель ("пробел" или "равно")
    static private Boolean isDelimeter(char c) {
        if (c == ' ' || c == '=') {
            return true;
        } else {
            return false;
        }

    }

    //Метод возвращает true, если проверяемый символ - оператор
    static private Boolean isOperator(char c) {
        if (c == '(' || c == ')' || c == '+' || c == '-' || c == '*' || c == '/' || c == '^') {
            return true;
        } else {
            return false;
        }
    }

    //Метод возвращает true, если проверяемый символ - тригонометрическая функция
    static private Boolean isTrigonometry(char c) {
        if (c == 'c' || c == 's' || c == 't') {
            return true;
        } else {
            return false;
        }
    }

    //Метод возвращает приоритет оператора
    static private byte getPriority(char s) {
        switch (s) {
            case '(':
                return 0;
            case ')':
                return 1;
            case '+':
                return 2;
            case '-':
                return 3;
            case '*':
                return 4;
            case '/':
                return 4;
            case '^':
                return 5;
            default:
                return 6;
        }
    }

    //Метод для замены метематических обозначений функций на символы принятые в программе
    static private String onReplace(String input) {

        String result = input;
        result = result.replace("cos", "c");
        result = result.replace("sin", "s");
        result = result.replace("tan", "t");
        result = result.replace("Pi", String.format("%.5f", Math.PI));
        result = result.replace(",", ".");

        return result;
    }


    //"Входной" метод класса
    static public double calculate(String input, int isRad) {

        String afterReplace = onReplace(input);
        String noTrigonom = solveTrigonometry(afterReplace, isRad);
        String output = getExpression(noTrigonom); //Преобразовываем выражение в постфиксную запись
        double result = counting(output); //Решаем полученное выражение
        return result; //Возвращаем результат
    }

    //Решаем все тригонометрические функции
    static private String solveTrigonometry(String input, int isRad) {

        StringBuffer result = new StringBuffer(50);
        result.append(input);
        String intermediateResult = "";
        Queue<Character> queue = new LinkedList<>();
        Character fun;


        for (int i = 0; i < result.length(); ) //Для каждого символа в входной строке
        {
            //Усли символ тригонометрическая функция
            if (isTrigonometry(result.charAt(i))) {

                fun = input.charAt(i);
                result.deleteCharAt(i);
                result.deleteCharAt(i);

                int i1 = i + 2;
                int bracket = 1;
                while (bracket != 0) {

                    if (input.charAt(i1) == '(') {
                        bracket++;
                    }
                    if (input.charAt(i1) == ')') {
                        bracket--;

                        if (bracket == 0) {
                            result.deleteCharAt(i);
                            break;
                        }
                    }

                    //Добавляем символ в очередь
                    queue.add(input.charAt(i1));
                    i1++;
                    result.deleteCharAt(i);
                }


                Character c = queue.poll();
                intermediateResult += c;

                while (!queue.isEmpty()) {
                    c = queue.poll();
                    intermediateResult += c;
                }

                //Если внутри выражения есть еще тригонометрические функции
                for (int k = 0;  k < intermediateResult.length(); k ++){
                    if(isTrigonometry(intermediateResult.charAt(k))){
                        intermediateResult = solveTrigonometry(intermediateResult, isRad);
                    }
                }


                //Если внутри скобок целое выражение, то сперва расчитываем его
                String argsStr = getExpression(intermediateResult);
                double arg = counting(argsStr);

                if (isRad == 0) {
                    arg = Math.toRadians(arg);
                }

                switch (fun) {
                    case 'c':
                        intermediateResult = String.format("%.5f", Math.cos(arg));
                        break;
                    case 's':
                        intermediateResult = String.format("%.5f", Math.sin(arg));
                        break;
                    case 't':
                        intermediateResult = String.format("%.5f", Math.tan(arg));
                        break;
                }

                String r = intermediateResult.replace(",", ".");

                //Вставляем полученный результат
                result.insert(i, r);
            } else {
                input = result.toString();
                intermediateResult = "";
                i++;
            }

        }

        return result.toString();
    }


    //ПРеобразование в постфиксную запись
    static private String getExpression(String input) {
        String output = ""; //Строка для хранения выражения
        Stack<Character> operStack = new Stack<>(); //Стек для хранения операторов

        for (int i = 0; i < input.length(); i++) //Для каждого символа в входной строке
        {
            //Разделители пропускаем
            if (isDelimeter(input.charAt(i)))
                continue; //Переходим к следующему символу

            //Если символ - цифра, то считываем все число
            if (Character.isDigit(input.charAt(i))) //Если цифра
            {
                //Читаем до разделителя или оператора, что бы получить число
                while (!isDelimeter(input.charAt(i)) && !isOperator(input.charAt(i))) {
                    output += input.charAt(i); //Добавляем каждую цифру числа к нашей строке
                    i++; //Переходим к следующему символу

                    if (i == input.length()) break; //Если символ - последний, то выходим из цикла
                }

                output += " "; //Дописываем после числа пробел в строку с выражением
                i--; //Возвращаемся на один символ назад, к символу перед разделителем
            }

            //Если символ - оператор
            if (isOperator(input.charAt(i))) //Если оператор
            {
                if (input.charAt(i) == '(') //Если символ - открывающая скобка
                    operStack.push(input.charAt(i)); //Записываем её в стек
                else if (input.charAt(i) == ')') //Если символ - закрывающая скобка
                {


                    //Выписываем все операторы до открывающей скобки в строку
                    Character c = operStack.pop();

                    while (c != '(') {
                        output += c;
                        output += " ";
                        c = operStack.pop();
                    }
                } else //Если любой другой оператор
                {
                    if (operStack.size() > 0) //Если в стеке есть элементы
                        if (getPriority(input.charAt(i)) <= getPriority(operStack.peek())) //И если приоритет нашего оператора меньше или равен приоритету оператора на вершине стека
                            output += operStack.pop().toString() + " "; //То добавляем последний оператор из стека в строку с выражением

                    operStack.push(input.charAt(i)); //Если стек пуст, или же приоритет оператора выше - добавляем операторов на вершину стека

                }
            }
        }

        //Когда прошли по всем символам, выкидываем из стека все оставшиеся там операторы в строку
        while (operStack.size() > 0)
            output += operStack.pop() + " ";

        return output; //Возвращаем выражение в постфиксной записи
    }

    //Метод для решения выражения
    static private double counting(String input) {
        double result = 0; //Результат
        Stack<Double> temp = new Stack<Double>(); //Dhtvtyysq стек для решения

        for (int i = 0; i < input.length(); i++) //Для каждого символа в строке
        {
            //Если символ - цифра, то читаем все число и записываем на вершину стека
            if (Character.isDigit(input.charAt(i))) {
                String a = "";

                while (!isDelimeter(input.charAt(i)) && !isOperator(input.charAt(i))) //Пока не разделитель
                {
                    a += input.charAt(i); //Добавляем
                    i++;
                    if (i == input.length()) break;
                }
                temp.push(Double.parseDouble(a)); //Записываем в стек
                i--;
            } else if (isOperator(input.charAt(i))) //Если символ - оператор
            {
                //Берем два последних значения из стека
                double a = temp.pop();
                double b = temp.pop();

                switch (input.charAt(i)) //И производим над ними действие, согласно оператору
                {
                    case '+':
                        result = b + a;
                        break;
                    case '-':
                        result = b - a;
                        break;
                    case '*':
                        result = b * a;
                        break;
                    case '/':
                        result = b / a;
                        break;
                    case '^':
                        result = Math.pow(b, a);
                        break;
                }
                temp.push(result); //Результат вычисления записываем обратно в стек
            }
        }
        return temp.peek(); //Забираем результат всех вычислений из стека и возвращаем его
    }
}
