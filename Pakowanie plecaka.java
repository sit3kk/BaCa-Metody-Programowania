//Konrad Sitek

import java.util.Scanner;


/*/
Znajdywanie pierwszego zestawu podanych liczb ktorych suma jest rowna podanej liczbie
Wersja rekurencyjna i iteracyjna wykorzystujaca stos

Funkcja rekurencyjna oznacza indeksy ktorych suma pasuje do wyniku nastepnie sa one wypisawane

Funkcja iteracyjna przechodzi po kazdym elemencie, po znalezieniu odpowiadajcej galezi cofa sie takze zapisujac indeksy

 */


public class Source
{
    public static Scanner sc = new Scanner(System.in);
    public static int n;
    public static boolean found = false;
    public static int[] result,arr;

    public static class Stack
    {

        Stack prev; //Referencja do poprzedniego elementu
        int currentSum,index;

        Stack(Stack p, int index_,int currentSum_)
        {
            this.prev = p;
            this.currentSum = currentSum_;
            this.index = index_;

        }

    }

    public static void rec_pakuj(int[] arr,  int index,int currentSum,int[] tmp)
    {

        if(currentSum == n) //Jezeli zostalo znalezione kopiowanie tablicy zawierajace poprawne indeksy i zakonczenie dzialania
        {
            found = true;
            System.arraycopy(tmp,0,result,0,arr.length);
            return;
        }
        else if(index == arr.length || found == true) return;
        else
        {
            tmp[index] = 1; //Jezeli indeks pasuje do zestawu elementow

            currentSum += arr[index]; //Dodanie wartosci kryjacej sie pod danym indeksem

            rec_pakuj(arr,index+1,currentSum,tmp); //Wywolanie funkcji z inkrementacja indeksu

            currentSum -= arr[index]; //Odjecie wartosci kryjacej sie pod danym indeksem

            tmp[index] = -1; //Wartosc pod danym indeksem nie moze sie zawierac

            rec_pakuj(arr,index+1,currentSum,tmp); //Wywolanie nowej odnogi bez podanego indeksu
        }
        return;
    }

    public static void iter_pakuj(int[] arr)
    {


        Stack stack = new Stack(null,0,0);
        stack = new Stack(new Stack(stack,1,0),1,arr[0]); //Dodanie pierwszego wezla

        while(stack!=null)
        {
            int index = stack.index;
            int sum = stack.currentSum;

            stack = stack.prev;


            if(sum == n) //Jezeli suma jest rowna zakoncz dzialanie
            {
                Stack tmp = stack;

                while(tmp.prev != null) //Przejscie po kolejnych wezlach do tylu i zapisanie poprawnych indeksow
                {
                    result[tmp.index-1] = 1;
                    tmp = tmp.prev;

                }
                return;
            }
            else if(sum < n && index < arr.length ) //Jezeli suma i indeks mniejszy dodanie nowego wezla
            {

                stack = new Stack(new Stack(stack,index+1,sum), index+1, sum + arr[index]);
            }
        }

    }


    public static void main(String [] args)
    {


        int z = sc.nextInt();

        for(int i = 0; i < z ; i++)
        {
            n = sc.nextInt();
            int k = sc.nextInt();

            arr = new int[k];

            for(int j = 0; j < k ; j++) arr[j] = sc.nextInt();


            found = false;
            result = new int[arr.length];
            int[] tmp = new int[arr.length];



             rec_pakuj(arr,0,0,tmp);

            if(found == false) System.out.println("BRAK"); //Jezeli funkcja rekurencyjna niz nie znalazla
            else
            {

                 System.out.print("REC: "+n+" =");
                 for(int x = 0 ; x < arr.length; x++) if(result[x] == 1) System.out.print(" " + arr[x]);
                 System.out.println();


                result = new int[arr.length];

                System.out.print("ITER: "+n+" =");
                iter_pakuj(arr);
                for(int x = 0 ; x < arr.length; x++) if(result[x] == 1) System.out.print(" " + arr[x]);
                System.out.println();


            }

        }

    }

}
/*/
In
30
11
18
6 2 2 6 3 5 9 11 7 3 7 7 3 3 5 6 5 4
9
6
6 2 3 4 6 9
12
6
3 5 6 5 6 9
26
16
13 18 10 5 11 19 11 16 22 7 24 23 12 25 5 12
13
4
13 8 5 11
11
12
2 8 7 5 11 1 2 4 2 11 7 8
24
8
7 17 9 23 8 15 1 13
14
3
3 5 9
9
8
1 8 9 9 6 9 3 9
21
17
11 6 8 1 13 3 13 5 19 17 6 3 17 2 8 4 7
20
18
16 11 16 10 15 9 10 20 14 3 15 18 17 15 15 14 3 1
8
3
2 4 5
8
3
8 8 1
20
17
5 15 19 19 10 12 2 19 18 10 10 9 11 18 6 10 4
26
10
5 23 16 12 9 17 18 18 4 5
9
18
9 6 5 7 1 1 9 3 9 4 2 7 8 6 4 7 6 8
15
13
8 14 7 14 3 2 3 13 2 7 10 5 4
20
20
19 15 3 7 5 12 6 1 20 8 9 5 14 3 10 8 12 15 19 1
30
10
10 14 7 24 14 26 18 27 4 27
30
13
10 30 25 1 23 2 10 17 19 7 9 6 18
11
18
6 10 9 3 7 7 9 2 5 5 2 1 10 11 7 7 9 9
22
20
11 15 11 3 5 18 7 21 10 17 14 18 20 10 21 10 8 9 19 17
10
20
1 4 10 10 2 4 7 8 6 9 1 5 1 2 9 4 1 8 3 10
13
11
1 10 13 4 5 8 7 6 6 10 10
11
5
6 10 10 10 8
8
15
8 6 6 3 1 4 4 5 4 4 5 8 2 1 3
18
13
1 16 16 17 15 14 10 12 8 5 14 18 17
13
18
2 11 8 1 13 13 8 12 12 3 13 9 6 10 3 7 11 8
22
19
19 16 10 10 8 22 16 4 22 20 5 11 14 2 7 8 7 16 15
24
19
14 18 12 13 8 3 4 15 17 15 24 22 1 19 23 22 21 19 6



Out
REC: 11 = 6 2 3
ITER: 11 = 6 2 3
REC: 9 = 6 3
ITER: 9 = 6 3
REC: 12 = 3 9
ITER: 12 = 3 9
REC: 26 = 10 5 11
ITER: 26 = 10 5 11
REC: 13 = 13
ITER: 13 = 13
REC: 11 = 2 8 1
ITER: 11 = 2 8 1
REC: 24 = 7 17
ITER: 24 = 7 17
REC: 14 = 5 9
ITER: 14 = 5 9
REC: 9 = 1 8
ITER: 9 = 1 8
REC: 21 = 11 6 1 3
ITER: 21 = 11 6 1 3
REC: 20 = 16 3 1
ITER: 20 = 16 3 1
BRAK
REC: 8 = 8
ITER: 8 = 8
REC: 20 = 5 15
ITER: 20 = 5 15
REC: 26 = 5 16 5
ITER: 26 = 5 16 5
REC: 9 = 9
ITER: 9 = 9
REC: 15 = 8 7
ITER: 15 = 8 7
REC: 20 = 19 1
ITER: 20 = 19 1
REC: 30 = 26 4
ITER: 30 = 26 4
REC: 30 = 10 1 2 10 7
ITER: 30 = 10 1 2 10 7
REC: 11 = 6 3 2
ITER: 11 = 6 3 2
REC: 22 = 11 11
ITER: 22 = 11 11
REC: 10 = 1 4 2 1 1 1
ITER: 10 = 1 4 2 1 1 1
REC: 13 = 1 4 8
ITER: 13 = 1 4 8
BRAK
REC: 8 = 8
ITER: 8 = 8
REC: 18 = 1 17
ITER: 18 = 1 17
REC: 13 = 2 11
ITER: 13 = 2 11
REC: 22 = 16 4 2
ITER: 22 = 16 4 2
REC: 24 = 14 3 1 6
ITER: 24 = 14 3 1 6

Process finished with exit code 0



 */