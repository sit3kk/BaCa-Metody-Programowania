//Konrad Sitek - 4


import java.util.Scanner;


public class Source {





    public static Scanner scn = new Scanner(System.in);


    public static int[] bubblesort(int[] tab)
    {

        for(int i = 0 ; i < tab.length-1 ; i++)
        {
            for(int j = 0 ; j < tab.length-i-1 ; j++)
            {
                if(tab[j]>tab[j+1])
                {
                    int tmp = tab[j]; //Przekazanie komorki do tymczasowej zmiennej
                    tab[j] = tab[j+1];
                    tab[j+1] = tmp; //Zamiana miejscami komorek
                }
            }
        }

        return tab;

    }




    public static int triangle(int[] tab)
    {


        int count = 0, count2 = 0;

        bubblesort(tab); //Sortowanie tablicy O(n^2)

        for(int i = 0; i < tab.length; ++i) //Wypisanie elementow tablicy
        {
            if((i+1)%25==0 && i!=0) System.out.print(tab[i]+"\n"); //Nowa linia co 25 komorke
            else if(i!=tab.length-1) System.out.print(tab[i] + " ");
            else System.out.print(tab[i]+"\n");


        }


        for(int i = 0 ; i < tab.length - 2 ; ++i)
        {
            int k = i + 2;

            for(int j = i + 1 ; j < tab.length ; ++j)
            {

                while(k < tab.length && tab[i] + tab[j] > tab[k]) ++k;



                if(k>j)
                {
                    count += k - j - 1; //Obliczanie ilosci mozliwych kombinacji trojkata
                    if(count2 <= 10)
                    {
                        for(int x = i  + 2 ; x < k ; ++x)
                        {

                            if((tab[i] + tab[j] > tab[x] || tab[i]+tab[x] > tab[j] || tab[j]+tab[x] > tab[i]) && j!=x && j < x) //Wypisywanie trojek
                            {
                                if(count2 < 9) System.out.print("("+i+","+j+","+x+") ");
                                else if(count2 == 9) System.out.print("("+i+","+j+","+x+")"); //Brak spacji na koncu lini
                                count2++; //Inkrementacja licznika wypisanych trojek
                            }
                        }

                    }

                }


            }

        }



        if(count != 0) System.out.print("\nNumber of triangles: " + count+'\n'); //Wypisywanie ilosci trojkatow
        else System.out.print("Triangles cannot be built\n"); //Jezli nie mozna utworzyc zadnego trojkata
        return 0;
    }






    public static void main(String[] args)
    {
        int nz = scn.nextInt();
        for(int set = 0 ; set < nz ; set++) //Przechodzenie do kolejnych zestawow
        {

            int n = scn.nextInt();
            int[] tab = new int[n];
            for(int i = 0 ; i < n ; i++)  tab[i] = scn.nextInt(); //Pobranie elementow do tablicy



            System.out.print(set+1 + ": n= "+n+"\n");
            triangle(tab);


        }

    }
}

/*/
Input/Output

5
0
3
-1
-2
-3
3
0
0
0
26
-1 1 2 3 4 5 6 7 8 9 10 11 12 13 14 15 16 17 18 19 20 21 22 23 24 25
25
1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1

1: n= 0
Triangles cannot be built
2: n= 3
-3 -2 -1
Triangles cannot be built
3: n= 3
0 0 0
Triangles cannot be built
4: n= 26
-1 1 2 3 4 5 6 7 8 9 10 11 12 13 14 15 16 17 18 19 20 21 22 23 24
25
(2,3,4) (2,4,5) (2,5,6) (2,6,7) (2,7,8) (2,8,9) (2,9,10) (2,10,11) (2,11,12) (2,12,13)
Number of triangles: 1078
5: n= 25
1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1
(0,1,2) (0,1,3) (0,1,4) (0,1,5) (0,1,6) (0,1,7) (0,1,8) (0,1,9) (0,1,10) (0,1,11)
Number of triangles: 2300

Process finished with exit code 0



 */